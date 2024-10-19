package momosetkn.liquibase.command.client

import liquibase.command.CommandScope
import momosetkn.liquibase.scope.CustomScope

object LiquibaseCommandExecutor {
    private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)

    fun executeCommand(
        command: List<String>,
        args: List<Pair<String, String>>,
    ) {
        @Suppress("SpreadOperator")
        val commandScope = if (LiquibaseCommandClient.everyUseNewClassloader) {
            CustomScope.createWithNewClassloader(
                CommandScope::class,
                *command.toTypedArray()
            )
        } else {
            CommandScope(*command.toTypedArray())
        }
        args.forEach { arg ->
            val (key, value) = arg
            commandScope.addArgumentValue(key, value)
        }
        logArgs(command, args)
        commandScope.execute()
    }

    private fun logArgs(
        command: List<String>,
        args: List<Pair<String, String>>,
    ) {
        val maskedArgs = passwordMasking(args)
        log.info("execute liquibase. command: {}, params: {},", command, maskedArgs.joinToString(" "))
    }

    private fun passwordMasking(args: List<Pair<String, String>>): List<String> =
        args.map { (key, value) ->
            if (key.contains("password", ignoreCase = true)) {
                key to "***masked***"
            } else {
                key to value
            }
        }.map { (key, value) -> "$key=$value" }
}
