package momosetkn.liquibase.kotlin.parser

abstract class KotlinTypesafeDatabaseChangeLog(
    internal val body: momosetkn.liquibase.kotlin.dsl.ChangeLogDsl.() -> Unit
)
