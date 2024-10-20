package momosetkn.liquibase.client

import liquibase.Contexts
import liquibase.LabelExpression
import liquibase.Liquibase
import liquibase.Scope
import liquibase.command.CommandScope
import liquibase.command.core.GenerateChangelogCommandStep
import liquibase.command.core.helpers.DbUrlConnectionArgumentsCommandStep
import liquibase.command.core.helpers.PreCompareCommandStep
import liquibase.command.core.helpers.ReferenceDbUrlConnectionCommandStep
import liquibase.database.Database
import liquibase.exception.LiquibaseException
import liquibase.resource.ResourceAccessor
import liquibase.structure.DatabaseObject
import java.io.PrintStream
import java.io.Writer

class ExtendedLiquibase(
    changeLogFile: String,
    database: Database,
    resourceAccessor: ResourceAccessor = Scope.getCurrentScope().resourceAccessor,
    private val createPrintStream: () -> PrintStream,
) : Liquibase(changeLogFile, resourceAccessor, database,) {
    // to public futureRollbackSQL method, because the original futureRollbackSQL method is protected.
    @Throws(LiquibaseException::class)
    fun extendedFutureRollbackSQL(
        count: Int? = null,
        tag: String? = null,
        contexts: Contexts? = null,
        labelExpression: LabelExpression?,
        output: Writer? = null,
        checkLiquibaseTables: Boolean
    ) {
        return futureRollbackSQL(count, tag, contexts, labelExpression, output, checkLiquibaseTables)
    }

    fun diffChangelog(
        referenceDatabase: Database,
        outputStream: PrintStream = createPrintStream(),
        vararg snapshotTypes: Class<out DatabaseObject?>?
    ) {
        // Reference liquibase.command.core.DiffToChangeLogCommand.run
        CommandScope("diffChangelog")
            .addArgumentValue(GenerateChangelogCommandStep.CHANGELOG_FILE_ARG, changeLogFile)
            .addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, database)
            .addArgumentValue(ReferenceDbUrlConnectionCommandStep.REFERENCE_DATABASE_ARG, referenceDatabase)
            .addArgumentValue(PreCompareCommandStep.SNAPSHOT_TYPES_ARG, snapshotTypes)
            .setOutput(outputStream)
            .execute()
    }
}
