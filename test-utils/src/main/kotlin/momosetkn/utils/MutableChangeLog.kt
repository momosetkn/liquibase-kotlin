package momosetkn.utils

import momosetkn.liquibase.kotlin.dsl.ChangeLogDsl
import momosetkn.liquibase.kotlin.parser.KotlinCompiledDatabaseChangeLog
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

private typealias Key = Pair<Long, KClass<out MutableDsl>>
private typealias DslBlock = ChangeLogDsl.() -> Unit

class MutableChangeLog : KotlinCompiledDatabaseChangeLog({
    evalDsl()
}) {
    companion object : MutableDsl
}

interface MutableDsl {
    fun set(block: DslBlock) {
        mutableThreadLocalDsls[key()] = block
    }

    fun clear() {
        mutableThreadLocalDsls.remove(key())
    }

    fun ChangeLogDsl.evalDsl() {
        val block = mutableThreadLocalDsls[key()] ?: return
        block()
    }

    private fun key(): Key =
        Pair(Thread.currentThread().id, this::class)

    companion object {
        private val mutableThreadLocalDsls = ConcurrentHashMap<Key, DslBlock>()

        @Synchronized
        fun clear() {
            mutableThreadLocalDsls.clear()
        }
    }
}
