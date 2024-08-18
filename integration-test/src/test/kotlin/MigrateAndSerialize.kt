import io.kotest.core.spec.style.FunSpec

class MigrateAndSerialize : FunSpec({
    val jdbcUrl = "jdbc:tc:postgresql:9.6.8:///test?TC_DAEMON=true"
    xcontext("Migrate and serialize") {
        test("can migrate") {
            LiquibaseCommand.commandUnuseChangelog(
                driverClassName = "org.testcontainers.jdbc.ContainerDatabaseDriver",
                jdbcUrl = jdbcUrl,
                user = "root",
                password = "test",
                command = "update",
                changelogFile = ".",
            )
        }
    }
})
