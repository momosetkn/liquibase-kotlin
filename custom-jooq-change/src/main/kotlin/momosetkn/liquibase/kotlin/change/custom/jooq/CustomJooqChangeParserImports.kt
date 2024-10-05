package momosetkn.liquibase.kotlin.change.custom.jooq

import momosetkn.liquibase.kotlin.parser.KotlinScriptParserImports

class CustomJooqChangeParserImports : KotlinScriptParserImports {
    override fun imports() = listOf(
        "momosetkn.liquibase.kotlin.change.custom.jooq.customJooqChange",
        "org.jooq.core.dsl.QueryDsl",
    )
}
