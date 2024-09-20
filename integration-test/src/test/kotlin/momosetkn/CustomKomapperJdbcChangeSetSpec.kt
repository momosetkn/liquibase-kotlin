package momosetkn

import io.kotest.core.spec.style.FunSpec
import momosetkn.liquibase.client.LiquibaseClient
import momosetkn.liquibase.kotlin.change.customKomapperJdbcChange
import momosetkn.utils.DDLUtils.shouldBeEqualDdl
import momosetkn.utils.Database
import momosetkn.utils.InterchangeableChangeLog
import momosetkn.utils.set
import org.komapper.core.dsl.QueryDsl

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

    context("customKomapperJdbcChange") {
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
        }
    }
})
