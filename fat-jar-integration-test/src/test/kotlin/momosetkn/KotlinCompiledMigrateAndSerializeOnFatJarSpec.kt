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

class KotlinCompiledMigrateAndSerializeOnFatJarSpec : FunSpec({
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
            val client = JarUtils.run {
                globalArgs {
                    general {
                        showBanner = false
                        logLevel = "debug"
                    }
                }
            }
            client.update(
                driver = container.driver,
                url = container.jdbcUrl,
                username = container.username,
                password = container.password,
                changelogFile = PARSER_INPUT_CHANGELOG,
            )
            client.rollback(
                driver = container.driver,
                url = container.jdbcUrl,
                username = container.username,
                password = container.password,
                changelogFile = PARSER_INPUT_CHANGELOG,
                tag = "started",
            )
            client.update(
                driver = container.driver,
                url = container.jdbcUrl,
                username = container.username,
                password = container.password,
                changelogFile = PARSER_INPUT_CHANGELOG,
            )
            val actualSerializedChangeLogFile =
                Paths.get(Constants.RESOURCE_DIR, SERIALIZER_ACTUAL_CHANGELOG)
            val f = actualSerializedChangeLogFile.toFile()
            if (f.exists()) f.delete()
            client.generateChangelog(
                driver = container.driver,
                url = container.jdbcUrl,
                username = container.username,
                password = container.password,
                changelogFile = Paths.get(moduleName).resolve(actualSerializedChangeLogFile).toString(),
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
        val moduleName = "fat-jar-integration-test"
        private fun getFileAsString(path: String) =
            Paths.get(Constants.RESOURCE_DIR, path).toFile().readText()

        private val changeSetRegex = Regex("""changeSet\(author = "(.+)", id = "(\d+)-(\d)"\) \{""")

        private fun String.maskingChangeSet() =
            this.replace(changeSetRegex, "changeSet(author = \"**********\", id = \"*************-\$3\") {")

        private val PARSER_INPUT_CHANGELOG = CompiledDatabaseChangelogAll::class.qualifiedName!!
        private const val SERIALIZER_ACTUAL_CHANGELOG =
            "KotlinCompiledMigrateAndSerializeOnFatJarSpec/serializer_actual/ChangeLog0.kt"

        // To avoid auto-formatting, do not add a file extension.
        private const val SERIALIZER_EXPECT_CHANGELOG =
            "KotlinCompiledMigrateAndSerializeOnFatJarSpec/serializer_expect/ChangeLog0_kt"
        private const val PARSER_EXPECT_DDL = "KotlinCompiledMigrateAndSerializeOnFatJarSpec/parser_expect/db.ddl-0.sql"
    }
}
