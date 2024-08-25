package momosetkn.liquibase.kotlin.dsl

@ChangeLogDslMarker
class KeyValueDsl {
    private var map = mutableMapOf<String, Any?>()

    internal operator fun invoke(
        block: KeyValueDsl.() -> Unit,
    ): Map<String, Any?> {
        block(this)
        return map
    }

    fun param(
        name: String,
        value: Any?,
    ) {
        map[name] = value
    }
}
