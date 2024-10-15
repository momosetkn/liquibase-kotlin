package momosetkn.utils.children

import momosetkn.liquibase.kotlin.parser.KotlinCompiledDatabaseChangeLog
import momosetkn.utils.MutableDsl

class MutableChangeLog1 : KotlinCompiledDatabaseChangeLog({
    evalDsl()
}) {
    companion object : MutableDsl
}

class MutableChangeLog2 : KotlinCompiledDatabaseChangeLog({
    evalDsl()
}) {
    companion object : MutableDsl
}

class MutableChangeLog3 : KotlinCompiledDatabaseChangeLog({
    evalDsl()
}) {
    companion object : MutableDsl
}

class MutableChangeLog4 : KotlinCompiledDatabaseChangeLog({
    evalDsl()
}) {
    companion object : MutableDsl
}

class DummyClass
