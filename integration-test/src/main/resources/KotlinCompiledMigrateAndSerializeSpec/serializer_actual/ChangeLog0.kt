package KotlinCompiledMigrateAndSerializeSpec.serializer_actual

import momosetkn.liquibase.kotlin.parser.KotlinCompiledDatabaseChangeLog

class ChangeLog0 : KotlinCompiledDatabaseChangeLog({
    changeSet(author = "momose (generated)", id = "1726746037188-1") {
        createTable(tableName = "company") {
            column(name = "id", type = "UUID") {
                constraints(nullable = false, primaryKey = true, primaryKeyName = "company_pkey")
            }
            column(name = "name", type = "VARCHAR(256)")
        }
    }

    changeSet(author = "momose (generated)", id = "1726746037188-2") {
        createTable(tableName = "created_by_komapper") {
            column(name = "id", type = "UUID") {
                constraints(nullable = false)
            }
            column(name = "name", type = "VARCHAR(256)")
        }
    }

    changeSet(author = "momose (generated)", id = "1726746037188-3") {
        createTable(tableName = "employee") {
            column(name = "id", type = "UUID") {
                constraints(nullable = false, primaryKey = true, primaryKeyName = "employee_pkey")
            }
            column(name = "company_id", type = "UUID") {
                constraints(nullable = false)
            }
            column(name = "new_name", type = "VARCHAR(256)")
            column(name = "not_null_name", type = "VARCHAR(256)") {
                constraints(nullable = false)
            }
        }
    }

    changeSet(author = "momose (generated)", id = "1726746037188-4") {
        addForeignKeyConstraint(baseColumnNames = "company_id", baseTableName = "employee", constraintName = "employee_company_id_fkey", deferrable = false, initiallyDeferred = false, onDelete = "CASCADE", onUpdate = "RESTRICT", referencedColumnNames = "id", referencedTableName = "company", validate = true)
    }

})
