package momosetkn.liquibase.client

class LiquibaseSystemEnvArgsDsl {
    private var configuration: LiquibaseConfigurationArgs? = null

    internal operator fun invoke(
        block: LiquibaseSystemEnvArgsDsl.() -> Unit,
    ): List<LiquibaseSystemEnvArgs> {
        block(this)
        return listOfNotNull(
            configuration
        )
    }

    fun configuration(
        block: LiquibaseConfigurationArgs.() -> Unit,
    ) {
        configuration = LiquibaseConfigurationArgs().apply(block)
    }
}
