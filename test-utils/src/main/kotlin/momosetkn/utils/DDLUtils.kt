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
        val databaseChangeLogTableDdl =
            Regex(
                """
                CREATE CACHED TABLE "PUBLIC"\."DATABASECHANGELOG"\(
                ( {4}.*\n)+\);
                """.trimIndent()
            )
        val databaseChangeLogLockTableDdl =
            Regex(
                """
                CREATE CACHED TABLE "PUBLIC"\."DATABASECHANGELOGLOCK"\(
                ( {4}.*\n)+\);
                ALTER TABLE "PUBLIC"\."DATABASECHANGELOGLOCK" ADD CONSTRAINT "PUBLIC"\."PK_DATABASECHANGELOGLOCK" PRIMARY KEY\("ID"\);
                """.trimIndent()
            )
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
