package momosetkn

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import momosetkn.liquibase.changelogs.customechannge.ExecuteCountTaskCustomChange
import momosetkn.liquibase.client.LiquibaseClient
import momosetkn.liquibase.kotlin.parser.KotlinCompiledDatabaseChangeLog
import momosetkn.liquibase.kotlin.serializer.KotlinCompiledChangeLogSerializer
import momosetkn.utils.Constants
import momosetkn.utils.Database
import java.nio.file.Paths

class CustomChangeMigrationSpec : FunSpec({
    beforeSpec {
        Database.start()
        KotlinCompiledChangeLogSerializer.sourceRootPath = Paths.get(Constants.RESOURCE_DIR)
    }
    afterSpec {
        Database.stop()
    }

    context("Migrate and serialize") {
        test("can migrate") {
            val client = LiquibaseClient {
                globalArgs {
                    general {
                        showBanner = false
                    }
                }
            }
            val container = Database.startedContainer
            client.update(
                driver = container.driver,
                url = container.jdbcUrl,
                username = container.username,
                password = container.password,
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
