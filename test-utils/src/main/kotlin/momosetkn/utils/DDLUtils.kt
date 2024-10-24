package momosetkn.utils

import momosetkn.utils.StringUtils.toLf
import org.intellij.lang.annotations.Language

object DDLUtils {
    fun String.omitComment(): String {
        val commentRegex = Regex("""^--.*$""", RegexOption.MULTILINE)
        return this.replace(commentRegex, "")
    }
    fun String.normalize(): String {
        val newlineRegex = Regex("""\n+""")
        return this.replace(newlineRegex, "\n").trim()
    }
    fun String.bulkExclude(
        vararg list: Any,
    ): String {
        return list.fold(this) { acc, cur ->
            when (cur) {
                is String -> acc.replace(cur, "")
                is Regex -> acc.replace(cur, "")
                else -> acc
            }
        }
    }

    fun String.toMainDdl(): String {
        val databaseChangeLogTableDdl = """
            CREATE CACHED TABLE "PUBLIC"."DATABASECHANGELOG"(
                "ID" CHARACTER VARYING(255) NOT NULL,
                "AUTHOR" CHARACTER VARYING(255) NOT NULL,
                "FILENAME" CHARACTER VARYING(255) NOT NULL,
                "DATEEXECUTED" TIMESTAMP NOT NULL,
                "ORDEREXECUTED" INTEGER NOT NULL,
                "EXECTYPE" CHARACTER VARYING(10) NOT NULL,
                "MD5SUM" CHARACTER VARYING(35),
                "DESCRIPTION" CHARACTER VARYING(255),
                "COMMENTS" CHARACTER VARYING(255),
                "TAG" CHARACTER VARYING(255),
                "LIQUIBASE" CHARACTER VARYING(20),
                "CONTEXTS" CHARACTER VARYING(255),
                "LABELS" CHARACTER VARYING(255),
                "DEPLOYMENT_ID" CHARACTER VARYING(36)
            );
        """.trimIndent()
        val databaseChangeLogLockTableDdl = """
            CREATE CACHED TABLE "PUBLIC"."DATABASECHANGELOGLOCK"(
                "ID" INTEGER NOT NULL,
                "LOCKED" BOOLEAN NOT NULL,
                "LOCKGRANTED" TIMESTAMP,
                "LOCKEDBY" CHARACTER VARYING(255)
            );
            ALTER TABLE "PUBLIC"."DATABASECHANGELOGLOCK" ADD CONSTRAINT "PUBLIC"."PK_DATABASECHANGELOGLOCK" PRIMARY KEY("ID");
        """.trimIndent()
        return this
            .toLf()
            .bulkExclude(
                "SET DB_CLOSE_DELAY -1;",
                Regex("^CREATE USER IF NOT EXISTS.*$", RegexOption.MULTILINE),
                databaseChangeLogTableDdl,
                databaseChangeLogLockTableDdl
            ).omitComment().normalize()
    }

    fun sql(
        @Language("sql") sql: String
    ) = sql
}
