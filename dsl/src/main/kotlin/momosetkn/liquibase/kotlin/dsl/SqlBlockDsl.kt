package momosetkn.liquibase.kotlin.dsl

@ChangeLogDslMarker
class SqlBlockDsl {
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
        comment = comment?.let {
            "$it $value"
        } ?: value
    }
}

data class SqlBlockDslResult(
    val comment: String?,
    val sql: String,
)
