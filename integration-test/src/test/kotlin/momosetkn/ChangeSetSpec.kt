package momosetkn

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import komapper.databasechangelog
import momosetkn.liquibase.client.LiquibaseClient
import momosetkn.liquibase.kotlin.dsl.ChangeSetDsl
import momosetkn.utils.DDLUtils.shouldBeEqualDdl
import momosetkn.utils.Database
import momosetkn.utils.DatabaseKomapperExtensions.komapperDb
import momosetkn.utils.InterchangeableChangeLog
import momosetkn.utils.set
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.single

class ChangeSetSpec : FunSpec({
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

    context("comment") {
        InterchangeableChangeLog.set {
            changeSet(author = "user", id = "100") {
                comment("comment_123") // precedence this
                comment("comment_456")
            }
        }
        test("can migrate") {
            subject()
            val db = Database.komapperDb()
            val d = Meta.databasechangelog
            val result = db.runQuery {
                QueryDsl.from(d).single()
            }
            result.comments shouldBe "comment_123"
        }
    }
    context("preConditions") {
        fun databaseUsername() = Database.startedContainer.username
        context("postgresql and <currentUser>") {
            InterchangeableChangeLog.set {
                changeSet(author = "user", id = "100") {
                    preConditions(
                        onFail = "MARK_RAN",
                    ) {
                        dbms(type = "postgresql")
                        runningAs(username = databaseUsername())
                    }
                    createCompanyTable()
                }
            }
            test("can migrate") {
                subject()
                Database.shouldBeEqualDdl(
                    """
                    CREATE TABLE public.company (
                        id uuid NOT NULL,
                        name character varying(256)
                    );
                    ALTER TABLE public.company OWNER TO test;
                    ALTER TABLE ONLY public.company
                        ADD CONSTRAINT company_pkey PRIMARY KEY (id);
                    """.trimIndent()
                )
                val db = Database.komapperDb()
                val d = Meta.databasechangelog
                val result = db.runQuery {
                    QueryDsl.from(d).single()
                }
                result.id shouldBe "100"
            }
        }
        context("mysql and root") {
            InterchangeableChangeLog.set {
                changeSet(author = "user", id = "100") {
                    preConditions(
                        onFail = "MARK_RAN",
                    ) {
                        dbms(type = "mysql")
                        runningAs(username = "root")
                    }
                    createCompanyTable()
                }
            }
            test("can migrate") {
                subject()
                Database.shouldBeEqualDdl("")
                val db = Database.komapperDb()
                val d = Meta.databasechangelog
                val result = db.runQuery {
                    QueryDsl.from(d).single()
                }
                result.exectype shouldBe "MARK_RAN"
            }
        }
        context("(mysql and root) or (postgresql and currentUser)") {
            InterchangeableChangeLog.set {
                changeSet(author = "user", id = "100") {
                    preConditions(
                        onFail = "MARK_RAN",
                    ) {
                        or {
                            and {
                                dbms(type = "mysql")
                                runningAs(username = "root")
                            }
                            and {
                                dbms(type = "postgresql")
                                runningAs(username = databaseUsername())
                            }
                        }
                    }
                    createCompanyTable()
                }
            }
            test("can migrate") {
                subject()
                Database.shouldBeEqualDdl(
                    """
                    CREATE TABLE public.company (
                        id uuid NOT NULL,
                        name character varying(256)
                    );
                    ALTER TABLE public.company OWNER TO test;
                    ALTER TABLE ONLY public.company
                        ADD CONSTRAINT company_pkey PRIMARY KEY (id);
                    """.trimIndent()
                )
                val db = Database.komapperDb()
                val d = Meta.databasechangelog
                val result = db.runQuery {
                    QueryDsl.from(d).single()
                }
                result.id shouldBe "100"
            }
        }
    }

    context("executeCommand") {
        InterchangeableChangeLog.set {
            changeSet(author = "user", id = "100") {
                executeCommand(
                    executable = "docker",
                    timeout = "10s"
                ) {
                    arg("ps")
                }
            }
        }
        test("can migrate") {
            subject()
            // confirm output docker help
        }
    }
    context("output") {
        InterchangeableChangeLog.set {
            changeSet(author = "user", id = "100") {
                output(
                    message = "output_message",
                )
            }
        }
        test("can migrate") {
            subject()
            // confirm output "output_message"
        }
    }

    context("sql") {
        InterchangeableChangeLog.set {
            changeSet(author = "user", id = "100") {
                sql(
                    """
                    create table table_a (
                        id int primary key,
                        name varchar(255)
                    )
                    """.trimIndent()
                )
            }
        }
        test("can migrate") {
            subject()
            Database.shouldBeEqualDdl(
                """
                    CREATE TABLE public.table_a (
                        id integer NOT NULL,
                        name character varying(255)
                    );
                    ALTER TABLE public.table_a OWNER TO test;
                    ALTER TABLE ONLY public.table_a
                        ADD CONSTRAINT table_a_pkey PRIMARY KEY (id);
                """.trimIndent()
            )
        }
    }
    context("sql(original)") {
        InterchangeableChangeLog.set {
            changeSet(author = "user", id = "100") {
                sql {
                    """
                    create table table_a (
                        id int primary key,
                        name varchar(255)
                    )
                    """.trimIndent()
                }
            }
        }
        test("can migrate") {
            subject()
            Database.shouldBeEqualDdl(
                """
                    CREATE TABLE public.table_a (
                        id integer NOT NULL,
                        name character varying(255)
                    );
                    ALTER TABLE public.table_a OWNER TO test;
                    ALTER TABLE ONLY public.table_a
                        ADD CONSTRAINT table_a_pkey PRIMARY KEY (id);
                """.trimIndent()
            )
        }
    }
    context("sqlFile") {
        InterchangeableChangeLog.set {
            changeSet(author = "user", id = "100") {
                sqlFile(
                    path = "ChangeSetSpec/sqlFile.sql",
                    encoding = "Shift_JIS"
                )
            }
        }
        test("can migrate") {
            subject()
            Database.shouldBeEqualDdl(
                """
                   CREATE TABLE public."寿司" (
                        "ｉｄ" integer NOT NULL,
                        "寿司ネタの名前" character varying(255)
                    );
                    ALTER TABLE public."寿司" OWNER TO test;
                    ALTER TABLE ONLY public."寿司"
                        ADD CONSTRAINT "寿司_pkey" PRIMARY KEY ("ｉｄ");
                """.trimIndent()
            )
        }
    }
    context("stop") {
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
                stop("stop")
            }
            changeSet(author = "user", id = "300") {
                // not executed
                createTable(tableName = "company") {
                    column(name = "id", type = "UUID") {
                        constraints(nullable = false, primaryKey = true)
                    }
                    column(name = "name", type = "VARCHAR(256)")
                }
            }
        }
        test("throw error") {
            shouldThrow<IllegalStateException> {
                subject()
            }
            Database.shouldBeEqualDdl(
                """
                    CREATE TABLE public.company (
                        id uuid NOT NULL,
                        name character varying(256)
                    );
                    ALTER TABLE public.company OWNER TO test;
                    ALTER TABLE ONLY public.company
                        ADD CONSTRAINT company_pkey PRIMARY KEY (id);
                """.trimIndent()
            )
        }
    }
    context("tagDatabase") {
        InterchangeableChangeLog.set {
            changeSet(author = "user", id = "100") {
                tagDatabase("example_tag1")
            }
        }
        test("can migrate") {
            subject()
            val db = Database.komapperDb()
            val d = Meta.databasechangelog
            val result = db.runQuery {
                QueryDsl.from(d).single()
            }
            result.tag shouldBe "example_tag1"
        }
    }
})

private fun ChangeSetDsl.createCompanyTable() {
    createTable(tableName = "company") {
        column(name = "id", type = "UUID") {
            constraints(nullable = false, primaryKey = true)
        }
        column(name = "name", type = "VARCHAR(256)")
    }
}
