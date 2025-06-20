package momosetkn

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotInclude
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.verify
import liquibase.change.custom.CustomChange
import liquibase.change.custom.CustomTaskChange
import liquibase.change.custom.CustomTaskRollback
import liquibase.changelog.ChangeSet
import liquibase.database.Database
import liquibase.exception.ValidationErrors
import liquibase.resource.ResourceAccessor
import momosetkn.liquibase.client.LiquibaseClient
import momosetkn.liquibase.client.LiquibaseDatabaseFactory
import momosetkn.liquibase.extension.SlowLogChangeExecListener
import momosetkn.liquibase.kotlin.serializer.KotlinCompiledChangeLogSerializer
import momosetkn.utils.Constants
import momosetkn.utils.DatabaseServer
import momosetkn.utils.MutableChangeLog
import java.nio.file.Paths

class SlowLogChangeExecListenerSpec : FunSpec({
    lateinit var targetDatabaseServer: DatabaseServer

    val log = mockk<org.slf4j.Logger>(relaxed = true)
    val changeExecListener = SlowLogChangeExecListener(
        thresholdMillis = 1_000,
        log = log
    )

    fun createLiquibaseClient(): LiquibaseClient {
        val databaseConfig = targetDatabaseServer.startedServer
        val database = LiquibaseDatabaseFactory.create(
            driver = databaseConfig.driver,
            url = databaseConfig.jdbcUrl,
            username = databaseConfig.username,
            password = databaseConfig.password,
        )
        return LiquibaseClient(
            changeLogFile = MutableChangeLog::class.qualifiedName!!,
            database = database,
        ).also {
            it.changeExecListener = changeExecListener
        }
    }

    beforeSpec {
        KotlinCompiledChangeLogSerializer.sourceRootPath = Paths.get(Constants.TEST_RESOURCE_DIR)
    }
    beforeEach {
        targetDatabaseServer = SharedResources.getTargetDatabaseServer().also {
            it.startAndClear()
        }
        clearAllMocks()
    }

    context("slow execute change") {
        MutableChangeLog.set {
            changeSet(author = "user", id = "100") {
                customChange(`class` = SlowExecuteCustomChange::class.qualifiedName!!)
            }
        }
        test("log slow changeSet") {
            val liquibaseClient = createLiquibaseClient()
            liquibaseClient.update()

            verify {
                log.warn(
                    "Slow ChangeSet: {} {} took {}ms",
                    "executed",
                    withArg { changeSet ->
                        changeSet.toString().shouldBe("momosetkn.utils.MutableChangeLog::100::user")
                    },
                    withArg { duration: Long ->
                        duration.shouldBeGreaterThan(changeExecListener.thresholdMillis)
                    },
                )
            }
        }
    }

    context("slow rollback change") {
        MutableChangeLog.set {
            changeSet(author = "user", id = "0") {
                tagDatabase(tag = "initial")
            }
            changeSet(author = "user", id = "100") {
                customChange(`class` = SlowRollbackCustomChange::class.qualifiedName!!)
            }
        }
        test("slow changeSet warn log by warn log") {
            val liquibaseClient = createLiquibaseClient()
            liquibaseClient.update()
            liquibaseClient.rollback(tagToRollBackTo = "initial")

            verify {
                log.warn(
                    "Slow ChangeSet: {} {} took {}ms",
                    "rolled back",
                    withArg { changeSet ->
                        changeSet.toString().shouldBe("momosetkn.utils.MutableChangeLog::100::user")
                    },
                    withArg { duration: Long -> duration.shouldBeGreaterThan(changeExecListener.thresholdMillis) },
                )
            }
        }
    }
    context("not slow change") {
        MutableChangeLog.set {
            changeSet(author = "user", id = "0") {
                tagDatabase(tag = "initial")
            }
            changeSet(author = "user", id = "100") {
                customChange(`class` = ExecuteCustomChange::class.qualifiedName!!)
            }
        }
        test("not warn log") {
            val liquibaseClient = createLiquibaseClient()
            liquibaseClient.update()
            liquibaseClient.rollback(tagToRollBackTo = "initial")

            verify(exactly = 0) {
                log.warn(
                    any<String>(),
                    any<String>(),
                    any<ChangeSet>(),
                    any<Long>(),
                )
            }
            verify {
                log.debug(
                    "ChangeSet: {} {} took {}ms",
                    "executed",
                    withArg { changeSet ->
                        changeSet.toString().shouldBe("momosetkn.utils.MutableChangeLog::100::user")
                    },
                    withArg { duration: Long -> duration.shouldBeLessThan(changeExecListener.thresholdMillis) },
                )
            }
            verify {
                log.debug(
                    "ChangeSet: {} {} took {}ms",
                    "rolled back",
                    withArg { changeSet ->
                        changeSet.toString().shouldBe("momosetkn.utils.MutableChangeLog::100::user")
                    },
                    withArg { duration: Long -> duration.shouldBeLessThan(changeExecListener.thresholdMillis) },
                )
            }
            verify(exactly = 0) {
                log.warn(
                    withArg { message: String ->
                        message.shouldNotInclude("Slow ChangeSet")
                    },
                    any<String>(),
                    any<ChangeSet>(),
                    any<Long>(),
                )
            }
        }
    }
})

interface RollbackTaskCustomChange : CustomChange, CustomTaskChange, CustomTaskRollback {
    override fun getConfirmationMessage(): String {
        return "confirmationMessage"
    }

    override fun setUp() = Unit

    override fun setFileOpener(resourceAccessor: ResourceAccessor) {
        // no-op
    }

    override fun validate(database: Database): ValidationErrors {
        return ValidationErrors()
    }
}

class SlowExecuteCustomChange : RollbackTaskCustomChange {
    override fun execute(database: Database) {
        Thread.sleep(1_100)
        println("execute")
    }

    override fun rollback(database: Database) {
        println("rollback")
    }
}

class SlowRollbackCustomChange : RollbackTaskCustomChange {
    override fun execute(database: Database) {
        println("execute")
    }

    override fun rollback(database: Database) {
        Thread.sleep(1_100)
        println("rollback")
    }
}

class ExecuteCustomChange : RollbackTaskCustomChange {
    override fun execute(database: Database) {
        println("execute")
    }

    override fun rollback(database: Database) {
        println("rollback")
    }
}
