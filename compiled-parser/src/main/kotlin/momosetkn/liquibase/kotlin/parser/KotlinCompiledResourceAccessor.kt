package momosetkn.liquibase.kotlin.parser

import liquibase.Scope
import liquibase.resource.ClassLoaderResourceAccessor
import liquibase.resource.Resource
import liquibase.resource.ResourceAccessor
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

/**
 * This class implements the `ResourceAccessor` interface to provide access to compiled Kotlin classes.
 * This class is find the class or package with delegate to `ClassLoaderResourceAccessor`.
 */
class KotlinCompiledResourceAccessor(
    private val classLoaderResourceAccessor: ClassLoaderResourceAccessor = ClassLoaderResourceAccessor(),
) : ResourceAccessor by classLoaderResourceAccessor {
    override fun search(path: String, recursive: Boolean): MutableList<Resource> {
        return classLoaderResourceAccessor.search(toJavaIdentifierName(path), recursive)
    }

    override fun getAll(path: String): MutableList<Resource> {
        return classLoaderResourceAccessor.getAll(toJavaIdentifierName(path))
    }

    /**
     * To transform java indetification name.
     *
     * example
     * aaa/bbb/com.example.hoge/hogehoge.class -> aaa/bbb/com/example/hoge/hogehoge.class
     * aaa/bbb/com.example.hoge/ -> aaa/bbb/com/example/hoge/
     */
    private fun toJavaIdentifierName(root: String): String {
        val replacedRoot = root
            .replace("file:", "")
            .replace("\\", "/")
        val decodedRoot = try {
            URLDecoder.decode(replacedRoot, StandardCharsets.UTF_8.name())
        } catch (e: UnsupportedEncodingException) {
            Scope.getCurrentScope().getLog(javaClass)
                .fine("Failed to decode path $root; continuing without decoding.", e)
            replacedRoot
        }
        val splited = decodedRoot.split("/")
        val init = splited.dropLast(2)
        val packageName = splited.dropLast(1).last().replace(".", "/")
        val className = splited.last()
        return (init + packageName + className).joinToString("/")
    }
}
