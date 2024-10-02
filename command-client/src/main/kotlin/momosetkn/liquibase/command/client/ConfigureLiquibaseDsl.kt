package momosetkn.liquibase.command.client

class ConfigureLiquibaseDsl {
    private var configuredArgs: ConfiguredArgs = ConfiguredArgs()

    internal operator fun invoke(
        block: ConfigureLiquibaseDslBlock,
    ): ConfiguredArgs {
        block(this)
        return configuredArgs
    }

    fun globalArgs(block: LiquibaseGlobalArgsDslBlock) {
        configuredArgs = configuredArgs.copy(
            globalArgsDslBlock = block,
        )
    }

    fun commandArgs(block: LiquibaseCommandArgsDslBlock) {
        configuredArgs = configuredArgs.copy(
            commandArgsDslBlock = block,
        )
    }

    fun systemEnvArg(block: LiquibaseSystemEnvArgsDslBlock) {
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
