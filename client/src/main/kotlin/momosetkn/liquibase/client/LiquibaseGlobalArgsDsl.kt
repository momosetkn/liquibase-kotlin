package momosetkn.liquibase.client

@ConfigureLiquibaseDslMarker
class LiquibaseGlobalArgsDsl {
    private var general: LiquibaseGeneralGlobalArgs? = null
    private var dbclhistory: LiquibaseDbclhistoryArgs? = null
    private var executors: LiquibaseExecutorsArgs? = null
    private var extensions: LiquibaseExtensionsArgs? = null
    private var mongoDB: LiquibaseMongoDBArgs? = null

    internal operator fun invoke(
        block: LiquibaseGlobalArgsDsl.() -> Unit,
    ): List<LiquibaseGlobalArgs> {
        block(this)
        return listOfNotNull(
            general,
            dbclhistory,
            executors,
            extensions,
            mongoDB,
        )
    }

    fun general(
        block: LiquibaseGeneralGlobalArgs.() -> Unit,
    ) {
        general = LiquibaseGeneralGlobalArgs().apply(block)
    }

    fun dbclhistory(
        block: LiquibaseDbclhistoryArgs.() -> Unit,
    ) {
        dbclhistory = LiquibaseDbclhistoryArgs().apply(block)
    }

    fun executors(
        block: LiquibaseExecutorsArgs.() -> Unit,
    ) {
        executors = LiquibaseExecutorsArgs().apply(block)
    }

    fun extensions(
        block: LiquibaseExtensionsArgs.() -> Unit,
    ) {
        extensions = LiquibaseExtensionsArgs().apply(block)
    }

    fun mongoDB(
        block: LiquibaseMongoDBArgs.() -> Unit,
    ) {
        mongoDB = LiquibaseMongoDBArgs().apply(block)
    }
}
