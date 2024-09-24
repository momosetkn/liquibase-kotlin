package momosetkn.liquibase.kotlin.serializer

import liquibase.change.ConstraintsConfig
import liquibase.serializer.LiquibaseSerializable
import liquibase.util.ISODateFormat
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.superclasses

class KotlinChangeLogSerializerCore {
    private val isoFormat = ISODateFormat()
    private val getterMethodMap = ConcurrentHashMap<KClass<*>, Map<String, KFunction<*>>>()

    @Suppress("NestedBlockDepth")
    fun serializeLiquibaseSerializable(
        change: LiquibaseSerializable,
        indentLevel: Int = 0,
    ): String {
        val fields = change.serializableFields
        val children = mutableListOf<String>()
        val attributes = mutableSetOf<String>()

        fields.forEach { field ->
            val fieldValue = change.getSerializableFieldValue(field)
            if (fieldValue != null) {
                val serializationType = change.getSerializableFieldType(field)
                when {
                    fieldValue is Collection<*> -> {
                        fieldValue.filterIsInstance<LiquibaseSerializable>().forEach {
                            children.add(serializeLiquibaseSerializable(it))
                        }
                    }

                    serializationType == LiquibaseSerializable.SerializationType.NESTED_OBJECT ||
                        fieldValue is ConstraintsConfig -> {
                        children.add(serializeLiquibaseSerializable(fieldValue as LiquibaseSerializable))
                    }

                    serializationType == LiquibaseSerializable.SerializationType.DIRECT_VALUE -> {
                        children.clear()
                        children.add(serializeString(fieldValue.toString()))
                    }

                    serializationType == LiquibaseSerializable.SerializationType.NAMED_FIELD -> {
                        attributes.add(field)
                    }

                    else -> {
                        error("Illegal type of fieldValue: $fieldValue or serializationType: $serializationType")
                    }
                }
            }
        }

        val serializedChange =
            if (attributes.isNotEmpty()) {
                val serializedNamedParameters =
                    serializeNamedParameters(
                        attributes,
                        change,
                    ).joinToString(", ")
                "${change.serializedObjectName}($serializedNamedParameters)"
            } else {
                change.serializedObjectName
            }

        val body = wrapBlock(children = children, serializedChange = serializedChange)
        return (1..indentLevel).fold(body) { acc, _ ->
            indent(acc)
        }
    }

    private fun wrapBlock(
        children: List<String>,
        serializedChange: String,
    ): String = when {
        children.isNotEmpty() -> {
            val renderedChildren = children.joinToString("\n") { indent(it) }
            listOf(
                "$serializedChange {",
                renderedChildren,
                "}"
            ).joinToString("\n")
        }

        else -> serializedChange
    }

    private fun indent(text: String?): String {
        return text
            ?.lineSequence()
            ?.joinToString("\n") {
                "    $it"
            } ?: ""
    }

    private fun serializeNamedParameters(
        propertyNames: Set<String>,
        obj: Any,
    ): List<String> {
        // Order to alphabet. Because it cannot be sorted in the defined order.
        return propertyNames
            .sorted()
            .mapNotNull { propertyName ->
                val prop = getGetterMethod(obj::class, propertyName)
                val propertyValue = prop.call(obj)
                propertyValue?.let {
                    val propertyString =
                        when (propertyValue) {
                            is Boolean -> propertyValue.toString()
                            is Number -> propertyValue.toString()
                            is java.sql.Timestamp ->
                                serializeString(
                                    isoFormat.format(propertyValue),
                                )

                            else -> serializeString(propertyValue.toString())
                        }
                    "$propertyName = $propertyString"
                }
            }
    }

    private fun getGetterMethod(kClass: KClass<*>, propertyName: String): KFunction<*> {
        val map = getterMethodMap.getOrPut(kClass) {
            // include superclass
            val kClasses = listOf(listOf(kClass), kClass.superclasses).flatten().asReversed()
            kClasses
                .flatMap { it.memberFunctions }
                .filter { method ->
                    // getter method
                    method.parameters.none { it.kind == KParameter.Kind.VALUE }
                }
                .associateBy { method ->
                    method.name
                        .removePrefix("get")
                        .removePrefix("is")
                        .replaceFirstChar { it.lowercase() }
                }
        }
        return checkNotNull(map[propertyName]) {
            "$kClass is not found $propertyName getter method."
        }
    }

    private fun serializeString(s: String): String {
        val replaced = s
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
        return "\"${replaced}\""
    }
}
