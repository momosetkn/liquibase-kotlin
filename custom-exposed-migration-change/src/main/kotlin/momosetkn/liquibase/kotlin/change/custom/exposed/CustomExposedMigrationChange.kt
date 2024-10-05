package momosetkn.liquibase.kotlin.change.custom.exposed

import liquibase.exception.ValidationErrors
import momosetkn.liquibase.kotlin.change.custom.core.addCustomChange
import momosetkn.liquibase.kotlin.dsl.ChangeSetDsl

fun ChangeSetDsl.customExposedMigrationChange(
    confirmationMessage: String = "Executed CustomExposedMigrationChange.",
    rollback: ((org.jetbrains.exposed.sql.Database) -> Unit)? = null,
    validate: (org.jetbrains.exposed.sql.Database) -> ValidationErrors = { ValidationErrors() },
    execute: (org.jetbrains.exposed.sql.Database) -> Unit,
) {
    val change = if (rollback != null) {
        val define = CustomRollbackableTaskChangeDefineImpl(
            execute,
            validate,
            rollback,
            confirmationMessage,
        )
        RollbackTaskCustomExposedMigrationChange(define)
    } else {
        val define = CustomTaskChangeDefineImpl(
            execute,
            validate,
            confirmationMessage,
        )
        ForwardOnlyTaskCustomExposedMigrationChange(define)
    }
    addCustomChange(change)
}
