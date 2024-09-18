package momosetkn.liquibase.kotlin.dsl

import liquibase.changelog.DatabaseChangeLog
import momosetkn.liquibase.kotlin.dsl.Expressions.evalExpressions

@ChangeLogDslMarker
class ArgumentDsl(
    private val changeLog: DatabaseChangeLog,
) {
    private val args = mutableListOf<Any>()

    internal operator fun invoke(
        block: ArgumentDsl.() -> Unit,
    ): List<Any> {
        block(this)
        return args.toList()
    }

    fun arg(value: String) {
        args.add(value.evalExpressions(changeLog))
    }
}
