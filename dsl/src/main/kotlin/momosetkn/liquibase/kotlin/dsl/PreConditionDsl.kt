package momosetkn.liquibase.kotlin.dsl

import liquibase.changelog.DatabaseChangeLog
import liquibase.precondition.CustomPreconditionWrapper
import liquibase.precondition.Precondition
import liquibase.precondition.PreconditionLogic
import liquibase.precondition.core.AndPrecondition
import liquibase.precondition.core.ChangeLogPropertyDefinedPrecondition
import liquibase.precondition.core.ChangeSetExecutedPrecondition
import liquibase.precondition.core.ColumnExistsPrecondition
import liquibase.precondition.core.DBMSPrecondition
import liquibase.precondition.core.ForeignKeyExistsPrecondition
import liquibase.precondition.core.IndexExistsPrecondition
import liquibase.precondition.core.NotPrecondition
import liquibase.precondition.core.ObjectQuotingStrategyPrecondition
import liquibase.precondition.core.OrPrecondition
import liquibase.precondition.core.PreconditionContainer
import liquibase.precondition.core.PreconditionContainer.ErrorOption
import liquibase.precondition.core.PreconditionContainer.FailOption
import liquibase.precondition.core.PreconditionContainer.OnSqlOutputOption
import liquibase.precondition.core.PrimaryKeyExistsPrecondition
import liquibase.precondition.core.RowCountPrecondition
import liquibase.precondition.core.RunningAsPrecondition
import liquibase.precondition.core.SequenceExistsPrecondition
import liquibase.precondition.core.SqlPrecondition
import liquibase.precondition.core.TableExistsPrecondition
import liquibase.precondition.core.TableIsEmptyPrecondition
import liquibase.precondition.core.UniqueConstraintExistsPrecondition
import liquibase.precondition.core.ViewExistsPrecondition
import momosetkn.liquibase.kotlin.dsl.Expressions.evalClassNameExpressions
import momosetkn.liquibase.kotlin.dsl.Expressions.evalExpressions
import momosetkn.liquibase.kotlin.dsl.Expressions.tryEvalExpressions
import momosetkn.liquibase.kotlin.dsl.Expressions.tryEvalExpressionsOrNull
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

@ChangeLogDslMarker
@Suppress("TooManyFunctions")
class PreConditionDsl<PRECONDITION_LOGIC : PreconditionLogic>(
    private val changeLog: DatabaseChangeLog,
    private val buildPreconditionContainer: () -> PRECONDITION_LOGIC,
) {
    @Suppress("ktlint:standard:backing-property-naming")
    private val _preConditions: MutableList<Precondition> = mutableListOf()

    internal operator fun invoke(
        block: PreConditionDsl<PRECONDITION_LOGIC>.() -> Unit
    ): PRECONDITION_LOGIC {
        block(this)
        return buildPreconditionContainer().apply {
            _preConditions.forEach {
                addNestedPrecondition(it)
            }
        }
    }

    fun and(block: PreConditionDsl<PreconditionLogic>.() -> Unit) {
        val precondition = nestedPrecondition(AndPrecondition::class, block)
        _preConditions.add(precondition)
    }

    fun or(block: PreConditionDsl<PreconditionLogic>.() -> Unit) {
        val precondition = nestedPrecondition(OrPrecondition::class, block)
        _preConditions.add(precondition)
    }

    fun not(block: PreConditionDsl<PreconditionLogic>.() -> Unit) {
        val precondition = nestedPrecondition(NotPrecondition::class, block)
        _preConditions.add(precondition)
    }

    fun changeLogPropertyDefined(
        property: String,
        value: String? = null,
    ) {
        val precondition = ChangeLogPropertyDefinedPrecondition()
        precondition.property = property.evalExpressions(changeLog)
        value?.also { precondition.value = it.evalExpressions(changeLog) }
        _preConditions.add(precondition)
    }

    fun changeSetExecuted(
        id: String,
        author: String,
        changeLogFile: String,
    ) {
        val precondition = ChangeSetExecutedPrecondition()
        precondition.id = id.evalExpressions(changeLog)
        precondition.author = author.evalExpressions(changeLog)
        precondition.changeLogFile = changeLogFile.evalExpressions(changeLog)
        _preConditions.add(precondition)
    }

    fun columnExists(
        columnName: String,
        tableName: String,
        schemaName: String? = null,
    ) {
        val precondition = ColumnExistsPrecondition()
        precondition.columnName = columnName.evalExpressions(changeLog)
        precondition.tableName = tableName.evalExpressions(changeLog)
        schemaName?.also { precondition.schemaName = it.evalExpressions(changeLog) }
        _preConditions.add(precondition)
    }

    fun dbms(
        type: String,
    ) {
        val precondition = DBMSPrecondition()
        precondition.type = type.evalExpressions(changeLog)
        _preConditions.add(precondition)
    }

    fun expectedQuotingStrategy(
        strategy: String,
    ) {
        val precondition = ObjectQuotingStrategyPrecondition()
        precondition.setStrategy(strategy.evalExpressions(changeLog))
        _preConditions.add(precondition)
    }

    fun foreignKeyConstraintExists(
        foreignKeyName: String,
        foreignKeyTableName: String,
        schemaName: String? = null,
    ) {
        val precondition = ForeignKeyExistsPrecondition()
        precondition.foreignKeyName = foreignKeyName.evalExpressions(changeLog)
        precondition.foreignKeyTableName = foreignKeyTableName.evalExpressions(changeLog)
        schemaName?.also { precondition.schemaName = it.evalExpressions(changeLog) }
        _preConditions.add(precondition)
    }

    fun indexExists(
        indexName: String? = null,
        columnNames: String? = null,
        tableName: String? = null,
        schemaName: String? = null,
    ) {
        val precondition = IndexExistsPrecondition()
        indexName?.also { precondition.indexName = it.evalExpressions(changeLog) }
        columnNames?.also { precondition.columnNames = it.evalExpressions(changeLog) }
        tableName?.also { precondition.tableName = it.evalExpressions(changeLog) }
        schemaName?.also { precondition.schemaName = it.evalExpressions(changeLog) }
        _preConditions.add(precondition)
    }

    fun primaryKeyExists(
        primaryKeyName: String? = null,
        tableName: String? = null,
        schemaName: String? = null,
    ) {
        val precondition = PrimaryKeyExistsPrecondition()
        primaryKeyName?.also { precondition.primaryKeyName = it.evalExpressions(changeLog) }
        tableName?.also { precondition.tableName = it.evalExpressions(changeLog) }
        schemaName?.also { precondition.schemaName = it.evalExpressions(changeLog) }
        _preConditions.add(precondition)
    }

    fun rowCount(
        expectedRows: Long,
        tableName: String,
    ) {
        val precondition = RowCountPrecondition()
        precondition.expectedRows = expectedRows
        precondition.tableName = tableName.evalExpressions(changeLog)
        _preConditions.add(precondition)
    }

    fun runningAs(
        username: String,
    ) {
        val precondition = RunningAsPrecondition()
        precondition.username = username.evalExpressions(changeLog)
        _preConditions.add(precondition)
    }

    fun sequenceExists(
        sequenceName: String,
        schemaName: String? = null,
    ) {
        val precondition = SequenceExistsPrecondition()
        precondition.sequenceName = sequenceName.evalExpressions(changeLog)
        schemaName?.also { precondition.schemaName = it.evalExpressions(changeLog) }
        _preConditions.add(precondition)
    }

    fun sqlCheck(
        expectedResult: Any,
        block: () -> String,
    ) {
        val precondition = SqlPrecondition()
        precondition.expectedResult = expectedResult.tryEvalExpressions(changeLog).toString()
        precondition.sql = block().evalExpressions(changeLog)
        _preConditions.add(precondition)
    }

    fun tableExists(
        tableName: String,
        schemaName: String? = null,
    ) {
        val precondition = TableExistsPrecondition()
        precondition.tableName = tableName.evalExpressions(changeLog)
        schemaName?.also { precondition.schemaName = it.evalExpressions(changeLog) }
        _preConditions.add(precondition)
    }

    fun tableIsEmpty(
        tableName: String,
        catalogName: String? = null,
        schemaName: String? = null,
    ) {
        val precondition = TableIsEmptyPrecondition()
        precondition.tableName = tableName.evalExpressions(changeLog)
        catalogName?.also { precondition.catalogName = it.evalExpressions(changeLog) }
        schemaName?.also { precondition.schemaName = it.evalExpressions(changeLog) }
        _preConditions.add(precondition)
    }

    fun uniqueConstraintExists(
        tableName: String,
        columnNames: String? = null,
        constraintName: String? = null,
    ) {
        val precondition = UniqueConstraintExistsPrecondition()
        precondition.tableName = tableName.evalExpressions(changeLog)
        columnNames?.also { precondition.columnNames = it.evalExpressions(changeLog) }
        constraintName?.also { precondition.constraintName = it.evalExpressions(changeLog) }
        _preConditions.add(precondition)
    }

    fun viewExists(
        viewName: String,
        schemaName: String? = null,
    ) {
        val precondition = ViewExistsPrecondition()
        precondition.viewName = viewName.evalExpressions(changeLog)
        schemaName?.also { precondition.schemaName = it.evalExpressions(changeLog) }
        _preConditions.add(precondition)
    }

    fun customPrecondition(
        @Suppress("FunctionParameterNaming")
        `class`: Any? = null,
        clazz: Any? = null,
        className: String? = null,
        block: KeyValueDsl.() -> Unit,
    ) {
        val precondition = CustomPreconditionWrapper()

        @Suppress("MaxLineLength")
        val overrideClassName = (`class` ?: clazz ?: className)
            ?: error("Should specify either 'class' or 'clazz' or 'className' property for 'customPrecondition' preConditions")
        precondition.className = overrideClassName.evalClassNameExpressions(changeLog)
        val dsl = KeyValueDsl(changeLog)
        val map = dsl(block)
        map.forEach { (key, value) ->
            val expandedValue = value.tryEvalExpressionsOrNull(changeLog)?.toString()
            precondition.setParam(key, expandedValue)
        }

        _preConditions.add(precondition)
    }

    private fun nestedPrecondition(
        preconditionClass: KClass<out PreconditionLogic>,
        block: NestedPreConditionDsl.() -> Unit,
    ): PreconditionLogic {
        val dsl =
            PreConditionDsl(
                changeLog = changeLog,
                buildPreconditionContainer = {
                    preconditionClass.primaryConstructor!!.call()
                },
            )
        return dsl(block)
    }

    companion object {
        fun build(
            changeLog: DatabaseChangeLog,
            preconditionContainerContext: PreconditionContainerContext,
        ): PreConditionDsl<PreconditionContainer> =
            PreConditionDsl(
                changeLog = changeLog,
                buildPreconditionContainer = {
                    buildPreconditionContainer(preconditionContainerContext)
                },
            )

        private fun buildPreconditionContainer(context: PreconditionContainerContext) =
            PreconditionContainer().apply {
                context.onError?.also {
                    this.onError = ErrorOption.valueOf(it)
                }
                this.onErrorMessage = context.onErrorMessage
                context.onFail?.also {
                    this.onFail = FailOption.valueOf(it)
                }
                this.onFailMessage = context.onFailMessage
                context.onSqlOutput?.also {
                    this.onSqlOutput = OnSqlOutputOption.valueOf(it)
                }
            }
    }
}

data class PreconditionContainerContext(
    val onError: String? = null,
    val onErrorMessage: String? = null,
    val onFail: String? = null,
    val onFailMessage: String? = null,
    val onSqlOutput: String? = null,
)

typealias RootPreConditionDsl = PreConditionDsl<PreconditionContainer>
typealias NestedPreConditionDsl = PreConditionDsl<PreconditionLogic>
