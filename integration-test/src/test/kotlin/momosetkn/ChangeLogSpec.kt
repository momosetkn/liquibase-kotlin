package momosetkn

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.mpp.file
import komapper.databasechangelog
import momosetkn.liquibase.command.client.LiquibaseCommandClient
import momosetkn.utils.DatabaseKomapperExtensions.komapperDb
import momosetkn.utils.DatabaseServer
import momosetkn.utils.InterchangeableChangeLog
import momosetkn.utils.set
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.single

class ChangeLogSpec : FunSpec({
    beforeEach {
        DatabaseServer.start()
    }
    afterEach {
        DatabaseServer.stop()
    }
    val client = LiquibaseCommandClient {
        globalArgs {
            general {
                showBanner = false
            }
        }
    }
    fun subject() {
        val container = DatabaseServer.startedContainer
        client.update(
            driver = container.driver,
            url = container.jdbcUrl,
            username = container.username,
            password = container.password,
            changelogFile = InterchangeableChangeLog::class.qualifiedName!!,
        )
    }

    context("property") {
        InterchangeableChangeLog.set {
            property(name = "key1", value = "value1")
            property(name = "key2", value = "value2")
            property(file = "ChangeLogSpec/prop.txt")
            changeSet(author = "user", id = "100") {
                tagDatabase("\${key1}_\${file_key3}")
            }
        }
        test("can use property") {
            subject()
            val db = DatabaseServer.komapperDb()
            val d = Meta.databasechangelog
            val result = db.runQuery {
                QueryDsl.from(d).single()
            }
            result.tag shouldBe "value1_file_value3"
        }
    }
})
