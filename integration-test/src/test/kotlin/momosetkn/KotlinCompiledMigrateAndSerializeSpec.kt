package momosetkn

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import momosetkn.Constants.RESOURCE_DIR
import momosetkn.ResourceUtils.getResourceAsString
import momosetkn.liquibase.changelogs.CompiledDatabaseChangelogAll
import momosetkn.liquibase.client.LiquibaseClient
import momosetkn.liquibase.kotlin.serializer.KotlinCompiledChangeLogSerializer
import java.nio.file.Paths

class KotlinCompiledMigrateAndSerializeSpec : FunSpec({
    beforeSpec {
        Database.start()
        KotlinCompiledChangeLogSerializer.sourceRootPath = Paths.get(RESOURCE_DIR)
    }
    afterSpec {
        Database.stop()
    }

    context("Migrate and serialize") {
        test("can migrate") {
            val client = LiquibaseClient {
                globalArgs {
                    general {
                        showBanner = false
                    }
                }
            }
            val container = Database.startedContainer
            client.update(
                driver = "org.postgresql.Driver",
                url = container.jdbcUrl,
                username = container.username,
                password = container.password,
                changelogFile = PARSER_INPUT_CHANGELOG,
            )
            client.rollback(
                driver = "org.postgresql.Driver",
                url = container.jdbcUrl,
                username = container.username,
                password = container.password,
                changelogFile = PARSER_INPUT_CHANGELOG,
                tag = "started",
            )
            client.update(
                driver = "org.postgresql.Driver",
                url = container.jdbcUrl,
                username = container.username,
                password = container.password,
                changelogFile = PARSER_INPUT_CHANGELOG,
            )
            val actualSerializedChangeLogFile =
                Paths.get(RESOURCE_DIR, SERIALIZER_ACTUAL_CHANGELOG)
            val f = actualSerializedChangeLogFile.toFile()
            if (f.exists()) f.delete()
            client.generateChangelog(
                driver = "org.postgresql.Driver",
                url = container.jdbcUrl,
                username = container.username,
                password = container.password,
                changelogFile = actualSerializedChangeLogFile.toString(),
            )
            val ddl = Database.generateDdl()

            // check database
            val expectedDdl = getResourceAsString(PARSER_EXPECT_DDL)
            ddl shouldBe expectedDdl

            // check serializer
            val actual = getFileAsString(SERIALIZER_ACTUAL_CHANGELOG)
                .maskingChangeSet()
            val expect = getResourceAsString(SERIALIZER_EXPECT_CHANGELOG)
                .maskingChangeSet()
            actual shouldBe expect
        }
    }
}) {
    companion object {
        private fun getFileAsString(path: String) =
            Paths.get(RESOURCE_DIR, path).toFile().readText()

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
