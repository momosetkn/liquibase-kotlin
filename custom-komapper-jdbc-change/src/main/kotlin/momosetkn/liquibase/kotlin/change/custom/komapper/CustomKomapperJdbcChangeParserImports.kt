package momosetkn.liquibase.kotlin.change.custom.komapper

import momosetkn.liquibase.kotlin.parser.KotlinScriptParserImports

class CustomKomapperJdbcChangeParserImports : KotlinScriptParserImports {
    override fun imports() = listOf(
        "momosetkn.liquibase.kotlin.change.custom.komapper.customKomapperJdbcChange",
        "org.komapper.core.dsl.QueryDsl",
    )
}
