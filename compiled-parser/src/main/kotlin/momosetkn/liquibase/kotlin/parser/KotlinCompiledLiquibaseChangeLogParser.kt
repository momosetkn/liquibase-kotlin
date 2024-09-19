package momosetkn.liquibase.kotlin.parser

import liquibase.change.ChangeMetaData.PRIORITY_DEFAULT
import liquibase.changelog.ChangeLogParameters
import liquibase.changelog.DatabaseChangeLog
import liquibase.parser.ChangeLogParser
import liquibase.resource.ResourceAccessor
import momosetkn.liquibase.kotlin.dsl.ChangeLogDsl
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class KotlinCompiledLiquibaseChangeLogParser : ChangeLogParser {
    override fun parse(
        physicalChangeLogLocation: String,
        changeLogParameters: ChangeLogParameters,
        resourceAccessor: ResourceAccessor,
    ): DatabaseChangeLog {
        val databaseChangeLog = DatabaseChangeLog(physicalChangeLogLocation)
        updateChangeLog(
            databaseChangeLog = databaseChangeLog,
            changeLogParameters = changeLogParameters,
            resourceAccessor = resourceAccessor,
        )
        return databaseChangeLog
    }

    private fun updateChangeLog(
        databaseChangeLog: DatabaseChangeLog,
        changeLogParameters: ChangeLogParameters,
        resourceAccessor: ResourceAccessor,
    ) {
        databaseChangeLog.changeLogParameters = changeLogParameters
        val compiledDatabaseChangeLog = getCompiledDatabaseChangeLog(databaseChangeLog)
        val changeLogOverride = KotlinCompiledChangeLogDslOverride(
            sourceChangeLog = databaseChangeLog,
            resourceAccessor = resourceAccessor,
        )
        val dsl =
            ChangeLogDsl(
                changeLog = databaseChangeLog,
                resourceAccessor = resourceAccessor,
                changeLogDslOverride = changeLogOverride,
            )
        compiledDatabaseChangeLog.body(dsl)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getCompiledDatabaseChangeLog(databaseChangeLog: DatabaseChangeLog): KotlinCompiledDatabaseChangeLog {
        val className = databaseChangeLog.physicalFilePath.toClassName()
        val clazz = Class.forName(className).kotlin as KClass<KotlinCompiledDatabaseChangeLog>
        val constructor = clazz.primaryConstructor
        requireNotNull(constructor) {
            "$className constructor is not found"
        }
        val compiledDatabaseChangeLog = constructor.call()
        return compiledDatabaseChangeLog
    }

    override fun supports(
        changeLogFile: String,
        resourceAccessor: ResourceAccessor,
    ): Boolean {
        return runCatching {
            Class.forName(changeLogFile.toClassName())
        }.fold(
            onSuccess = {
                KotlinCompiledDatabaseChangeLog::class.java.isAssignableFrom(it)
            },
            onFailure = { false },
        )
    }

    private fun String.toClassName(): String {
        return this.removeSuffix(".kt")
            .removeSuffix(".class")
            .replace("/", ".")
    }

    override fun getPriority() = PRIORITY_DEFAULT
}
