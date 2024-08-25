package momosetkn.liquibase.kotlin.parser

import liquibase.change.ChangeMetaData.PRIORITY_DEFAULT
import liquibase.changelog.ChangeLogParameters
import liquibase.changelog.DatabaseChangeLog
import liquibase.exception.ChangeLogParseException
import liquibase.parser.ChangeLogParser
import liquibase.parser.ChangeLogParserFactory
import liquibase.resource.ResourceAccessor
import java.io.InputStreamReader

class KotlinLiquibaseChangeLogParser : ChangeLogParser {
    override fun parse(
        physicalChangeLogLocation: String,
        changeLogParameters: ChangeLogParameters,
        resourceAccessor: ResourceAccessor,
    ): DatabaseChangeLog {
        // windows path to a multi-platform path
        val fixedPhysicalChangeLogLocation = physicalChangeLogLocation.replace("\\\\", "/")
        val changeLogScript =
            getChangeLogKotlinScriptCode(
                resourceAccessor = resourceAccessor,
                physicalChangeLogLocation = fixedPhysicalChangeLogLocation,
            )

        val databaseChangeLog = DatabaseChangeLog(fixedPhysicalChangeLogLocation)
        updateChangeLog(
            filePath = fixedPhysicalChangeLogLocation,
            databaseChangeLog = databaseChangeLog,
            changeLogParameters = changeLogParameters,
            resourceAccessor = resourceAccessor,
            changeLogScript = changeLogScript,
        )
        return databaseChangeLog
    }

    private fun updateChangeLog(
        filePath: String,
        databaseChangeLog: DatabaseChangeLog,
        changeLogParameters: ChangeLogParameters,
        resourceAccessor: ResourceAccessor,
        changeLogScript: String,
    ) {
        databaseChangeLog.changeLogParameters = changeLogParameters

        val changeLogBuilderDsl =
            momosetkn.liquibase.kotlin.dsl.ChangeLogBuilderDsl(
                databaseChangeLog,
                resourceAccessor,
            )

        EvaluateLiquibaseDsl.evaluate(
            filePath = filePath,
            changeLogScript = changeLogScript,
            context = changeLogBuilderDsl,
        )
    }

    private fun getChangeLogKotlinScriptCode(
        resourceAccessor: ResourceAccessor,
        physicalChangeLogLocation: String,
    ): String {
        val resource =
            resourceAccessor.get(physicalChangeLogLocation)
                ?: throw ChangeLogParseException(
                    "$physicalChangeLogLocation does not exist",
                )
        val isr = InputStreamReader(resource.openInputStream(), "UTF8")
        val sourceCode = isr.use { it.readText() }
        return sourceCode
    }

    override fun supports(
        changeLogFile: String,
        resourceAccessor: ResourceAccessor,
    ) = changeLogFile.endsWith(".kts")

    override fun getPriority() = PRIORITY_DEFAULT
}
