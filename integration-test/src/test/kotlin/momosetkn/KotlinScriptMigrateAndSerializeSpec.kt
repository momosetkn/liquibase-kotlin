package momosetkn

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import momosetkn.liquibase.client.LiquibaseClient
import momosetkn.utils.Constants
import momosetkn.utils.DDLUtils.shouldBeEqualDdl
import momosetkn.utils.Database
import momosetkn.utils.ResourceUtils
import momosetkn.utils.ResourceUtils.getResourceAsString
import java.nio.file.Paths

class KotlinScriptMigrateAndSerializeSpec : FunSpec({
    beforeSpec {
        Database.start()
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
                changelogFile = PARSER_INPUT_CHANGELOG,
            )
            client.rollback(
                driver = container.driver,
                url = container.jdbcUrl,
                username = container.username,
                password = container.password,
                changelogFile = PARSER_INPUT_CHANGELOG,
                tag = "started",
            )
            client.update(
                driver = container.driver,
                url = container.jdbcUrl,
                username = container.username,
                password = container.password,
                changelogFile = PARSER_INPUT_CHANGELOG,
            )
            val actualSerializedChangeLogFile =
                Paths.get(Constants.RESOURCE_DIR, SERIALIZER_ACTUAL_CHANGELOG)
            val f = actualSerializedChangeLogFile.toFile()
            if (f.exists()) f.delete()
            client.generateChangelog(
                driver = container.driver,
                url = container.jdbcUrl,
                username = container.username,
                password = container.password,
                changelogFile = actualSerializedChangeLogFile.toString(),
            )

            // check database
            val expectedDdl = getResourceAsString(PARSER_EXPECT_DDL)
            Database.shouldBeEqualDdl(expectedDdl)

            // check serializer
            val actual = ResourceUtils.getResourceFileAsString(SERIALIZER_ACTUAL_CHANGELOG)
                .maskingChangeSet()
            val expect = getResourceAsString(SERIALIZER_EXPECT_CHANGELOG)
                .maskingChangeSet()
            actual shouldBe expect
        }
    }
}) {
    companion object {
        private val changeSetRegex = Regex("""changeSet\(author = "(.+)", id = "(\d+)-(\d)"\) \{""")

        private fun String.maskingChangeSet() =
            this.replace(changeSetRegex, "changeSet(author = \"**********\", id = \"*************-\$3\") {")

        private const val PARSER_INPUT_CHANGELOG =
            "KotlinScriptMigrateAndSerializeSpec/parser_input/db.changelog-all.kts"

        @Suppress("MaxLineLength")
        private const val SERIALIZER_ACTUAL_CHANGELOG =
            "KotlinScriptMigrateAndSerializeSpec/serializer_actual/db.changelog-0.kts"

        // To avoid auto-formatting, do not add a file extension.
        @Suppress("MaxLineLength")
        private const val SERIALIZER_EXPECT_CHANGELOG =
            "KotlinScriptMigrateAndSerializeSpec/serializer_expect/db.changelog-0_kts"
        private const val PARSER_EXPECT_DDL = "KotlinScriptMigrateAndSerializeSpec/parser_expect/db.ddl-0.sql"
    }
}
