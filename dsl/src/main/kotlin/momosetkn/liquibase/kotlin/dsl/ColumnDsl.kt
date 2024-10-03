package momosetkn.liquibase.kotlin.dsl

import liquibase.change.ColumnConfig
import liquibase.changelog.DatabaseChangeLog
import liquibase.statement.DatabaseFunction
import momosetkn.liquibase.kotlin.dsl.Expressions.evalExpressions
import momosetkn.liquibase.kotlin.dsl.util.DateUtils.toDate
import java.math.BigInteger
import java.time.temporal.TemporalAccessor

@ChangeLogDslMarker
class ColumnDsl(
    private val changeLog: DatabaseChangeLog,
) {
    private val columns = mutableListOf<ColumnConfig>()

    internal operator fun invoke(
        block: ColumnDsl.() -> Unit,
    ): List<ColumnConfig> {
        block(this)
        return columns.toList()
    }

    fun column(
        name: String,
        type: String? = null,
        value: String? = null,
        autoIncrement: Boolean? = null,
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
        remarks: String? = null,
        startWith: Long? = null,
        valueBlobFile: String? = null, // maybe for update or delete
        valueBoolean: Boolean? = null,
        valueClobFile: String? = null,
        valueComputed: String? = null,
        valueDate: TemporalAccessor? = null,
        valueNumeric: Number? = null,
        block: (ConstraintDsl.() -> Unit)? = null,
    ) {
        val column = ColumnConfig()

        column.name = name.evalExpressions(changeLog)
        column.type = type?.evalExpressions(changeLog)
        column.value = value?.evalExpressions(changeLog)
        column.isAutoIncrement = autoIncrement
        column.computed = computed
        column.defaultValue = defaultValue?.evalExpressions(changeLog)
        column.defaultValueBoolean = defaultValueBoolean
        defaultValueComputed?.also {
            column.defaultValueComputed = DatabaseFunction(it)
        }
        column.defaultValueConstraintName = defaultValueConstraintName?.evalExpressions(changeLog)
        defaultValueDate?.also {
            column.defaultValueDate = it.toDate()
        }
        column.defaultValueNumeric = defaultValueNumeric
        column.descending = descending
        column.encoding = encoding
        column.generationType = generationType?.evalExpressions(changeLog)
        incrementBy?.also {
            column.incrementBy = BigInteger.valueOf(it)
        }
        column.remarks = remarks?.evalExpressions(changeLog)
        startWith?.also {
            column.startWith = BigInteger.valueOf(it)
        }
        column.valueBlobFile = valueBlobFile
        column.valueBoolean = valueBoolean
        column.valueClobFile = valueClobFile
        valueComputed?.also {
            column.valueComputed = DatabaseFunction(it)
        }
        valueDate?.also {
            column.valueDate = it.toDate()
        }
        column.valueNumeric = valueNumeric

        block?.let {
            val constraintDsl = ConstraintDsl(changeLog)
            column.constraints = constraintDsl(it)
        }
        columns.add(column)
    }
}
