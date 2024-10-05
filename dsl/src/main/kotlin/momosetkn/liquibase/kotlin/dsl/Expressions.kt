package momosetkn.liquibase.kotlin.dsl

import liquibase.changelog.DatabaseChangeLog
import kotlin.reflect.KClass

/**
 * Expressions is for liquibase input String. therefore this function return is shouldBe String.
 */
internal object Expressions {
    @Suppress("UNCHECKED_CAST")
    fun <E : Any> E.tryEvalExpressions(changeLog: DatabaseChangeLog): E {
        return when (this) {
            is String -> this.evalExpressions(changeLog) as E
            else -> this
        }
    }

    // Any is because it is for DSL.
    fun Any.evalClassNameExpressions(changeLog: DatabaseChangeLog): String {
        return when (this) {
            is KClass<*> -> checkNotNull(this.qualifiedName)
            is Class<*> -> checkNotNull(this.canonicalName)
            is String -> this
            else -> error("unsupported type: $this")
        }.evalExpressions(changeLog)
    }

    fun String.evalExpressions(changeLog: DatabaseChangeLog): String {
        return changeLog.changeLogParameters
            ?.expandExpressions(
                this,
                changeLog,
            )
            ?: this
    }
}
