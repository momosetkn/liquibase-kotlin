@file:Suppress("ktlint:standard:no-consecutive-comments", "CyclomaticComplexMethod")

package momosetkn.liquibase.client

import liquibase.CatalogAndSchema
import liquibase.Contexts
import liquibase.LabelExpression
import liquibase.Liquibase
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
import liquibase.serializer.ChangeLogSerializer
import liquibase.structure.DatabaseObject
import java.io.PrintStream
import java.io.Writer
import java.util.Date

@Suppress("TooManyFunctions", "LargeClass")
class LiquibaseClient(
    val changeLogFile: String,
    val database: Database,
    val resourceAccessor: ResourceAccessor = Scope.getCurrentScope().resourceAccessor,
) : AutoCloseable {
    private val liquibase = Liquibase(changeLogFile, resourceAccessor, database)

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
        this.liquibase.update()
    }

    @Throws(LiquibaseException::class)
    fun update(contexts: String?) {
        this.liquibase.update(contexts)
    }

    @Throws(LiquibaseException::class)
    fun update(
        contexts: Contexts,
    ) {
        return this.liquibase.update(contexts)
    }

    @Throws(LiquibaseException::class)
    fun update(
        contexts: Contexts,
        labelExpression: LabelExpression,
    ) {
        return this.liquibase.update(contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun update(
        contexts: Contexts,
        labelExpression: LabelExpression,
        checkLiquibaseTables: Boolean = true
    ) {
        return this.liquibase.update(contexts, labelExpression, checkLiquibaseTables)
    }

    @Throws(LiquibaseException::class)
    fun update(contexts: String?, output: Writer?) {
        return this.liquibase.update(contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun update(contexts: Contexts?, output: Writer?) {
        return this.liquibase.update(contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun update(
        contexts: Contexts?,
        labelExpression: LabelExpression?,
        output: Writer?,
    ) {
        this.liquibase.update(
            contexts,
            labelExpression,
            output,
        )
    }

    @Throws(LiquibaseException::class)
    fun update(
        contexts: Contexts?,
        labelExpression: LabelExpression?,
        output: Writer?,
        checkLiquibaseTables: Boolean = true, // Unused
    ) {
        this.liquibase.update(
            contexts,
            labelExpression,
            output,
            checkLiquibaseTables
        )
    }

    @Throws(LiquibaseException::class)
    fun updateCountSql(count: Int, contexts: Contexts?, labelExpression: LabelExpression?, output: Writer?) {
        return this.liquibase.updateCountSql(count, contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun update(changesToApply: Int, contexts: String?) {
        return this.liquibase.update(changesToApply, contexts)
    }

    @Throws(LiquibaseException::class)
    fun update(changesToApply: Int, contexts: Contexts?, labelExpression: LabelExpression?) {
        return this.liquibase.update(changesToApply, contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun update(tag: String?, contexts: String?) {
        return this.liquibase.update(tag, contexts)
    }

    @Throws(LiquibaseException::class)
    fun update(tag: String?, contexts: Contexts?) {
        return this.liquibase.update(tag, contexts)
    }

    @Throws(LiquibaseException::class)
    fun update(tag: String?, contexts: Contexts?, labelExpression: LabelExpression?) {
        return this.liquibase.update(tag, contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun updateToTagSql(tag: String, contexts: Contexts?, labelExpression: LabelExpression?, writer: Writer?) {
        return this.liquibase.updateToTagSql(tag, contexts, labelExpression, writer)
    }

    @Throws(LiquibaseException::class)
    fun update(changesToApply: Int, contexts: String?, output: Writer?) {
        return this.liquibase.update(changesToApply, contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun update(changesToApply: Int, contexts: Contexts?, labelExpression: LabelExpression?, output: Writer?) {
        return this.liquibase.update(changesToApply, contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun update(tag: String?, contexts: String?, output: Writer?) {
        return this.liquibase.update(tag, contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun update(tag: String?, contexts: Contexts?, output: Writer?) {
        return this.liquibase.update(tag, contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun update(tag: String?, contexts: Contexts?, labelExpression: LabelExpression?, output: Writer?) {
        return this.liquibase.update(tag, contexts, labelExpression, output)
    }

    @Deprecated("use {@link LoggingExecutorTextUtil#outputHeader(String, Database, String))}")
    @Throws(
        DatabaseException::class
    )
    fun outputHeader(message: String?) {
        return this.liquibase.outputHeader(message)
    }

    // ---------- RollbackCountSql Family of methods
    @Throws(LiquibaseException::class)
    fun rollback(changesToRollback: Int, contexts: String?, output: Writer?) {
        return this.liquibase.rollback(changesToRollback, contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(changesToRollback: Int, contexts: Contexts?, output: Writer?) {
        return this.liquibase.rollback(changesToRollback, contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(changesToRollback: Int, contexts: Contexts?, labelExpression: LabelExpression?, output: Writer?) {
        return this.liquibase.rollback(changesToRollback, contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(changesToRollback: Int, rollbackScript: String?, contexts: String?, output: Writer?) {
        return this.liquibase.rollback(changesToRollback, rollbackScript, contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(changesToRollback: Int, rollbackScript: String?, contexts: Contexts?, output: Writer?) {
        return this.liquibase.rollback(changesToRollback, rollbackScript, contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(
        changesToRollback: Int,
        rollbackScript: String?,
        contexts: Contexts?,
        labelExpression: LabelExpression?,
        output: Writer?
    ) {
        return this.liquibase
            .rollback(changesToRollback, rollbackScript, contexts, labelExpression, output)
    }

    // ---------- End RollbackCountSql Family of methods
    // ---------- RollbackCount Family of methods
    @Throws(LiquibaseException::class)
    fun rollback(changesToRollback: Int, contexts: String?) {
        return this.liquibase.rollback(changesToRollback, contexts)
    }

    @Throws(LiquibaseException::class)
    fun rollback(changesToRollback: Int, contexts: Contexts?, labelExpression: LabelExpression?) {
        return this.liquibase.rollback(changesToRollback, contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun rollback(changesToRollback: Int, rollbackScript: String?, contexts: String?) {
        return this.liquibase.rollback(changesToRollback, rollbackScript, contexts)
    }

    @Throws(LiquibaseException::class)
    fun rollback(
        changesToRollback: Int,
        rollbackScript: String?,
        contexts: Contexts?,
        labelExpression: LabelExpression?
    ) {
        return this.liquibase.rollback(changesToRollback, rollbackScript, contexts, labelExpression)
    }

    // ---------- End RollbackCount Family of methods
    // ---------- RollbackSQL Family of methods
    @Throws(LiquibaseException::class)
    fun rollback(tagToRollBackTo: String?, contexts: String?, output: Writer?) {
        return this.liquibase.rollback(tagToRollBackTo, contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(tagToRollBackTo: String?, contexts: Contexts?, output: Writer?) {
        return this.liquibase.rollback(tagToRollBackTo, contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(tagToRollBackTo: String?, contexts: Contexts?, labelExpression: LabelExpression?, output: Writer?) {
        return this.liquibase.rollback(tagToRollBackTo, contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(tagToRollBackTo: String?, rollbackScript: String?, contexts: String?, output: Writer?) {
        return this.liquibase.rollback(tagToRollBackTo, rollbackScript, contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(tagToRollBackTo: String?, rollbackScript: String?, contexts: Contexts?, output: Writer?) {
        return this.liquibase.rollback(tagToRollBackTo, rollbackScript, contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(
        tagToRollBackTo: String?,
        rollbackScript: String?,
        contexts: Contexts?,
        labelExpression: LabelExpression?,
        output: Writer?
    ) {
        return this.liquibase
            .rollback(tagToRollBackTo, rollbackScript, contexts, labelExpression, output)
    }

    // ---------- End RollbackSQL Family of methods
    // ---------- Rollback (To Tag) Family of methods
    @Throws(LiquibaseException::class)
    fun rollback(tagToRollBackTo: String?, contexts: String?) {
        return this.liquibase.rollback(tagToRollBackTo, contexts)
    }

    @Throws(LiquibaseException::class)
    fun rollback(tagToRollBackTo: String?, contexts: Contexts?) {
        return this.liquibase.rollback(tagToRollBackTo, contexts)
    }

    @Throws(LiquibaseException::class)
    fun rollback(tagToRollBackTo: String?, contexts: Contexts?, labelExpression: LabelExpression?) {
        return this.liquibase.rollback(tagToRollBackTo, contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun rollback(tagToRollBackTo: String?, rollbackScript: String?, contexts: String?) {
        return this.liquibase.rollback(tagToRollBackTo, rollbackScript, contexts)
    }

    @Throws(LiquibaseException::class)
    fun rollback(
        tagToRollBackTo: String?,
        rollbackScript: String?,
        contexts: Contexts?,
    ) {
        return this.liquibase.rollback(tagToRollBackTo, rollbackScript, contexts, LabelExpression())
    }

    @Throws(LiquibaseException::class)
    fun rollback(
        tagToRollBackTo: String?,
        rollbackScript: String?,
        contexts: Contexts?,
        labelExpression: LabelExpression
    ) {
        return this.liquibase.rollback(tagToRollBackTo, rollbackScript, contexts, labelExpression)
    }

    // ---------- End Rollback (To Tag) Family of methods
    // ---------- RollbackToDateSql Family of methods
    @Throws(LiquibaseException::class)
    fun rollback(dateToRollBackTo: Date?, contexts: String?, output: Writer?) {
        return this.liquibase.rollback(dateToRollBackTo, contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(dateToRollBackTo: Date?, rollbackScript: String?, contexts: String?, output: Writer?) {
        return this.liquibase.rollback(dateToRollBackTo, rollbackScript, contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(dateToRollBackTo: Date?, contexts: Contexts?, labelExpression: LabelExpression?, output: Writer?) {
        return this.liquibase.rollback(dateToRollBackTo, contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun rollback(
        dateToRollBackTo: Date?,
        rollbackScript: String?,
        contexts: Contexts?,
        labelExpression: LabelExpression?,
        output: Writer?
    ) {
        return this.liquibase
            .rollback(dateToRollBackTo, rollbackScript, contexts, labelExpression, output)
    }

    // ---------- End RollbackToDateSql Family of methods
    // ---------- RollbackToDate Family of methods
    @Throws(LiquibaseException::class)
    fun rollback(dateToRollBackTo: Date?, contexts: String?) {
        return this.liquibase.rollback(dateToRollBackTo, contexts)
    }

    @Throws(LiquibaseException::class)
    fun rollback(dateToRollBackTo: Date?, contexts: Contexts?, labelExpression: LabelExpression?) {
        return this.liquibase.rollback(dateToRollBackTo, contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun rollback(dateToRollBackTo: Date?, rollbackScript: String?, contexts: String?) {
        return this.liquibase.rollback(dateToRollBackTo, rollbackScript, contexts)
    }

    @Throws(LiquibaseException::class)
    fun rollback(
        dateToRollBackTo: Date?,
        rollbackScript: String?,
        contexts: Contexts?,
        labelExpression: LabelExpression?
    ) {
        return this.liquibase.rollback(dateToRollBackTo, rollbackScript, contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun changeLogSync(contexts: String?, output: Writer?) {
        return this.liquibase.changeLogSync(contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun changeLogSync(contexts: Contexts?, labelExpression: LabelExpression?, output: Writer?) {
        return this.liquibase.changeLogSync(contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun changeLogSync(contexts: String?) {
        return this.liquibase.changeLogSync(contexts)
    }

    @Deprecated("use version with LabelExpression")
    @Throws(LiquibaseException::class)
    fun changeLogSync(contexts: Contexts?) {
        return this.liquibase.changeLogSync(contexts)
    }

    @Throws(LiquibaseException::class)
    fun changeLogSync(contexts: Contexts?, labelExpression: LabelExpression?) {
        return this.liquibase.changeLogSync(contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun changeLogSync(tag: String?, contexts: String?) {
        return this.liquibase.changeLogSync(tag, contexts)
    }

    @Throws(LiquibaseException::class)
    fun changeLogSync(tag: String?, contexts: Contexts?, labelExpression: LabelExpression?) {
        return this.liquibase.changeLogSync(tag, contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun changeLogSync(tag: String, contexts: String?, output: Writer?) {
        return this.liquibase.changeLogSync(tag, contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun changeLogSync(tag: String, contexts: Contexts?, labelExpression: LabelExpression?, output: Writer?) {
        return this.liquibase.changeLogSync(tag, contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun markNextChangeSetRan(contexts: String?, output: Writer?) {
        return this.liquibase.markNextChangeSetRan(contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun markNextChangeSetRan(contexts: Contexts?, labelExpression: LabelExpression?, output: Writer?) {
        return this.liquibase.markNextChangeSetRan(contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun markNextChangeSetRan(contexts: String?) {
        return this.liquibase.markNextChangeSetRan(contexts)
    }

    @Throws(LiquibaseException::class)
    fun markNextChangeSetRan(contexts: Contexts?, labelExpression: LabelExpression?) {
        return this.liquibase.markNextChangeSetRan(contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun futureRollbackSQL(contexts: String?, output: Writer?) {
        return this.liquibase.futureRollbackSQL(contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun futureRollbackSQL(output: Writer?) {
        return this.liquibase.futureRollbackSQL(output)
    }

    @Throws(LiquibaseException::class)
    fun futureRollbackSQL(contexts: String?, output: Writer?, checkLiquibaseTables: Boolean) {
        return this.liquibase.futureRollbackSQL(contexts, output, checkLiquibaseTables)
    }

    @Throws(LiquibaseException::class)
    fun futureRollbackSQL(count: Int?, contexts: String?, output: Writer?) {
        return this.liquibase.futureRollbackSQL(count, contexts, output)
    }

    @Throws(LiquibaseException::class)
    fun futureRollbackSQL(contexts: Contexts?, labelExpression: LabelExpression?, output: Writer?) {
        return this.liquibase.futureRollbackSQL(contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun futureRollbackSQL(count: Int?, contexts: String?, output: Writer?, checkLiquibaseTables: Boolean) {
        return this.liquibase.futureRollbackSQL(count, contexts, output, checkLiquibaseTables)
    }

    @Throws(LiquibaseException::class)
    fun futureRollbackSQL(
        count: Int?,
        contexts: Contexts?,
        labelExpression: LabelExpression?,
        output: Writer?,
    ) {
        return this.liquibase
            .futureRollbackSQL(count, contexts, labelExpression, output)
    }

    @Throws(LiquibaseException::class)
    fun futureRollbackSQL(
        count: Int?,
        contexts: Contexts?,
        labelExpression: LabelExpression?,
        output: Writer?,
        checkLiquibaseTables: Boolean = true
    ) {
        return this.liquibase
            .futureRollbackSQL(count, contexts, labelExpression, output, checkLiquibaseTables)
    }

    @Throws(LiquibaseException::class)
    fun futureRollbackSQL(tag: String?, contexts: Contexts?, labelExpression: LabelExpression?, output: Writer?) {
        return this.liquibase.futureRollbackSQL(tag, contexts, labelExpression, output)
    }

    @Throws(DatabaseException::class)
    fun dropAll() {
        return this.liquibase.dropAll()
    }

    @Throws(DatabaseException::class)
    fun dropAll(dropDbclhistory: Boolean?) {
        return this.liquibase.dropAll(dropDbclhistory)
    }

    @Throws(DatabaseException::class)
    fun dropAll(vararg schemas: CatalogAndSchema?) {
        return this.liquibase.dropAll(*schemas)
    }

    @Throws(DatabaseException::class)
    fun dropAll(dropDbclhistory: Boolean?, vararg schemas: CatalogAndSchema?) {
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
    fun updateTestingRollback(contexts: String?) {
        return this.liquibase.updateTestingRollback(contexts)
    }

    @Throws(LiquibaseException::class)
    fun updateTestingRollback(contexts: Contexts?, labelExpression: LabelExpression?) {
        return this.liquibase.updateTestingRollback(contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun updateTestingRollback(tag: String?, contexts: Contexts?, labelExpression: LabelExpression?) {
        return this.liquibase.updateTestingRollback(tag, contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun checkLiquibaseTables(
        updateExistingNullChecksums: Boolean,
        databaseChangeLog: DatabaseChangeLog,
        contexts: Contexts?,
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
    fun reportLocks(out: PrintStream?) {
        return this.liquibase.reportLocks(out)
    }

    @Throws(LiquibaseException::class)
    fun forceReleaseLocks() {
        return this.liquibase.forceReleaseLocks()
    }

    @Deprecated("use version with LabelExpression")
    @Throws(LiquibaseException::class)
    fun listUnrunChangeSets(contexts: Contexts?): List<ChangeSet> {
        return this.liquibase.listUnrunChangeSets(contexts)
    }

    @Throws(LiquibaseException::class)
    fun listUnrunChangeSets(
        contexts: Contexts?,
        labels: LabelExpression,
    ): List<ChangeSet> {
        return this.liquibase.listUnrunChangeSets(contexts, labels)
    }

    @Throws(LiquibaseException::class)
    fun listUnrunChangeSets(
        contexts: Contexts?,
        labels: LabelExpression,
        checkLiquibaseTables: Boolean = true
    ): List<ChangeSet> {
        return this.liquibase.listUnrunChangeSets(contexts, labels, checkLiquibaseTables)
    }

    @Deprecated("use version with LabelExpression")
    @Throws(LiquibaseException::class)
    fun getChangeSetStatuses(contexts: Contexts?): List<ChangeSetStatus> {
        return this.liquibase.getChangeSetStatuses(contexts)
    }

    @Throws(LiquibaseException::class)
    fun getChangeSetStatuses(contexts: Contexts?, labelExpression: LabelExpression): List<ChangeSetStatus> {
        return this.liquibase.getChangeSetStatuses(contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun getChangeSetStatuses(
        contexts: Contexts?,
        labelExpression: LabelExpression,
        checkLiquibaseTables: Boolean
    ): List<ChangeSetStatus> {
        return this.liquibase.getChangeSetStatuses(contexts, labelExpression, checkLiquibaseTables)
    }

    @Throws(LiquibaseException::class)
    fun reportStatus(verbose: Boolean, contexts: String?, out: Writer?) {
        return this.liquibase.reportStatus(verbose, contexts, out)
    }

    @Throws(LiquibaseException::class)
    fun reportStatus(verbose: Boolean, contexts: Contexts?, out: Writer?) {
        return this.liquibase.reportStatus(verbose, contexts, out)
    }

    @Throws(LiquibaseException::class)
    fun reportStatus(verbose: Boolean, contexts: Contexts?, labels: LabelExpression?, out: Writer?) {
        return this.liquibase.reportStatus(verbose, contexts, labels, out)
    }

    @Throws(LiquibaseException::class)
    fun listUnexpectedChangeSets(contexts: String?): Collection<RanChangeSet> {
        return this.liquibase.listUnexpectedChangeSets(contexts)
    }

    @Throws(LiquibaseException::class)
    fun listUnexpectedChangeSets(contexts: Contexts?, labelExpression: LabelExpression?): Collection<RanChangeSet> {
        return this.liquibase.listUnexpectedChangeSets(contexts, labelExpression)
    }

    @Throws(LiquibaseException::class)
    fun reportUnexpectedChangeSets(verbose: Boolean, contexts: String?, out: Writer?) {
        return this.liquibase.reportUnexpectedChangeSets(verbose, contexts, out)
    }

    @Throws(LiquibaseException::class)
    fun reportUnexpectedChangeSets(
        verbose: Boolean,
        contexts: Contexts?,
        labelExpression: LabelExpression?,
        out: Writer?
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
    fun generateDocumentation(outputDirectory: String?) {
        return this.liquibase.generateDocumentation(outputDirectory)
    }

    @Throws(LiquibaseException::class)
    fun generateDocumentation(outputDirectory: String?, contexts: String?) {
        return this.liquibase.generateDocumentation(outputDirectory, contexts)
    }

    @Throws(LiquibaseException::class)
    fun generateDocumentation(outputDirectory: String?, contexts: String?, vararg schemaList: CatalogAndSchema?) {
        return this.liquibase.generateDocumentation(outputDirectory, contexts, *schemaList)
    }

    @Throws(LiquibaseException::class)
    fun generateDocumentation(
        outputDirectory: String?,
        contexts: Contexts?,
        labelExpression: LabelExpression?,
        vararg schemaList: CatalogAndSchema?
    ) {
        return this.liquibase
            .generateDocumentation(outputDirectory, contexts, labelExpression, *schemaList)
    }

    @Throws(LiquibaseException::class)
    fun diff(referenceDatabase: Database?, targetDatabase: Database?, compareControl: CompareControl?): DiffResult {
        return this.liquibase.diff(referenceDatabase, targetDatabase, compareControl)
    }

    @Throws(LiquibaseException::class)
    fun validate() {
        return this.liquibase.validate()
    }

    fun setChangeLogParameter(key: String?, value: Any?) {
        return this.liquibase.setChangeLogParameter(key, value)
    }

    @SafeVarargs
    @Throws(DatabaseException::class, CommandExecutionException::class)
    fun generateChangeLog(
        catalogAndSchema: CatalogAndSchema?,
        changeLogWriter: DiffToChangeLog?,
        outputStream: PrintStream?,
        vararg snapshotTypes: Class<out DatabaseObject?>?
    ) {
        return this.liquibase
            .generateChangeLog(catalogAndSchema, changeLogWriter, outputStream, *snapshotTypes)
    }

    @SafeVarargs
    @Throws(DatabaseException::class, CommandExecutionException::class)
    fun generateChangeLog(
        catalogAndSchema: CatalogAndSchema?,
        changeLogWriter: DiffToChangeLog?,
        outputStream: PrintStream?,
        changeLogSerializer: ChangeLogSerializer?,
        vararg snapshotTypes: Class<out DatabaseObject?>?
    ) {
        return this.liquibase.generateChangeLog(
            catalogAndSchema,
            changeLogWriter,
            outputStream,
            changeLogSerializer,
            *snapshotTypes
        )
    }

    @Throws(LiquibaseException::class)
    override fun close() {
        return this.liquibase.close()
    }
}
