package momosetkn.liquibase.scope

import liquibase.Scope
import liquibase.listener.LiquibaseListener
import kotlin.reflect.KClass

object CustomScope {
    fun <T : Any, R> executeWithNewClassloader(
        clazz: KClass<T>,
        vararg args: Any,
        block: (T) -> R,
    ): R {
        val classloader = ReflectionUtils::getURLClassloaderByClass
        val customServiceLocator = CustomServiceLocator(classloader)

        val map = mapOf(
            Scope.Attr.serviceLocator.name to customServiceLocator,
        )
        val instance = ReflectionUtils.getInstanceWithClassLoader(
            clazz.java,
            classloader,
            *args,
        )
        val scopeId = Scope.enter(null, map)

        try {
            return block(instance)
        } finally {
            Scope.exit(scopeId)
        }
    }

    @Throws(Exception::class)
    fun enter(listener: LiquibaseListener?, scopeValues: Map<String, Any?>): String {
        val scopeUtils = ReflectionUtils.getInstanceWithClassLoader(
            clazz = ScopeUtils::class.java,
            getClassloaderByClass = ReflectionUtils::getURLClassloaderByClass,
        )
        val classloader = ReflectionUtils::getURLClassloaderByClass
        val customServiceLocator = CustomServiceLocator(classloader)
        val scope = scopeUtils.createCustomRootScope(customServiceLocator)
        val child = ExScope(scope, scopeValues)
        child.listener = listener
        Scope.scopeManager.get().setCurrentScope(child)

        return child.scopeId
    }
}

class ExScope(
    parent: Scope,
    scopeValues: Map<String, Any?>
) : Scope(parent, scopeValues)
