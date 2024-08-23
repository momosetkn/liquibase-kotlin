import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MigrateAndSerialize : FunSpec({
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
            val ddl = Database.generateDdl()

            ddl shouldBe String(
                this::class.java.classLoader.getResourceAsStream("db.changelog/parser_expect/db.changelog-0.sql")
                    .readAllBytes()
            )
        }
    }

    context("Migrate and serialize2") {
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
             val DIFF_TYPES_OPTION = "--diff-types=tables,columns,indexes,foreignkeys,primarykeys,uniqueconstraints,sequences"

            LiquibaseCommand.command(
                driverClassName = "org.postgresql.Driver",
                jdbcUrl = container.jdbcUrl,
                user = container.username,
                password = container.password,
                command = "generate-changelog",
                changelogFile = "db.changelog/serializer_actual/db.changelog-1.xml",
                DIFF_TYPES_OPTION,
            )
            val ddl = Database.generateDdl()

            ddl shouldBe String(
                this::class.java.classLoader.getResourceAsStream("db.changelog/parser_expect/db.changelog-0.sql")
                    .readAllBytes()
            )
        }
    }
})
