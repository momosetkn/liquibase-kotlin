package momosetkn.liquibase.kotlin.change

import liquibase.change.custom.CustomChange
import liquibase.change.custom.CustomChangeWrapper
import liquibase.change.custom.setCustomChange
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
        RollbackTaskCustomChange(define)
    } else {
        val define = CustomTaskChangeDefineImpl(
            execute,
            validate,
            confirmationMessage,
        )
        ForwardOnlyTaskCustomChange(define)
    }
    addChange(change)
}

private fun ChangeSetDsl.addChange(change: CustomChange) {
    val customChangeWrapper = this.changeSetSupport.createChange("customChange") as CustomChangeWrapper
    customChangeWrapper.setCustomChange(change)
    changeSetSupport.addChange(customChangeWrapper)
}
