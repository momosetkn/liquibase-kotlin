package momosetkn.liquibase.changelogs

import momosetkn.liquibase.kotlin.parser.KotlinTypesafeDatabaseChangeLog

class TypesafeDatabaseChangelogAll : KotlinTypesafeDatabaseChangeLog({
    property(name = "includeDir", value = "momosetkn.liquibase.changelogs.main")
    includeAll("\${includeDir}")
})
