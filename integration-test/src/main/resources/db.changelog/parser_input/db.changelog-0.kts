databaseChangeLog {
    changeSet(author = "momose (generated)", id = "1715520327312-1") {
        createTable(tableName = "company") {
            column(name = "id", type = "VARBINARY(16)") {
                constraints(nullable = false, primaryKey = true)
            }
            column(name = "name", type = "VARCHAR(256)")
        }
    }
}
