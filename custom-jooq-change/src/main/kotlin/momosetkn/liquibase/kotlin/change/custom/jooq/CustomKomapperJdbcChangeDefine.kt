package momosetkn.liquibase.kotlin.change.custom.jooq

import liquibase.exception.ValidationErrors

sealed interface CustomTaskChangeDefine

data class CustomTaskChangeDefineImpl(
    val executeBlock: (org.jooq.DSLContext) -> Unit,
    val validateBlock: (org.jooq.DSLContext) -> ValidationErrors,
    val confirmationMessage: String,
) : CustomTaskChangeDefine

data class CustomRollbackableTaskChangeDefineImpl(
    val executeBlock: (org.jooq.DSLContext) -> Unit,
    val validateBlock: (org.jooq.DSLContext) -> ValidationErrors,
    val rollbackBlock: (org.jooq.DSLContext) -> Unit,
    val confirmationMessage: String,
) : CustomTaskChangeDefine
