package momosetkn.liquibase.kotlin.change.custom.jooq

import org.jooq.SQLDialect
import org.jooq.impl.DefaultDSLContext

object LiquibaseJooqConfig {
    private val dialectMap =
        SQLDialect.entries.associateBy { it.name.lowercase() } + mapOf(
            "postgresql" to SQLDialect.POSTGRES,
        )

    var provideDSLContext: (
        javaxSqlDataSource: javax.sql.DataSource,
        liquibaseDatabaseShortName: String
    ) -> org.jooq.DSLContext = ::defaultProvideDSLContext

    fun defaultProvideDSLContext(
        javaxSqlDataSource: javax.sql.DataSource,
        liquibaseDatabaseShortName: String
    ): org.jooq.DSLContext {
        return DefaultDSLContext(javaxSqlDataSource, getDialect(liquibaseDatabaseShortName))
    }

    private fun getDialect(liquibaseDatabaseShortName: String): SQLDialect {
        return dialectMap[liquibaseDatabaseShortName] ?: SQLDialect.DEFAULT
    }
}
