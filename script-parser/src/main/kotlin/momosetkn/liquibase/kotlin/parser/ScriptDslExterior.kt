package momosetkn.liquibase.kotlin.parser

import momosetkn.liquibase.kotlin.dsl.ChangeLogDsl

/**
 * Kotlin script exterior.
 * same to [momosetkn.liquibase.kotlin.dsl.ChangeLogBuilderDsl.databaseChangeLog]
 */
fun databaseChangeLog(
    block: (ChangeLogDsl).() -> Unit,
) {
    ChangeLogDslBlocks.items.add(block)
}
