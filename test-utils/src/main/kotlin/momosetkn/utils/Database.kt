package momosetkn.utils

import org.h2.tools.Server
import org.slf4j.LoggerFactory
import java.net.ServerSocket
import java.sql.Connection
import java.sql.DriverManager
import kotlin.system.measureTimeMillis

object DatabaseServer {
    private val log = LoggerFactory.getLogger(this.javaClass.name)

    private var container: DatabaseConfig? = null

    // For a clean run every time,
    private const val DATABASE_DIRECTORY = "./build/tmp/test"
    private const val DATABASE_NAME = "liquibase_kotlin_test"

    // Automatically find a free port
    private val PORT = ServerSocket(0).use { socket ->
        socket.localPort
    }

    val startedContainer
        get() =
            requireNotNull(container) {
                "momosetkn.Database is not started"
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
        val databaseConfig = DatabaseConfig(
            driver = "org.h2.Driver",
            jdbcUrl = "jdbc:h2:tcp://127.0.0.1:$PORT/$DATABASE_DIRECTORY/$DATABASE_NAME",
            username = "sa",
            password = "",
        )
        Server.createTcpServer(
            "-tcpPort",
            "$PORT",
            "-tcpAllowOthers"
        ).start()
        Class.forName(databaseConfig.driver)
        val launchTime =
            measureTimeMillis {
                // create db
                val conn = DriverManager.getConnection(
                    "jdbc:h2:$DATABASE_DIRECTORY/$DATABASE_NAME;DB_CLOSE_DELAY=-1",
                    databaseConfig.username,
                    databaseConfig.password
                )
                conn.use {
                    checkDb(it)
                }
            }
        log.info("database started in $launchTime ms")
        return databaseConfig
    }

    @Synchronized
    fun stop() {
        this.container?.also { container ->
            getConnection(container).use {
                dropDb(it)
            }
        }
    }

    fun generateDdl(): String {
        return getConnection(startedContainer).use {
            val stmt = it.createStatement()
            val rs = stmt.executeQuery("SCRIPT NODATA")

            val schema = StringBuilder()

            while (rs.next()) {
                schema.append(rs.getString(1)).append("\n")
            }

            // Integration into LF
            schema.toString()
        }
    }

    private fun getConnection(container: DatabaseConfig) =
        DriverManager.getConnection(container.jdbcUrl, container.username, container.password)

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
