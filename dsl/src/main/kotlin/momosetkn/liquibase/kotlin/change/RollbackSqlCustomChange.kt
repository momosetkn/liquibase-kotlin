package momosetkn.liquibase.kotlin.change

import liquibase.change.custom.CustomChange
import liquibase.change.custom.CustomSqlChange
import liquibase.change.custom.CustomSqlRollback
import liquibase.database.Database
import liquibase.exception.ValidationErrors
import liquibase.resource.ResourceAccessor
import liquibase.statement.SqlStatement

class RollbackSqlCustomChange(
    private val generateStatementsBlock: context(ParamsContext)
    (database: Database) -> Array<SqlStatement>,
    private val validateBlock: context(ParamsContext)
    (database: Database) -> ValidationErrors,
    private val generateRollbackStatementsBlock: context(ParamsContext)
    (database: Database) -> Array<SqlStatement>,
    private val confirmationMessage: String,
    private val params: Map<String, Any?>?,
) : CustomChange, CustomSqlChange, CustomSqlRollback {
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

    override fun generateStatements(database: Database): Array<SqlStatement> {
        return generateStatementsBlock(paramsContext, database)
    }

    override fun generateRollbackStatements(database: Database): Array<SqlStatement> {
        return generateRollbackStatementsBlock(paramsContext, database)
    }
}
