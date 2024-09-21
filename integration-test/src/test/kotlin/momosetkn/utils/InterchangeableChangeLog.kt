package momosetkn.utils

import momosetkn.liquibase.kotlin.parser.KotlinCompiledDatabaseChangeLog

class InterchangeableChangeLog : KotlinCompiledDatabaseChangeLog({
    mutableDsl(this)
}) {
    companion object
}

private var mutableDsl: momosetkn.liquibase.kotlin.dsl.ChangeLogDsl.() -> Unit = {}

fun InterchangeableChangeLog.Companion.set(
    block: momosetkn.liquibase.kotlin.dsl.ChangeLogDsl.() -> Unit
) {
    mutableDsl = block
}
