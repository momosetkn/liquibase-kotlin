package momosetkn.liquibase.kotlin.dsl

import liquibase.changelog.DatabaseChangeLog

internal fun Any?.expandExpressions(changeLog: DatabaseChangeLog): String? {
    if (this == null) {
        return null
    }
    return changeLog.changeLogParameters
        ?.expandExpressions(
            this.toString(),
            changeLog,
        )
        ?: this.toString()
}
