package momosetkn.liquibase.changelogs.main

import momosetkn.liquibase.kotlin.parser.KotlinTypesafeDatabaseChangeLog

class TypesafeDatabaseChangelog0 : KotlinTypesafeDatabaseChangeLog({
    changeSet(author = "momose (generated)", id = "1715520327312-0") {
        tagDatabase("started")
    }

    changeSet(author = "momose (generated)", id = "1715520327312-10") {
        createTable(tableName = "company") {
            column(name = "id", type = "UUID") {
                constraints(nullable = false, primaryKey = true)
            }
            column(name = "name", type = "VARCHAR(256)")
        }
    }
})
