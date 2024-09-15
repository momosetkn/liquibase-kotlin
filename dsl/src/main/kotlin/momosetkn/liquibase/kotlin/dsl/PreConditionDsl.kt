package momosetkn.liquibase.kotlin.dsl

import liquibase.changelog.DatabaseChangeLog
import liquibase.precondition.CustomPreconditionWrapper
import liquibase.precondition.Precondition
import liquibase.precondition.PreconditionLogic
import liquibase.precondition.core.AndPrecondition
import liquibase.precondition.core.NotPrecondition
import liquibase.precondition.core.OrPrecondition
import liquibase.precondition.core.PreconditionContainer
import liquibase.precondition.core.PreconditionContainer.ErrorOption
import liquibase.precondition.core.PreconditionContainer.FailOption
import liquibase.precondition.core.PreconditionContainer.OnSqlOutputOption
import liquibase.precondition.core.SqlPrecondition
import kotlin.reflect.KClass

// TODO: add other precondition
@ChangeLogDslMarker
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

    fun sqlCheck(
        expectedResult: Any,
        block: () -> String,
    ) {
        val precondition = SqlPrecondition()
        precondition.expectedResult = expectedResult.tryEvalExpressions(changeLog).toString()
        precondition.sql = block().evalExpressions(changeLog)
        _preConditions.add(precondition)
    }

    fun customPrecondition(
        `class`: String? = null,
        clazz: String? = null,
        className: String? = null,
        block: KeyValueDsl.() -> Unit,
    ) {
        val precondition = CustomPreconditionWrapper()

        val overrideClassName =
            `class` ?: clazz ?: className
                ?: error(SHOULD_SPECIFY_CLASS_MESSAGE)
        precondition.className = overrideClassName.evalExpressions(changeLog)
        val dsl = KeyValueDsl(changeLog)
        val map = dsl(block)
        map.forEach { (key, value) ->
            val expandedValue = value.tryEvalExpressionsOrNull(changeLog)?.toString()
            precondition.setParam(key, expandedValue)
        }

        _preConditions.add(precondition)
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

    private fun nestedPrecondition(
        preconditionClass: KClass<out PreconditionLogic>,
        block: NestedPreConditionDsl.() -> Unit,
    ): PreconditionLogic {
        val dsl =
            PreConditionDsl(
                changeLog = changeLog,
                buildPreconditionContainer = {
                    preconditionClass.java
                        .getDeclaredConstructor()
                        .newInstance()
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

const val SHOULD_SPECIFY_CLASS_MESSAGE =
    "Should specify either 'class' or 'clazz' or 'className' property for 'customPrecondition' preConditions"
