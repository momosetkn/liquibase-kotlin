package momosetkn.liquibase.scope

import java.lang.reflect.Constructor
import java.net.URL
import java.net.URLClassLoader

object ReflectionUtils {
    fun <T : Any> getInstanceWithClassLoader(
        clazz: Class<T>,
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

    private fun <T : Any> isKotlinClazz(clazz: Class<T>): Boolean {
        val metadataAnnotation = clazz.getAnnotation(Metadata::class.java)
        return metadataAnnotation != null
    }

    private fun getConstructor(
        clazz: Class<*>,
        getClassloaderByClass: (Class<*>) -> ClassLoader,
        argsSize: Int,
    ): Constructor<*> {
        val customClassLoader = getClassloaderByClass(clazz)
        val constructors = customClassLoader.loadClass(clazz.canonicalName).constructors
        val constructor = constructors.find {
            it.parameters.size == argsSize
        } ?: error("not found constructor. clazz: $clazz")
        return constructor
    }
}
