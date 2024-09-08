package momosetkn.liquibase.kotlin.serializer

import liquibase.changelog.ChangeLogChild
import liquibase.changelog.ChangeSet
import liquibase.serializer.ChangeLogSerializer
import liquibase.serializer.LiquibaseSerializable

class KotlinTypesafeChangeLogSerializer : ChangeLogSerializer {
    private val kotlinChangeLogSerializerCore = KotlinChangeLogSerializerCore()

    override fun getValidFileExtensions(): Array<String> = arrayOf("kt")

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
            val packageName = getPackageName(changeSets)
            if (packageName.isNotEmpty()) {
                out.write("package $packageName\n\n".toByteArray())
            }
            val className = getClassName(changeSets)
            out.write("class $className : TypesafeDatabaseChangeLog({\n".toByteArray())
            changeSets.forEach { changeSet ->
                val line = kotlinChangeLogSerializerCore.serializeLiquibaseSerializable(
                    change = changeSet,
                    indentLevel = 1,
                ) + "\n\n"
                out.write(line.toByteArray())
            }
            out.write("})\n".toByteArray())
        }
    }

    private fun <T : ChangeLogChild> getPackageName(changeSets: List<T>): String {
        val filePath = (changeSets[0] as? ChangeSet)?.filePath
            ?: return ""
        return filePath
            .removePrefix(sourceRootDirectory.toString())
            .removePrefix("/")
            .substringBeforeLast("/")
            .replace("/", ".")
    }

    private fun <T : ChangeLogChild> getClassName(changeSets: List<T>): String {
        val filePath = (changeSets[0] as? ChangeSet)?.filePath
            ?: return "ChangeLog${System.currentTimeMillis()}"
        return filePath.split("/").last().removeSuffix(".kt")
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

    companion object {
        var sourceRootDirectory = java.nio.file.Path.of("src.main.kotlin")
    }
}
