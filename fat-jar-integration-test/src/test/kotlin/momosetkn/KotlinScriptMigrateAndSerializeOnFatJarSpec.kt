package momosetkn

import io.kotest.core.spec.style.FunSpec
import momosetkn.utils.Constants
import momosetkn.utils.DDLUtils.sql
import momosetkn.utils.DDLUtils.toMainDdl
import momosetkn.utils.Database
import momosetkn.utils.JarUtils
import momosetkn.utils.ResourceUtils
import momosetkn.utils.ResourceUtils.getResourceAsString
import momosetkn.utils.shouldMatchWithoutLineBreaks
import java.nio.file.Paths

class KotlinScriptMigrateAndSerializeOnFatJarSpec : FunSpec({
    beforeSpec {
        JarUtils.build()
        Database.start()
    }
    afterSpec {
        Database.stop()
    }

    context("Migrate and serialize") {
        test("can migrate") {
            val container = Database.startedContainer
            JarUtils.run(
                "update",
                container.driver,
                container.jdbcUrl,
                container.username,
                container.password,
                PARSER_INPUT_CHANGELOG
            )
            JarUtils.run(
                "rollback",
                container.driver,
                container.jdbcUrl,
                container.username,
                container.password,
                PARSER_INPUT_CHANGELOG,
                "started",
            )
            JarUtils.run(
                "update",
                container.driver,
                container.jdbcUrl,
                container.username,
                container.password,
                PARSER_INPUT_CHANGELOG
            )
            val actualSerializedChangeLogFile =
                Paths.get(Constants.RESOURCE_DIR, SERIALIZER_ACTUAL_CHANGELOG)
            val f = actualSerializedChangeLogFile.toFile()
            if (f.exists()) f.delete()
            JarUtils.run(
                "generateChangelog",
                container.driver,
                container.jdbcUrl,
                container.username,
                container.password,
                actualSerializedChangeLogFile.toString()
            )

            // check database
            val expectedDdl = getResourceAsString(PARSER_EXPECT_DDL)
            Database.generateDdl().toMainDdl() shouldMatchWithoutLineBreaks sql(expectedDdl)

            // check serializer
            val actual = ResourceUtils.getResourceFileAsString(SERIALIZER_ACTUAL_CHANGELOG)
                .maskingChangeSet()
            val expect = getResourceAsString(SERIALIZER_EXPECT_CHANGELOG)
                .maskingChangeSet()
            actual shouldMatchWithoutLineBreaks expect
        }
    }
}) {
    companion object {
        private val changeSetRegex = Regex("""changeSet\(author = "(.+)", id = "(\d+)-(\d)"\) \{""")

        private fun String.maskingChangeSet() =
            this.replace(changeSetRegex, "changeSet(author = \"**********\", id = \"*************-\$3\") {")

        private const val PARSER_INPUT_CHANGELOG =
            "KotlinScriptMigrateAndSerializeOnFatJarSpec/parser_input/db.changelog-all.kts"

        @Suppress("MaxLineLength")
        private const val SERIALIZER_ACTUAL_CHANGELOG =
            "KotlinScriptMigrateAndSerializeOnFatJarSpec/serializer_actual/db.changelog-0.kts"

        // To avoid auto-formatting, do not add a file extension.
        @Suppress("MaxLineLength")
        private const val SERIALIZER_EXPECT_CHANGELOG =
            "KotlinScriptMigrateAndSerializeOnFatJarSpec/serializer_expect/db.changelog-0_kts"
        private const val PARSER_EXPECT_DDL = "KotlinScriptMigrateAndSerializeOnFatJarSpec/parser_expect/db.ddl-0.sql"
    }
}
