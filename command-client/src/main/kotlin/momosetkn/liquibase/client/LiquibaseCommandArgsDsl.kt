package momosetkn.liquibase.client

class LiquibaseCommandArgsDsl {
    private var customLogging: LiquibaseCustomLoggingCommandArgs? = null
    private var connection: LiquibaseConnectionArgs? = null
    private var init: LiquibaseInitArgs? = null
    private var flowFiles: LiquibaseFlowFilesArgs? = null
    private var policyChecks: LiquibasePolicyChecksArgs? = null
    private var operationReports: LiquibaseOperationReportsArgs? = null
    private var generalCommand: LiquibaseGeneralCommandArgs? = null

    internal operator fun invoke(
        block: LiquibaseCommandArgsDsl.() -> Unit,
    ): List<LiquibaseCommandArgs> {
        block(this)
        return listOfNotNull(
            customLogging,
            connection,
            init,
            flowFiles,
            policyChecks,
            operationReports,
            generalCommand,
        )
    }

    fun customLogging(
        block: LiquibaseCustomLoggingCommandArgs.() -> Unit,
    ) {
        customLogging = LiquibaseCustomLoggingCommandArgs().apply(block)
    }

    fun connection(
        block: LiquibaseConnectionArgs.() -> Unit,
    ) {
        connection = LiquibaseConnectionArgs().apply(block)
    }

    fun init(
        block: LiquibaseInitArgs.() -> Unit,
    ) {
        init = LiquibaseInitArgs().apply(block)
    }

    fun flowFiles(
        block: LiquibaseFlowFilesArgs.() -> Unit,
    ) {
        flowFiles = LiquibaseFlowFilesArgs().apply(block)
    }

    fun policyChecks(
        block: LiquibasePolicyChecksArgs.() -> Unit,
    ) {
        policyChecks = LiquibasePolicyChecksArgs().apply(block)
    }

    fun operationReports(
        block: LiquibaseOperationReportsArgs.() -> Unit,
    ) {
        operationReports = LiquibaseOperationReportsArgs().apply(block)
    }

    fun generalCommand(
        block: LiquibaseGeneralCommandArgs.() -> Unit,
    ) {
        generalCommand = LiquibaseGeneralCommandArgs().apply(block)
    }
}
