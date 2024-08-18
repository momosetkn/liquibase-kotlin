package momosetkn.liquibase.kotlin.dsl.util

object StringsUtil {
    fun String.splitAndTrim(): List<String> =
        this.split(",").map { it.trim() }.filter { it.isNotEmpty() }
}
