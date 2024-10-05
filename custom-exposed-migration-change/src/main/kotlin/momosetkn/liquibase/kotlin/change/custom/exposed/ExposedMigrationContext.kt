package momosetkn.liquibase.kotlin.change.custom.exposed

import momosetkn.liquibase.kotlin.change.custom.komapper.toJavaxSqlDataSource

internal fun liquibase.database.Database.toExposedMigrationDatabase(): org.jetbrains.exposed.sql.Database {
    val datasource = this.toJavaxSqlDataSource()
    val database = LiquibaseExposedMigrationConfig.provideDatabase(datasource, this.shortName)
    return database
}
