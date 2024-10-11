package momosetkn

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import momosetkn.liquibase.changelogs.customechannge.ExecuteCountTaskCustomChange
import momosetkn.liquibase.command.client.LiquibaseCommandClient
import momosetkn.liquibase.kotlin.parser.KotlinCompiledDatabaseChangeLog
import momosetkn.liquibase.kotlin.serializer.KotlinCompiledChangeLogSerializer
import momosetkn.utils.Constants
import java.nio.file.Paths

class CustomChangeMigrationSpec : FunSpec({
    val targetDatabaseServer = SharedResources.getTargetDatabaseServer()
    beforeSpec {
        KotlinCompiledChangeLogSerializer.sourceRootPath = Paths.get(Constants.TEST_RESOURCE_DIR)
    }
    beforeEach {
        targetDatabaseServer.startAndClear()
    }

    context("Migrate and serialize") {
        test("can migrate") {
            val client = LiquibaseCommandClient {
                global {
                    general {
                        showBanner = false
                    }
                }
            }
            val server = targetDatabaseServer.startedServer
            client.update(
                driver = server.driver,
                url = server.jdbcUrl,
                username = server.username,
                password = server.password,
                changelogFile = CustomChangeMigrationSpecChangelog1::class.qualifiedName!!,
            )

            ExecuteCountTaskCustomChange.executeCount shouldBe 1
        }
    }
})

class CustomChangeMigrationSpecChangelog1 : KotlinCompiledDatabaseChangeLog({
    changeSet(author = "momose (generated)", id = "100-10") {
        customChange(`class` = ExecuteCountTaskCustomChange::class)
    }
})
