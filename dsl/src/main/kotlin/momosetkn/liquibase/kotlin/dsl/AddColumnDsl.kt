package momosetkn.liquibase.kotlin.dsl

import liquibase.change.AddColumnConfig
import liquibase.changelog.DatabaseChangeLog
import liquibase.statement.DatabaseFunction
import momosetkn.liquibase.kotlin.dsl.Expressions.evalExpressions
import momosetkn.liquibase.kotlin.dsl.Expressions.evalExpressionsOrNull
import momosetkn.liquibase.kotlin.dsl.util.DateUtils.toDate
import java.math.BigInteger
import java.time.temporal.TemporalAccessor

/**
 * Using for AddColumn and CreateIndex DSL.
 */
@ChangeLogDslMarker
class AddColumnDsl(
    private val changeLog: DatabaseChangeLog,
) {
    private val columns = mutableListOf<AddColumnConfig>()

    internal operator fun invoke(
        block: AddColumnDsl.() -> Unit,
    ): List<AddColumnConfig> {
        block(this)
        return columns.toList()
    }

    fun column(
        name: String,
        type: String? = null,
        value: String? = null,
        afterColumn: String? = null,
        autoIncrement: Boolean? = null,
        beforeColumn: String? = null,
        computed: Boolean? = null,
        defaultValue: String? = null,
        defaultValueBoolean: Boolean? = null,
        defaultValueComputed: String? = null,
        defaultValueConstraintName: String? = null,
        defaultValueDate: TemporalAccessor? = null,
        defaultValueNumeric: Number? = null,
        descending: Boolean? = false,
        encoding: String? = null,
        generationType: String? = null,
        incrementBy: Long? = null,
        position: Int? = null,
        remarks: String? = null,
        startWith: Long? = null,
        block: (ConstraintDsl.() -> Unit)? = null,
    ) {
        val column = AddColumnConfig()

        column.name = name.evalExpressions(changeLog)
        column.type = type?.evalExpressions(changeLog)
        column.value = value.evalExpressionsOrNull(changeLog)
        afterColumn?.also {
            column.afterColumn = afterColumn.evalExpressions(changeLog)
        }
        column.isAutoIncrement = autoIncrement
        beforeColumn?.also {
            column.beforeColumn = it.evalExpressions(changeLog)
        }
        column.computed = computed
        column.defaultValue = defaultValue.evalExpressionsOrNull(changeLog)
        column.defaultValueBoolean = defaultValueBoolean
        defaultValueComputed?.also {
            column.defaultValueComputed = DatabaseFunction(it)
        }
        column.defaultValueConstraintName = defaultValueConstraintName.evalExpressionsOrNull(changeLog)
        defaultValueDate?.also {
            column.defaultValueDate = it.toDate()
        }
        column.defaultValueNumeric = defaultValueNumeric
        column.descending = descending
        column.encoding = encoding
        column.generationType = generationType.evalExpressionsOrNull(changeLog)
        incrementBy?.also {
            column.incrementBy = BigInteger.valueOf(it)
        }
        position?.also {
            column.position = position
        }
        column.remarks = remarks.evalExpressionsOrNull(changeLog)
        startWith?.also {
            column.startWith = BigInteger.valueOf(it)
        }

        block?.let {
            val constraintDsl = ConstraintDsl(changeLog)
            column.constraints = constraintDsl(it)
        }
        columns.add(column)
    }
}
