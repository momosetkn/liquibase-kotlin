package momosetkn.liquibase.kotlin.change

import momosetkn.sql.DatasourceProxy
import momosetkn.sql.NotCloseConnectionProxy
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

internal fun liquibase.database.Database.toKomapperJdbcDatabase(): org.komapper.jdbc.JdbcDatabase {
    val datasource = this.toJavaxSqlDataSource()
    val database = LiquibaseKomapperJdbcConfig.provideJdbcDatabase(datasource, this.shortName)
    return database
}

private fun liquibase.database.Database.toJavaxSqlDataSource(): DatasourceProxy {
    val liquibaseJdbcConnection = this.connection as liquibase.database.jvm.JdbcConnection
    val con = connectionProperty.get(liquibaseJdbcConnection) as java.sql.Connection
    return DatasourceProxy(NotCloseConnectionProxy(con))
}

private val connectionProperty = liquibase.database.jvm.JdbcConnection::class.declaredMemberProperties
    .first { it.name == "con" }
    .apply {
        isAccessible = true
    }
