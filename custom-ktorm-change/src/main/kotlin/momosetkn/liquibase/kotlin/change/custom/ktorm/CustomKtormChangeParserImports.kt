package momosetkn.liquibase.kotlin.change.custom.ktorm

import momosetkn.liquibase.kotlin.parser.KotlinScriptParserImports

class CustomKtormChangeParserImports : KotlinScriptParserImports {
    override fun imports() = listOf(
        "momosetkn.liquibase.kotlin.change.custom.ktorm.customKtormChange",
    )
}
