package momosetkn.liquibase.changelogs

import momosetkn.liquibase.kotlin.parser.KotlinTypesafeDatabaseChangeLog

class TypesafeDatabaseChangelogAll : KotlinTypesafeDatabaseChangeLog({
    includeAll("momosetkn.liquibase.changelogs.main")
})
