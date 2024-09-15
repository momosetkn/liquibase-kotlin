package momosetkn.liquibase.client

data class ConfigureLiquibaseState(
    var globalArgsDslBlock: LiquibaseGlobalArgsDslBlock? = null,
    var commandArgsDslBlock: LiquibaseCommandArgsDslBlock? = null,
    var systemEnvArgsDslBlock: LiquibaseSystemEnvArgsDslBlock? = null,
)

interface ConfigureLiquibase {
    var configureState: ConfigureLiquibaseState

    fun configureGlobalArgs(block: LiquibaseGlobalArgsDslBlock) {
        configureState = configureState.copy(
            globalArgsDslBlock = block,
        )
    }

    fun configureCommandArgs(block: LiquibaseCommandArgsDslBlock) {
        configureState = configureState.copy(
            commandArgsDslBlock = block,
        )
    }

    fun configureSystemEnvArg(block: LiquibaseSystemEnvArgsDslBlock) {
        configureState = configureState.copy(
            systemEnvArgsDslBlock = block,
        )
    }

    fun getGlobalArgs(): List<String> {
        val dsl = LiquibaseGlobalArgsDsl()
        val args = configureState.globalArgsDslBlock?.let { dsl(it) }
        return args?.flatMap { it.serialize() } ?: emptyList()
    }

    fun getCommandArgs(): List<String> {
        val dsl = LiquibaseCommandArgsDsl()
        val args = configureState.commandArgsDslBlock?.let { dsl(it) }
        return args?.flatMap { it.serialize() } ?: emptyList()
    }

    fun setSystemEnvArgs() {
        val dsl = LiquibaseSystemEnvArgsDsl()
        val args = configureState.systemEnvArgsDslBlock?.let { dsl(it) }
        args
            ?.flatMap { it.serialize() }
            ?.forEach {
                System.setProperty(it.first, it.second)
            }
    }
}
