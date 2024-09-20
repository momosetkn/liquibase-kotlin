package momosetkn.utils

import momosetkn.liquibase.kotlin.parser.KotlinCompiledDatabaseChangeLog

fun InterchangeableChangeLog.Companion.set(
    block: momosetkn.liquibase.kotlin.dsl.ChangeLogDsl.() -> Unit
) {
    mutableDsl = block
}
private var mutableDsl: momosetkn.liquibase.kotlin.dsl.ChangeLogDsl.() -> Unit = {}

class InterchangeableChangeLog : KotlinCompiledDatabaseChangeLog({
    mutableDsl(this)
}) {
    companion object
}
