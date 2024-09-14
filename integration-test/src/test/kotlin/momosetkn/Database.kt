package momosetkn

import org.slf4j.LoggerFactory
import org.testcontainers.containers.Container
import org.testcontainers.containers.PostgreSQLContainer

object Database {
    private val log = LoggerFactory.getLogger(this.javaClass.name)
    private var container: PostgreSQLContainer<*>? = null
    val startedContainer
        get() =
            requireNotNull(container) {
                "momosetkn.Database is not started"
            }

    fun start() {
        val image = org.testcontainers.utility.DockerImageName.parse("postgres:15.8")
        container = PostgreSQLContainer(image)

        val launchTime =
            kotlin.system.measureTimeMillis {
                startedContainer.start()
            }

        log.info("database started in $launchTime ms")
    }

    fun executeCommand(vararg args: String): Container.ExecResult {
        return startedContainer.execInContainer(*args)
    }

    fun stop() {
        container?.stop()
        log.info("database stop")
    }

    fun generateDdl(): String? {
        val commandResult =
            executeCommand(
                "pg_dump",
                "-h",
                "localhost",
                "-p",
                PostgreSQLContainer.POSTGRESQL_PORT.toString(),
                "-U",
                startedContainer.username,
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
