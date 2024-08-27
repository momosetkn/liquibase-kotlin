package momosetkn.liquibase.kotlin.change

import liquibase.change.custom.CustomChange
import liquibase.change.custom.CustomTaskChange
import liquibase.database.Database
import liquibase.exception.ValidationErrors
import liquibase.resource.ResourceAccessor

class ForwardOnlyTaskCustomChange(
    private val executeBlock: ((database: Database) -> Unit),
    private val validateBlock: ((database: Database) -> ValidationErrors),
    private val confirmationMessage: String,
) : CustomChange, CustomTaskChange {
    private var resourceAccessor: ResourceAccessor? = null
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
}
