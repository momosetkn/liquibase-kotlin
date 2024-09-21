package momosetkn.liquibase.kotlin.dsl.util

import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

internal object ReflectionUtils {
    fun <T : Any> KClass<T>.new(): T {
        val constructor = this.primaryConstructor ?: this.constructors.find { it.parameters.isEmpty() }
        return requireNotNull(constructor) {
            "constructor is not found. class=$this"
        }.call()
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Any> loadKClass(className: String): KClass<T> {
        return Class.forName(className).kotlin as KClass<T>
    }
}
