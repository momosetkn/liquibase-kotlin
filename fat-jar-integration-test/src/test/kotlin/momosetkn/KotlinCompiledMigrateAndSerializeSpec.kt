package momosetkn

import io.kotest.core.spec.style.FunSpec
import momosetkn.liquibase.changelogs.CompiledDatabaseChangelogAll
import momosetkn.liquibase.kotlin.serializer.KotlinCompiledChangeLogSerializer
import momosetkn.utils.Constants
import momosetkn.utils.DDLUtils.sql
import momosetkn.utils.DDLUtils.toMainDdl
import momosetkn.utils.Database
import momosetkn.utils.JarUtils
import momosetkn.utils.ResourceUtils.getResourceAsString
import momosetkn.utils.shouldMatchWithoutLineBreaks
import java.nio.file.Paths

class KotlinCompiledMigrateAndSerializeSpec : FunSpec({
    beforeSpec {
        JarUtils.build()
        Database.start()
        KotlinCompiledChangeLogSerializer.sourceRootPath = Paths.get(Constants.RESOURCE_DIR)
    }
    afterSpec {
        Database.stop()
    }

    context("Migrate and serialize") {
        test("can migrate") {
            val container = Database.startedContainer
            JarUtils.run(
                "update",
                container.driver,
                container.jdbcUrl,
                container.username,
                container.password,
                PARSER_INPUT_CHANGELOG
            )
            JarUtils.run(
                "rollback",
                container.driver,
                container.jdbcUrl,
                container.username,
                container.password,
                PARSER_INPUT_CHANGELOG,
                "started",
            )
            JarUtils.run(
                "update",
                container.driver,
                container.jdbcUrl,
                container.username,
                container.password,
                PARSER_INPUT_CHANGELOG
            )
            val actualSerializedChangeLogFile =
                Paths.get(Constants.RESOURCE_DIR, SERIALIZER_ACTUAL_CHANGELOG)
            val f = actualSerializedChangeLogFile.toFile()
            if (f.exists()) f.delete()
            JarUtils.run(
                "generateChangelog",
                container.driver,
                container.jdbcUrl,
                container.username,
                container.password,
                actualSerializedChangeLogFile.toString()
            )
            // check database
            val expectedDdl = getResourceAsString(PARSER_EXPECT_DDL)
            Database.generateDdl().toMainDdl() shouldMatchWithoutLineBreaks sql(expectedDdl)

            // check serializer
            val actual = getFileAsString(SERIALIZER_ACTUAL_CHANGELOG)
                .maskingChangeSet()
            val expect = getResourceAsString(SERIALIZER_EXPECT_CHANGELOG)
                .maskingChangeSet()
            actual shouldMatchWithoutLineBreaks expect
        }
    }
}) {
    companion object {
        private fun getFileAsString(path: String) =
            Paths.get(Constants.RESOURCE_DIR, path).toFile().readText()

        private val changeSetRegex = Regex("""changeSet\(author = "(.+)", id = "(\d+)-(\d)"\) \{""")

        private fun String.maskingChangeSet() =
            this.replace(changeSetRegex, "changeSet(author = \"**********\", id = \"*************-\$3\") {")

        private val PARSER_INPUT_CHANGELOG = CompiledDatabaseChangelogAll::class.qualifiedName!!
        private const val SERIALIZER_ACTUAL_CHANGELOG =
            "KotlinCompiledMigrateAndSerializeSpec/serializer_actual/ChangeLog0.kt"

        // To avoid auto-formatting, do not add a file extension.
        private const val SERIALIZER_EXPECT_CHANGELOG =
            "KotlinCompiledMigrateAndSerializeSpec/serializer_expect/ChangeLog0_kt"
        private const val PARSER_EXPECT_DDL = "KotlinCompiledMigrateAndSerializeSpec/parser_expect/db.ddl-0.sql"
    }
}
