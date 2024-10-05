package momosetkn.liquibase.kotlin.change.custom.jooq

import liquibase.exception.ValidationErrors
import momosetkn.liquibase.kotlin.change.custom.komapper.addCustomChange
import momosetkn.liquibase.kotlin.dsl.ChangeSetDsl

fun ChangeSetDsl.customJooqChange(
    confirmationMessage: String = "Executed CustomJooqChange.",
    rollback: ((org.jooq.DSLContext) -> Unit)? = null,
    validate: (org.jooq.DSLContext) -> ValidationErrors = { ValidationErrors() },
    execute: (org.jooq.DSLContext) -> Unit,
) {
    val change = if (rollback != null) {
        val define = CustomRollbackableTaskChangeDefineImpl(
            execute,
            validate,
            rollback,
            confirmationMessage,
        )
        RollbackTaskCustomJooqChange(define)
    } else {
        val define = CustomTaskChangeDefineImpl(
            execute,
            validate,
            confirmationMessage,
        )
        ForwardOnlyTaskCustomJooqChange(define)
    }
    addCustomChange(change)
}
