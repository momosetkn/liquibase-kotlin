package momosetkn

object ResourceUtils {
    internal fun getResourceAsString(path: String) =
        Thread.currentThread()
            .contextClassLoader
            .getResourceAsStream(path)!!
            .readAllBytes()
            .let { String(it) }
}
