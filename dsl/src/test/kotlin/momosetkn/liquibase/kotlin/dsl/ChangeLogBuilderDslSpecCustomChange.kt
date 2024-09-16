package momosetkn.liquibase.kotlin.dsl

import liquibase.change.custom.CustomChange
import liquibase.change.custom.CustomTaskChange
import liquibase.change.custom.CustomTaskRollback
import liquibase.database.Database
import liquibase.exception.ValidationErrors
import liquibase.resource.ResourceAccessor

class ChangeLogBuilderDslSpecCustomChange : CustomChange, CustomTaskChange, CustomTaskRollback {
    private var resourceAccessor: ResourceAccessor? = null

    fun setValue(v: String) {
        value = v
    }

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
        value += "e"
    }

    override fun rollback(database: Database?) {
        value += "r"
    }

    companion object {
        var value = ""
    }
}
