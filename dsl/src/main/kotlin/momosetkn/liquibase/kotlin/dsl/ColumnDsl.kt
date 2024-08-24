package momosetkn.liquibase.kotlin.dsl

import liquibase.change.AddColumnConfig
import liquibase.change.Change
import liquibase.change.ColumnConfig
import liquibase.change.core.AbstractModifyDataChange
import liquibase.change.core.LoadDataColumnConfig
import liquibase.changelog.DatabaseChangeLog
import liquibase.exception.ChangeLogParseException
import liquibase.statement.DatabaseFunction
import liquibase.util.ISODateFormat
import java.math.BigInteger
import kotlin.reflect.KClass

@ChangeLogDslMarker
class IColumnDsl<out COLUMN_CONFIG : ColumnConfig>(
    private val changeLog: DatabaseChangeLog,
    private val columnConfigClass: KClass<COLUMN_CONFIG>,
    private val changeSetId: String = "<unknown>",
    private val changeName: String = "<unknown>",
    private val change: Change,
) {
    internal val _columns = mutableListOf<ColumnConfig>()
    internal val columns : List<COLUMN_CONFIG>
        get() = _columns.toList() as List<COLUMN_CONFIG>

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
        defaultValueDate: String? = null,
        defaultValueNumeric: Number? = null,
        descending: Boolean? = false,
        encoding: String? = null,
        generationType: String? = null,
        incrementBy: Long? = null,
        position: Int? = null,
        remarks: String? = null,
        startWith: Long? = null,
        valueBlobFile: String? = null, // maybe for update or delete
        valueBoolean: Boolean? = null,
        valueClobFile: String? = null,
        valueComputed: String? = null,
        valueDate: String? = null,
        valueNumeric: Number? = null,
        block: (ConstraintDsl.() -> Unit)? = null,
    ) {
        val column = columnConfigClass.java.getDeclaredConstructor().newInstance()
        val addColumnConfig = column as? AddColumnConfig

        fun nullAddColumnConfigMessage() =
            "ChangeSet '$changeSetId': columns are not allowed in '$changeName' changes."

        column.name = name
        column.type = type
        column.value = value
        afterColumn?.also {
            requireNotNull(addColumnConfig) {
                nullAddColumnConfigMessage()
            }
            addColumnConfig.afterColumn = afterColumn
        }
        column.isAutoIncrement = autoIncrement
        beforeColumn?.also {
            requireNotNull(addColumnConfig) {
                nullAddColumnConfigMessage()
            }
            addColumnConfig.beforeColumn = beforeColumn
        }
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
        position?.also {
            requireNotNull(addColumnConfig) {
                nullAddColumnConfigMessage()
            }
            addColumnConfig.position = position
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

        block?.let {
            val constraintDsl = ConstraintDsl(changeLog)
            it(constraintDsl)
            column.constraints = constraintDsl.constraint
        }
        _columns.add(column)
    }

    fun where(whereClause: String) {
        val expandedWhereClause = whereClause.expandExpressions(changeLog)
        try {
            change as AbstractModifyDataChange
            change.where = expandedWhereClause
        } catch (e: ClassCastException) {
            throw ChangeLogParseException(
                "ChangeSet '$changeSetId': a where clause is invalid for '$changeName' changes.",
                e,
            )
        }
    }

    fun whereParams(block: WhereParamsDsl.() -> Unit) {
        change as AbstractModifyDataChange
        val whereParamsDsl =
            WhereParamsDsl(
                changeLog = changeLog,
                change = change,
            )
        whereParamsDsl.block()
    }
}

// TODO: It might be better to divide the class itself into three.?
typealias ColumnDsl = IColumnDsl<ColumnConfig>
typealias AddColumnDsl = IColumnDsl<AddColumnConfig>
typealias LoadDataColumnDsl = IColumnDsl<LoadDataColumnConfig>
