package momosetkn.liquibase.kotlin.change

import liquibase.change.custom.CustomChange
import liquibase.change.custom.CustomSqlChange
import liquibase.change.custom.CustomSqlRollback
import liquibase.database.Database
import liquibase.exception.ValidationErrors
import liquibase.resource.ResourceAccessor
import liquibase.statement.SqlStatement

class RollbackSqlCustomChange(
    private val generateStatementsBlock: ((database: Database) -> Array<SqlStatement>),
    private val validateBlock: ((database: Database) -> ValidationErrors),
    private val generateRollbackStatementsBlock: ((database: Database) -> Array<SqlStatement>),
    private val confirmationMessage: String,
) : CustomChange, CustomSqlChange, CustomSqlRollback {
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

    override fun generateStatements(database: Database): Array<SqlStatement> {
        return generateStatementsBlock(database)
    }

    override fun generateRollbackStatements(database: Database): Array<SqlStatement> {
        return generateRollbackStatementsBlock(database)
    }
}
