package momosetkn.command

import java.io.File
import java.io.PrintWriter

fun main() {
    /*
    // https://docs.liquibase.com/change-types/home.html
console.log(Array.from(document.querySelectorAll("#mc-main-content a")).reduce((acc, cur) => acc + "\n"+(cur.href),""))
     */
    val codeString = code()

    writeFile(codeString)
}

private fun code(): String {
    val codeString =
        listOf(
            """
            // init
            """.trimIndent(),
            generateCommand("https://docs.liquibase.com/commands/init/copy.html"),
            generateCommand("https://docs.liquibase.com/commands/init/project.html"),
            generateCommand("https://docs.liquibase.com/commands/init/start-h2.html"),
            """
            // update
            """.trimIndent(),
            generateCommand("https://docs.liquibase.com/commands/update/update.html"),
            generateCommand("https://docs.liquibase.com/commands/update/update-sql.html"),
            generateCommand("https://docs.liquibase.com/commands/update/update-count.html"),
            generateCommand("https://docs.liquibase.com/commands/update/update-count-sql.html"),
            generateCommand("https://docs.liquibase.com/commands/update/update-to-tag.html"),
            generateCommand("https://docs.liquibase.com/commands/update/update-to-tag-sql.html"),
            generateCommand("https://docs.liquibase.com/commands/update/update-testing-rollback.html"),
            generateCommand("https://docs.liquibase.com/commands/update/update-one-changeset.html"),
            generateCommand("https://docs.liquibase.com/commands/update/update-one-changeset-sql.html"),
            """
            // rollback
            """.trimIndent(),
            generateCommand("https://docs.liquibase.com/commands/rollback/rollback.html"),
            generateCommand("https://docs.liquibase.com/commands/rollback/rollback-sql.html"),
            generateCommand("https://docs.liquibase.com/commands/rollback/rollback-to-date.html"),
            generateCommand("https://docs.liquibase.com/commands/rollback/rollback-to-date-sql.html"),
            generateCommand("https://docs.liquibase.com/commands/rollback/rollback-count.html"),
            generateCommand("https://docs.liquibase.com/commands/rollback/rollback-count-sql.html"),
            generateCommand("https://docs.liquibase.com/commands/rollback/future-rollback-sql.html"),
            generateCommand("https://docs.liquibase.com/commands/rollback/future-rollback-from-tag-sql.html"),
            generateCommand("https://docs.liquibase.com/commands/rollback/future-rollback-count-sql.html"),
            generateCommand("https://docs.liquibase.com/commands/rollback/rollback-one-changeset.html"),
            generateCommand("https://docs.liquibase.com/commands/rollback/rollback-one-changeset-sql.html"),
            generateCommand("https://docs.liquibase.com/commands/rollback/rollback-one-update.html"),
            generateCommand("https://docs.liquibase.com/commands/rollback/rollback-one-update-sql.html"),
            """
            // inspection
            """.trimIndent(),
            generateCommand("https://docs.liquibase.com/commands/inspection/diff.html"),
            generateCommand("https://docs.liquibase.com/commands/inspection/diff-changelog.html"),
            generateCommand("https://docs.liquibase.com/commands/inspection/snapshot.html"),
            generateCommand("https://docs.liquibase.com/commands/inspection/snapshot-reference.html"),
            generateCommand("https://docs.liquibase.com/commands/inspection/generate-changelog.html"),
            generateCommand("https://docs.liquibase.com/commands/inspection/diff-json.html"),
            """
            // change-tracking
            """.trimIndent(),
            generateCommand("https://docs.liquibase.com/commands/change-tracking/history.html"),
            generateCommand("https://docs.liquibase.com/commands/change-tracking/status.html"),
            generateCommand("https://docs.liquibase.com/commands/change-tracking/unexpected-changesets.html"),
            generateCommand("https://docs.liquibase.com/commands/change-tracking/connect.html"),
            generateCommand("https://docs.liquibase.com/commands/change-tracking/dbcl-history.html"),

            """
            // utility
            """.trimIndent(),
            generateCommand("https://docs.liquibase.com/commands/utility/db-doc.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/tag.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/tag-exists.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/validate.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/calculate-checksum.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/clear-checksums.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/list-locks.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/release-locks.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/changelog-sync.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/changelog-sync-sql.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/changelog-sync-to-tag.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/changelog-sync-to-tag-sql.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/mark-next-changeset-ran.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/mark-next-changeset-ran-sql.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/drop-all.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/execute-sql.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/set-contexts.html"),
            generateCommand("https://docs.liquibase.com/commands/utility/set-labels.html"),
            """
            // checks
            """.trimIndent(),
            generateCommand("https://docs.liquibase.com/commands/quality-checks/subcommands/bulk-set.html"),
            generateCommand("https://docs.liquibase.com/commands/quality-checks/subcommands/copy.html"),
            generateCommand("https://docs.liquibase.com/commands/quality-checks/subcommands/create.html"),
            generateCommand("https://docs.liquibase.com/commands/quality-checks/subcommands/customize.html"),
            generateCommand("https://docs.liquibase.com/commands/quality-checks/subcommands/delete.html"),
            generateCommand("https://docs.liquibase.com/commands/quality-checks/subcommands/disable.html"),
            generateCommand("https://docs.liquibase.com/commands/quality-checks/subcommands/enable.html"),
            generateCommand("https://docs.liquibase.com/commands/quality-checks/subcommands/reset.html"),
            generateCommand("https://docs.liquibase.com/commands/quality-checks/subcommands/run.html"),
            generateCommand("https://docs.liquibase.com/commands/quality-checks/subcommands/show.html"),
            """
            // Flow
            """.trimIndent(),
            generateCommand("https://docs.liquibase.com/commands/flow/flow.html"),
            generateCommand("https://docs.liquibase.com/commands/flow/flow-validate.html"),
        ).joinToString("\n")
    return codeString
}

private fun writeFile(codeString: String) {
    val file = File("generate-liquibase-kwrapper/src/main/kotlin/command", "KLiquibaseCommand.kt")
    PrintWriter(file).use { out ->
        out.println(
            """
            package momosetkn.liquibase

            import momosetkn.liquibase.LiquibaseCommandInstance.executeLiquibaseCommandLine

            @Suppress("LargeClass", "TooManyFunctions")
            object LiquibaseCommandFunctions {
            """.trimIndent()
        )
        out.println(codeString)
        out.println("}")
    }
}
