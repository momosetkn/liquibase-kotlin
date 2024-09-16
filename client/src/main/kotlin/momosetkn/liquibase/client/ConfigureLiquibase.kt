package momosetkn.liquibase.client

typealias ConfigureLiquibaseDslBlock = ConfigureLiquibaseDsl.() -> Unit
typealias LiquibaseGlobalArgsDslBlock = LiquibaseGlobalArgsDsl.() -> Unit
typealias LiquibaseCommandArgsDslBlock = LiquibaseCommandArgsDsl.() -> Unit
typealias LiquibaseSystemEnvArgsDslBlock = LiquibaseSystemEnvArgsDsl.() -> Unit

interface ConfigureLiquibase {
    // require override
    val configureBlock: ConfigureLiquibaseDslBlock

    val configuredArgs: ConfiguredArgs
        get() {
            val dsl = ConfigureLiquibaseDsl()
            return dsl(configureBlock)
        }

    fun getGlobalArgs(): List<String> {
        val dsl = LiquibaseGlobalArgsDsl()
        val args = configuredArgs.globalArgsDslBlock?.let { dsl(it) } ?: emptyList()
        return args.flatMap { it.serialize() }
    }

    fun getCommandArgs(): List<String> {
        val dsl = LiquibaseCommandArgsDsl()
        val args = configuredArgs.commandArgsDslBlock?.let { dsl(it) } ?: emptyList()
        return args.flatMap { it.serialize() }
    }

    fun setSystemEnvArgs() {
        val dsl = LiquibaseSystemEnvArgsDsl()
        val args = configuredArgs.systemEnvArgsDslBlock?.let { dsl(it) } ?: emptyList()
        args
            .flatMap { it.serialize() }
            .forEach { (key, value) ->
                System.setProperty(key, value)
            }
    }
}
