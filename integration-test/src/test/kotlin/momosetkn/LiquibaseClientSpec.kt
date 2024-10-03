package momosetkn

import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import komapper.databasechangelog
import liquibase.Scope
import liquibase.changelog.ChangeLogParameters
import momosetkn.liquibase.client.LiquibaseClient
import momosetkn.liquibase.client.LiquibaseDatabaseFactory
import momosetkn.liquibase.client.configureLiquibase
import momosetkn.liquibase.kotlin.parser.KotlinCompiledLiquibaseChangeLogParser
import momosetkn.liquibase.kotlin.serializer.KotlinCompiledChangeLogSerializer
import momosetkn.utils.Constants
import momosetkn.utils.Database
import momosetkn.utils.DatabaseKomapperExtensions.komapperDb
import momosetkn.utils.InterchangeableChangeLog
import momosetkn.utils.set
import momosetkn.utils.toVersion
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import java.io.StringWriter
import java.nio.file.Paths

@OptIn(ExperimentalKotest::class)
class LiquibaseClientSpec : FunSpec({
    beforeSpec {
        KotlinCompiledChangeLogSerializer.sourceRootPath = Paths.get(Constants.RESOURCE_DIR)
    }
    beforeEach {
        Database.start()
        configureLiquibase {
            global {
                general {
                    showBanner = false
                }
            }
        }
    }
    afterEach {
        Database.stop()
    }

    fun liquibaseClient(changeLogFile: String? = null): LiquibaseClient {
        val container = Database.startedContainer
        val database = LiquibaseDatabaseFactory.create(
            driver = container.driver,
            url = container.jdbcUrl,
            username = container.username,
            password = container.password,
        )
        return LiquibaseClient(
            changeLogFile = changeLogFile ?: InterchangeableChangeLog::class.qualifiedName!!,
            database = database,
        )
    }

    context("update(tag)") {
        InterchangeableChangeLog.set {
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
        test("can migrate") {
            liquibaseClient().update(tag = "finish",)

            val db = Database.komapperDb()
            val d = Meta.databasechangelog
            val result = db.runQuery {
                QueryDsl.from(d)
            }
            result.size shouldBe 2
        }
    }

    context("generateChangeLog") {
        val outputFile = File("src/main/resources/${LiquibaseClientSpec::class.simpleName}/generateChangeLog.kt")
        InterchangeableChangeLog.set {
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
        beforeEach {
            if (outputFile.exists()) {
                outputFile.delete()
            }
        }
        test("can generated") {
            val databaseChangeLog = KotlinCompiledLiquibaseChangeLogParser().parse(
                physicalChangeLogLocation = InterchangeableChangeLog::class.qualifiedName!!,
                changeLogParameters = ChangeLogParameters(),
                resourceAccessor = Scope.getCurrentScope().resourceAccessor,
            )
            databaseChangeLog.physicalFilePath = outputFile.toString()

            val baos = ByteArrayOutputStream()
            liquibaseClient().update()
            liquibaseClient(outputFile.toString()).generateChangeLog(
                outputStream = PrintStream(baos),
            )
            val generateResult = baos.toString()
            println(generateResult) // empty

            val actual = outputFile.readText()

            actual shouldStartWith "package LiquibaseClientSpec"
        }
    }

    context("updateCountSql").config(
        enabled = System.getProperty("liquibaseVersion").toVersion() >= "4.29.2".toVersion()
    ) {
        InterchangeableChangeLog.set {
            changeSet(author = "user", id = "100") {
                createTable(tableName = "company") {
                    column(name = "id", type = "UUID") {
                        constraints(nullable = false, primaryKey = true)
                    }
                    column(name = "name", type = "VARCHAR(256)")
                }
            }
        }
        test("can output sql") {
            val sw = StringWriter()
            liquibaseClient().updateCountSql(1, output = sw)
            println(sw.toString())
            sw.toString() shouldStartWith "-- Create Database Lock Table"
        }
    }

    context("updateToTagSql").config(
        enabled = System.getProperty("liquibaseVersion").toVersion() >= "4.29.2".toVersion()
    ) {
        InterchangeableChangeLog.set {
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
        test("can output sql") {
            val sw = StringWriter()
            liquibaseClient().updateToTagSql("finish", output = sw)
            println(sw.toString())
            sw.toString() shouldStartWith "-- Create Database Lock Table"
        }
    }
})
