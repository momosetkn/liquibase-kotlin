package momosetkn.liquibase.kotlin.dsl

@ChangeLogDslMarker
class CommentDsl {
    internal var comment: String? = null

    fun comment(value: String) {
        comment =
            if (comment != null) {
                "$comment $value"
            } else {
                value
            }
    }
}
