databaseChangeLog {
    // employee
    changeSet(author = "momose (generated)", id = "1715520327312-20") {
        createTable(tableName = "employee") {
            column(name = "id", type = "UUID") {
                constraints(nullable = false, primaryKey = true)
            }
            column(name = "company_id", type = "UUID") {
                constraints(nullable = false)
            }
            column(name = "name", type = "VARCHAR(256)")
            column(name = "not_null_name", type = "VARCHAR(256)") {
                constraints(nullable = false)
            }
            column(name = "not_null_name2", type = "VARCHAR(256)") {
                constraints(nullable = false)
            }
        }
    }
    changeSet(author = "momose (generated)", id = "1715520327312-21") {
        addForeignKeyConstraint(
            baseColumnNames = "company_id",
            baseTableName = "employee",
            constraintName = "employee_company_id_fkey",
            deferrable = false,
            initiallyDeferred = false,
            onDelete = "CASCADE",
            onUpdate = "RESTRICT",
            referencedColumnNames = "id",
            referencedTableName = "company",
            validate = true,
        )
    }

    changeSet(author = "momose (generated)", id = "1715520327312-30") {
        dropColumn(columnName = "not_null_name2", tableName = "employee")
        rollback {
            addColumn(tableName = "employee") {
                column(name = "not_null_name2", type = "VARCHAR(256)") {
                    constraints(nullable = false)
                }
            }
        }
    }

    changeSet(author = "momose (generated)", id = "1715520327312-31") {
        renameColumn(
            tableName = "employee",
            oldColumnName = "name",
            newColumnName = "new_name",
            columnDataType = "VARCHAR(256)",
        )
    }

    changeSet(author = "momose (generated)", id = "1715520327312-40") {
        customKomapperJdbcChange(
            execute = { db ->
                val query = QueryDsl.executeScript(
                    """
                    CREATE TABLE created_by_komapper (
                        id uuid NOT NULL,
                        name character varying(256)
                    );
                    """.trimIndent()
                )
                db.runQuery(query)
            },
            rollback = { db ->
                val query = QueryDsl.executeScript("DROP TABLE created_by_komapper")
                db.runQuery(query)
            },
        )
    }
}
