package momosetkn.liquibase.kotlin.dsl

import liquibase.change.AddColumnConfig
import liquibase.changelog.DatabaseChangeLog
import liquibase.statement.DatabaseFunction
import liquibase.util.ISODateFormat
import java.math.BigInteger

@ChangeLogDslMarker
class AddColumnDsl(
    private val changeLog: DatabaseChangeLog,
) {
    private val columnConfigClass = AddColumnConfig::class

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
        defaultValueDate: String? = null,
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
        val column = columnConfigClass.java.getDeclaredConstructor().newInstance()

        column.name = name
        column.type = type
        column.value = value
        afterColumn?.also {
            column.afterColumn = afterColumn
        }
        column.isAutoIncrement = autoIncrement
        beforeColumn?.also {
            column.beforeColumn = beforeColumn
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
            column.position = position
        }
        column.remarks = remarks
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
