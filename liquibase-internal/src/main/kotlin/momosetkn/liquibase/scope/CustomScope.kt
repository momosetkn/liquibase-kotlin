package momosetkn.liquibase.scope

import kotlin.reflect.KClass

object CustomScope {
    fun <T : Any> createWithNewClassloader(
        clazz: KClass<T>,
        vararg args: Any,
    ): T {
        val classloader = ReflectionUtils::getURLClassloaderByClass
        val customServiceLocator = CustomServiceLocator(classloader)
        ScopeUtils.createCustomRootScope(customServiceLocator)
        return ReflectionUtils.getInstanceWithClassLoader(
            clazz,
            classloader,
            *args,
        )
    }
}
