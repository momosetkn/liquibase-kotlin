package momosetkn.utils

import java.nio.file.Paths

object ResourceUtils {
    fun getResourceAsString(path: String) =
        Thread.currentThread()
            .contextClassLoader
            .getResourceAsStream(path)!!
            .readAllBytes()
            .let { String(it) }
    fun getResourceFileAsString(path: String) =
        Paths.get(Constants.TEST_RESOURCE_DIR, path).toFile().readText()
}
