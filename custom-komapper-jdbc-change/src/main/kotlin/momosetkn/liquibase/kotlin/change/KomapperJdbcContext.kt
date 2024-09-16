package momosetkn.liquibase.kotlin.change

import momosetkn.sql.DatasourceProxy
import momosetkn.sql.NotCloseConnectionProxy
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

internal fun <R : Any> withKomapperJdbcContext(
    database: liquibase.database.Database,
    block: KomapperJdbcContext.() -> R,
): R {
    val datasource = database.toJavaxSqlDataSource()
    val db = LiquibaseKomapperJdbcConfig.provideJdbcDatabase(datasource, database.shortName)
    return with(KomapperJdbcContext(db)) {
        block()
    }
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

data class KomapperJdbcContext(
    val db: org.komapper.jdbc.JdbcDatabase,
)
