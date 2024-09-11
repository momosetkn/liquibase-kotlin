package momosetkn.liquibase.kotlin.change

import liquibase.change.custom.CustomChange
import liquibase.change.custom.CustomTaskChange
import liquibase.change.custom.CustomTaskRollback
import liquibase.database.Database
import liquibase.exception.ValidationErrors
import liquibase.resource.ResourceAccessor

class RollbackTaskCustomChange(
    private val executeBlock: ParamsContext.(database: Database) -> Unit,
    private val validateBlock: ParamsContext.(database: Database) -> ValidationErrors,
    private val rollbackBlock: ParamsContext.(database: Database) -> Unit,
    private val confirmationMessage: String,
    private val params: Map<String, Any?>?,
) : CustomChange, CustomTaskChange, CustomTaskRollback {
    private var resourceAccessor: ResourceAccessor? = null
    private val paramsContext = ParamsContext(params ?: emptyMap())
    private var alreadyRollbackFlg = false
    override fun getConfirmationMessage(): String {
        return confirmationMessage
    }

    override fun setUp() = Unit

    override fun setFileOpener(resourceAccessor: ResourceAccessor) {
        this.resourceAccessor = resourceAccessor
    }

    override fun validate(database: Database): ValidationErrors {
        return validateBlock(paramsContext, database)
    }

    override fun execute(database: Database) {
        executeBlock(paramsContext, database)
    }

    override fun rollback(database: Database) {
        // FIXME: CustomTaskRollback has a bug that causes it to be rolled back twice, but there is a workaround.
        if (!alreadyRollbackFlg) {
            rollbackBlock(paramsContext, database)
            alreadyRollbackFlg = true
        }
    }
}
