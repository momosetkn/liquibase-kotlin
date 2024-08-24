databaseChangeLog {
     changeSet(id = "1724505385194-1", author = "momose (generated)") {
          createTable(tableName = "company") {
               column(type = "UUID", name = "id") {
                    constraints(nullable = false, primaryKeyName = "company_pkey", primaryKey = true)
               }
               column(type = "VARCHAR(256)", name = "name")
          }
     }

     changeSet(id = "1724505385194-2", author = "momose (generated)") {
          createTable(tableName = "employee") {
               column(type = "UUID", name = "id") {
                    constraints(nullable = false, primaryKeyName = "employee_pkey", primaryKey = true)
               }
               column(type = "UUID", name = "company_id") {
                    constraints(nullable = false)
               }
               column(type = "VARCHAR(256)", name = "new_name")
               column(type = "VARCHAR(256)", name = "not_null_name") {
                    constraints(nullable = false)
               }
               column(type = "VARCHAR(256)", name = "not_null_name2") {
                    constraints(nullable = false)
               }
          }
     }

     changeSet(id = "1724505385194-3", author = "momose (generated)") {
          addForeignKeyConstraint(deferrable = false, baseColumnNames = "company_id", constraintName = "employee_company_id_fkey", referencedTableName = "company", onDelete = "CASCADE", referencedColumnNames = "id", initiallyDeferred = false, onUpdate = "RESTRICT", baseTableName = "employee", validate = true)
     }

}
