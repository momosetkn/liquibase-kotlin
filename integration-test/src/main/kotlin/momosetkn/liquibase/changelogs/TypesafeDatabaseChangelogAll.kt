package momosetkn.liquibase.changelogs

import momosetkn.liquibase.kotlin.parser.TypesafeDatabaseChangeLog

class TypesafeDatabaseChangelogAll : TypesafeDatabaseChangeLog({
    includeAll("momosetkn.liquibase.changelogs.main")
})
