package momosetkn.liquibase.kotlin.dsl

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import liquibase.change.core.CreateIndexChange
import liquibase.change.core.CreateTableChange
import liquibase.change.custom.CustomChangeWrapper
import liquibase.changelog.DatabaseChangeLog
import liquibase.sdk.resource.MockResourceAccessor

class ChangeLogBuilderDslSpec : FunSpec({
    data class TestData(
        val path: String,
        val databaseChangeLog: DatabaseChangeLog,
        val mockResourceAccessor: MockResourceAccessor,
        val dsl: ChangeLogBuilderDsl,
    )

    fun createTestData(): TestData {
        val path = "file.kts"
        val databaseChangeLog = DatabaseChangeLog(path)
        val mockResourceAccessor = MockResourceAccessor()
        val dsl = ChangeLogBuilderDsl(
            changeLog = databaseChangeLog,
            resourceAccessor = mockResourceAccessor
        )
        return TestData(path, databaseChangeLog, mockResourceAccessor, dsl)
    }
    test("createTable") {
        val testData = createTestData()
        testData.dsl.databaseChangeLog {
            changeSet(id = "1", author = "user") {
                createTable(tableName = "company") {
                    column(name = "id", type = "UUID") {
                        constraints(nullable = false, primaryKey = true)
                    }
                    column(name = "name", type = "VARCHAR(256)")
                }
            }
        }

        testData.databaseChangeLog.changeSets.size shouldBe 1
        val changes = testData.databaseChangeLog.changeSets.first().changes
        changes.size shouldBe 1
        changes.first().shouldBeInstanceOf<CreateTableChange>()
    }
    context("customChange") {
        test("customTaskChange") {
            var executeCallCount = 0
            var rollbackCallCount = 0
            val testData = createTestData()
            testData.dsl.databaseChangeLog {
                changeSet(id = "2", author = "user") {
                    customChange(
                        confirmationMessage = "hello world",
                        execute = {
                            executeCallCount = executeCallCount + 1
                            val mockParam = params["MockParam"] as MockParam
                            mockParam.param1 = mockParam.param1 + 1
                        },
                        rollback = {
                            rollbackCallCount = rollbackCallCount + 1
                            val mockParam = params["MockParam"] as MockParam
                            mockParam.param1 = mockParam.param1 - 1
                        }
                    ) {
                        param("key1", 1)
                        param("key2", "value2")
                        param("key3", null)
                        param("MockParam", MockParam)
                    }
                }
            }

            testData.databaseChangeLog.changeSets.size shouldBe 1
            val changes = testData.databaseChangeLog.changeSets.first().changes
            changes.size shouldBe 1
            changes.first().shouldBeInstanceOf<CustomChangeWrapper>()
        }
    }
    test("createIndex") {
        val testData = createTestData()
        testData.dsl.databaseChangeLog {
            changeSet(id = "3", author = "user") {
                createIndex(
                    associatedWith = "",
                    indexName = "project_users_id_fk",
                    tableName = "project"
                ) {
                    column(name = "column_name1", type = "varchar(255)")
                    column(name = "column_name2", type = "varchar(255)")
                }
            }
        }

        testData.databaseChangeLog.changeSets.size shouldBe 1
        val changes = testData.databaseChangeLog.changeSets.first().changes
        changes.size shouldBe 1
        changes.first().shouldBeInstanceOf<CreateIndexChange>()
        val typedChange = changes.first() as CreateIndexChange
        typedChange.columns.map { it.name } shouldBe listOf("column_name1", "column_name2")
    }
})

object MockParam {
    var param1: Int = 0
}
