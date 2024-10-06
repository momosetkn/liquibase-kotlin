package momosetkn.liquibase.kotlin.change.custom.core

import liquibase.change.custom.CustomChange
import liquibase.change.custom.CustomTaskChange
import liquibase.database.Database
import liquibase.exception.ValidationErrors
import liquibase.resource.ResourceAccessor

class ForwardOnlyTaskCustomChange<T : Any>(
    private val define: CustomTaskChangeDefineImpl<T>,
) : CustomChange, CustomTaskChange {
    private var resourceAccessor: ResourceAccessor? = null
    override fun getConfirmationMessage(): String {
        return define.confirmationMessage
    }

    override fun setUp() = Unit

    override fun setFileOpener(resourceAccessor: ResourceAccessor) {
        this.resourceAccessor = resourceAccessor
    }

    override fun validate(database: Database): ValidationErrors {
        return define.validateBlock(define.transformDatabase(database))
    }

    override fun execute(database: Database) {
        define.executeBlock(define.transformDatabase(database))
    }
}
