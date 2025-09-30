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
    // value is null when notfound return
    override fun search(path: String, recursive: Boolean): List<Resource>? {
        val items = containPackageNameToClasspathNames(path)
        val results = items.flatMap {
            classLoaderResourceAccessor.search(it, recursive) ?: emptyList()
        }
        return results.ifEmpty { null }
    }

    // value is null when notfound return
    override fun getAll(path: String): List<Resource>? {
        val items = containPackageNameToClasspathNames(path)
        val results = items.flatMap {
            classLoaderResourceAccessor.getAll(it) ?: emptyList()
        }
        return results.ifEmpty { null }
    }

    /**
     * Transform contain package name filepath to name on classpath.
     * Support only package-name directory and .class file
     *
     * example
     * aaa/bbb/com.example.hoge/hogehoge.class -> aaa/bbb/com/example/hoge/hogehoge.class
     * aaa/bbb/com.example.hoge/ -> aaa/bbb/com/example/hoge/
     */
    private fun containPackageNameToClasspathNames(containPackageName: String): List<String> {
        val replaced = containPackageName
            .replace("file:", "")
            .replace("\\", "/")
        val decoded = try {
            URLDecoder.decode(replaced, StandardCharsets.UTF_8.name())
        } catch (e: UnsupportedEncodingException) {
            Scope.getCurrentScope().getLog(javaClass)
                .fine("Failed to decode path $containPackageName; continuing without decoding.", e)
            replaced
        }
        val replacedPackageName = decoded
            .replace(".", "/")
        val replacedPackageDirectoryName = if (!replacedPackageName.endsWith("/class")) {
            replacedPackageName
        } else {
            // cannot use `class` in package name
            null
        }
        val classFileName = if (!replacedPackageName.endsWith("/")) {
            replacedPackageName.removeSuffix("/class") + ".class"
        } else {
            null
        }
        return listOfNotNull(
            replacedPackageDirectoryName,
            classFileName
        )
    }
}
