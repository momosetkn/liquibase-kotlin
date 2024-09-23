package momosetkn.liquibase.changelogs.customechannge

import liquibase.change.custom.CustomChange
import liquibase.change.custom.CustomTaskChange
import liquibase.database.Database
import liquibase.exception.ValidationErrors
import liquibase.resource.ResourceAccessor

class ExecuteCountTaskCustomChange : CustomChange, CustomTaskChange {
    private var resourceAccessor: ResourceAccessor? = null
    override fun getConfirmationMessage(): String {
        return "confirmationMessage"
    }

    override fun setUp() = Unit

    override fun setFileOpener(resourceAccessor: ResourceAccessor) {
        this.resourceAccessor = resourceAccessor
    }

    override fun validate(database: Database): ValidationErrors {
        return ValidationErrors()
    }

    override fun execute(database: Database) {
        executeCount++
    }

    companion object {
        var executeCount = 0
    }
}
