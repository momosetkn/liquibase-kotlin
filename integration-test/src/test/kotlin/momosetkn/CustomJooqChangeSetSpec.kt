package momosetkn

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import komapper.company2
import liquibase.exception.CommandExecutionException
import momosetkn.liquibase.command.client.LiquibaseCommandClient
import momosetkn.liquibase.kotlin.change.custom.jooq.customJooqChange
import momosetkn.utils.ChangeLogDslBlock
import momosetkn.utils.DDLUtils.sql
import momosetkn.utils.DDLUtils.toMainDdl
import momosetkn.utils.DatabaseKomapperExtensions.komapperDb
import momosetkn.utils.DatabaseServer
import momosetkn.utils.MutableChangeLog
import momosetkn.utils.changeLogDsl
import momosetkn.utils.shouldMatchWithoutLineBreaks
import org.jooq.impl.DSL
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.single
import java.util.UUID

class CustomJooqChangeSetSpec : FunSpec({
    lateinit var targetDatabaseServer: DatabaseServer
    beforeSpec {
        targetDatabaseServer = SharedResources.getTargetDatabaseServer()
    }
    beforeEach {
        targetDatabaseServer.startAndClear()
    }
    val client = LiquibaseCommandClient {
        global {
            general {
                showBanner = false
            }
        }
    }

    context("forwardOnly") {
        fun subject(dsl: ChangeLogDslBlock) {
            MutableChangeLog.set(dsl)
            val server = targetDatabaseServer.startedServer
            client.update(
                driver = server.driver,
                url = server.jdbcUrl,
                username = server.username,
                password = server.password,
                changelogFile = MutableChangeLog::class.qualifiedName!!,
            )
        }
        val c = Meta.company2
        val dsl = changeLogDsl {
            changeSet(author = "momose", id = "100-10") {
                customJooqChange(
                    execute = { db ->
                        val query =
                            """
                                CREATE TABLE company2 (
                                    id uuid primary key,
                                    name character varying(255)
                                );
                            """.trimIndent()
                        db.execute(query)
                    },
                    rollback = { db ->
                        val query = "DROP TABLE company2"
                        db.execute(query)
                    },
                )
            }
            changeSet(author = "momose", id = "100-20") {
                customJooqChange(
                    execute = { db ->
                        val query = db
                            .insertInto(DSL.table("company2"))
                            .set(DSL.field("id", UUID::class.java), UUID.randomUUID())
                            .set(DSL.field("name", String::class.java), "CreatedByJooq_name")
                        query.execute()
                    },
                )
            }
        }
        test("can migrate") {
            subject(dsl)
            targetDatabaseServer.generateDdl().toMainDdl() shouldMatchWithoutLineBreaks sql(
                """
                    CREATE CACHED TABLE "PUBLIC"."COMPANY2"(
                        "ID" UUID NOT NULL,
                        "NAME" CHARACTER VARYING(255)
                    );
                    ALTER TABLE "PUBLIC"."COMPANY2" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_A" PRIMARY KEY("ID");
                """.trimIndent()
            )
            val db = targetDatabaseServer.komapperDb()
            val query = QueryDsl.from(c).single()
            val item = db.runQuery(query)
            item.name shouldBe "CreatedByJooq_name"
        }
    }

    context("forward and rollback") {
        val startedTag = "started"
        fun subject(dsl: ChangeLogDslBlock) {
            MutableChangeLog.set(dsl)
            val server = targetDatabaseServer.startedServer
            client.update(
                driver = server.driver,
                url = server.jdbcUrl,
                username = server.username,
                password = server.password,
                changelogFile = MutableChangeLog::class.qualifiedName!!,
            )
            client.rollback(
                driver = server.driver,
                url = server.jdbcUrl,
                username = server.username,
                password = server.password,
                changelogFile = MutableChangeLog::class.qualifiedName!!,
                tag = startedTag,
            )
        }

        val c = Meta.company2
        context("rollback arg is given") {
            val dsl = changeLogDsl {
                changeSet(author = "momose", id = "0-10") {
                    tagDatabase(startedTag)
                }
                changeSet(author = "momose", id = "100-10") {
                    customJooqChange(
                        execute = { db ->
                            val query = """
                                    CREATE TABLE company2 (
                                        id uuid primary key,
                                        name character varying(255)
                                    );
                            """.trimIndent()
                            db.execute(query)
                        },
                        rollback = { db ->
                            val query = "DROP TABLE company2"
                            db.execute(query)
                        },
                    )
                }
                changeSet(author = "momose", id = "100-20") {
                    customJooqChange(
                        execute = { db ->
                            val query = db
                                .insertInto(DSL.table("company2"))
                                .set(DSL.field("id", UUID::class.java), UUID.randomUUID())
                                .set(DSL.field("name", String::class.java), "CreatedByJooq_name")
                            query.execute()
                        },
                        rollback = { db ->
                            val query = "truncate table company2"
                            db.execute(query)
                        }
                    )
                }
            }
            test("can rollback") {
                subject(dsl)
                targetDatabaseServer.generateDdl().toMainDdl() shouldMatchWithoutLineBreaks sql("")
            }
        }
        context("rollback arg is none") {
            val dsl = changeLogDsl {
                changeSet(author = "momose", id = "0-10") {
                    tagDatabase(startedTag)
                }
                changeSet(author = "momose", id = "100-10") {
                    customJooqChange(
                        execute = { db ->
                            val query =
                                """
                                    CREATE TABLE company2 (
                                        id uuid primary key,
                                        name character varying(255)
                                    );
                                """.trimIndent()
                            db.execute(query)
                        },
                        rollback = { db ->
                            val query = "DROP TABLE company2"
                            db.execute(query)
                        },
                    )
                }
                changeSet(author = "momose", id = "100-20") {
                    customJooqChange(
                        execute = { db ->
                            val query = db
                                .insertInto(DSL.table("company2"))
                                .set(DSL.field("id", UUID::class.java), UUID.randomUUID())
                                .set(DSL.field("name", String::class.java), "CreatedByJooq_name")
                            query.execute()
                        }
                    )
                }
            }
            test("throw error") {
                shouldThrow<CommandExecutionException> {
                    subject(dsl)
                }
            }
        }
    }
})
