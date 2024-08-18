package momosetkn.liquibase.kotlin.dsl

@ChangeLogDslMarker
class KeyValueDsl {
    internal var map = mutableMapOf<String, Any?>()

    fun param(
        name: String,
        value: Any?,
    ) {
        map[name] = value
    }
}
