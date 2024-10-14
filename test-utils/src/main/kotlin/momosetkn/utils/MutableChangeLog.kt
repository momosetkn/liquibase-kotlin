package momosetkn.utils

import momosetkn.liquibase.kotlin.parser.KotlinCompiledDatabaseChangeLog

class MutableChangeLog : KotlinCompiledDatabaseChangeLog({
    mutableDsl.get()(this)
}) {
    companion object {
        val mutableDsl = ThreadLocal<DslBlock>()

        fun set(block: DslBlock) {
            mutableDsl.set(block)
        }
    }
}

private typealias DslBlock = momosetkn.liquibase.kotlin.dsl.ChangeLogDsl.() -> Unit
