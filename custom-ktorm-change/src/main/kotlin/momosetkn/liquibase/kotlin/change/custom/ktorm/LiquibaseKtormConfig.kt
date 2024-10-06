package momosetkn.liquibase.kotlin.change.custom.ktorm

import org.ktorm.database.Database
import org.ktorm.database.SqlDialect
import java.util.ServiceLoader

object LiquibaseKtormConfig {
    private val dialectMap = run {
        val loader = ServiceLoader.load(SqlDialect::class.java)
        loader
            .associateBy {
                val className = it::class.simpleName
                checkNotNull(className)
                    .removeSuffix("Dialect").lowercase()
            }
    }

    var provideDatabase: (
        javaxSqlDataSource: javax.sql.DataSource,
        liquibaseDatabaseShortName: String
    ) -> Database = LiquibaseKtormConfig::defaultProvideDatabase

    fun defaultProvideDatabase(
        javaxSqlDataSource: javax.sql.DataSource,
        liquibaseDatabaseShortName: String
    ): Database {
        val dialect = getDialect(liquibaseDatabaseShortName)
        val database = Database.connect(
            javaxSqlDataSource,
            dialect
        )
        return database
    }

    internal fun getDialect(liquibaseDatabaseShortName: String): SqlDialect {
        return dialectMap[liquibaseDatabaseShortName] ?: StandardSqlDialect
    }
}

object StandardSqlDialect : SqlDialect
