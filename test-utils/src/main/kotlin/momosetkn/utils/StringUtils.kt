package momosetkn.utils

object StringUtils {
    fun String.toLf(): String {
        return this.replace(System.lineSeparator(), "\n")
    }
}
