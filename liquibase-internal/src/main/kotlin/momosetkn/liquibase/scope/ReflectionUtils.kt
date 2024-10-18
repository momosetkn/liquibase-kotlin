package momosetkn.liquibase.scope

import java.lang.reflect.Constructor
import java.net.URL
import java.net.URLClassLoader
import kotlin.reflect.KClass

object ReflectionUtils {
    fun <T : Any> getInstanceWithClassLoader(
        clazz: KClass<T>,
        getClassloaderByClass: (Class<*>) -> ClassLoader,
        vararg args: Any,
    ): T {
        val constructor = getConstructor(clazz, getClassloaderByClass, args.size)

        val isKotlin = isKotlinClazz(clazz)
        @Suppress("UNCHECKED_CAST")
        return if (isKotlin) {
            constructor.newInstance(*args)
        } else {
            constructor.newInstance(args)
        } as T
    }

    fun getURLClassloaderByClass(interfaceType: Class<*>): URLClassLoader {
        val location = interfaceType.getProtectionDomain().codeSource.location
        val classLoader = URLClassLoader(arrayOf<URL>(location))
        return classLoader
    }

    private fun <T : Any> isKotlinClazz(clazz: KClass<T>): Boolean {
        val metadataAnnotation = clazz.java.getAnnotation(Metadata::class.java)
        return metadataAnnotation != null
    }

    private fun getConstructor(
        clazz: KClass<*>,
        getClassloaderByClass: (Class<*>) -> ClassLoader,
        argsSize: Int,
    ): Constructor<*> {
        val customClassLoader = getClassloaderByClass(clazz.java)
        val constructors = customClassLoader.loadClass(clazz.qualifiedName).constructors
        val constructor = constructors.find {
            it.parameters.size == argsSize
        } ?: error("not found constructor. clazz: $clazz")
        return constructor
    }
}
