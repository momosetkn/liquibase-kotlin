import momosetkn.liquibase.kotlin.dsl.ChangeLogDsl
import momosetkn.liquibase.kotlin.parser.ChangeLogDslBlocks

/**
 * Kotlin script exterior.
 * For convenience, root scope.
 * same to [momosetkn.liquibase.kotlin.dsl.ChangeLogBuilderDsl.databaseChangeLog]
 */
fun databaseChangeLog(
    block: (ChangeLogDsl).() -> Unit,
) {
    ChangeLogDslBlocks.items.add(block)
}
