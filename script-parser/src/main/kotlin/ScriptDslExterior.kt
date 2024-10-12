import momosetkn.liquibase.kotlin.dsl.ChangeLogDsl

/**
 * Kotlin script exterior.
 * For convenience, root scope.
 * It exists only for the purpose of autocompletion in the IDE. therefore method-body is empty.
 * same to [momosetkn.liquibase.kotlin.dsl.ChangeLogBuilderDsl.databaseChangeLog]
 */
fun databaseChangeLog(
    block: (ChangeLogDsl).() -> Unit,
) {}
