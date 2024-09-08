package momosetkn.liquibase.kotlin.parser

abstract class TypesafeDatabaseChangeLog(
    internal val body: momosetkn.liquibase.kotlin.dsl.ChangeLogDsl.() -> Unit
)
