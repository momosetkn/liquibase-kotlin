package momosetkn.liquibase.client

import liquibase.integration.commandline.LiquibaseCommandLine
import java.util.concurrent.Executors

object LiquibaseCommandExecutor {
    /**
     * Limit the execution of Liquibase to a single thread.
     * One reason is to prevent interference between migrators,
     * but there are also the following issues, so it will be executed on a fixed thread.
     * ```
     * Error below when executing Liquibase in parallel (executing in a different thread from the first execution).
     *
     * Unexpected argument(s):
     * --changelog-file=db/..., --url=jdbc:..., --driver=com..., --username=root, --password=test
     *
     * For detailed help, try 'liquibase --help' or 'liquibase <command-name> --help'
     */
    private val executor = Executors.newSingleThreadExecutor()
    private val cli = LiquibaseCommandLine()
    private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)

    init {
        Runtime.getRuntime().addShutdownHook(
            Thread {
                shutdown()
            },
        )
    }

    fun shutdown() {
        executor.shutdown()
    }

    fun executeLiquibaseCommandLine(args: List<String>) {
        val typedArgs = args.toTypedArray()
        logArgs(typedArgs)
        // In the following case, it will terminate the process using System.exit.
        // liquibase.integration.commandline.LiquibaseCommandLine.main
        val future =
            executor.submit<Int> {
                cli.execute(typedArgs)
            }
        val returnCode = future.get()
        check(returnCode == 0) {
            "liquibase command failed, return code: $returnCode"
        }
    }

    private fun logArgs(args: Array<String>) {
        val maskedArgs = passwordMasking(args)
        log.info("execute liquibase. params: ${maskedArgs.joinToString(" ")}")
    }

    private fun passwordMasking(args: Array<String>): List<String> =
        args.map { arg ->
            if ("password" in arg.substringBefore("=")) {
                arg.replaceAfter("=", "***masked***")
            } else {
                arg
            }
        }
}
