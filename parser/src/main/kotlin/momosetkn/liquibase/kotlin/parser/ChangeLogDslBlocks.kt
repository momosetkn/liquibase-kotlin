package momosetkn.liquibase.kotlin.parser

import momosetkn.liquibase.kotlin.dsl.ChangeLogDsl

typealias ChangeLogDslBlock = (ChangeLogDsl).() -> Unit
internal object ChangeLogDslBlocks {
    var items = mutableListOf<ChangeLogDslBlock>()
}
