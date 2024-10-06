package momosetkn.liquibase.kotlin.change.custom.ktorm

import liquibase.exception.ValidationErrors
import momosetkn.liquibase.kotlin.change.custom.core.CustomRollbackableTaskChangeDefineImpl
import momosetkn.liquibase.kotlin.change.custom.core.CustomTaskChangeDefineImpl
import momosetkn.liquibase.kotlin.change.custom.core.ForwardOnlyTaskCustomChange
import momosetkn.liquibase.kotlin.change.custom.core.RollbackTaskCustomChange
import momosetkn.liquibase.kotlin.change.custom.core.addCustomChange
import momosetkn.liquibase.kotlin.change.custom.komapper.toJavaxSqlDataSource
import momosetkn.liquibase.kotlin.dsl.ChangeSetDsl

fun ChangeSetDsl.customKtormChange(
    confirmationMessage: String = "Executed CustomKtormChange.",
    rollback: ((org.ktorm.database.Database) -> Unit)? = null,
    validate: (org.ktorm.database.Database) -> ValidationErrors = { ValidationErrors() },
    execute: (org.ktorm.database.Database) -> Unit,
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

private fun transformDatabase(liquibaseDatabase: liquibase.database.Database): org.ktorm.database.Database {
    val datasource = liquibaseDatabase.toJavaxSqlDataSource()
    val database = LiquibaseKtormConfig.provideDatabase(datasource, liquibaseDatabase.shortName)
    return database
}
