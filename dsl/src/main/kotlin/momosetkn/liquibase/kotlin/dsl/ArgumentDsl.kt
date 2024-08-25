package momosetkn.liquibase.kotlin.dsl

@ChangeLogDslMarker
class ArgumentDsl {
    private val args = mutableListOf<Any>()

    internal operator fun invoke(
        block: ArgumentDsl.() -> Unit,
    ): List<Any> {
        block(this)
        return args.toList()
    }

    fun arg(value: String) {
        args.add(value)
    }
}
