package momosetkn.liquibase.kotlin.change.custom.exposed

import liquibase.exception.ValidationErrors
import momosetkn.liquibase.kotlin.change.custom.core.CustomRollbackableTaskChangeDefineImpl
import momosetkn.liquibase.kotlin.change.custom.core.CustomTaskChangeDefineImpl
import momosetkn.liquibase.kotlin.change.custom.core.ForwardOnlyTaskCustomChange
import momosetkn.liquibase.kotlin.change.custom.core.RollbackTaskCustomChange
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
            executeBlock = execute,
            validateBlock = validate,
            rollbackBlock = rollback,
            confirmationMessage = confirmationMessage,
            transformDatabase = { it.toExposedMigrationDatabase() },
        )
        RollbackTaskCustomChange(define)
    } else {
        val define = CustomTaskChangeDefineImpl(
            executeBlock = execute,
            validateBlock = validate,
            confirmationMessage = confirmationMessage,
            transformDatabase = { it.toExposedMigrationDatabase() },
        )
        ForwardOnlyTaskCustomChange(define)
    }
    addCustomChange(change)
}
