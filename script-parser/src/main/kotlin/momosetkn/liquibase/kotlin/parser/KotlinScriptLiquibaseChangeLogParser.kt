package momosetkn.liquibase.kotlin.parser

import liquibase.change.ChangeMetaData.PRIORITY_DEFAULT
import liquibase.changelog.ChangeLogParameters
import liquibase.changelog.DatabaseChangeLog
import liquibase.exception.ChangeLogParseException
import liquibase.parser.ChangeLogParser
import liquibase.resource.ResourceAccessor
import momosetkn.liquibase.kotlin.dsl.ChangeLogDsl
import java.io.InputStreamReader
import java.util.ServiceLoader

class KotlinScriptLiquibaseChangeLogParser : ChangeLogParser {
    private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)

    private val imports = ServiceLoader.load(KotlinScriptParserImports::class.java)
        .flatMap { it.imports() }

    override fun parse(
        physicalChangeLogLocation: String,
        changeLogParameters: ChangeLogParameters,
        resourceAccessor: ResourceAccessor,
    ): DatabaseChangeLog {
        // windows path to a multi-platform path
        val fixedPhysicalChangeLogLocation = physicalChangeLogLocation.replace("\\\\", "/")
        val databaseChangeLog = DatabaseChangeLog(fixedPhysicalChangeLogLocation)
        return runCatching {
            val changeLogScript =
                getChangeLogKotlinScriptCode(
                    resourceAccessor = resourceAccessor,
                    physicalChangeLogLocation = fixedPhysicalChangeLogLocation,
                )
            updateChangeLog(
                filePath = fixedPhysicalChangeLogLocation,
                databaseChangeLog = databaseChangeLog,
                changeLogParameters = changeLogParameters,
                resourceAccessor = resourceAccessor,
                changeLogScript = changeLogScript,
            )
        }.fold(
            onSuccess = { databaseChangeLog },
            onFailure = {
                log.error("error in KotlinScriptLiquibaseChangeLogParser", it)
                throw it
            }
        )
    }

    private fun updateChangeLog(
        filePath: String,
        databaseChangeLog: DatabaseChangeLog,
        changeLogParameters: ChangeLogParameters,
        resourceAccessor: ResourceAccessor,
        changeLogScript: String,
    ) {
        databaseChangeLog.changeLogParameters = changeLogParameters

        ChangeLogDslBlocks.items.clear()
        EvaluateLiquibaseDsl.evaluate(
            filePath = filePath,
            changeLogScript = changeLogScript,
            imports = imports,
        )
        ChangeLogDslBlocks.items.forEach { block ->
            val dsl =
                ChangeLogDsl(
                    changeLog = databaseChangeLog,
                    resourceAccessor = resourceAccessor,
                )
            block(dsl)
        }
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
