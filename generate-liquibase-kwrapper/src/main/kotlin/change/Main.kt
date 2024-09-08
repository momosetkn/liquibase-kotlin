package momosetkn.change

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
            // Entities
            """.trimIndent(),
            GenerateChange.main("https://docs.liquibase.com/change-types/create-table.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/drop-table.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/set-table-remarks.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/rename-table.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/add-column.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/drop-column.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/rename-column.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/modify-data-type.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/set-column-remarks.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/add-auto-increment.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/create-index.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/drop-index.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/create-view.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/drop-view.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/rename-view.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/create-procedure.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/drop-procedure.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/create-sequence.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/drop-sequence.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/rename-sequence.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/alter-sequence.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/create-function.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/drop-function.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/create-package.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/create-package-body.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/drop-package.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/drop-package-body.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/create-synonym.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/drop-synonym.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/create-trigger.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/enable-trigger.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/drop-trigger.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/disable-trigger.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/rename-trigger.html"),
            """
            // Constraints
            """.trimIndent(),
            GenerateChange.main(
                "https://docs.liquibase.com/change-types/add-check-constraint.html",
            ),
            GenerateChange.main(
                "https://docs.liquibase.com/change-types/enable-check-constraint.html",
            ),
            GenerateChange.main(
                "https://docs.liquibase.com/change-types/drop-check-constraint.html",
            ),
            GenerateChange.main(
                "https://docs.liquibase.com/change-types/disable-check-constraint.html",
            ),
            GenerateChange.main("https://docs.liquibase.com/change-types/add-default-value.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/drop-default-value.html"),
            GenerateChange.main(
                "https://docs.liquibase.com/change-types/add-foreign-key-constraint.html",
            ),
            GenerateChange.main(
                "https://docs.liquibase.com/change-types/drop-foreign-key-constraint.html",
            ),
            GenerateChange.main(
                "https://docs.liquibase.com/change-types/drop-all-foreign-key-constraints.html",
            ),
            GenerateChange.main(
                "https://docs.liquibase.com/change-types/add-not-null-constraint.html",
            ),
            GenerateChange.main(
                "https://docs.liquibase.com/change-types/drop-not-null-constraint.html",
            ),
            GenerateChange.main("https://docs.liquibase.com/change-types/add-primary-key.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/drop-primary-key.html"),
            GenerateChange.main(
                "https://docs.liquibase.com/change-types/add-unique-constraint.html",
            ),
            GenerateChange.main(
                "https://docs.liquibase.com/change-types/drop-unique-constraint.html",
            ),
            """
            // Data
            """.trimIndent(),
            GenerateChange.main("https://docs.liquibase.com/change-types/add-lookup-table.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/delete.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/insert.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/load-data.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/load-update-data.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/merge-columns.html"),
//            GenerateChange.main("https://docs.liquibase.com/change-types/modify-sql.html"), error occurred
            GenerateChange.main("https://docs.liquibase.com/change-types/update.html"),
            """
            // Miscellaneous
            """.trimIndent(),
            GenerateChange.main("https://docs.liquibase.com/change-types/custom-change.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/empty.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/execute-command.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/mark-unused.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/output.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/sql.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/sql-file.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/stop.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/tag-database.html"),
            """
            // Other tags
            """.trimIndent(),
            GenerateChange.main("https://docs.liquibase.com/change-types/include.html"),
            GenerateChange.main("https://docs.liquibase.com/change-types/includeall.html"),
//            GenerateChange.main("https://docs.liquibase.com/change-types/modifychangesets.html"), // error occurred
            GenerateChange.main(
                "https://docs.liquibase.com/change-types/remove-change-set-property.html",
            ),
        ).joinToString("\n")
    return codeString
}

private fun writeFile(codeString: String) {
    val file = File("dsl/src/main/kotlin/momosetkn/liquibase/kotlinh/dsl", "KLiquibaseChangeCommand.kt.codegen")
    PrintWriter(file).use { out ->
        out.println(codeString)
    }
}
