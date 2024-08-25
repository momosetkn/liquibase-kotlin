package momosetkn.liquibase.kotlin.dsl

import liquibase.change.ColumnConfig
import liquibase.change.core.LoadDataColumnConfig
import liquibase.changelog.DatabaseChangeLog
import liquibase.statement.DatabaseFunction
import liquibase.util.ISODateFormat
import java.math.BigInteger

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
        defaultValueDate: String? = null,
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
        valueDate: String? = null,
        valueNumeric: Number? = null,
    ) {
        val column = columnConfigClass.java.getDeclaredConstructor().newInstance()

        column.name = name
        column.type = type
        column.value = value
        column.isAutoIncrement = autoIncrement
        column.computed = computed
        column.defaultValue = defaultValue
        column.defaultValueBoolean = defaultValueBoolean
        defaultValueComputed?.also {
            column.defaultValueComputed = DatabaseFunction(it)
        }
        column.defaultValueConstraintName = defaultValueConstraintName
        defaultValueDate?.also {
            column.defaultValueDate = ISODateFormat().parse(it)
        }
        column.defaultValueNumeric = defaultValueNumeric
        column.descending = descending
        column.encoding = encoding
        column.generationType = generationType
        incrementBy?.also {
            column.incrementBy = BigInteger.valueOf(it)
        }
        column.remarks = remarks
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
            column.valueDate = ISODateFormat().parse(it)
        }
        column.valueNumeric = valueNumeric
        columns.add(column)
    }

    fun where(whereClause: String) {
        val expandedWhereClause = whereClause.expandExpressions(changeLog)
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
