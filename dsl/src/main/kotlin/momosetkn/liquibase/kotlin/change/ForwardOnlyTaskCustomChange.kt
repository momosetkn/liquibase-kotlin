package momosetkn.liquibase.kotlin.change

import liquibase.change.custom.CustomChange
import liquibase.change.custom.CustomTaskChange
import liquibase.database.Database
import liquibase.exception.ValidationErrors
import liquibase.resource.ResourceAccessor

class ForwardOnlyTaskCustomChange(
    private val executeBlock: ParamsContext.(database: Database) -> Unit,
    private val validateBlock: ParamsContext.(database: Database) -> ValidationErrors,
    private val confirmationMessage: String,
    private val params: Map<String, Any?>?,
) : CustomChange, CustomTaskChange {
    private var resourceAccessor: ResourceAccessor? = null
    private val paramsContext = ParamsContext(params ?: emptyMap())
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
}
