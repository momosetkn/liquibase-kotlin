package momosetkn.change

import org.jsoup.Jsoup

object GenerateChange {
    fun main(uri: String): String {
        val doc =
            Jsoup
                .connect(
                    uri,
                ).get()
        println(doc.title())
        val methodName = doc.select("h1")[0].text()
        val newsHeadlines = doc.select("#mc-main-content > table")
        val availableAttributes = newsHeadlines[0]
//    val databaseSupport = newsHeadlines[1]
        val trs = availableAttributes.select("tbody > tr")
        val items =
            trs.map { tr ->
                val td = tr.select("td")
                val name = td[0].text()
                val requiredFor = td[2].text()
                Item(
                    name = name,
                    required = requiredFor == "all",
                )
            }
        val formatedArgs =
            items.joinToString("\n") {
                val typeString =
                    if (it.required) {
                        " : String,"
                    } else {
                        " : String? = null,"
                    }
                "${it.name}$typeString"
            }
        val formatedMutableChange =
            items.joinToString("\n") {
                "change[\"${it.name}\"] = ${it.name}"
            }
        return """
            fun $methodName(
                $formatedArgs
            ) {
                val change = createChange("$methodName")
                $formatedMutableChange
                addChange(change)
            }
            """.trimIndent()
    }

    private data class Item(
        val name: String,
        val required: Boolean,
    )
}
