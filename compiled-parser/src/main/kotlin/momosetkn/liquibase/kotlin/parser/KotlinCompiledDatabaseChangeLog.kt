package momosetkn.liquibase.kotlin.parser

abstract class KotlinCompiledDatabaseChangeLog(
    internal val body: momosetkn.liquibase.kotlin.dsl.ChangeLogDsl.() -> Unit
)
