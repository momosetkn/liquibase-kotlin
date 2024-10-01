package momosetkn.liquibase.client

class LiquibaseGlobalArgsDsl {
    private var info: LiquibaseInfoArgs? = null
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
            info,
            general,
            dbclhistory,
            executors,
            extensions,
            mongoDB,
        )
    }

    fun info(
        block: LiquibaseInfoArgs.() -> Unit,
    ) {
        info = LiquibaseInfoArgs().apply(block)
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