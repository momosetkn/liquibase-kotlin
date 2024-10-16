package momosetkn.liquibase.kotlin.change.custom.exposed

import liquibase.exception.ValidationErrors
import momosetkn.liquibase.kotlin.change.custom.core.CustomRollbackableTaskChangeDefineImpl
import momosetkn.liquibase.kotlin.change.custom.core.CustomTaskChangeDefineImpl
import momosetkn.liquibase.kotlin.change.custom.core.ForwardOnlyTaskCustomChange
import momosetkn.liquibase.kotlin.change.custom.core.RollbackTaskCustomChange
import momosetkn.liquibase.kotlin.change.custom.core.addCustomChange
import momosetkn.liquibase.kotlin.change.custom.core.toJavaxSqlDataSource
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

private fun transformDatabase(liquibaseDatabase: liquibase.database.Database): org.jetbrains.exposed.sql.Database {
    val datasource = liquibaseDatabase.toJavaxSqlDataSource()
    val database = LiquibaseExposedMigrationConfig.provideDatabase(datasource, liquibaseDatabase.shortName)
    return database
}
