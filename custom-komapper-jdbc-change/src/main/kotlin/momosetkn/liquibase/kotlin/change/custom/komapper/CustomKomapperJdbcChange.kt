package momosetkn.liquibase.kotlin.change.custom.komapper

import liquibase.exception.ValidationErrors
import momosetkn.liquibase.kotlin.dsl.ChangeSetDsl

fun ChangeSetDsl.customKomapperJdbcChange(
    confirmationMessage: String = "Executed CustomKomapperChange.",
    rollback: ((org.komapper.jdbc.JdbcDatabase) -> Unit)? = null,
    validate: (org.komapper.jdbc.JdbcDatabase) -> ValidationErrors = { ValidationErrors() },
    execute: (org.komapper.jdbc.JdbcDatabase) -> Unit,
) {
    val change = if (rollback != null) {
        val define = CustomRollbackableTaskChangeDefineImpl(
            execute,
            validate,
            rollback,
            confirmationMessage,
        )
        RollbackTaskCustomKomapperJdbcChange(define)
    } else {
        val define = CustomTaskChangeDefineImpl(
            execute,
            validate,
            confirmationMessage,
        )
        ForwardOnlyTaskCustomKomapperJdbcChange(define)
    }
    addCustomChange(change)
}
