package momosetkn.liquibase.changelogs

import momosetkn.liquibase.kotlin.parser.KotlinCompiledDatabaseChangeLog

class CompiledDatabaseChangelogAll : KotlinCompiledDatabaseChangeLog({
    property(name = "includeDir", value = "momosetkn.liquibase.changelogs.main")
    includeAll("\${includeDir}")
})
