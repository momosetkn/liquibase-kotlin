package momosetkn.utils

import org.slf4j.LoggerFactory
import java.net.ServerSocket
import java.nio.file.Path
import java.sql.Connection
import java.sql.DriverManager
import kotlin.system.measureTimeMillis

object DatabaseServer {
    private val log = LoggerFactory.getLogger(this.javaClass.name)

    private var container: DatabaseConfig? = null
    private val gradlewUtils = GradlewUtils()

    // For a clean run every time,
    private const val DATABASE_NAME = "liquibase_kotlin_test"
    private const val LAUNCH_INITIAL_WAIT = 200L
    private const val CONFIRM_ESTABLISHED_INTERVAL = 50L

    // Automatically find a free port
    private val PORT = ServerSocket(0).use { socket ->
        socket.localPort
    }

    val startedContainer
        get() =
            requireNotNull(container) {
                "${DatabaseServer::class.qualifiedName} is not started"
            }

    @Synchronized
    fun start() {
        if (this.container == null) {
            this.container = createServer()
        }
        getConnection(startedContainer).use {
            checkDb(it)
            dropDb(it)
        }
    }

    private fun createServer(): DatabaseConfig {
        val dir = databaseDirectory()
        val databaseConfig = DatabaseConfig(
            driver = "org.h2.Driver",
            jdbcUrl = "jdbc:h2:tcp://127.0.0.1:$PORT/$dir/$DATABASE_NAME",
            username = "sa",
            password = "",
        )
        Class.forName(databaseConfig.driver)
        val launchTime =
            measureTimeMillis {
                lunchH2Server(PORT)
                recursiveCheckDbLoop(databaseConfig)
            }
        log.info("database started in $launchTime ms")
        return databaseConfig
    }

    @Synchronized
    fun clear() {
        this.container?.also { container ->
            getConnection(container).use {
                dropDb(it)
            }
        }
    }

    private fun lunchH2Server(port: Int): () -> Unit {
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

    fun generateDdl(): String {
        return getConnection(startedContainer).use {
            val stmt = it.createStatement()
            val rs = stmt.executeQuery("SCRIPT NODATA")

            val schema = StringBuilder()

            while (rs.next()) {
                schema.append(rs.getString(1)).append("\n")
            }

            schema.toString()
        }
    }

    fun getConnection(container: DatabaseConfig) =
        DriverManager.getConnection(container.jdbcUrl, container.username, container.password)

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
}

data class DatabaseConfig(
    val driver: String,
    val jdbcUrl: String,
    val username: String,
    val password: String,
)
