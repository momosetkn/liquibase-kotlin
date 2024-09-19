package momosetkn.liquibase.kotlin.change

import momosetkn.liquibase.kotlin.parser.KotlinScriptParserImports

class CustomKomapperJdbcChangeParserImports : KotlinScriptParserImports {
    override fun imports() = listOf(
        "momosetkn.liquibase.kotlin.change.customKomapperJdbcChange",
        "momosetkn.liquibase.kotlin.parser.KotlinDatabaseChangeLog",
        "org.komapper.core.dsl.QueryDsl",
    )
}
