package momosetkn.liquibase.kotlin.dsl.util

import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

internal object ReflectionUtils {
    inline fun <reified T : Any> KClass<T>.new(): T {
        return requireNotNull(this.primaryConstructor) {
            "primaryConstructor is null. class=$this"
        }.call()
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Any> loadKClass(className: String): KClass<T> {
        return Class.forName(className).kotlin as KClass<T>
    }
}
