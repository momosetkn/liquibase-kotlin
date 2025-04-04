package momosetkn.utils

import org.slf4j.LoggerFactory
import java.net.ServerSocket
import java.nio.file.Path
import java.sql.Connection
import java.sql.DriverManager
import kotlin.system.measureTimeMillis

class DatabaseServer {
    private val log = LoggerFactory.getLogger(this.javaClass.name)

    private var config: DatabaseConfig? = null
    private val h2DatabaseServerLauncher = H2DatabaseServerLauncher()

    // Automatically find a free port
    private val port = ServerSocket(0).use { socket ->
        socket.localPort
    }

    // For a clean run every time,
    private val databaseName = "lk_test_${port}_${System.currentTimeMillis()}"

    val startedServer
        get() =
            requireNotNull(config) {
                "${DatabaseServer::class.qualifiedName} is not started"
            }

    @Synchronized
    fun startAndClear() {
        if (this.config == null) {
            this.config = createServer()
        }
        getConnection(startedServer).use {
            checkDb(it)
            dropDb(it)
        }
    }

    private fun createServer(): DatabaseConfig {
        val dir = databaseDirectory()
        val databaseConfig = DatabaseConfig(
            driver = "org.h2.Driver",
            jdbcUrl = "jdbc:h2:tcp://127.0.0.1:$port/$dir/$databaseName",
            username = "sa",
            password = "",
        )
        Class.forName(databaseConfig.driver)
        val launchTime =
            measureTimeMillis {
                h2DatabaseServerLauncher.launch(port)
                recursiveCheckDbLoop(databaseConfig)
            }
        log.info("database started in $launchTime ms")
        return databaseConfig
    }

    @Synchronized
    fun clear() {
        this.config?.also { server ->
            getConnection(server).use {
                dropDb(it)
            }
        }
    }

    fun generateDdl(): String {
        return getConnection(startedServer).use {
            val stmt = it.createStatement()
            val rs = stmt.executeQuery("SCRIPT NODATA")

            val schema = StringBuilder()

            while (rs.next()) {
                schema.append(rs.getString(1)).append("\n")
            }

            schema.toString()
        }
    }

    fun getConnection(config: DatabaseConfig) =
        DriverManager.getConnection(config.jdbcUrl, config.username, config.password)

    private fun databaseDirectory() =
        Path.of(System.getProperty("user.dir")).resolveSibling("build/tmp/test")

    private tailrec fun recursiveCheckDbLoop(container: DatabaseConfig) {
        val isSuccess = runCatching {
            getConnection(container).use {
                checkDb(it)
            }
        }.isSuccess
        if (isSuccess) {
            return
        } else {
            Thread.sleep(CONFIRM_ESTABLISHED_INTERVAL)
            recursiveCheckDbLoop(container)
        }
    }

    private fun checkDb(conn: Connection) {
        val stmt = conn.createStatement()
        val resultSet = stmt.executeQuery("select 1")
        resultSet.next()
        check(resultSet.getInt(1) == 1) {
            "database connection failed"
        }
    }

    private fun dropDb(conn: Connection) {
        val stmt = conn.createStatement()
        stmt.execute("DROP ALL OBJECTS")
    }

    companion object {
        private const val CONFIRM_ESTABLISHED_INTERVAL = 50L
    }
}

private class H2DatabaseServerLauncher(
    val sameProcess: Boolean = true
) {
    private val log = LoggerFactory.getLogger(this.javaClass.name)

    private val gradlewUtils = GradlewUtils()

    fun launch(port: Int): () -> Unit {
        return if (sameProcess) {
            launchSameProcess(port)
        } else {
            launchOtherProcess(port)
        }
    }

    private fun launchOtherProcess(port: Int): () -> Unit {
        val command = listOfNotNull(
            gradlewUtils.getDefaultShell(),
            "./gradlew",
            "test-utils:startH2Server",
            "-Pport=$port"
        )
        val destroy = gradlewUtils.executeCommand(command)
        Thread.sleep(LAUNCH_INITIAL_WAIT)

        return destroy
    }

    private fun launchSameProcess(port: Int): () -> Unit {
        val args = listOf("-tcp", "-tcpAllowOthers", "-tcpDaemon", "-tcpPort", "$port", "-trace", "-ifNotExists")

        @Suppress("SpreadOperator")
        val server = org.h2.tools.Server.createTcpServer(*args.toTypedArray())
        server.start()
        log.info(server.getStatus())
        return {
            server.stop()
        }
    }

    companion object {
        private const val LAUNCH_INITIAL_WAIT = 200L
    }
}

data class DatabaseConfig(
    val driver: String,
    val jdbcUrl: String,
    val username: String,
    val password: String,
)
