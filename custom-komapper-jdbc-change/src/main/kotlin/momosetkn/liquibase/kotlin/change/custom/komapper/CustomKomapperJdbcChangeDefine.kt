package momosetkn.liquibase.kotlin.change.custom.komapper

import liquibase.exception.ValidationErrors

sealed interface CustomTaskChangeDefine

data class CustomTaskChangeDefineImpl(
    val executeBlock: (org.komapper.jdbc.JdbcDatabase) -> Unit,
    val validateBlock: (org.komapper.jdbc.JdbcDatabase) -> ValidationErrors,
    val confirmationMessage: String,
) : CustomTaskChangeDefine

data class CustomRollbackableTaskChangeDefineImpl(
    val executeBlock: (org.komapper.jdbc.JdbcDatabase) -> Unit,
    val validateBlock: (org.komapper.jdbc.JdbcDatabase) -> ValidationErrors,
    val rollbackBlock: (org.komapper.jdbc.JdbcDatabase) -> Unit,
    val confirmationMessage: String,
) : CustomTaskChangeDefine
