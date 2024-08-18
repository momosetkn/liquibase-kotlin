package momosetkn.liquibase.kotlin.dsl

import liquibase.change.ColumnConfig
import liquibase.change.core.AbstractModifyDataChange
import liquibase.changelog.DatabaseChangeLog

@ChangeLogDslMarker
class WhereParamsDsl(
    private val changeLog: DatabaseChangeLog,
    private val change: AbstractModifyDataChange,
) {
    fun param(
        name: String,
        value: String?,
    ) {
        val columnConfig = ColumnConfig()
        columnConfig.name = name.expandExpressions(changeLog)
        columnConfig.value = value.expandExpressions(changeLog)

        change.addWhereParam(columnConfig)
    }
}
