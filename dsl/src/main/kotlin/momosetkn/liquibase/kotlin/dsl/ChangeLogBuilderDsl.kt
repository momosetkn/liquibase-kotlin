package momosetkn.liquibase.kotlin.dsl

import liquibase.ContextExpression
import liquibase.Labels
import liquibase.changelog.DatabaseChangeLog
import liquibase.database.ObjectQuotingStrategy
import liquibase.resource.ResourceAccessor

@ChangeLogDslMarker
class ChangeLogBuilderDsl(
    private val changeLog: DatabaseChangeLog,
    private val resourceAccessor: ResourceAccessor,
) {
    fun databaseChangeLog(
//        // assigned by momosetkn.liquibase.kotlin.parser.KotlinLiquibaseChangeLogParser.updateChangeLog
//        physicalFilePath: String? = changeLog.physicalFilePath,
//        logicalFilePath: String? = null,
//        objectQuotingStrategy: String? = null,
////        changeLogParameters: Map<String, Any>? = null,
////        runtimeEnvironment: Map<String, Any>? = null,
//        rootChangeLog: DatabaseChangeLog? = null,
//        parentChangeLog: String? = null,
//        context: String? = null, //  same to contextFilter
//        contextFilter: String? = null,
//        includeContextFilter: String? = null,
//        includeLabels: String? = null,
//        includeIgnore: Boolean? = null,
        block: (ChangeLogDsl).() -> Unit,
    ) {
//        changeLog.physicalFilePath = physicalFilePath
//        changeLog.logicalFilePath = logicalFilePath
//        objectQuotingStrategy?.also {
//            changeLog.objectQuotingStrategy = ObjectQuotingStrategy.valueOf(it)
//        }
//        changeLog.rootChangeLog = rootChangeLog
//        parentChangeLog?.also {
//            changeLog.parentChangeLog = DatabaseChangeLog(parentChangeLog)
//        }
//        val contextFilterOrContext = contextFilter ?: context
//        contextFilterOrContext?.also {
//            changeLog.contextFilter = ContextExpression(contextFilterOrContext)
//        }
//        includeContextFilter?.also {
//            changeLog.includeContextFilter = ContextExpression(it)
//        }
//        includeLabels?.also {
//            changeLog.includeLabels = Labels(it)
//        }
//        changeLog.isIncludeIgnore = includeIgnore ?: false
        val dsl =
            ChangeLogDsl(
                changeLog = changeLog,
                resourceAccessor = resourceAccessor,
            )
        block(dsl)
    }
}
