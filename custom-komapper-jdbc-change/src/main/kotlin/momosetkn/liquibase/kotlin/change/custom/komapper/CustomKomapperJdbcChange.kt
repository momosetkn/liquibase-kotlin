package momosetkn.liquibase.kotlin.change.custom.komapper

import liquibase.exception.ValidationErrors
import momosetkn.liquibase.kotlin.change.custom.core.CustomRollbackableTaskChangeDefineImpl
import momosetkn.liquibase.kotlin.change.custom.core.CustomTaskChangeDefineImpl
import momosetkn.liquibase.kotlin.change.custom.core.ForwardOnlyTaskCustomChange
import momosetkn.liquibase.kotlin.change.custom.core.RollbackTaskCustomChange
import momosetkn.liquibase.kotlin.change.custom.core.addCustomChange
import momosetkn.liquibase.kotlin.dsl.ChangeSetDsl

fun ChangeSetDsl.customKomapperJdbcChange(
    confirmationMessage: String = "Executed CustomKomapperChange.",
    rollback: ((org.komapper.jdbc.JdbcDatabase) -> Unit)? = null,
    validate: (org.komapper.jdbc.JdbcDatabase) -> ValidationErrors = { ValidationErrors() },
    execute: (org.komapper.jdbc.JdbcDatabase) -> Unit,
) {
    val change = if (rollback != null) {
        val define = CustomRollbackableTaskChangeDefineImpl(
            executeBlock = execute,
            validateBlock = validate,
            rollbackBlock = rollback,
            confirmationMessage = confirmationMessage,
            transformDatabase = { it.toKomapperJdbcDatabase() },
        )
        RollbackTaskCustomChange(define)
    } else {
        val define = CustomTaskChangeDefineImpl(
            executeBlock = execute,
            validateBlock = validate,
            confirmationMessage = confirmationMessage,
            transformDatabase = { it.toKomapperJdbcDatabase() },
        )
        ForwardOnlyTaskCustomChange(define)
    }
    addCustomChange(change)
}
