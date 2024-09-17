package momosetkn.liquibase.kotlin.change

import liquibase.change.custom.CustomChange
import liquibase.change.custom.CustomTaskChange
import liquibase.change.custom.CustomTaskRollback
import liquibase.database.Database
import liquibase.exception.ValidationErrors
import liquibase.resource.ResourceAccessor

class RollbackTaskCustomChange(
    private val define: CustomRollbackableTaskChangeDefineImpl,
) : CustomChange, CustomTaskChange, CustomTaskRollback {
    private var alreadyRollbackFlg = false
    private var resourceAccessor: ResourceAccessor? = null
    override fun getConfirmationMessage(): String {
        return define.confirmationMessage
    }

    override fun setUp() = Unit

    override fun setFileOpener(resourceAccessor: ResourceAccessor) {
        this.resourceAccessor = resourceAccessor
    }

    override fun validate(database: Database): ValidationErrors {
        return define.validateBlock(database.toKomapperJdbcDatabase())
    }

    override fun execute(database: Database) {
        define.executeBlock(database.toKomapperJdbcDatabase())
    }

    override fun rollback(database: Database) {
        // FIXME: CustomTaskRollback has a bug that causes it to be rolled back twice, but there is a workaround.
        // bugfix: https://github.com/liquibase/liquibase/pull/6266
        if (!alreadyRollbackFlg) {
            define.rollbackBlock(database.toKomapperJdbcDatabase())
            alreadyRollbackFlg = true
        }
    }
}
