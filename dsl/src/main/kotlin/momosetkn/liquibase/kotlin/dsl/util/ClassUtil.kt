package momosetkn.liquibase.kotlin.dsl.util

import kotlin.reflect.KClass

internal object ClassUtil {
    fun Any.toClassName(): String {
        return when (this) {
            is KClass<*> -> checkNotNull(this.qualifiedName)
            else -> this.toString()
        }
    }
}
