package momosetkn.liquibase.kotlin.change.custom.exposed

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.vendors.H2Dialect
import org.jetbrains.exposed.sql.vendors.MariaDBDialect
import org.jetbrains.exposed.sql.vendors.MysqlDialect
import org.jetbrains.exposed.sql.vendors.OracleDialect
import org.jetbrains.exposed.sql.vendors.PostgreSQLDialect
import org.jetbrains.exposed.sql.vendors.SQLServerDialect
import org.jetbrains.exposed.sql.vendors.SQLiteDialect
import org.jetbrains.exposed.sql.vendors.VendorDialect

object LiquibaseExposedMigrationConfig {
    var provideDatabase: (
        javaxSqlDataSource: javax.sql.DataSource,
        liquibaseDatabaseShortName: String
    ) -> Database = LiquibaseExposedMigrationConfig::defaultProvideDatabase

    fun defaultProvideDatabase(
        javaxSqlDataSource: javax.sql.DataSource,
        liquibaseDatabaseShortName: String
    ): Database {
        val dialect = getDialect(liquibaseDatabaseShortName)
        val db = Database.connect(
            datasource = javaxSqlDataSource,
            databaseConfig = dialect?.let {
                DatabaseConfig { explicitDialect = dialect }
            },
        )
        return db
    }

    internal fun getDialect(liquibaseDatabaseShortName: String): VendorDialect? {
        return when (liquibaseDatabaseShortName) {
            "h2" -> H2Dialect()
            "mariadb" -> MariaDBDialect()
            "mysql" -> MysqlDialect()
            "oracle" -> OracleDialect()
            "postgresql" -> PostgreSQLDialect()
            "sqlserver" -> SQLServerDialect()
            "sqlite" -> SQLiteDialect()
            else -> null
        }
    }
}
