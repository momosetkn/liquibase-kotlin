package momosetkn.liquibase.kotlin.change

import org.komapper.jdbc.JdbcDatabase
import org.komapper.jdbc.JdbcDialects

object LiquibaseKomapperConfig {
    var provideJdbcDatabase: (
        javaxSqlDataSource: javax.sql.DataSource,
        liquibaseDatabaseShortName: String
    ) -> JdbcDatabase = { javaxSqlDataSource, liquibaseDatabaseShortName ->
        JdbcDatabase(javaxSqlDataSource, JdbcDialects.get(liquibaseDatabaseShortName))
    }
}
