package momosetkn.utils

import org.slf4j.LoggerFactory
import org.testcontainers.containers.Container
import org.testcontainers.containers.PostgreSQLContainer

class PostgreSqlDatabaseServer : DatabaseServer {
    private val log = LoggerFactory.getLogger(this.javaClass.name)
    private var container: PostgreSQLContainer<*>? = null
    override val startedServer: DatabaseConfig
        get() = run {
            val container = requireNotNull(container) {
                "${this::class.qualifiedName} is not started"
            }
            DatabaseConfig(
                driver = container.driverClassName,
                jdbcUrl = container.jdbcUrl,
                username = container.username,
                password = container.password,
            )
        }

    override fun startAndClear() {
        clear()
        val image = org.testcontainers.utility.DockerImageName.parse("postgres:latest")
        container = PostgreSQLContainer(image)

        val launchTime =
            kotlin.system.measureTimeMillis {
                container!!.start()
            }

        log.info("database started in $launchTime ms")
    }

    private fun executeCommand(vararg args: String): Container.ExecResult {
        return container!!.execInContainer(*args)
    }

    override fun clear() {
        container?.also {
            it.stop()
            log.info("database stop")
        }
    }

    override fun generateDdl(): String {
        val commandResult =
            executeCommand(
                "pg_dump",
                "-h",
                "localhost",
                "-p",
                PostgreSQLContainer.POSTGRESQL_PORT.toString(),
                "-U",
                container!!.username,
                "-s",
            )
        check(commandResult.exitCode == 0 && commandResult.stdout.isNotEmpty()) {
            """
                exitCode is ${commandResult.exitCode}
                stdout is ${commandResult.stdout}
            """.trimIndent()
        }
        return commandResult.stdout
    }
}
