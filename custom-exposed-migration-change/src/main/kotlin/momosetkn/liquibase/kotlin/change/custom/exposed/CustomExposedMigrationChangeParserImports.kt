package momosetkn.liquibase.kotlin.change.custom.exposed

import momosetkn.liquibase.kotlin.parser.KotlinScriptParserImports

class CustomExposedMigrationChangeParserImports : KotlinScriptParserImports {
    override fun imports() = listOf(
        "momosetkn.liquibase.kotlin.change.custom.exposed.customExposedMigrationChange",
        "org.jetbrains.exposed.sql.*",
        "org.jetbrains.exposed.sql.transactions.transaction",
    )
}
