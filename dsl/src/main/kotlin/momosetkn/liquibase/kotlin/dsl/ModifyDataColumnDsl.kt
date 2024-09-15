package momosetkn.liquibase.kotlin.dsl

import liquibase.change.ColumnConfig
import liquibase.change.core.LoadDataColumnConfig
import liquibase.changelog.DatabaseChangeLog
import liquibase.statement.DatabaseFunction
import momosetkn.liquibase.kotlin.dsl.util.DateUtils.toDate
import java.math.BigInteger
import java.time.temporal.TemporalAccessor

@ChangeLogDslMarker
class ModifyDataColumnDsl(
    private val changeLog: DatabaseChangeLog,
) {
    private val columnConfigClass = LoadDataColumnConfig::class

    private val columns = mutableListOf<LoadDataColumnConfig>()
    private var where: String? = null
    private val params = mutableListOf<ColumnConfig>()

    internal operator fun invoke(
        block: ModifyDataColumnDsl.() -> Unit,
    ): ModifyDataColumnDslResult {
        block(this)
        return ModifyDataColumnDslResult(
            columns = columns.toList(),
            where = where,
            params = params,
        )
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
        valueBlobFile: String? = null,
        valueBoolean: Boolean? = null,
        valueClobFile: String? = null,
        valueComputed: String? = null,
        valueDate: TemporalAccessor? = null,
        valueNumeric: Number? = null,
    ) {
        val column = columnConfigClass.java.getDeclaredConstructor().newInstance()

        column.name = name.evalExpressions(changeLog)
        column.type = type.evalExpressionsOrNull(changeLog)
        column.value = value.evalExpressionsOrNull(changeLog)
        column.isAutoIncrement = autoIncrement
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
        column.remarks = remarks.evalExpressionsOrNull(changeLog)
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
        columns.add(column)
    }

    fun where(whereClause: String) {
        val expandedWhereClause = whereClause.evalExpressions(changeLog)
        where = expandedWhereClause
    }

    fun whereParams(block: WhereParamsDsl.() -> Unit) {
        val dsl = WhereParamsDsl(changeLog)
        params.addAll(dsl(block))
    }
}

internal data class ModifyDataColumnDslResult(
    val columns: List<LoadDataColumnConfig>,
    val where: String?,
    val params: List<ColumnConfig>,
)
