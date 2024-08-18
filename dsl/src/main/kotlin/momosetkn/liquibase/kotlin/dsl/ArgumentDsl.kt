package momosetkn.liquibase.kotlin.dsl

@ChangeLogDslMarker
class ArgumentDsl {
    internal var args = mutableListOf<Any>()

    fun arg(value: String) {
        args.add(value)
    }
}
