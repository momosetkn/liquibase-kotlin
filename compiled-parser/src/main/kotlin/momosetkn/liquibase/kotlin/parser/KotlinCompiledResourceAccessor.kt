package momosetkn.liquibase.kotlin.parser

import liquibase.Scope
import liquibase.resource.ClassLoaderResourceAccessor
import liquibase.resource.Resource
import liquibase.resource.ResourceAccessor
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

/**
 * This ResourceAccessor for can specifying packageName with include/includeAll change.
 * This class implements the `ResourceAccessor` interface to provide access to compiled Kotlin classes.
 * This class is find the class or package with delegate to `ClassLoaderResourceAccessor`.
 */
class KotlinCompiledResourceAccessor(
    private val classLoaderResourceAccessor: ClassLoaderResourceAccessor = ClassLoaderResourceAccessor(),
) : ResourceAccessor by classLoaderResourceAccessor {
    override fun search(path: String, recursive: Boolean): List<Resource> {
        return classLoaderResourceAccessor.search(containPackageNameToClasspathName(path), recursive)
    }

    override fun getAll(path: String): List<Resource> {
        return classLoaderResourceAccessor.getAll(containPackageNameToClasspathName(path))
    }

    /**
     * Transform contain package name filepath to name on classpath.
     *
     * example
     * aaa/bbb/com.example.hoge/hogehoge.class -> aaa/bbb/com/example/hoge/hogehoge.class
     * aaa/bbb/com.example.hoge/ -> aaa/bbb/com/example/hoge/
     */
    private fun containPackageNameToClasspathName(containPackageName: String): String {
        val replaced = containPackageName
            .replace("file:", "")
            .replace("\\", "/")
        val decodedRoot = try {
            URLDecoder.decode(replaced, StandardCharsets.UTF_8.name())
        } catch (e: UnsupportedEncodingException) {
            Scope.getCurrentScope().getLog(javaClass)
                .fine("Failed to decode path $containPackageName; continuing without decoding.", e)
            replaced
        }
        val splited = decodedRoot.split("/")
        val init = splited.dropLast(2)
        val packageName = splited.dropLast(1).last().replace(".", "/")
        val className = splited.last()
        return (init + packageName + className).joinToString("/")
    }
}
