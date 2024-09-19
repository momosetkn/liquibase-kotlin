package momosetkn.liquibase.kotlin.dsl

import liquibase.changelog.DatabaseChangeLog
import momosetkn.liquibase.kotlin.dsl.Expressions.evalExpressions

@ChangeLogDslMarker
class SqlBlockDsl(
    private val changeLog: DatabaseChangeLog,
) {
    private var comment: String? = null

    internal operator fun invoke(
        block: SqlBlockDsl.() -> String,
    ): SqlBlockDslResult {
        val sql = block(this)
        return SqlBlockDslResult(
            comment = comment,
            sql = sql,
        )
    }

    fun comment(value: String) {
        val evaluatedValue = value.evalExpressions(changeLog)
        comment = comment?.let {
            "$it $evaluatedValue"
        } ?: evaluatedValue
    }
}

data class SqlBlockDslResult(
    val comment: String?,
    val sql: String,
)
