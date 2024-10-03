package momosetkn.liquibase.client

typealias ConfigureLiquibaseDslBlock = ConfigureLiquibaseDsl.() -> Unit
typealias LiquibaseGlobalArgsDslBlock = LiquibaseGlobalArgsDsl.() -> Unit
typealias LiquibaseSystemEnvArgsDslBlock = LiquibaseSystemArgs.() -> Unit

private class ConfigureLiquibase(
    private val configureBlock: ConfigureLiquibaseDslBlock
) {
    fun configure() {
        val configuredArgs = configuredArgs()
        val allArgs = getAllArgs(configuredArgs)
        allArgs
            .forEach { (key, value) ->
                if (value == null) {
                    System.clearProperty(key)
                } else {
                    System.setProperty(key, value)
                }
            }
    }

    private fun configuredArgs(): ConfiguredArgs {
        val dsl = ConfigureLiquibaseDsl()
        return dsl(configureBlock)
    }

    private fun getAllArgs(configuredArgs: ConfiguredArgs): List<Pair<String, String?>> {
        return getGlobalArgs(configuredArgs) + getSystemEnvArgs(configuredArgs)
    }

    private fun getGlobalArgs(configuredArgs: ConfiguredArgs): List<Pair<String, String?>> {
        val dsl = LiquibaseGlobalArgsDsl()
        val args = configuredArgs.globalDslBlock?.let { dsl(it) } ?: emptyList()
        return args.flatMap { it.serialize() }
    }

    private fun getSystemEnvArgs(configuredArgs: ConfiguredArgs): List<Pair<String, String?>> {
        val liquibaseSystemArgs = LiquibaseSystemArgs()
        val args = configuredArgs.systemDslBlock?.let {
            it(liquibaseSystemArgs)
            liquibaseSystemArgs.serialize()
        } ?: emptyList()
        return args
    }
}

fun configureLiquibase(
    block: ConfigureLiquibaseDslBlock,
) = ConfigureLiquibase(block).configure()
