package momosetkn.liquibase.kotlin.change.custom.jooq

import org.jooq.SQLDialect
import org.jooq.impl.DefaultDSLContext

object LiquibaseJooqConfig {
    var provideDSLContext: (
        javaxSqlDataSource: javax.sql.DataSource,
        liquibaseDatabaseShortName: String
    ) -> org.jooq.DSLContext = { javaxSqlDataSource, liquibaseDatabaseShortName ->
        DefaultDSLContext(javaxSqlDataSource, getDialect(liquibaseDatabaseShortName))
    }

    private fun getDialect(liquibaseDatabaseShortName: String): SQLDialect {
        return SQLDialect.entries
            .find { it.name.lowercase() == liquibaseDatabaseShortName }
            ?: SQLDialect.DEFAULT
    }
}
