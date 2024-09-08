package momosetkn.liquibase.kotlin.serializer

import liquibase.change.ConstraintsConfig
import liquibase.changelog.ChangeLogChild
import liquibase.changelog.ChangeSet
import liquibase.serializer.ChangeLogSerializer
import liquibase.serializer.LiquibaseSerializable
import liquibase.util.ISODateFormat
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.superclasses

class KotlinChangeLogSerializer : ChangeLogSerializer {
    private val isoFormat = ISODateFormat()
    private val getterMethodMap = ConcurrentHashMap<KClass<*>, Map<String, KFunction<*>>>()

    override fun getValidFileExtensions(): Array<String> = arrayOf("kts")

    override fun getPriority(): Int = ChangeLogSerializer.PRIORITY_DEFAULT

    override fun serialize(
        serializable: LiquibaseSerializable,
        pretty: Boolean,
    ): String {
        require(pretty) {
            "'pretty = false' is not allowed"
        }
        return serializeChange(serializable)
    }

    override fun <T : ChangeLogChild> write(
        changeSets: List<T>,
        out: java.io.OutputStream,
    ) {
        out.use {
            out.write("databaseChangeLog {\n".toByteArray())
            changeSets.forEach {
                val line = indent(serializeChange(it)) + "\n\n"
                out.write(line.toByteArray())
            }
            out.write("}\n".toByteArray())
        }
    }

    override fun append(
        changeSet: ChangeSet,
        changeLogFile: java.io.File,
    ): Unit =
        throw UnsupportedOperationException(
            """
            KotlinChangeLogSerializer does not supported appending to changelog files.
            """.trimIndent(),
        )

    private fun serializeChange(change: LiquibaseSerializable): String {
        val fields = change.serializableFields
        val children = mutableListOf<String>()
        val attributes = mutableSetOf<String>()
        var textBody: String? = null

        fields.forEach { field ->
            val fieldValue = change.getSerializableFieldValue(field)
            if (fieldValue != null) {
                val serializationType = change.getSerializableFieldType(field)
                when {
                    fieldValue is Collection<*> -> {
                        fieldValue.filterIsInstance<LiquibaseSerializable>().forEach {
                            children.add(serializeChange(it))
                        }
                    }

                    serializationType == LiquibaseSerializable.SerializationType.NESTED_OBJECT || fieldValue is ConstraintsConfig -> {
                        children.add(serializeChange(fieldValue as LiquibaseSerializable))
                    }

                    serializationType == LiquibaseSerializable.SerializationType.DIRECT_VALUE -> {
                        textBody = fieldValue.toString()
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

        return when {
            children.isNotEmpty() -> {
                val renderedChildren = children.joinToString("\n") { indent(it) }
                listOf(
                    "$serializedChange {",
                    renderedChildren,
                    "}"
                ).joinToString("\n")
            }

            textBody != null -> {
                listOf(
                    "$serializedChange {",
                    indent(serializeString(textBody!!)),
                    "}"
                ).joinToString("\n")
            }

            else -> serializedChange
        }
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
            .replace("'", "\\")
            .replace("\n", "\\n")
        return "\"${replaced}\""
    }
}
