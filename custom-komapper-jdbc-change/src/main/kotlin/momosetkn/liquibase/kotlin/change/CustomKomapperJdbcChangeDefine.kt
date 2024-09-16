package momosetkn.liquibase.kotlin.change

import liquibase.exception.ValidationErrors

sealed interface CustomTaskChangeDefine

data class CustomTaskChangeDefineImpl(
    val executeBlock: KomapperJdbcContext.() -> Unit,
    val validateBlock: KomapperJdbcContext.() -> ValidationErrors,
    val confirmationMessage: String,
) : CustomTaskChangeDefine

data class CustomRollbackableTaskChangeDefineImpl(
    val executeBlock: KomapperJdbcContext.() -> Unit,
    val validateBlock: KomapperJdbcContext.() -> ValidationErrors,
    val rollbackBlock: KomapperJdbcContext.() -> Unit,
    val confirmationMessage: String,
) : CustomTaskChangeDefine
