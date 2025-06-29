package momosetkn

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import komapper.databasechangelog
import momosetkn.liquibase.client.LiquibaseClient
import momosetkn.liquibase.kotlin.serializer.KotlinCompiledChangeLogSerializer
import momosetkn.utils.Constants
import momosetkn.utils.DatabaseKomapperExtensions.komapperDb
import momosetkn.utils.DatabaseServer
import momosetkn.utils.MutableChangeLog
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import java.nio.file.Paths
import java.sql.DriverManager

class CreateLiquibaseClientSpec : FunSpec({
    lateinit var targetDatabaseServer: DatabaseServer

    beforeSpec {
        KotlinCompiledChangeLogSerializer.sourceRootPath = Paths.get(Constants.TEST_RESOURCE_DIR)
        MutableChangeLog.set {
            changeSet(author = "user", id = "100") {
                createTable(tableName = "company") {
                    column(name = "id", type = "UUID") {
                        constraints(nullable = false, primaryKey = true)
                    }
                    column(name = "name", type = "VARCHAR(256)")
                }
            }
            changeSet(author = "user", id = "200") {
                tagDatabase(tag = "finish")
            }
        }
    }
    beforeEach {
        targetDatabaseServer = SharedResources.getTargetDatabaseServer()
        targetDatabaseServer.startAndClear()
    }

    context("Create by java.sql.Connection") {
        test("can migrate") {
            val databaseConfig = targetDatabaseServer.startedServer
            Class.forName(databaseConfig.driver)
            val connection = DriverManager.getConnection(
                databaseConfig.jdbcUrl,
                databaseConfig.username,
                databaseConfig.password
            )

            val liquibaseClient = LiquibaseClient(
                changeLogFile = MutableChangeLog::class.qualifiedName!!,
                connection = connection
            )
            liquibaseClient.update(tag = "finish")

            val db = targetDatabaseServer.komapperDb()
            val d = Meta.databasechangelog
            val result = db.runQuery {
                QueryDsl.from(d)
            }
            result.size shouldBe 2
        }
    }
    context("Create by javax.sql.DataSource") {
        test("can migrate") {
            val databaseConfig = targetDatabaseServer.startedServer
            val hikariConfig = HikariConfig().apply {
                driverClassName = databaseConfig.driver
                jdbcUrl = databaseConfig.jdbcUrl
                username = databaseConfig.username
                password = databaseConfig.password
                maximumPoolSize = 1
                isAutoCommit = false
            }
            val hikariDataSource = HikariDataSource(hikariConfig)

            val liquibaseClient = LiquibaseClient(
                changeLogFile = MutableChangeLog::class.qualifiedName!!,
                datasource = hikariDataSource
            )
            liquibaseClient.update(tag = "finish")

            val db = targetDatabaseServer.komapperDb()
            val d = Meta.databasechangelog
            val result = db.runQuery {
                QueryDsl.from(d)
            }
            result.size shouldBe 2
        }
    }
})
