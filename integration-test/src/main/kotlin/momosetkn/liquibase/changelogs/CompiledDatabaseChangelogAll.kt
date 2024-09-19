package momosetkn.liquibase.changelogs

import momosetkn.liquibase.kotlin.parser.KotlinDatabaseChangeLog

class CompiledDatabaseChangelogAll : KotlinDatabaseChangeLog({
    property(name = "includeDir", value = "momosetkn.liquibase.changelogs.main")
    includeAll("\${includeDir}")
})
