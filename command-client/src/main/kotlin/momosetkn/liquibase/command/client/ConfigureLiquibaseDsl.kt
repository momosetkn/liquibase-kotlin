package momosetkn.liquibase.command.client

@ConfigureLiquibaseDslMarker
class ConfigureLiquibaseDsl {
    private var configuredArgs: ConfiguredArgs = ConfiguredArgs()

    internal operator fun invoke(
        block: ConfigureLiquibaseDslBlock,
    ): ConfiguredArgs {
        block(this)
        return configuredArgs
    }

    fun global(block: LiquibaseGlobalArgsDslBlock) {
        configuredArgs = configuredArgs.copy(
            globalArgsDslBlock = block,
        )
    }

    fun command(block: LiquibaseCommandArgsDslBlock) {
        configuredArgs = configuredArgs.copy(
            commandArgsDslBlock = block,
        )
    }

    fun systemEnv(block: LiquibaseSystemEnvArgsDslBlock) {
        configuredArgs = configuredArgs.copy(
            systemEnvArgsDslBlock = block,
        )
    }
}

data class ConfiguredArgs(
    var globalArgsDslBlock: LiquibaseGlobalArgsDslBlock? = null,
    var commandArgsDslBlock: LiquibaseCommandArgsDslBlock? = null,
    var systemEnvArgsDslBlock: LiquibaseSystemEnvArgsDslBlock? = null,
)

@DslMarker
annotation class ConfigureLiquibaseDslMarker
