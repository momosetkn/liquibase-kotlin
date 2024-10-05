package momosetkn.liquibase.kotlin.change.custom.core

import liquibase.database.Database
import liquibase.exception.ValidationErrors

sealed interface CustomTaskChangeDefine<T : Any> {
    val transformDatabase: (database: Database) -> T
}

data class CustomTaskChangeDefineImpl<T : Any>(
    val executeBlock: (T) -> Unit,
    val validateBlock: (T) -> ValidationErrors,
    val confirmationMessage: String,
    override val transformDatabase: (database: Database) -> T,
) : CustomTaskChangeDefine<T>

data class CustomRollbackableTaskChangeDefineImpl<T : Any>(
    val executeBlock: (T) -> Unit,
    val validateBlock: (T) -> ValidationErrors,
    val rollbackBlock: (T) -> Unit,
    val confirmationMessage: String,
    override val transformDatabase: (database: Database) -> T,
) : CustomTaskChangeDefine<T>
