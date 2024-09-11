package momosetkn.liquibase.kotlin.change

import liquibase.change.custom.CustomChange
import liquibase.change.custom.CustomSqlChange
import liquibase.database.Database
import liquibase.exception.ValidationErrors
import liquibase.resource.ResourceAccessor
import liquibase.statement.SqlStatement

class ForwardOnlySqlCustomChange(
    private val generateStatementsBlock: ParamsContext.(database: Database) -> Array<SqlStatement>,
    private val validateBlock: ParamsContext.(database: Database) -> ValidationErrors,
    private val confirmationMessage: String,
    private val params: Map<String, Any?>?,
) : CustomChange, CustomSqlChange {
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
}
