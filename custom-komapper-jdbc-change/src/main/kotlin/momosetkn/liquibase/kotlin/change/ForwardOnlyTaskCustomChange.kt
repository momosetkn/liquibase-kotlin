package momosetkn.liquibase.kotlin.change

import liquibase.change.custom.CustomChange
import liquibase.change.custom.CustomTaskChange
import liquibase.database.Database
import liquibase.exception.ValidationErrors
import liquibase.resource.ResourceAccessor

class ForwardOnlyTaskCustomChange(
    private val define: CustomTaskChangeDefineImpl,
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
        return withKomapperJdbcContext(database) {
            define.validateBlock(this)
        }
    }

    override fun execute(database: Database) {
        withKomapperJdbcContext(database) {
            define.executeBlock(this)
        }
    }
}
