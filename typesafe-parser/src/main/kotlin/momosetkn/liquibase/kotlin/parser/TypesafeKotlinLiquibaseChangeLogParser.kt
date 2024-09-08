package momosetkn.liquibase.kotlin.parser

import liquibase.change.ChangeMetaData.PRIORITY_DEFAULT
import liquibase.changelog.ChangeLogParameters
import liquibase.changelog.DatabaseChangeLog
import liquibase.parser.ChangeLogParser
import liquibase.resource.ResourceAccessor
import momosetkn.liquibase.kotlin.dsl.ChangeLogDsl
import kotlin.reflect.KClass

class TypesafeKotlinLiquibaseChangeLogParser : ChangeLogParser {
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
        val typesafeDatabaseChangeLog = getTypesafeDatabaseChangeLog(databaseChangeLog)
        val changeLogOverride = TypesafeKotlinChangeLogDslOverride(
            sourceChangeLog = databaseChangeLog,
            resourceAccessor = resourceAccessor,
        )
        val dsl =
            ChangeLogDsl(
                changeLog = databaseChangeLog,
                resourceAccessor = resourceAccessor,
                changeLogDslOverride = changeLogOverride,
            )
        typesafeDatabaseChangeLog.body(dsl)
    }

    private fun getTypesafeDatabaseChangeLog(databaseChangeLog: DatabaseChangeLog): TypesafeDatabaseChangeLog {
        // not support .java, .scala
        val className = databaseChangeLog.physicalFilePath.removeSuffix(".kt")
        val clazz = Class.forName(className).kotlin as KClass<TypesafeDatabaseChangeLog>
        val constructor = clazz.constructors.find { it.parameters.isEmpty() }
        requireNotNull(constructor) {
            "$className constructor is not found"
        }
        val typesafeDatabaseChangeLog = constructor.call()
        return typesafeDatabaseChangeLog
    }

    override fun supports(
        changeLogFile: String,
        resourceAccessor: ResourceAccessor,
    ) = changeLogFile.endsWith(".kt")

    override fun getPriority() = PRIORITY_DEFAULT
}
