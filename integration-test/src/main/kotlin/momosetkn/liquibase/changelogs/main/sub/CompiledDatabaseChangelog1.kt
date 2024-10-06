package momosetkn.liquibase.changelogs.main.sub

import momosetkn.liquibase.kotlin.change.custom.exposed.customExposedMigrationChange
import momosetkn.liquibase.kotlin.change.custom.jooq.customJooqChange
import momosetkn.liquibase.kotlin.change.custom.komapper.customKomapperJdbcChange
import momosetkn.liquibase.kotlin.parser.KotlinCompiledDatabaseChangeLog
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import org.komapper.core.dsl.QueryDsl

class CompiledDatabaseChangelog1 : KotlinCompiledDatabaseChangeLog({
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
        createIndex(associatedWith = "", indexName = "EMPLOYEE_COMPANY_ID_FKEY_INDEX_21", tableName = "EMPLOYEE") {
            column(name = "company_id")
        }
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

    changeSet(author = "momose (generated)", id = "1715520327312-50") {
        customJooqChange(
            execute = { db ->
                val query =
                    """
                    CREATE TABLE created_by_jooq (
                        id uuid NOT NULL,
                        name character varying(256)
                    );
                    """.trimIndent()
                db.execute(query)
            },
            rollback = { db ->
                val query = "DROP TABLE created_by_jooq"
                db.execute(query)
            },
        )
    }

    changeSet(author = "momose (generated)", id = "1715520327312-60") {
        @Suppress("MagicNumber")
        val createdByExposed = object : Table("created_by_exposed") {
            val id = integer("id").autoIncrement()
            val name = varchar("name", 256)
            override val primaryKey = PrimaryKey(id)
        }
        customExposedMigrationChange(
            execute = { db ->
                transaction(db) {
                    SchemaUtils.create(createdByExposed)
                }
            },
            rollback = { db ->
                transaction(db) {
                    SchemaUtils.drop(createdByExposed)
                }
            },
        )
    }
})
