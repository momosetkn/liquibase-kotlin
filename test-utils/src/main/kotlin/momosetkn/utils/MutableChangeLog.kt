package momosetkn.utils

import momosetkn.liquibase.kotlin.dsl.ChangeLogDsl
import momosetkn.liquibase.kotlin.parser.KotlinCompiledDatabaseChangeLog
import momosetkn.utils.children.MutableChangeLog1
import momosetkn.utils.children.MutableChangeLog2
import momosetkn.utils.children.MutableChangeLog3
import momosetkn.utils.children.MutableChangeLog4
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

private typealias Key = Pair<Long, KClass<out MutableDsl>>
typealias ChangeLogDslBlock = ChangeLogDsl.() -> Unit
typealias ChangeLogBuilderDslBlock = MutableChangeLogBuilderDsl.() -> Unit

class MutableChangeLog : KotlinCompiledDatabaseChangeLog({
    evalDsl()
}) {
    companion object : MutableDsl
}

interface MutableDsl {
    fun set(block: ChangeLogDslBlock) {
        mutableThreadLocalDsls[key()] = block
    }

    fun get(): ChangeLogDslBlock? {
        return mutableThreadLocalDsls[key()]
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
        private val mutableThreadLocalDsls = ConcurrentHashMap<Key, ChangeLogDslBlock>()
    }
}

class MutableChangeLogBuilderDsl {
    internal var changeLogDslBlock: ChangeLogDslBlock? = null
    fun changeLog(block: ChangeLogDslBlock): ChangeLogDslBlock {
        this.changeLogDslBlock = block
        return block
    }

    internal var changeLog1DslBlock: ChangeLogDslBlock? = null
    fun changeLog1(block: ChangeLogDslBlock) {
        this.changeLog1DslBlock = block
    }

    internal var changeLog2DslBlock: ChangeLogDslBlock? = null
    fun changeLog2(block: ChangeLogDslBlock) {
        this.changeLog1DslBlock = block
    }

    internal var changeLog3DslBlock: ChangeLogDslBlock? = null
    fun changeLog3(block: ChangeLogDslBlock) {
        this.changeLog3DslBlock = block
    }

    internal var changeLog4DslBlock: ChangeLogDslBlock? = null
    fun changeLog4(block: ChangeLogDslBlock) {
        this.changeLog4DslBlock = block
    }

    fun set() {
        val immutableChangeLogDslBlock = changeLogDslBlock
        val immutableChangeLog1dslBlock = changeLog1DslBlock
        val immutableChangeLog2dslBlock = changeLog2DslBlock
        val immutableChangeLog3dslBlock = changeLog3DslBlock
        val immutableChangeLog4dslBlock = changeLog4DslBlock

        if (immutableChangeLogDslBlock != null) {
            MutableChangeLog.set(immutableChangeLogDslBlock)
        } else {
            MutableChangeLog.clear()
        }
        if (immutableChangeLog1dslBlock != null) {
            MutableChangeLog1.set(immutableChangeLog1dslBlock)
        } else {
            MutableChangeLog1.clear()
        }
        if (immutableChangeLog2dslBlock != null) {
            MutableChangeLog2.set(immutableChangeLog2dslBlock)
        } else {
            MutableChangeLog2.clear()
        }
        if (immutableChangeLog3dslBlock != null) {
            MutableChangeLog3.set(immutableChangeLog3dslBlock)
        } else {
            MutableChangeLog3.clear()
        }
        if (immutableChangeLog4dslBlock != null) {
            MutableChangeLog4.set(immutableChangeLog4dslBlock)
        } else {
            MutableChangeLog4.clear()
        }
    }
}

fun changeLogsDsl(block: ChangeLogBuilderDslBlock): MutableChangeLogBuilderDsl {
    val dslDsl = MutableChangeLogBuilderDsl()
    block(dslDsl)
    return dslDsl
}

fun changeLogDsl(block: ChangeLogDslBlock) = block
