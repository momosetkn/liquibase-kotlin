package momosetkn.liquibase.kotlin.dsl

import liquibase.changelog.DatabaseChangeLog

@Suppress("UNCHECKED_CAST")
internal fun <E : Any> E.tryEvalExpressions(changeLog: DatabaseChangeLog): E {
    return when (this) {
        is String -> checkNotNull(this.evalExpressions(changeLog))
        else -> this
    } as E
}

internal fun <E : Any> E?.tryEvalExpressionsOrNull(changeLog: DatabaseChangeLog): E? {
    return this?.tryEvalExpressions(changeLog)
}

internal fun String.evalExpressions(changeLog: DatabaseChangeLog): String {
    return changeLog.changeLogParameters
        ?.expandExpressions(
            this,
            changeLog,
        )
        ?: this
}

internal fun String?.evalExpressionsOrNull(changeLog: DatabaseChangeLog): String? {
    return this?.evalExpressions(changeLog)
}
