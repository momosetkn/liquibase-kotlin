package momosetkn.liquibase

import liquibase.integration.commandline.LiquibaseCommandLine
import java.util.concurrent.Executors

object LiquibaseCommand {
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

    @Suppress("LongParameterList")
    fun command(
        driverClassName: String,
        jdbcUrl: String,
        user: String,
        password: String?,
        command: String,
        changelogFile: String? = null,
        vararg options: String?,
    ) {
        val argsList = listOfNotNull(
            "--show-banner=false",
            "--log-level=trace",
            command,
            changelogFile?.let { "--changelog-file=$changelogFile" },
            // target
            "--url=$jdbcUrl",
            "--driver=$driverClassName",
            "--username=$user",
            password?.let { "--password=$it" },
            // command
        ) + options.mapNotNull { it }
        executeLiquibaseCommandLine(
            argsList.toTypedArray(),
        )
    }

    private fun executeLiquibaseCommandLine(args: Array<String>) {
        logArgs(args)
        // In the following case, it will terminate the process using System.exit.
        // liquibase.integration.commandline.LiquibaseCommandLine.main
        val future =
            executor.submit<Int> {
                cli.execute(args)
            }
        val returnCode = future.get()
        if (returnCode != 0) {
            error("liquibase command failed, return code: $returnCode")
        }
    }

    private fun logArgs(args: Array<String>) {
        val maskedArgs = passwordMasking(args)
        log.info("execute liquibase. params: ${maskedArgs.joinToString(" ")}")
    }

    private fun passwordMasking(args: Array<String>): List<String> =
        args.map { arg ->
            if (
                arg.startsWith("--password=") ||
                arg.startsWith("--reference-password=")
            ) {
                arg.replaceAfter("=", "********")
            } else {
                arg
            }
        }
}
