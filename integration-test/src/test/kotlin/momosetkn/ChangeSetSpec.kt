package momosetkn

import io.kotest.core.spec.style.FunSpec
import momosetkn.liquibase.client.LiquibaseClient
import momosetkn.utils.DDLUtils.shouldBeEqualDdl
import momosetkn.utils.Database
import momosetkn.utils.InterchangeableChangeLog
import momosetkn.utils.set

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
})
