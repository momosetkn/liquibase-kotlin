package momosetkn.liquibase.kotlin.serializer

import liquibase.changelog.ChangeLogChild
import liquibase.changelog.ChangeSet
import liquibase.serializer.ChangeLogSerializer
import liquibase.serializer.LiquibaseSerializable
import liquibase.util.ISODateFormat
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.isAccessible

class KotlinChangeLogSerializer : ChangeLogSerializer {
    private val isoFormat = ISODateFormat()
    private val propertyMap = ConcurrentHashMap<KClass<*>, Map<String, KProperty1<out Any, *>>>()

    override fun getValidFileExtensions(): Array<String> = arrayOf("kts")

    override fun getPriority(): Int = ChangeLogSerializer.PRIORITY_DEFAULT

    override fun serialize(
        serializable: LiquibaseSerializable,
        pretty: Boolean,
    ): String {
        require(pretty) {
            "'pretty = false' is not allowed"
        }
        return prettySerialize(serializable)
    }

    override fun <T : ChangeLogChild> write(
        changeSets: List<T>,
        out: java.io.OutputStream,
    ) {
        out.use {
            out.write("databaseChangeLog {\n".toByteArray())
            out.write(
                changeSets
                    .joinToString("\n\n") {
                        indent(prettySerialize(it))
                    }.toByteArray(),
            )
            out.write("\n\n}\n".toByteArray())
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

    private fun prettySerialize(change: LiquibaseSerializable): String {
        val fields = change.serializableFields
        val children = mutableListOf<String>()
        val attributes = mutableListOf<String>()
        var textBody: String? = null

        fields.forEach { field ->
            val fieldValue = change.getSerializableFieldValue(field)
            if (fieldValue != null) {
                val serializationType = change.getSerializableFieldType(field)
                when {
                    fieldValue is Collection<*> -> {
                        fieldValue.filterIsInstance<LiquibaseSerializable>().forEach {
                            children.add(prettySerialize(it))
                        }
                    }

                    serializationType == LiquibaseSerializable.SerializationType.NESTED_OBJECT -> {
                        children.add(prettySerialize(fieldValue as LiquibaseSerializable))
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
                val propertyList =
                    buildPropertyListFrom(
                        attributes,
                        change,
                    ).joinToString(", ")
                "${change.serializedObjectName}($propertyList)"
            } else {
                change.serializedObjectName
            }

        return when {
            children.isNotEmpty() -> {
                val renderedChildren = children.joinToString("\n") { indent(it) }
                """
                $serializedChange {
                $renderedChildren
                }
                """.trimIndent()
            }

            textBody != null -> {
                """
                "$serializedChange {
                    ${wrapQuoteString(textBody!!)}
                }"
                """.trimIndent()
            }

            else -> serializedChange
        }
    }

    private fun indent(text: String?): String {
        return text
            ?.lineSequence()
            ?.joinToString("\n") {
                "     $it"
            } ?: ""
    }

    private fun buildPropertyListFrom(
        propertyNames: List<String>,
        obj: Any,
    ): List<String> {
        return propertyNames.mapNotNull { propertyName ->
            val prop = getProperty(obj::class, propertyName)
            val propertyValue = prop.call(obj)
            propertyValue?.let {
                val propertyString =
                    when (propertyValue) {
                        is Boolean -> propertyValue.toString()
                        is Number -> propertyValue.toString()
                        is java.sql.Timestamp ->
                            wrapQuoteString(
                                isoFormat.format(propertyValue),
                            )

                        else -> wrapQuoteString(propertyValue.toString())
                    }
                "$propertyName = $propertyString"
            }
        }
    }

    private fun getProperty(kClass: KClass<*>, propertyName: String): KProperty1<out Any, *> {
        val map = propertyMap.getOrPut(kClass) {
            // include superclass
            val kClasses = listOf(listOf(kClass), kClass.superclasses).flatten()
            kClasses
                .flatMap { it.declaredMemberProperties }
                .associateBy { it.name }
        }

        return checkNotNull(map[propertyName]) {
            "$kClass is not found $propertyName property."
        }.apply {
            isAccessible = true
        }
    }

    private fun wrapQuoteString(s: String): String {
        var result = s
        if (result.startsWith('"')) {
            result = "\\\"" + result.removePrefix("\"")
        }
        if (result.endsWith('"')) {
            result = result.removeSuffix("\"") + "\\\""
        }
        if (s.contains("\"") || s.contains("\\")) {
            return "\"\"\"$result\"\"\""
        }
        return "\"$result\""
    }
}
