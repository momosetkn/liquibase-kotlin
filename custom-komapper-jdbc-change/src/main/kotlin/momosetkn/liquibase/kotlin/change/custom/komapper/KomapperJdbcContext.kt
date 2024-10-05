package momosetkn.liquibase.kotlin.change.custom.komapper

internal fun liquibase.database.Database.toKomapperJdbcDatabase(): org.komapper.jdbc.JdbcDatabase {
    val datasource = this.toJavaxSqlDataSource()
    val database = LiquibaseKomapperJdbcConfig.provideJdbcDatabase(datasource, this.shortName)
    return database
}
