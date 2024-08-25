package momosetkn.liquibase.kotlin.dsl

import liquibase.change.core.LoadDataColumnConfig
import liquibase.statement.DatabaseFunction
import liquibase.util.ISODateFormat
import java.math.BigInteger

@ChangeLogDslMarker
class LoadDataColumnDsl {
    private val columnConfigClass = LoadDataColumnConfig::class

    private val columns = mutableListOf<LoadDataColumnConfig>()

    internal operator fun invoke(
        block: LoadDataColumnDsl.() -> Unit,
    ): List<LoadDataColumnConfig> {
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
}
