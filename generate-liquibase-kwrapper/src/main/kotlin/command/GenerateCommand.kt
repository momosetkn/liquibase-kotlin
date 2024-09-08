package momosetkn.command

import org.jsoup.Jsoup

fun generateCommand(uri: String): String {
    val doc =
        Jsoup
            .connect(
                uri,
            ).get()
    println(doc.title())
    val command = doc.select("h1")[0].text()
    val methodName = command.toCamelCase()
    val newsHeadlines = doc.select("#cli > table")
    val availableAttributes = newsHeadlines[0]
//    val databaseSupport = newsHeadlines[1]
    val trs = availableAttributes.select("tbody > tr")
    val items =
        trs.map { tr ->
            val td = tr.select("td")
            val td0 = td[0].text()
            val (rawName1, rawType) = td0.split("=")
            val name = rawName1.removePrefix("--").toCamelCase()
            val type = mapOf(
                "<string>" to "String",
                "<true|false>" to "Boolean",
                "<int>" to "Int",
            )[rawType]
            checkNotNull(type)
            val requirement = td[2].text()
            Item(
                liquibaseName = rawName1,
                name = name,
                type = type,
                required = requirement == "Required",
            )
        }
    val formatedArgs =
        items.joinToString("\n") {
            val typeString =
                if (it.required) {
                    " : ${it.type},"
                } else {
                    " : ${it.type}? = null,"
                }
            "${it.name}$typeString"
        }
    val assignParams =
        items.joinToString("\n") {
            val body = "\"${it.liquibaseName}=\$${it.name}\""
            if (it.required) {
                body
            } else {
                "${it.name}?.let { $body }"
            } + ","
        }
    return """
            fun $methodName(
                $formatedArgs
            ) {
                val argsList = listOfNotNull(
                                "$command",
                                $assignParams
                                )
        executeLiquibaseCommandLine(
            argsList.toTypedArray(),
        )
            }
        """.trimIndent()
}
val delimiter = Regex("[ -]")
fun String.toCamelCase(): String {
    return this.split(delimiter)
        .joinToString("") {
            it.replaceFirstChar(Char::uppercaseChar)
        }
        .replaceFirstChar { it.lowercase() }
}

private data class Item(
    val liquibaseName: String,
    val name: String,
    val type: String,
    val required: Boolean,
)
