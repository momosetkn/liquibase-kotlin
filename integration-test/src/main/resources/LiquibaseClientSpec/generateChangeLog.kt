package LiquibaseClientSpec

import momosetkn.liquibase.kotlin.parser.KotlinCompiledDatabaseChangeLog

class generateChangeLog : KotlinCompiledDatabaseChangeLog({
    changeSet(author = "momose (generated)", id = "1727978904617-1") {
        createTable(tableName = "COMPANY") {
            column(name = "ID", type = "UUID") {
                constraints(nullable = false, primaryKey = true, primaryKeyName = "PK_COMPANY")
            }
            column(name = "NAME", type = "VARCHAR(256)")
        }
    }

})
