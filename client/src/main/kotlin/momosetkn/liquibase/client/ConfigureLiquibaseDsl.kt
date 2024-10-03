package momosetkn.liquibase.client

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
            globalDslBlock = block,
        )
    }

    fun system(block: LiquibaseSystemEnvArgsDslBlock) {
        configuredArgs = configuredArgs.copy(
            systemDslBlock = block,
        )
    }
}

data class ConfiguredArgs(
    var globalDslBlock: LiquibaseGlobalArgsDslBlock? = null,
    var systemDslBlock: LiquibaseSystemEnvArgsDslBlock? = null,
)
