package momosetkn.liquibase.kotlin.change.custom.komapper

import momosetkn.sql.DatasourceProxy
import momosetkn.sql.NotCloseConnectionProxy
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

fun liquibase.database.Database.toJavaxSqlDataSource(): DatasourceProxy {
    val liquibaseJdbcConnection = this.connection as liquibase.database.jvm.JdbcConnection
    val con = connectionProperty.get(liquibaseJdbcConnection) as java.sql.Connection
    return DatasourceProxy(NotCloseConnectionProxy(con))
}

private val connectionProperty = liquibase.database.jvm.JdbcConnection::class.declaredMemberProperties
    .first { it.name == "con" }
    .apply {
        isAccessible = true
    }
