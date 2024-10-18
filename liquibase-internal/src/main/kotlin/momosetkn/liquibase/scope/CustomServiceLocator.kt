package momosetkn.liquibase.scope

import liquibase.exception.ServiceNotFoundException
import liquibase.plugin.Plugin
import liquibase.servicelocator.ServiceLocator
import momosetkn.liquibase.scope.ReflectionUtils.getURLClassloaderByClass
import java.util.Collections
import java.util.ServiceLoader

/**
 * ServiceLoad with customClassloader[getURLClassloaderByClass]
 * alternative [liquibase.servicelocator.StandardServiceLocator]
 */
class CustomServiceLocator(
    private val getClassloaderByClass: (Class<*>) -> ClassLoader
) : ServiceLocator {
    private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)

    override fun getPriority(): Int {
        return Plugin.PRIORITY_DEFAULT
    }

    @Throws(ServiceNotFoundException::class)
    override fun <T : Any> findInstances(interfaceType: Class<T>): List<T> {
        val allInstances: MutableList<T> = ArrayList()

        val services: Iterator<T> =
            ServiceLoader.load(interfaceType, getClassloaderByClass(interfaceType)).iterator()
        @Suppress("TooGenericExceptionCaught")
        while (services.hasNext()) {
            try {
                val service = services.next()
                log.debug("Loaded " + interfaceType.name + " instance " + service::class.qualifiedName)
                allInstances.add(service)
            } catch (e: Throwable) {
                log.warn(e.message, e)
            }
        }

        return Collections.unmodifiableList(allInstances)
    }
}
