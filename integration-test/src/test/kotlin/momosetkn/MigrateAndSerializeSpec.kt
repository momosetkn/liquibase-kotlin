package  momosetkn

import Database
import LiquibaseCommand
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.nio.file.Paths

class MigrateAndSerializeSpec : FunSpec({
    beforeSpec {
        momosetkn.liquibase.kotlin.serializer.KotlinChangeLogSerializer.register()
        momosetkn.liquibase.kotlin.parser.KotlinLiquibaseChangeLogParser.register()
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
                changelogFile = "db.changelog/parser_input/db.changelog-0.kts",
            )
            val DIFF_TYPES_OPTION =
                "--diff-types=tables,columns,indexes,foreignkeys,primarykeys,uniqueconstraints,sequences"
            val actualSerializedChangeLogFile =
                Paths.get(resourceDir, "db.changelog/serializer_actual/db.changelog-0.kts")
            val f = actualSerializedChangeLogFile.toFile()
            if (f.exists()) f.delete()
            LiquibaseCommand.command(
                driverClassName = "org.postgresql.Driver",
                jdbcUrl = container.jdbcUrl,
                user = container.username,
                password = container.password,
                command = "generate-changelog",
                changelogFile = actualSerializedChangeLogFile.toString(),
                DIFF_TYPES_OPTION,
            )
            val ddl = Database.generateDdl()

            ddl shouldBe String(
                this::class.java.classLoader.getResourceAsStream("db.changelog/parser_expect/db.changelog-0.sql")
                    .readAllBytes()
            )
            getResourceAsString("db.changelog/serializer_actual/db.changelog-0.kts")
                .maskingChangeSet() shouldBe
                    getResourceAsString("db.changelog/serializer_expect/db.changelog-0.kts")
                        .maskingChangeSet()
        }
    }
})

private fun getResourceAsString(path: String) =
    Thread.currentThread().contextClassLoader.getResourceAsStream(path).let {
        String(
            it.readAllBytes()
        )
    }

val changeSetRegex = Regex("""changeSet\(author = "(.+)", id = "(\d+)-(\d)"\) \{""")

private fun String.maskingChangeSet() =
    this.replace(changeSetRegex, "changeSet(author = \"**********\", id = \"*************-\$3\") {")

const val resourceDir = "src/main/resources"
