package momosetkn.liquibase.kotlin.serializer

import liquibase.changelog.ChangeLogChild
import liquibase.changelog.ChangeSet
import liquibase.serializer.ChangeLogSerializer
import liquibase.serializer.LiquibaseSerializable

class KotlinScriptChangeLogSerializer : ChangeLogSerializer {
    private val kotlinChangeLogSerializerCore = KotlinChangeLogSerializerCore()

    override fun getValidFileExtensions(): Array<String> = arrayOf("kts")

    override fun getPriority(): Int = ChangeLogSerializer.PRIORITY_DEFAULT

    override fun serialize(
        serializable: LiquibaseSerializable,
        pretty: Boolean,
    ): String {
        require(pretty) {
            "'pretty = false' is not allowed"
        }
        return kotlinChangeLogSerializerCore.serializeLiquibaseSerializable(serializable)
    }

    override fun <T : ChangeLogChild> write(
        changeSets: List<T>,
        out: java.io.OutputStream,
    ) {
        out.use {
            out.write("databaseChangeLog {\n".toByteArray())
            changeSets.forEach { changeSet ->
                val line = kotlinChangeLogSerializerCore.serializeLiquibaseSerializable(
                    change = changeSet,
                    indentLevel = 1,
                ) + "\n\n"
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
}
