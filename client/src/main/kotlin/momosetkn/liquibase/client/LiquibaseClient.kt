@file:Suppress("ktlint:standard:no-consecutive-comments", "CyclomaticComplexMethod")

package momosetkn.liquibase.client

import liquibase.CatalogAndSchema
import liquibase.Contexts
import liquibase.LabelExpression
import liquibase.Scope
import liquibase.UpdateSummaryEnum
import liquibase.UpdateSummaryOutputEnum
import liquibase.change.CheckSum
import liquibase.changelog.ChangeLogParameters
import liquibase.changelog.ChangeSet
import liquibase.changelog.ChangeSetStatus
import liquibase.changelog.DatabaseChangeLog
import liquibase.changelog.RanChangeSet
import liquibase.changelog.visitor.ChangeExecListener
import liquibase.changelog.visitor.DefaultChangeExecListener
import liquibase.database.Database
import liquibase.diff.DiffResult
import liquibase.diff.compare.CompareControl
import liquibase.diff.output.changelog.DiffToChangeLog
import liquibase.exception.CommandExecutionException
import liquibase.exception.DatabaseException
import liquibase.exception.LiquibaseException
import liquibase.lockservice.DatabaseChangeLogLock
import liquibase.resource.ResourceAccessor
import liquibase.structure.DatabaseObject
import momosetkn.liquibase.client.DateUtils.toJavaUtilDate
import java.io.PrintStream
import java.io.Writer
import java.time.LocalDateTime
import kotlin.reflect.KClass

@Suppress("TooManyFunctions", "LargeClass")
class LiquibaseClient(
    val changeLogFile: String,
    val database: Database,
    val resourceAccessor: ResourceAccessor = Scope.getCurrentScope().resourceAccessor,
) : AutoCloseable {
    private val liquibase = ExtendedLiquibase(
        changeLogFile = changeLogFile,
        resourceAccessor = resourceAccessor,
        database = database,
    )

    private val logWriter = LiquibaseMultilineLogWriter()

    var showSummaryOutput: UpdateSummaryOutputEnum? = null
        set(value) {
            liquibase.setShowSummaryOutput(value)
            field = value
        }

    var showSummary: UpdateSummaryEnum? = null
        set(value) {
            liquibase.setShowSummary(value)
            field = value
        }

    val changeLogParameters: ChangeLogParameters?
        get() = liquibase.changeLogParameters

    var changeExecListener: ChangeExecListener? = null
        set(value) {
            liquibase.setChangeExecListener(value)
            field = value
        }

    val defaultChangeExecListener: DefaultChangeExecListener
        get() = liquibase.defaultChangeExecListener

    // comment-out. The following method is for the sole purpose of testing Liquibase.
//    fun getLog() {
//        this.liquibase.log
//    }

    @Throws(LiquibaseException::class)
    fun getDatabaseChangeLog(): DatabaseChangeLog {
        return this.liquibase.databaseChangeLog
    }

    @Throws(LiquibaseException::class)
    fun update() {
        this.liquibase.update(
            // FIXME: For some reason it doesn't work
//            Contexts(),
//            LabelExpression(),
//            logWriter,
        )
    }

    @Throws(LiquibaseException::class)
    fun update(
        tag: String? = null,
        contexts: Contexts? = null,
        labelExpression: LabelExpression? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase.update(tag, contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun update(
        tag: String? = null,
        contexts: String? = null,
        labelExpression: String? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase.update(tag, Contexts(contexts), LabelExpression(labelExpression), output)
    }

    @Throws(LiquibaseException::class)
    fun update(
        changesToApply: Int,
        contexts: Contexts? = null,
        labelExpression: LabelExpression? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase.update(changesToApply, contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun update(
        changesToApply: Int,
        contexts: String? = null,
        labelExpression: String? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase.update(changesToApply, Contexts(contexts), LabelExpression(labelExpression), output)
    }

    @Throws(LiquibaseException::class)
    fun updateCountSql(
        count: Int,
        contexts: Contexts? = null,
        labelExpression: LabelExpression? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase.updateCountSql(count, contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun updateToTagSql(
        tag: String,
        contexts: Contexts? = null,
        labelExpression: LabelExpression? = null,
        writer: Writer = logWriter,
    ) {
        return this.liquibase.updateToTagSql(tag, contexts, labelExpression, writer)
    }

    @Throws(LiquibaseException::class)
    fun rollback(
        changesToRollback: Int,
        rollbackScript: String? = null,
        contexts: Contexts? = null,
        labelExpression: LabelExpression? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase
            .rollback(changesToRollback, rollbackScript, contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(
        changesToRollback: Int,
        rollbackScript: String? = null,
        contexts: String? = null,
        labelExpression: String? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase
            .rollback(changesToRollback, rollbackScript, Contexts(contexts), LabelExpression(labelExpression), output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(
        tagToRollBackTo: String,
        rollbackScript: String? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase
            .rollback(tagToRollBackTo, rollbackScript, Contexts(), LabelExpression(), output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(
        tagToRollBackTo: String,
        rollbackScript: String? = null,
        contexts: Contexts? = null,
        labelExpression: LabelExpression? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase
            .rollback(tagToRollBackTo, rollbackScript, contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(
        tagToRollBackTo: String,
        rollbackScript: String? = null,
        contexts: String? = null,
        labelExpression: String? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase
            .rollback(tagToRollBackTo, rollbackScript, Contexts(contexts), LabelExpression(labelExpression), output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(
        dateToRollBackTo: LocalDateTime,
        rollbackScript: String? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase
            .rollback(dateToRollBackTo.toJavaUtilDate(), rollbackScript, Contexts(), LabelExpression(), output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(
        dateToRollBackTo: LocalDateTime,
        rollbackScript: String? = null,
        contexts: Contexts? = null,
        labelExpression: LabelExpression? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase
            .rollback(dateToRollBackTo.toJavaUtilDate(), rollbackScript, contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(
        dateToRollBackTo: LocalDateTime,
        rollbackScript: String? = null,
        contexts: String? = null,
        labelExpression: String? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase
            .rollback(
                dateToRollBackTo.toJavaUtilDate(),
                rollbackScript,
                Contexts(contexts),
                LabelExpression(labelExpression),
                output
            )
    }

    @Throws(LiquibaseException::class)
    fun changeLogSync(
        tag: String,
        contexts: Contexts? = null,
        labelExpression: LabelExpression? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase.changeLogSync(tag, contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun changeLogSync(
        tag: String,
        contexts: String? = null,
        labelExpression: String? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase.changeLogSync(tag, Contexts(contexts), LabelExpression(labelExpression), output)
    }

    @Throws(LiquibaseException::class)
    fun markNextChangeSetRan(
        contexts: Contexts? = null,
        labelExpression: LabelExpression? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase.markNextChangeSetRan(contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun markNextChangeSetRan(
        contexts: String? = null,
        labelExpression: String? = null,
        output: Writer = logWriter,
    ) {
        return this.liquibase.markNextChangeSetRan(Contexts(contexts), LabelExpression(labelExpression), output)
    }

    @Throws(LiquibaseException::class)
    fun futureRollbackSQL(
        count: Int? = null,
        tag: String? = null,
        contexts: Contexts? = null,
        labelExpression: LabelExpression?,
        output: Writer = logWriter,
        checkLiquibaseTables: Boolean
    ) {
        return this.liquibase
            .overrideFutureRollbackSQL(count, tag, contexts, labelExpression, output, checkLiquibaseTables)
    }

    @Throws(DatabaseException::class)
    fun dropAll(vararg schemas: CatalogAndSchema) {
        return this.liquibase.dropAll(*schemas)
    }

    @Throws(DatabaseException::class)
    fun dropAll(dropDbclhistory: Boolean? = null, vararg schemas: CatalogAndSchema) {
        return this.liquibase.dropAll(dropDbclhistory, *schemas)
    }

    @Throws(LiquibaseException::class)
    fun tag(tagString: String) {
        return this.liquibase.tag(tagString)
    }

    @Throws(LiquibaseException::class)
    fun tagExists(tagString: String): Boolean {
        return this.liquibase.tagExists(tagString)
    }

    @Throws(LiquibaseException::class)
    fun updateTestingRollback(contexts: String? = null) {
        return this.liquibase.updateTestingRollback(contexts)
    }

    @Throws(LiquibaseException::class)
    fun updateTestingRollback(contexts: Contexts? = null, labelExpression: LabelExpression? = null) {
        return this.liquibase.updateTestingRollback(contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun updateTestingRollback(
        tag: String? = null,
        contexts: Contexts? = null,
        labelExpression: LabelExpression? = null
    ) {
        return this.liquibase.updateTestingRollback(tag, contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun checkLiquibaseTables(
        updateExistingNullChecksums: Boolean,
        databaseChangeLog: DatabaseChangeLog,
        contexts: Contexts? = null,
        labelExpression: LabelExpression?
    ) {
        return this.liquibase.checkLiquibaseTables(
            updateExistingNullChecksums,
            databaseChangeLog,
            contexts,
            labelExpression
        )
    }

    @get:Throws(DatabaseException::class)
    val isSafeToRunUpdate: Boolean
        get() = this.liquibase.isSafeToRunUpdate

    @Throws(LiquibaseException::class)
    fun listLocks(): Array<DatabaseChangeLogLock> {
        return this.liquibase.listLocks()
    }

    @Throws(LiquibaseException::class)
    fun reportLocks(out: PrintStream? = null) {
        return this.liquibase.reportLocks(out)
    }

    @Throws(LiquibaseException::class)
    fun forceReleaseLocks() {
        return this.liquibase.forceReleaseLocks()
    }

    @Throws(LiquibaseException::class)
    fun listUnrunChangeSets(
        contexts: Contexts? = null,
        labels: LabelExpression,
        checkLiquibaseTables: Boolean = true
    ): List<ChangeSet> {
        return this.liquibase.listUnrunChangeSets(contexts, labels, checkLiquibaseTables)
    }

    @Throws(LiquibaseException::class)
    fun getChangeSetStatuses(
        contexts: Contexts? = null,
        labelExpression: LabelExpression,
        checkLiquibaseTables: Boolean = true
    ): List<ChangeSetStatus> {
        return this.liquibase.getChangeSetStatuses(contexts, labelExpression, checkLiquibaseTables)
    }

    @Throws(LiquibaseException::class)
    fun getChangeSetStatuses(
        contexts: String? = null,
        labelExpression: String,
        checkLiquibaseTables: Boolean = true
    ): List<ChangeSetStatus> {
        return this.liquibase
            .getChangeSetStatuses(Contexts(contexts), LabelExpression(labelExpression), checkLiquibaseTables)
    }

    @Throws(LiquibaseException::class)
    fun reportStatus(
        verbose: Boolean,
        contexts: Contexts? = null,
        labels: LabelExpression? = null,
        out: Writer = logWriter,
    ) {
        return this.liquibase.reportStatus(verbose, contexts, labels, out)
    }

    @Throws(LiquibaseException::class)
    fun reportStatus(
        verbose: Boolean,
        contexts: String? = null,
        labels: String? = null,
        out: Writer = logWriter,
    ) {
        return this.liquibase.reportStatus(verbose, Contexts(contexts), LabelExpression(labels), out)
    }

    @Throws(LiquibaseException::class)
    fun listUnexpectedChangeSets(
        contexts: Contexts? = null,
        labelExpression: LabelExpression? = null
    ): Collection<RanChangeSet> {
        return this.liquibase.listUnexpectedChangeSets(contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun listUnexpectedChangeSets(contexts: String? = null, labelExpression: String? = null): Collection<RanChangeSet> {
        return this.liquibase.listUnexpectedChangeSets(Contexts(contexts), LabelExpression(labelExpression))
    }

    @Throws(LiquibaseException::class)
    fun reportUnexpectedChangeSets(
        verbose: Boolean,
        contexts: String? = null,
        out: Writer = logWriter,
    ) {
        return this.liquibase.reportUnexpectedChangeSets(verbose, contexts, out)
    }

    @Throws(LiquibaseException::class)
    fun reportUnexpectedChangeSets(
        verbose: Boolean,
        contexts: Contexts? = null,
        labelExpression: LabelExpression? = null,
        out: Writer = logWriter,
    ) {
        return this.liquibase.reportUnexpectedChangeSets(verbose, contexts, labelExpression, out)
    }

    @Throws(LiquibaseException::class)
    fun clearCheckSums() {
        return this.liquibase.clearCheckSums()
    }

    @Throws(LiquibaseException::class)
    fun calculateCheckSum(changeSetIdentifier: String): CheckSum {
        return this.liquibase.calculateCheckSum(changeSetIdentifier)
    }

    @Throws(LiquibaseException::class)
    fun calculateCheckSum(changeSetPath: String, changeSetId: String, changeSetAuthor: String): CheckSum {
        return this.liquibase.calculateCheckSum(changeSetPath, changeSetId, changeSetAuthor)
    }

    @Throws(LiquibaseException::class)
    fun generateDocumentation(
        outputDirectory: String,
        contexts: Contexts? = null,
        labelExpression: LabelExpression? = null,
        vararg schemaList: CatalogAndSchema?
    ) {
        return this.liquibase
            .generateDocumentation(outputDirectory, contexts, labelExpression, *schemaList)
    }

    @Throws(LiquibaseException::class)
    fun diff(
        referenceDatabase: Database,
        targetDatabase: Database? = null,
        compareControl: CompareControl? = null
    ): DiffResult {
        return this.liquibase.diff(referenceDatabase, targetDatabase, compareControl)
    }

    @Throws(LiquibaseException::class)
    fun validate() {
        return this.liquibase.validate()
    }

    fun setChangeLogParameter(key: String, value: Any? = null) {
        return this.liquibase.setChangeLogParameter(key, value)
    }

    @Throws(DatabaseException::class, CommandExecutionException::class)
    fun generateChangeLog(
        catalogAndSchema: CatalogAndSchema = CatalogAndSchema.DEFAULT,
        changeLogWriter: DiffToChangeLog? = null,
        outputStream: PrintStream? = null,
        vararg snapshotTypes: KClass<out DatabaseObject>
    ) {
        @Suppress("SpreadOperator")
        return this.liquibase.generateChangeLog(
            catalogAndSchema,
            changeLogWriter,
            outputStream,
            *snapshotTypes.map { it.java }.toTypedArray()
        )
    }

    @Throws(LiquibaseException::class)
    override fun close() {
        return this.liquibase.close()
    }
}
