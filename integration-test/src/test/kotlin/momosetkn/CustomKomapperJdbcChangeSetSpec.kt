package momosetkn

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import komapper.CreatedByKomapper
import komapper.createdByKomapper
import momosetkn.liquibase.client.LiquibaseClient
import momosetkn.liquibase.kotlin.change.customKomapperJdbcChange
import momosetkn.utils.DDLUtils.shouldBeEqualDdl
import momosetkn.utils.Database
import momosetkn.utils.DatabaseKomapperExtensions.komapperDb
import momosetkn.utils.InterchangeableChangeLog
import momosetkn.utils.set
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.single
import java.util.UUID

class CustomKomapperJdbcChangeSetSpec : FunSpec({
    beforeEach {
        Database.start()
    }
    afterEach {
        Database.stop()
    }
    val client = LiquibaseClient {
        globalArgs {
            general {
                showBanner = false
            }
        }
    }

    context("forwardOnly") {
        fun subject() {
            val container = Database.startedContainer
            client.update(
                driver = "org.postgresql.Driver",
                url = container.jdbcUrl,
                username = container.username,
                password = container.password,
                changelogFile = InterchangeableChangeLog::class.qualifiedName!!,
            )
        }
        val c = Meta.createdByKomapper
        InterchangeableChangeLog.set {
            changeSet(author = "momose", id = "100-10") {
                customKomapperJdbcChange(
                    execute = { db ->
                        val query = QueryDsl.executeScript(
                            """
                                CREATE TABLE created_by_komapper (
                                    id uuid primary key,
                                    name character varying(255)
                                );
                            """.trimIndent()
                        )
                        db.runQuery(query)
                    },
                    rollback = { db ->
                        val query = QueryDsl.executeScript("DROP TABLE created_by_komapper")
                        db.runQuery(query)
                    },
                )
            }
            changeSet(author = "momose", id = "100-20") {
                customKomapperJdbcChange(
                    execute = { db ->
                        val query = QueryDsl.insert(c).single(
                            CreatedByKomapper(
                                id = UUID.randomUUID(),
                                name = "CreatedByKomapper_name",
                            )
                        )
                        db.runQuery(query)
                    },
                )
            }
        }
        test("can migrate") {
            subject()
            Database.shouldBeEqualDdl(
                """
                    CREATE TABLE public.created_by_komapper (
                        id uuid NOT NULL,
                        name character varying(255)
                    );
                    ALTER TABLE public.created_by_komapper OWNER TO test;
                    ALTER TABLE ONLY public.created_by_komapper
                        ADD CONSTRAINT created_by_komapper_pkey PRIMARY KEY (id);
                """.trimIndent()
            )
            val db = Database.komapperDb()
            val query = QueryDsl.from(c).single()
            val item = db.runQuery(query)
            item.name shouldBe "CreatedByKomapper_name"
        }
    }

    context("forward and rollback") {
        val startedTag = "started"
        fun subject() {
            val container = Database.startedContainer
            client.update(
                driver = "org.postgresql.Driver",
                url = container.jdbcUrl,
                username = container.username,
                password = container.password,
                changelogFile = InterchangeableChangeLog::class.qualifiedName!!,
            )
            client.rollback(
                driver = "org.postgresql.Driver",
                url = container.jdbcUrl,
                username = container.username,
                password = container.password,
                changelogFile = InterchangeableChangeLog::class.qualifiedName!!,
                tag = startedTag,
            )
        }
        val c = Meta.createdByKomapper
        context("rollback arg is given") {
            InterchangeableChangeLog.set {
                changeSet(author = "momose", id = "0-10") {
                    tagDatabase(startedTag)
                }
                changeSet(author = "momose", id = "100-10") {
                    customKomapperJdbcChange(
                        execute = { db ->
                            val query = QueryDsl.executeScript(
                                """
                                    CREATE TABLE created_by_komapper (
                                        id uuid primary key,
                                        name character varying(255)
                                    );
                                """.trimIndent()
                            )
                            db.runQuery(query)
                        },
                        rollback = { db ->
                            val query = QueryDsl.executeScript("DROP TABLE created_by_komapper")
                            db.runQuery(query)
                        },
                    )
                }
                changeSet(author = "momose", id = "100-20") {
                    customKomapperJdbcChange(
                        execute = { db ->
                            val query = QueryDsl.insert(c).single(
                                CreatedByKomapper(
                                    id = UUID.randomUUID(),
                                    name = "CreatedByKomapper_name",
                                )
                            )
                            db.runQuery(query)
                        },
                        rollback = { db ->
                            val query = QueryDsl.delete(c).all()
                            db.runQuery(query)
                        }
                    )
                }
            }
            test("can rollback") {
                subject()
                Database.shouldBeEqualDdl("")
            }
        }
        context("rollback arg is none") {
            InterchangeableChangeLog.set {
                changeSet(author = "momose", id = "0-10") {
                    tagDatabase(startedTag)
                }
                changeSet(author = "momose", id = "100-10") {
                    customKomapperJdbcChange(
                        execute = { db ->
                            val query = QueryDsl.executeScript(
                                """
                                    CREATE TABLE created_by_komapper (
                                        id uuid primary key,
                                        name character varying(255)
                                    );
                                """.trimIndent()
                            )
                            db.runQuery(query)
                        },
                        rollback = { db ->
                            val query = QueryDsl.executeScript("DROP TABLE created_by_komapper")
                            db.runQuery(query)
                        },
                    )
                }
                changeSet(author = "momose", id = "100-20") {
                    customKomapperJdbcChange(
                        execute = { db ->
                            val query = QueryDsl.insert(c).single(
                                CreatedByKomapper(
                                    id = UUID.randomUUID(),
                                    name = "CreatedByKomapper_name",
                                )
                            )
                            db.runQuery(query)
                        }
                    )
                }
            }
            test("throw error") {
                shouldThrow<IllegalStateException> {
                    subject()
                }
            }
        }
    }
})
