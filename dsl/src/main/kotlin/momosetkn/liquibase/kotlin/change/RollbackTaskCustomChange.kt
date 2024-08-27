package momosetkn.liquibase.kotlin.change

import liquibase.change.custom.CustomChange
import liquibase.change.custom.CustomTaskChange
import liquibase.change.custom.CustomTaskRollback
import liquibase.database.Database
import liquibase.exception.ValidationErrors
import liquibase.resource.ResourceAccessor

class RollbackTaskCustomChange(
    private val executeBlock: ((database: Database) -> Unit),
    private val validateBlock: ((database: Database) -> ValidationErrors),
    private val rollbackBlock: ((database: Database) -> Unit),
    private val confirmationMessage: String,
) : CustomChange, CustomTaskChange, CustomTaskRollback {
    private var resourceAccessor: ResourceAccessor? = null
    private var alreadyRollbackFlg = false
    override fun getConfirmationMessage(): String {
        return confirmationMessage
    }

    override fun setUp() = Unit

    override fun setFileOpener(resourceAccessor: ResourceAccessor) {
        this.resourceAccessor = resourceAccessor
    }

    override fun validate(database: Database): ValidationErrors {
        return validateBlock(database)
    }

    override fun execute(database: Database) {
        executeBlock(database)
    }

    override fun rollback(database: Database) {
        // FIXME: CustomTaskRollback has a bug that causes it to be rolled back twice, but there is a workaround.
        if (!alreadyRollbackFlg) {
            rollbackBlock(database)
            alreadyRollbackFlg = true
        }
    }
}
