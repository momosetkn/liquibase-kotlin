package momosetkn.liquibase.kotlin.parser

abstract class KotlinDatabaseChangeLog(
    internal val body: momosetkn.liquibase.kotlin.dsl.ChangeLogDsl.() -> Unit
)
