package momosetkn

import Database
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import momosetkn.liquibase.LiquibaseCommand
import java.nio.file.Paths

class MigrateAndSerializeSpec : FunSpec({
    beforeSpec {
        Database.start()
    }
    afterSpec {
        Database.stop()
    }

    context("Migrate and serialize") {
        test("can migrate") {
            val container = Database.startedContainer
            LiquibaseCommand.command(
                driverClassName = "org.postgresql.Driver",
                jdbcUrl = container.jdbcUrl,
                user = container.username,
                password = container.password,
                command = "update",
                changelogFile = PARSER_INPUT_CHANGELOG,
            )
            LiquibaseCommand.command(
                driverClassName = "org.postgresql.Driver",
                jdbcUrl = container.jdbcUrl,
                user = container.username,
                password = container.password,
                command = "rollback",
                changelogFile = PARSER_INPUT_CHANGELOG,
                "--tag=started",
            )
            LiquibaseCommand.command(
                driverClassName = "org.postgresql.Driver",
                jdbcUrl = container.jdbcUrl,
                user = container.username,
                password = container.password,
                command = "update",
                changelogFile = PARSER_INPUT_CHANGELOG,
            )
            val actualSerializedChangeLogFile =
                Paths.get(RESOURCE_DIR, SERIALIZER_ACTUAL_CHANGELOG)
            val f = actualSerializedChangeLogFile.toFile()
            if (f.exists()) f.delete()
            LiquibaseCommand.command(
                driverClassName = "org.postgresql.Driver",
                jdbcUrl = container.jdbcUrl,
                user = container.username,
                password = container.password,
                command = "generate-changelog",
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
})

private fun getResourceAsString(path: String) =
    Thread.currentThread().contextClassLoader.getResourceAsStream(path).let {
        String(
            it.readAllBytes()
        )
    }

private fun getFileAsString(path: String) =
    Paths.get(RESOURCE_DIR, path).toFile().readText()

val changeSetRegex = Regex("""changeSet\(author = "(.+)", id = "(\d+)-(\d)"\) \{""")

private fun String.maskingChangeSet() =
    this.replace(changeSetRegex, "changeSet(author = \"**********\", id = \"*************-\$3\") {")

const val PARSER_INPUT_CHANGELOG = "db.changelog/parser_input/db.changelog-0.kts"
const val SERIALIZER_ACTUAL_CHANGELOG = "db.changelog/serializer_actual/db.changelog-0.kts"

// To avoid auto-formatting, do not add a file extension.
const val SERIALIZER_EXPECT_CHANGELOG = "db.changelog/serializer_expect/db.changelog-0_kts"
const val PARSER_EXPECT_DDL = "db.changelog/parser_expect/db.ddl-0.sql"
const val RESOURCE_DIR = "src/main/resources"
