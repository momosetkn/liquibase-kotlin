databaseChangeLog {
    changeSet(author = "momose (generated)", id = "1727978904528-1") {
        createTable(tableName = "COMPANY") {
            column(name = "ID", type = "UUID") {
                constraints(nullable = false, primaryKey = true, primaryKeyName = "PK_COMPANY")
            }
            column(name = "NAME", type = "VARCHAR(256)")
        }
    }

    changeSet(author = "momose (generated)", id = "1727978904528-2") {
        createTable(tableName = "CREATED_BY_KOMAPPER") {
            column(name = "ID", type = "UUID") {
                constraints(nullable = false)
            }
            column(name = "NAME", type = "VARCHAR(256)")
        }
    }

    changeSet(author = "momose (generated)", id = "1727978904528-3") {
        createTable(tableName = "EMPLOYEE") {
            column(name = "ID", type = "UUID") {
                constraints(nullable = false, primaryKey = true, primaryKeyName = "PK_EMPLOYEE")
            }
            column(name = "COMPANY_ID", type = "UUID") {
                constraints(nullable = false)
            }
            column(name = "NEW_NAME", type = "VARCHAR(256)")
            column(name = "NOT_NULL_NAME", type = "VARCHAR(256)") {
                constraints(nullable = false)
            }
        }
    }

    changeSet(author = "momose (generated)", id = "1727978904528-4") {
        createIndex(associatedWith = "", indexName = "EMPLOYEE_COMPANY_ID_FKEY_INDEX_21", tableName = "EMPLOYEE") {
            column(name = "COMPANY_ID")
        }
    }

    changeSet(author = "momose (generated)", id = "1727978904528-5") {
        addForeignKeyConstraint(baseColumnNames = "COMPANY_ID", baseTableName = "EMPLOYEE", constraintName = "EMPLOYEE_COMPANY_ID_FKEY", deferrable = false, initiallyDeferred = false, onDelete = "CASCADE", onUpdate = "RESTRICT", referencedColumnNames = "ID", referencedTableName = "COMPANY", validate = true)
    }

}
