package momosetkn.liquibase.kotlin.change.custom.jooq

import momosetkn.liquibase.kotlin.change.custom.komapper.toJavaxSqlDataSource

internal fun liquibase.database.Database.toJooqDSLContext(): org.jooq.DSLContext {
    val datasource = this.toJavaxSqlDataSource()
    val database = LiquibaseJooqConfig.provideDSLContext(datasource, this.shortName)
    return database
}
