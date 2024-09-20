package momosetkn.utils

import io.kotest.matchers.shouldBe
import org.intellij.lang.annotations.Language

object DDLUtils {
    fun String.omitComment(): String {
        val commentRegex = Regex("""^--.*$""", RegexOption.MULTILINE)
        return this.replace(commentRegex, "")
    }
    fun String.omitLiquibaseTable(): String {
        return this.replace(
            """
            --
            -- Name: databasechangelog; Type: TABLE; Schema: public; Owner: test
            --

            CREATE TABLE public.databasechangelog (
                id character varying(255) NOT NULL,
                author character varying(255) NOT NULL,
                filename character varying(255) NOT NULL,
                dateexecuted timestamp without time zone NOT NULL,
                orderexecuted integer NOT NULL,
                exectype character varying(10) NOT NULL,
                md5sum character varying(35),
                description character varying(255),
                comments character varying(255),
                tag character varying(255),
                liquibase character varying(20),
                contexts character varying(255),
                labels character varying(255),
                deployment_id character varying(10)
            );


            ALTER TABLE public.databasechangelog OWNER TO test;

            --
            -- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: test
            --

            CREATE TABLE public.databasechangeloglock (
                id integer NOT NULL,
                locked boolean NOT NULL,
                lockgranted timestamp without time zone,
                lockedby character varying(255)
            );


            ALTER TABLE public.databasechangeloglock OWNER TO test;
            """.trimIndent(),
            "",
        ).replace(
            """
            ALTER TABLE ONLY public.databasechangeloglock
                ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);
            """.trimIndent(),
            ""
        )
    }
    fun String.omitVariable(): String {
        return this.replace(
            """
                SET statement_timeout = 0;
                SET lock_timeout = 0;
                SET idle_in_transaction_session_timeout = 0;
                SET client_encoding = 'UTF8';
                SET standard_conforming_strings = on;
                SELECT pg_catalog.set_config('search_path', '', false);
                SET check_function_bodies = false;
                SET xmloption = content;
                SET client_min_messages = warning;
                SET row_security = off;

                SET default_tablespace = '';

                SET default_table_access_method = heap;
            """.trimIndent(),
            "",
        )
    }
    fun String.normalize(): String {
        val newlineRegex = Regex("""\n+""")
        return this.replace(newlineRegex, "\n").trim()
    }

    fun String.toMainDdl(): String {
        return this.omitLiquibaseTable().omitVariable().omitComment().normalize()
    }

    infix fun Database.shouldBeEqualDdl(
        @Language("sql") s: String
    ) {
        val ddl = this.generateDdl()!!.toMainDdl()
        ddl shouldBe s
    }
}
