package momosetkn.liquibase.kotlin.change.custom.exposed

import liquibase.exception.ValidationErrors

sealed interface CustomTaskChangeDefine

data class CustomTaskChangeDefineImpl(
    val executeBlock: (org.jetbrains.exposed.sql.Database) -> Unit,
    val validateBlock: (org.jetbrains.exposed.sql.Database) -> ValidationErrors,
    val confirmationMessage: String,
) : CustomTaskChangeDefine

data class CustomRollbackableTaskChangeDefineImpl(
    val executeBlock: (org.jetbrains.exposed.sql.Database) -> Unit,
    val validateBlock: (org.jetbrains.exposed.sql.Database) -> ValidationErrors,
    val rollbackBlock: (org.jetbrains.exposed.sql.Database) -> Unit,
    val confirmationMessage: String,
) : CustomTaskChangeDefine
