package momosetkn

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import komapper.databasechangelog
import momosetkn.liquibase.command.client.LiquibaseCommandClient
import momosetkn.utils.DatabaseKomapperExtensions.komapperDb
import momosetkn.utils.DatabaseServer
import momosetkn.utils.MutableChangeLog
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.single

class ChangeLogSpec : FunSpec({
    lateinit var targetDatabaseServer: DatabaseServer
    beforeSpec {
        targetDatabaseServer = SharedResources.getTargetDatabaseServer()
    }
    beforeSpec {
        targetDatabaseServer.startAndClear()
    }
    val client = LiquibaseCommandClient {
        global {
            general {
                showBanner = false
            }
        }
    }

    fun subject() {
        val server = targetDatabaseServer.startedServer
        client.update(
            driver = server.driver,
            url = server.jdbcUrl,
            username = server.username,
            password = server.password,
            changelogFile = MutableChangeLog::class.qualifiedName!!,
        )
    }

    context("property") {
        MutableChangeLog.set {
            property(name = "key1", value = "value1")
            property(name = "key2", value = "value2")
            property(file = "ChangeLogSpec/prop.txt")
            changeSet(author = "user", id = "100") {
                tagDatabase("\${key1}_\${file_key3}")
            }
        }
        test("can use property") {
            subject()
            val db = targetDatabaseServer.komapperDb()
            val d = Meta.databasechangelog
            val result = db.runQuery {
                QueryDsl.from(d).single()
            }
            result.tag shouldBe "value1_file_value3"
        }
    }

    context("removeChangeSetProperty") {
        context("dbms = all, remove = afterColumn") {
            MutableChangeLog.set {
                removeChangeSetProperty(
                    change = "addColumn",
                    dbms = "all",
                    remove = "afterColumn",
                )
                changeSet(author = "user", id = "101") {
                    createTable(tableName = "company") {
                        column(type = "uuid", name = "id")
                        column(type = "varchar(512)", name = "description")
                    }
                }
                changeSet(author = "user", id = "102") {
                    addColumn(tableName = "company") {
                        column(
                            type = "varchar(255)",
                            name = "name",
                            afterColumn = "id" // to not set by removeChangeSetProperty
                        )
                    }
                }
            }
            // FIXME: failed. liquibase bug?
            // https://github.com/liquibase/liquibase/issues/5290
            xtest("NAME column be last") {
                subject()
                targetDatabaseServer.generateDdl() shouldContain """
                CREATE CACHED TABLE "PUBLIC"."COMPANY"(
                    "ID" UUID,
                    "DESCRIPTION" CHARACTER VARYING(512),
                    "NAME" CHARACTER VARYING(255)
                );
                """.trimIndent()
            }
        }
    }
})
