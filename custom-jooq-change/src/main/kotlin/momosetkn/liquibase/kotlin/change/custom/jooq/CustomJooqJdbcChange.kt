package momosetkn.liquibase.kotlin.change.custom.jooq

import liquibase.exception.ValidationErrors
import momosetkn.liquibase.kotlin.change.custom.core.CustomRollbackableTaskChangeDefineImpl
import momosetkn.liquibase.kotlin.change.custom.core.CustomTaskChangeDefineImpl
import momosetkn.liquibase.kotlin.change.custom.core.ForwardOnlyTaskCustomChange
import momosetkn.liquibase.kotlin.change.custom.core.RollbackTaskCustomChange
import momosetkn.liquibase.kotlin.change.custom.core.addCustomChange
import momosetkn.liquibase.kotlin.change.custom.komapper.toJavaxSqlDataSource
import momosetkn.liquibase.kotlin.dsl.ChangeSetDsl

fun ChangeSetDsl.customJooqChange(
    confirmationMessage: String = "Executed CustomJooqChange.",
    rollback: ((org.jooq.DSLContext) -> Unit)? = null,
    validate: (org.jooq.DSLContext) -> ValidationErrors = { ValidationErrors() },
    execute: (org.jooq.DSLContext) -> Unit,
) {
    val change = if (rollback != null) {
        val define = CustomRollbackableTaskChangeDefineImpl(
            executeBlock = execute,
            validateBlock = validate,
            rollbackBlock = rollback,
            confirmationMessage = confirmationMessage,
            transformDatabase = ::transformDatabase,
        )
        RollbackTaskCustomChange(define)
    } else {
        val define = CustomTaskChangeDefineImpl(
            executeBlock = execute,
            validateBlock = validate,
            confirmationMessage = confirmationMessage,
            transformDatabase = ::transformDatabase,
        )
        ForwardOnlyTaskCustomChange(define)
    }
    addCustomChange(change)
}

private fun transformDatabase(liquibaseDatabase: liquibase.database.Database): org.jooq.DSLContext {
    val datasource = liquibaseDatabase.toJavaxSqlDataSource()
    val database = LiquibaseJooqConfig.provideDSLContext(datasource, liquibaseDatabase.shortName)
    return database
}
