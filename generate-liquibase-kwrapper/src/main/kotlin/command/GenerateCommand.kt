package momosetkn.command

import org.jsoup.Jsoup
import org.jsoup.nodes.Element

fun generateCommand(uri: String): String {
    val doc =
        Jsoup
            .connect(
                uri,
            ).get()
    println(doc.title())
    val command = doc.select("h1")[0].text()
    val methodName = command.toCamelCase()
    val tables = doc.select("#cli > table")
    val items = tables.flatMap { table ->
        val trs = table.select("tbody > tr")
        trs.map(::item)
    }.mapNotNull { it }
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

private fun item(tr: Element): Item? {
    val td = tr.select("td")
    if (td.size == 0) return null
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
    return Item(
        liquibaseName = rawName1,
        name = name,
        type = type,
        required = requirement == "Required",
    )
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
