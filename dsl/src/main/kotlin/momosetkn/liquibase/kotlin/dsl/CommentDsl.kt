package momosetkn.liquibase.kotlin.dsl

@ChangeLogDslMarker
class CommentDsl {
    internal var comment: String? = null

    fun comment(value: String) {
        comment = comment?.let {
            "$it $value"
        } ?: value
    }
}
