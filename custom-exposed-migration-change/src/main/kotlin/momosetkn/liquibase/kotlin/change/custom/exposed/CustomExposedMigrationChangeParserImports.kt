package momosetkn.liquibase.kotlin.change.custom.exposed

import momosetkn.liquibase.kotlin.parser.KotlinScriptParserImports

class CustomExposedMigrationChangeParserImports : KotlinScriptParserImports {
    override fun imports() = listOf(
        "momosetkn.liquibase.kotlin.change.custom.exposed.customExposedMigrationChange",
        "org.jetbrains.exposed.v1.core.*",
        "org.jetbrains.exposed.v1.jdbc.*",
        "org.jetbrains.exposed.v1.jdbc.transactions.transaction",
    )
}
