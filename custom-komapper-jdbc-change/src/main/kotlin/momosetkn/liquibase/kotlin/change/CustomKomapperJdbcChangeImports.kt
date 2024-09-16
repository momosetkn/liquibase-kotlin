package momosetkn.liquibase.kotlin.change

import momosetkn.liquibase.kotlin.parser.KotlinScriptImports

class CustomKomapperJdbcChangeImports : KotlinScriptImports {
    override fun imports() = listOf(
        "momosetkn.liquibase.kotlin.change.customKomapperJdbcChange",
        "momosetkn.liquibase.kotlin.parser.KotlinTypesafeDatabaseChangeLog",
        "org.komapper.core.dsl.QueryDsl",
    )
}
