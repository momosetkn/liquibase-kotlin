package momosetkn

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import momosetkn.Constants.RESOURCE_DIR
import momosetkn.liquibase.changelogs.customechannge.ExecuteCountTaskCustomChange
import momosetkn.liquibase.client.LiquibaseClient
import momosetkn.liquibase.kotlin.parser.KotlinTypesafeDatabaseChangeLog
import momosetkn.liquibase.kotlin.serializer.KotlinTypesafeChangeLogSerializer
import java.nio.file.Paths

class CustomChangeMigrationSpec : FunSpec({
    beforeSpec {
        Database.start()
        KotlinTypesafeChangeLogSerializer.sourceRootPath = Paths.get(RESOURCE_DIR)
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
                driver = "org.postgresql.Driver",
                url = container.jdbcUrl,
                username = container.username,
                password = container.password,
                changelogFile = CustomChangeMigrationSpecChangelog1::class.qualifiedName!!,
            )

            ExecuteCountTaskCustomChange.executeCount shouldBe 1
        }
    }
})

class CustomChangeMigrationSpecChangelog1 : KotlinTypesafeDatabaseChangeLog({
    changeSet(author = "momose (generated)", id = "100-10") {
        customChange(`class` = ExecuteCountTaskCustomChange::class)
    }
})
