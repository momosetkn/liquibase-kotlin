package momosetkn.utils

import org.slf4j.LoggerFactory
import java.sql.DriverManager

object Database {
    private val log = LoggerFactory.getLogger(this.javaClass.name)

    private var container: DatabaseConfig? = null
    val startedContainer
        get() =
            requireNotNull(container) {
                "momosetkn.Database is not started"
            }

    fun getConnection() =
        DriverManager.getConnection(startedContainer.jdbcUrl, startedContainer.username, startedContainer.password)

    suspend fun start() {
        this.container = DatabaseConfig(
            driver = "org.h2.Driver",
            jdbcUrl = "jdbc:h2:~/testdb;AUTO_SERVER=TRUE",
            username = "sa",
            password = "",
        )
        Class.forName(startedContainer.driver)

        val launchTime =
            kotlin.system.measureTimeMillis {
                getConnection().use {
                    val stmt = it.createStatement()
                    val resultSet = stmt.executeQuery("select 1")
                    resultSet.next()
                    check(resultSet.getInt(1) == 1) {
                        "database connection failed"
                    }
                }
            }

        log.info("database started in $launchTime ms")
    }

    fun stop() {
        getConnection().use {
            val stmt = it.createStatement()
            stmt.execute("DROP ALL OBJECTS")
        }
    }

    fun generateDdl(): String {
        return getConnection().use {
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
}

data class DatabaseConfig(
    val driver: String,
    val jdbcUrl: String,
    val username: String,
    val password: String,
)
