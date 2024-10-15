package momosetkn.liquibase.kotlin.change.custom.komapper

import momosetkn.sql.DatasourceProxy
import momosetkn.sql.NotCloseConnectionProxy

fun liquibase.database.Database.toJavaxSqlDataSource(): DatasourceProxy {
    val liquibaseJdbcConnection = this.connection as liquibase.database.jvm.JdbcConnection
    val javaSqlConnection = liquibaseJdbcConnection.underlyingConnection
    return DatasourceProxy(NotCloseConnectionProxy(javaSqlConnection))
}
