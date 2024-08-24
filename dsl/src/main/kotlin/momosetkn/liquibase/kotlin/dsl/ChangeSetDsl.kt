package momosetkn.liquibase.kotlin.dsl

import liquibase.Scope
import liquibase.change.Change
import liquibase.change.ColumnConfig
import liquibase.change.core.AddAutoIncrementChange
import liquibase.change.core.AddColumnChange
import liquibase.change.core.AddDefaultValueChange
import liquibase.change.core.AddForeignKeyConstraintChange
import liquibase.change.core.AddLookupTableChange
import liquibase.change.core.AddNotNullConstraintChange
import liquibase.change.core.AddPrimaryKeyChange
import liquibase.change.core.AddUniqueConstraintChange
import liquibase.change.core.AlterSequenceChange
import liquibase.change.core.CreateIndexChange
import liquibase.change.core.CreateProcedureChange
import liquibase.change.core.CreateSequenceChange
import liquibase.change.core.CreateTableChange
import liquibase.change.core.CreateViewChange
import liquibase.change.core.DeleteDataChange
import liquibase.change.core.DropAllForeignKeyConstraintsChange
import liquibase.change.core.DropColumnChange
import liquibase.change.core.DropDefaultValueChange
import liquibase.change.core.DropForeignKeyConstraintChange
import liquibase.change.core.DropIndexChange
import liquibase.change.core.DropNotNullConstraintChange
import liquibase.change.core.DropPrimaryKeyChange
import liquibase.change.core.DropProcedureChange
import liquibase.change.core.DropSequenceChange
import liquibase.change.core.DropTableChange
import liquibase.change.core.DropUniqueConstraintChange
import liquibase.change.core.DropViewChange
import liquibase.change.core.ExecuteShellCommandChange
import liquibase.change.core.InsertDataChange
import liquibase.change.core.LoadDataChange
import liquibase.change.core.LoadUpdateDataChange
import liquibase.change.core.MergeColumnChange
import liquibase.change.core.ModifyDataTypeChange
import liquibase.change.core.OutputChange
import liquibase.change.core.RawSQLChange
import liquibase.change.core.RenameColumnChange
import liquibase.change.core.RenameSequenceChange
import liquibase.change.core.RenameTableChange
import liquibase.change.core.RenameViewChange
import liquibase.change.core.SQLFileChange
import liquibase.change.core.SetColumnRemarksChange
import liquibase.change.core.SetTableRemarksChange
import liquibase.change.core.StopChange
import liquibase.change.core.TagDatabaseChange
import liquibase.change.core.UpdateDataChange
import liquibase.change.custom.CustomChangeWrapper
import liquibase.changelog.ChangeSet
import liquibase.changelog.DatabaseChangeLog
import liquibase.exception.ChangeLogParseException
import liquibase.exception.RollbackImpossibleException

@ChangeLogDslMarker
class ChangeSetDsl(
    private val changeLog: DatabaseChangeLog,
    private val context: ChangeSetContext,
) {
    private val log = Scope.getCurrentScope().getLog(javaClass)

    private val changeSetSupport =
        ChangeSetSupport(
            changeSet = context.changeSet,
            inRollback = context.inRollback,
        )

    fun comment(text: String) {
        context.changeSet.comments = text.expandExpressions(changeLog)
    }

    fun preConditions(
        onError: String? = null,
        onErrorMessage: String? = null,
        onFail: String? = null,
        onFailMessage: String? = null,
        onSqlOutput: String? = null,
        block: RootPreConditionDsl.() -> Unit,
    ) {
        val preconditionContainerContext =
            PreconditionContainerContext(
                onError = onError,
                onFail = onFail,
                onFailMessage = onFailMessage,
                onErrorMessage = onErrorMessage,
                onSqlOutput = onSqlOutput,
            )
        val dsl =
            PreConditionDsl.build(
                changeLog = changeLog,
                preconditionContainerContext = preconditionContainerContext,
            )
        block(dsl)
        context.changeSet.preconditions = dsl.preconditionContainer
    }

    fun validCheckSum(checksum: String) {
        context.changeSet.addValidCheckSum(checksum)
    }

    fun rollback(block: ChangeSetDsl.() -> Unit) {
        val dsl =
            ChangeSetDsl(
                changeLog = changeLog,
                context = context,
            )
        block(dsl)
    }

    fun rollback(
        changeSetId: String,
        changeSetAuthor: String,
        changeSetPath: String? = null,
    ) {
        val overrideId = changeSetId.expandExpressions(changeLog)
        val overrideAuthor = changeSetAuthor.expandExpressions(changeLog)

        val overrideFilePath =
            changeSetPath?.let {
                changeSetPath.expandExpressions(changeLog)
            } ?: changeLog.filePath

        val referencedChangeSet =
            changeLog.getChangeSet(
                overrideFilePath,
                overrideAuthor,
                overrideId,
            ) ?: throw RollbackImpossibleException(
                "Could not find changeSet to use for rollback: $changeSetPath:$changeSetAuthor:$changeSetId",
            )
        referencedChangeSet.changes.forEach { change ->
            context.changeSet.addRollbackChange(change)
        }
    }

    // Entities
    fun createTable(
        catalogName: String? = null,
        ifNotExists: Boolean? = null,
        remarks: String? = null,
        schemaName: String? = null,
        tableName: String,
        tablespace: String? = null,
        block: ColumnDsl.() -> Unit,
    ) {
        val change = changeSetSupport.createChange("createTable") as CreateTableChange
        change.catalogName = catalogName
        change.ifNotExists = ifNotExists
        change.remarks = remarks
        change.schemaName = schemaName
        change.tableName = tableName
        change.tablespace = tablespace
        change.columns = change.toColumnDsl(block)
        changeSetSupport.addChange(change)
    }

    fun dropTable(
        cascadeConstraints: Boolean? = null,
        catalogName: String? = null,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("dropTable") as DropTableChange
        change.isCascadeConstraints = cascadeConstraints
        change.catalogName = catalogName
        change.schemaName = schemaName
        change.tableName = tableName
        changeSetSupport.addChange(change)
    }

    fun setTableRemarks(
        catalogName: String? = null,
        remarks: String,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("setTableRemarks") as SetTableRemarksChange
        change.catalogName = catalogName
        change.remarks = remarks
        change.schemaName = schemaName
        change.tableName = tableName
        changeSetSupport.addChange(change)
    }

    fun renameTable(
        catalogName: String? = null,
        newTableName: String,
        oldTableName: String,
        schemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("renameTable") as RenameTableChange
        change.catalogName = catalogName
        change.newTableName = newTableName
        change.oldTableName = oldTableName
        change.schemaName = schemaName
        changeSetSupport.addChange(change)
    }

    fun addColumn(
        catalogName: String? = null,
        schemaName: String? = null,
        tableName: String,
        block: AddColumnDsl.() -> Unit,
    ) {
        val change = changeSetSupport.createChange("addColumn") as AddColumnChange
        change.catalogName = catalogName
        change.schemaName = schemaName
        change.tableName = tableName
        change.columns = change.toColumnDsl(block)
        changeSetSupport.addChange(change)
    }

    fun dropColumn(
        catalogName: String? = null,
        columnName: String? = null,
        schemaName: String? = null,
        tableName: String,
        // Used when deleting multiple columns.
        block: (ColumnDsl.() -> Unit)? = null,
    ) {
        val change = changeSetSupport.createChange("dropColumn") as DropColumnChange
        change.catalogName = catalogName
        change.columnName = columnName
        change.schemaName = schemaName
        change.tableName = tableName
        block?.also { change.columns = change.toColumnDsl(block) }
        changeSetSupport.addChange(change)
    }

    fun renameColumn(
        catalogName: String? = null,
        columnDataType: String? = null,
        newColumnName: String,
        oldColumnName: String,
        remarks: String? = null,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("renameColumn") as RenameColumnChange
        change.catalogName = catalogName
        change.columnDataType = columnDataType
        change.newColumnName = newColumnName
        change.oldColumnName = oldColumnName
        change.remarks = remarks
        change.schemaName = schemaName
        change.tableName = tableName
        changeSetSupport.addChange(change)
    }

    fun modifyDataType(
        catalogName: String? = null,
        columnName: String,
        newDataType: String,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("modifyDataType") as ModifyDataTypeChange
        change.catalogName = catalogName
        change.columnName = columnName
        change.newDataType = newDataType
        change.schemaName = schemaName
        change.tableName = tableName
        changeSetSupport.addChange(change)
    }

    fun setColumnRemarks(
        catalogName: String? = null,
        columnName: String,
        remarks: String,
        schemaName: String? = null,
        tableName: String,
        columnDataType: String? = null,
        columnParentType: String? = null,
    ) {
        val change = changeSetSupport.createChange("setColumnRemarks") as SetColumnRemarksChange
        change.catalogName = catalogName
        change.columnName = columnName
        change.remarks = remarks
        change.schemaName = schemaName
        change.tableName = tableName
        change.columnDataType = columnDataType
        change.columnParentType = columnParentType
        changeSetSupport.addChange(change)
    }

    fun addAutoIncrement(
        catalogName: String? = null,
        columnDataType: String? = null,
        columnName: String,
        defaultOnNull: Boolean? = null,
        generationType: String? = null,
        incrementBy: Long? = null,
        schemaName: String? = null,
        startWith: Long? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("addAutoIncrement") as AddAutoIncrementChange
        change.catalogName = catalogName
        change.columnDataType = columnDataType
        change.columnName = columnName
        change.defaultOnNull = defaultOnNull
        change.generationType = generationType
        change.incrementBy = incrementBy?.toBigInteger()
        change.schemaName = schemaName
        change.startWith = startWith?.toBigInteger()
        change.tableName = tableName
        changeSetSupport.addChange(change)
    }

    fun createIndex(
        associatedWith: String? = null,
        catalogName: String? = null,
        clustered: Boolean? = null,
        indexName: String? = null,
        schemaName: String? = null,
        tableName: String,
        tablespace: String? = null,
        unique: Boolean? = null,
    ) {
        val change = changeSetSupport.createChange("createIndex") as CreateIndexChange
        change.associatedWith = associatedWith
        change.catalogName = catalogName
        change.clustered = clustered
        change.indexName = indexName
        change.schemaName = schemaName
        change.tableName = tableName
        change.tablespace = tablespace
        change.isUnique = unique
        changeSetSupport.addChange(change)
    }

    fun dropIndex(
        catalogName: String? = null,
        indexName: String,
        schemaName: String? = null,
        tableName: String? = null,
    ) {
        val change = changeSetSupport.createChange("dropIndex") as DropIndexChange
        change.catalogName = catalogName
        change.indexName = indexName
        change.schemaName = schemaName
        change.tableName = tableName
        changeSetSupport.addChange(change)
    }

    fun createView(
        catalogName: String? = null,
        encoding: String? = null,
        fullDefinition: Boolean? = null,
        path: String? = null,
        relativeToChangelogFile: Boolean? = null,
        remarks: String? = null,
        replaceIfExists: Boolean? = null,
        schemaName: String? = null,
        viewName: String,
        selectQuery: () -> String,
    ) {
        val change = changeSetSupport.createChange("createView") as CreateViewChange
        change.catalogName = catalogName
        change.encoding = encoding
        change.fullDefinition = fullDefinition
        change.path = path
        change.relativeToChangelogFile = relativeToChangelogFile
        change.remarks = remarks
        change.replaceIfExists = replaceIfExists
        change.schemaName = schemaName
        change.selectQuery = selectQuery().expandExpressions(changeLog)
        change.viewName = viewName
        changeSetSupport.addChange(change)
    }

    fun dropView(
        catalogName: String? = null,
        ifExists: Boolean? = null,
        schemaName: String? = null,
        viewName: String,
    ) {
        val change = changeSetSupport.createChange("dropView") as DropViewChange
        change.catalogName = catalogName
        change.isIfExists = ifExists
        change.schemaName = schemaName
        change.viewName = viewName
        changeSetSupport.addChange(change)
    }

    fun renameView(
        catalogName: String? = null,
        newViewName: String,
        oldViewName: String,
        schemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("renameView") as RenameViewChange
        change.catalogName = catalogName
        change.newViewName = newViewName
        change.oldViewName = oldViewName
        change.schemaName = schemaName
        changeSetSupport.addChange(change)
    }

    fun createProcedure(
        catalogName: String? = null,
        dbms: String? = null,
        encoding: String? = null,
        path: String,
        procedureName: String? = null,
        relativeToChangelogFile: Boolean? = null,
        replaceIfExists: Boolean? = null,
        schemaName: String? = null,
        procedureText: () -> String,
    ) {
        val change = changeSetSupport.createChange("createProcedure") as CreateProcedureChange
        change.catalogName = catalogName
        change.dbms = dbms
        change.encoding = encoding
        change.path = path
        change.procedureText = procedureText().expandExpressions(changeLog)
        change.procedureName = procedureName
        change.isRelativeToChangelogFile = relativeToChangelogFile
        change.replaceIfExists = replaceIfExists
        change.schemaName = schemaName
        changeSetSupport.addChange(change)
    }

    fun dropProcedure(
        catalogName: String? = null,
        procedureName: String,
        schemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("dropProcedure") as DropProcedureChange
        change.catalogName = catalogName
        change.procedureName = procedureName
        change.schemaName = schemaName
        changeSetSupport.addChange(change)
    }

    fun createSequence(
        cacheSize: Long? = null,
        catalogName: String? = null,
        cycle: Boolean? = null,
        dataType: String? = null,
        incrementBy: Long? = null,
        maxValue: Long? = null,
        minValue: Long? = null,
        ordered: Boolean? = null,
        schemaName: String? = null,
        sequenceName: String,
        startValue: Long? = null,
    ) {
        val change = changeSetSupport.createChange("createSequence") as CreateSequenceChange
        change.cacheSize = cacheSize?.toBigInteger()
        change.catalogName = catalogName
        change.cycle = cycle
        change.dataType = dataType
        change.incrementBy = incrementBy?.toBigInteger()
        change.maxValue = maxValue?.toBigInteger()
        change.minValue = minValue?.toBigInteger()
        change.isOrdered = ordered
        change.schemaName = schemaName
        change.sequenceName = sequenceName
        change.startValue = startValue?.toBigInteger()
        changeSetSupport.addChange(change)
    }

    fun dropSequence(
        catalogName: String? = null,
        schemaName: String? = null,
        sequenceName: String,
    ) {
        val change = changeSetSupport.createChange("dropSequence") as DropSequenceChange
        change.catalogName = catalogName
        change.schemaName = schemaName
        change.sequenceName = sequenceName
        changeSetSupport.addChange(change)
    }

    fun renameSequence(
        catalogName: String? = null,
        newSequenceName: String,
        oldSequenceName: String,
        schemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("renameSequence") as RenameSequenceChange
        change.catalogName = catalogName
        change.newSequenceName = newSequenceName
        change.oldSequenceName = oldSequenceName
        change.schemaName = schemaName
        changeSetSupport.addChange(change)
    }

    fun alterSequence(
        cacheSize: String? = null,
        catalogName: String? = null,
        cycle: Boolean? = null,
        dataType: String? = null,
        incrementBy: Long? = null,
        maxValue: String? = null,
        minValue: String? = null,
        ordered: Boolean? = null,
        schemaName: String? = null,
        sequenceName: String,
    ) {
        val change = changeSetSupport.createChange("alterSequence") as AlterSequenceChange
        change.cacheSize = cacheSize?.toBigInteger()
        change.catalogName = catalogName
        change.cycle = cycle
        change.dataType = dataType
        change.incrementBy = incrementBy?.toBigInteger()
        change.maxValue = maxValue?.toBigInteger()
        change.minValue = minValue?.toBigInteger()
        change.isOrdered = ordered
        change.schemaName = schemaName
        change.sequenceName = sequenceName
        changeSetSupport.addChange(change)
    }

    // for pro
//    fun createFunction(
//        catalogName: String? = null,
//        dbms: String? = null,
//        encoding: String? = null,
//        functionBody: String,
//        functionName: String,
//        path: String,
//        procedureText: String,
//        relativeToChangelogFile: Boolean? = null,
//        replaceIfExists: Boolean? = null,
//        schemaName: String? = null,
//    ) {
//        val change = changeSetSupport.createChange("createFunction") as CreateFunctionChange
//        change.catalogName = catalogName
//        change.dbms = dbms
//        change.encoding = encoding
//        change.functionBody = functionBody
//        change.functionName = functionName
//        change.path = path
//        change.procedureText = procedureText
//        change.isRelativeToChangelogFile = relativeToChangelogFile
//        change.replaceIfExists = replaceIfExists
//        change.schemaName = schemaName
//        changeSetSupport.addChange(change)
//    }
//
//    fun dropFunction(
//        catalogName: String? = null,
//        functionName: String,
//        schemaName: String? = null,
//    ) {
//        val change = changeSetSupport.createChange("dropFunction") as DropFunctionChange
//        change.catalogName = catalogName
//        change.functionName = functionName
//        change.schemaName = schemaName
//        changeSetSupport.addChange(change)
//    }
//
//    fun createPackage(
//        catalogName: String? = null,
//        dbms: String? = null,
//        encoding: String? = null,
//        packageName: String,
//        packageText: String,
//        path: String,
//        procedureText: String,
//        relativeToChangelogFile: Boolean? = null,
//        replaceIfExists: Boolean? = null,
//        schemaName: String? = null,
//    ) {
//        val change = changeSetSupport.createChange("createPackage") as CreatePackageChange
//        change.catalogName = catalogName
//        change.dbms = dbms
//        change.encoding = encoding
//        change.packageName = packageName
//        change.packageText = packageText
//        change.path = path
//        change.procedureText = procedureText
//        change.isRelativeToChangelogFile = relativeToChangelogFile
//        change.replaceIfExists = replaceIfExists
//        change.schemaName = schemaName
//        changeSetSupport.addChange(change)
//    }
//
//    fun createPackageBody(
//        catalogName: String? = null,
//        dbms: String? = null,
//        encoding: String? = null,
//        packageBodyName: String,
//        packageBodyText: String,
//        path: String,
//        procedureText: String,
//        relativeToChangelogFile: Boolean? = null,
//        replaceIfExists: Boolean? = null,
//        schemaName: String? = null,
//    ) {
//        val change = changeSetSupport.createChange("createPackageBody") as CreatePackageBodyChange
//        change.catalogName = catalogName
//        change.dbms = dbms
//        change.encoding = encoding
//        change.packageBodyName = packageBodyName
//        change.packageBodyText = packageBodyText
//        change.path = path
//        change.procedureText = procedureText
//        change.isRelativeToChangelogFile = relativeToChangelogFile
//        change.replaceIfExists = replaceIfExists
//        change.schemaName = schemaName
//        changeSetSupport.addChange(change)
//    }
//
//    fun dropPackage(
//        catalogName: String? = null,
//        packageName: String,
//        schemaName: String? = null,
//    ) {
//        val change = changeSetSupport.createChange("dropPackage") as DropPackageChange
//        change.catalogName = catalogName
//        change.packageName = packageName
//        change.schemaName = schemaName
//        changeSetSupport.addChange(change)
//    }
//
//    fun dropPackageBody(
//        catalogName: String? = null,
//        packageBodyName: String,
//        schemaName: String? = null,
//    ) {
//        val change = changeSetSupport.createChange("dropPackageBody") as DropPackageBodyChange
//        change.catalogName = catalogName
//        change.packageBodyName = packageBodyName
//        change.schemaName = schemaName
//        changeSetSupport.addChange(change)
//    }
//
//    fun createSynonym(
//        objectCatalogName: String? = null,
//        objectName: String,
//        objectSchemaName: String? = null,
//        objectType: String? = null,
//        private: String? = null,
//        replaceIfExists: Boolean? = null,
//        synonymCatalogName: String? = null,
//        synonymName: String,
//        synonymSchemaName: String? = null,
//    ) {
//        val change = changeSetSupport.createChange("createSynonym") as CreateSynonymChange
//        change.objectCatalogName = objectCatalogName
//        change.objectName = objectName
//        change.objectSchemaName = objectSchemaName
//        change.objectType = objectType
//        change.private = private
//        change.replaceIfExists = replaceIfExists
//        change.synonymCatalogName = synonymCatalogName
//        change.synonymName = synonymName
//        change.synonymSchemaName = synonymSchemaName
//        changeSetSupport.addChange(change)
//    }
//
//    fun dropSynonym(
//        objectType: String? = null,
//        private: String? = null,
//        synonymCatalogName: String? = null,
//        synonymName: String,
//        synonymSchemaName: String? = null,
//    ) {
//        val change = changeSetSupport.createChange("dropSynonym") as DropSynonymChange
//        change.objectType = objectType
//        change.private = private
//        change.synonymCatalogName = synonymCatalogName
//        change.synonymName = synonymName
//        change.synonymSchemaName = synonymSchemaName
//        changeSetSupport.addChange(change)
//    }
//
//    fun createTrigger(
//        catalogName: String? = null,
//        dbms: String? = null,
//        disabled: Boolean? = null,
//        encoding: String? = null,
//        path: String,
//        procedureText: String,
//        relativeToChangelogFile: Boolean? = null,
//        replaceIfExists: Boolean? = null,
//        schemaName: String? = null,
//        scope: String? = null,
//        tableName: String? = null,
//        triggerBody: String,
//        triggerName: String,
//    ) {
//        val change = changeSetSupport.createChange("createTrigger") as CreateTriggerChange
//        change.catalogName = catalogName
//        change.dbms = dbms
//        change.disabled = disabled
//        change.encoding = encoding
//        change.path = path
//        change.procedureText = procedureText
//        change.isRelativeToChangelogFile = relativeToChangelogFile
//        change.replaceIfExists = replaceIfExists
//        change.schemaName = schemaName
//        change.scope = scope
//        change.tableName = tableName
//        change.triggerBody = triggerBody
//        change.triggerName = triggerName
//        changeSetSupport.addChange(change)
//    }
//
//    fun enableTrigger(
//        catalogName: String? = null,
//        schemaName: String? = null,
//        scope: String? = null,
//        tableName: String? = null,
//        triggerName: String,
//    ) {
//        val change = changeSetSupport.createChange("enableTrigger") as EnableTriggerChange
//        change.catalogName = catalogName
//        change.schemaName = schemaName
//        change.scope = scope
//        change.tableName = tableName
//        change.triggerName = triggerName
//        changeSetSupport.addChange(change)
//    }
//
//    fun dropTrigger(
//        catalogName: String? = null,
//        schemaName: String? = null,
//        scope: String? = null,
//        tableName: String? = null,
//        triggerName: String,
//    ) {
//        val change = changeSetSupport.createChange("dropTrigger") as DropTriggerChange
//        change.catalogName = catalogName
//        change.schemaName = schemaName
//        change.scope = scope
//        change.tableName = tableName
//        change.triggerName = triggerName
//        changeSetSupport.addChange(change)
//    }
//
//    fun disableTrigger(
//        catalogName: String? = null,
//        schemaName: String? = null,
//        scope: String? = null,
//        tableName: String? = null,
//        triggerName: String,
//    ) {
//        val change = changeSetSupport.createChange("disableTrigger") as DisableTriggerChange
//        change.catalogName = catalogName
//        change.schemaName = schemaName
//        change.scope = scope
//        change.tableName = tableName
//        change.triggerName = triggerName
//        changeSetSupport.addChange(change)
//    }
//
//    fun renameTrigger(
//        catalogName: String? = null,
//        newTriggerName: String,
//        oldTriggerName: String,
//        schemaName: String? = null,
//        tableName: String? = null,
//    ) {
//        val change = changeSetSupport.createChange("renameTrigger") as RenameTriggerChange
//        change.catalogName = catalogName
//        change.newTriggerName = newTriggerName
//        change.oldTriggerName = oldTriggerName
//        change.schemaName = schemaName
//        change.tableName = tableName
//        changeSetSupport.addChange(change)
//    }
//
//    // Constraints
//    fun addCheckConstraint(
//        catalogName: String? = null,
//        constraintBody: String,
//        constraintName: String,
//        disabled: Boolean? = null,
//        schemaName: String? = null,
//        tableName: String,
//        validate: Boolean? = null,
//    ) {
//        val change = changeSetSupport.createChange("addCheckConstraint") as AddCheckConstraintChange
//        change.catalogName = catalogName
//        change.constraintBody = constraintBody
//        change.constraintName = constraintName
//        change.disabled = disabled
//        change.schemaName = schemaName
//        change.tableName = tableName
//        change.validate = validate
//        changeSetSupport.addChange(change)
//    }
//
//    fun enableCheckConstraint(
//        catalogName: String? = null,
//        constraintName: String,
//        schemaName: String? = null,
//        tableName: String,
//    ) {
//        val change = changeSetSupport.createChange("enableCheckConstraint") as EnableCheckConstraintChange
//        change.catalogName = catalogName
//        change.constraintName = constraintName
//        change.schemaName = schemaName
//        change.tableName = tableName
//        changeSetSupport.addChange(change)
//    }
//
//    fun dropCheckConstraint(
//        catalogName: String? = null,
//        constraintName: String,
//        schemaName: String? = null,
//        tableName: String,
//    ) {
//        val change = changeSetSupport.createChange("dropCheckConstraint") as DropCheckConstraintChange
//        change.catalogName = catalogName
//        change.constraintName = constraintName
//        change.schemaName = schemaName
//        change.tableName = tableName
//        changeSetSupport.addChange(change)
//    }
//
//    fun disableCheckConstraint(
//        catalogName: String? = null,
//        constraintName: String,
//        schemaName: String? = null,
//        tableName: String,
//    ) {
//        val change = changeSetSupport.createChange("disableCheckConstraint") as DisableCheckConstraintChange
//        change.catalogName = catalogName
//        change.constraintName = constraintName
//        change.schemaName = schemaName
//        change.tableName = tableName
//        changeSetSupport.addChange(change)
//    }

    fun addDefaultValue(
        catalogName: String? = null,
        columnDataType: String? = null,
        columnName: String,
        defaultValue: String? = null,
        defaultValueBoolean: Boolean? = null,
        defaultValueComputed: String? = null,
        defaultValueConstraintName: String? = null,
        defaultValueDate: String? = null,
        defaultValueNumeric: String? = null,
        defaultValueSequenceNext: String? = null,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("addDefaultValue") as AddDefaultValueChange
        change.catalogName = catalogName
        change.columnDataType = columnDataType
        change.columnName = columnName
        change.defaultValue = defaultValue
        change.defaultValueBoolean = defaultValueBoolean
//        change.defaultValueComputed = defaultValueComputed // TODO
        change.defaultValueConstraintName = defaultValueConstraintName
        change.defaultValueDate = defaultValueDate
        change.defaultValueNumeric = defaultValueNumeric
//        change.defaultValueSequenceNext = defaultValueSequenceNext // TODO
        change.schemaName = schemaName
        change.tableName = tableName
        changeSetSupport.addChange(change)
    }

    fun dropDefaultValue(
        catalogName: String? = null,
        columnDataType: String? = null,
        columnName: String,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("dropDefaultValue") as DropDefaultValueChange
        change.catalogName = catalogName
        change.columnDataType = columnDataType
        change.columnName = columnName
        change.schemaName = schemaName
        change.tableName = tableName
        changeSetSupport.addChange(change)
    }

    fun addForeignKeyConstraint(
        baseColumnNames: String,
        baseTableCatalogName: String? = null,
        baseTableName: String,
        baseTableSchemaName: String? = null,
        constraintName: String,
        deferrable: Boolean? = null,
        initiallyDeferred: Boolean? = null,
        onDelete: String? = null,
        onUpdate: String? = null,
        referencedColumnNames: String,
        referencedTableCatalogName: String? = null,
        referencedTableName: String,
        referencedTableSchemaName: String? = null,
        validate: Boolean? = null,
        referencesUniqueColumn: Boolean? = null,
    ) {
        val change = changeSetSupport.createChange("addForeignKeyConstraint") as AddForeignKeyConstraintChange
        change.baseColumnNames = baseColumnNames
        change.baseTableCatalogName = baseTableCatalogName
        change.baseTableName = baseTableName
        change.baseTableSchemaName = baseTableSchemaName
        change.constraintName = constraintName
        change.deferrable = deferrable
        change.initiallyDeferred = initiallyDeferred
        change.onDelete = onDelete
        change.onUpdate = onUpdate
        change.referencedColumnNames = referencedColumnNames
        change.referencedTableCatalogName = referencedTableCatalogName
        change.referencedTableName = referencedTableName
        change.referencedTableSchemaName = referencedTableSchemaName
        change.validate = validate
        referencesUniqueColumn?.also {
            log.warning("referencesUniqueColumn is deprecated")
        }
        change.referencesUniqueColumn = referencesUniqueColumn
        changeSetSupport.addChange(change)
    }

    fun dropForeignKeyConstraint(
        baseTableCatalogName: String? = null,
        baseTableName: String,
        baseTableSchemaName: String? = null,
        constraintName: String,
    ) {
        val change = changeSetSupport.createChange("dropForeignKeyConstraint") as DropForeignKeyConstraintChange
        change.baseTableCatalogName = baseTableCatalogName
        change.baseTableName = baseTableName
        change.baseTableSchemaName = baseTableSchemaName
        change.constraintName = constraintName
        changeSetSupport.addChange(change)
    }

    fun dropAllForeignKeyConstraints(
        baseTableCatalogName: String? = null,
        baseTableName: String,
        baseTableSchemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("dropAllForeignKeyConstraints") as DropAllForeignKeyConstraintsChange
        change.baseTableCatalogName = baseTableCatalogName
        change.baseTableName = baseTableName
        change.baseTableSchemaName = baseTableSchemaName
        changeSetSupport.addChange(change)
    }

    fun addNotNullConstraint(
        catalogName: String? = null,
        columnDataType: String? = null,
        columnName: String,
        constraintName: String? = null,
        defaultNullValue: String? = null,
        schemaName: String? = null,
        tableName: String,
        validate: Boolean? = null,
    ) {
        val change = changeSetSupport.createChange("addNotNullConstraint") as AddNotNullConstraintChange
        change.catalogName = catalogName
        change.columnDataType = columnDataType
        change.columnName = columnName
        change.constraintName = constraintName
        change.defaultNullValue = defaultNullValue
        change.schemaName = schemaName
        change.tableName = tableName
        change.validate = validate
        changeSetSupport.addChange(change)
    }

    fun dropNotNullConstraint(
        catalogName: String? = null,
        columnDataType: String? = null,
        columnName: String,
        constraintName: String? = null,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("dropNotNullConstraint") as DropNotNullConstraintChange
        change.catalogName = catalogName
        change.columnDataType = columnDataType
        change.columnName = columnName
        change.constraintName = constraintName
        change.schemaName = schemaName
        change.tableName = tableName
        changeSetSupport.addChange(change)
    }

    fun addPrimaryKey(
        catalogName: String? = null,
        clustered: Boolean? = null,
        columnNames: String,
        constraintName: String? = null,
        forIndexCatalogName: String? = null,
        forIndexName: String? = null,
        forIndexSchemaName: String? = null,
        schemaName: String? = null,
        tableName: String,
        tablespace: String? = null,
        validate: Boolean? = null,
    ) {
        val change = changeSetSupport.createChange("addPrimaryKey") as AddPrimaryKeyChange
        change.catalogName = catalogName
        change.clustered = clustered
        change.columnNames = columnNames
        change.constraintName = constraintName
        change.forIndexCatalogName = forIndexCatalogName
        change.forIndexName = forIndexName
        change.forIndexSchemaName = forIndexSchemaName
        change.schemaName = schemaName
        change.tableName = tableName
        change.tablespace = tablespace
        change.validate = validate
        changeSetSupport.addChange(change)
    }

    fun dropPrimaryKey(
        catalogName: String? = null,
        constraintName: String? = null,
        dropIndex: Boolean? = null,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("dropPrimaryKey") as DropPrimaryKeyChange
        change.catalogName = catalogName
        change.constraintName = constraintName
        change.dropIndex = dropIndex
        change.schemaName = schemaName
        change.tableName = tableName
        changeSetSupport.addChange(change)
    }

    fun addUniqueConstraint(
        catalogName: String? = null,
        clustered: Boolean? = null,
        columnNames: String,
        constraintName: String? = null,
        deferrable: Boolean? = null,
        disabled: Boolean? = null,
        forIndexCatalogName: String? = null,
        forIndexName: String? = null,
        forIndexSchemaName: String? = null,
        initiallyDeferred: Boolean? = null,
        schemaName: String? = null,
        tableName: String,
        tablespace: String? = null,
        validate: Boolean? = null,
    ) {
        val change = changeSetSupport.createChange("addUniqueConstraint") as AddUniqueConstraintChange
        change.catalogName = catalogName
        change.clustered = clustered
        change.columnNames = columnNames
        change.constraintName = constraintName
        change.deferrable = deferrable
        change.disabled = disabled
        change.forIndexCatalogName = forIndexCatalogName
        change.forIndexName = forIndexName
        change.forIndexSchemaName = forIndexSchemaName
        change.initiallyDeferred = initiallyDeferred
        change.schemaName = schemaName
        change.tableName = tableName
        change.tablespace = tablespace
        change.validate = validate
        changeSetSupport.addChange(change)
    }

    fun dropUniqueConstraint(
        catalogName: String? = null,
        constraintName: String,
        schemaName: String? = null,
        tableName: String,
        uniqueColumns: String? = null,
    ) {
        val change = changeSetSupport.createChange("dropUniqueConstraint") as DropUniqueConstraintChange
        change.catalogName = catalogName
        change.constraintName = constraintName
        change.schemaName = schemaName
        change.tableName = tableName
        change.uniqueColumns = uniqueColumns
        changeSetSupport.addChange(change)
    }

    // Data
    fun addLookupTable(
        constraintName: String? = null,
        existingColumnName: String,
        existingTableCatalogName: String? = null,
        existingTableName: String,
        existingTableSchemaName: String? = null,
        newColumnDataType: String? = null,
        newColumnName: String,
        newTableCatalogName: String? = null,
        newTableName: String,
        newTableSchemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("addLookupTable") as AddLookupTableChange
        change.constraintName = constraintName
        change.existingColumnName = existingColumnName
        change.existingTableCatalogName = existingTableCatalogName
        change.existingTableName = existingTableName
        change.existingTableSchemaName = existingTableSchemaName
        change.newColumnDataType = newColumnDataType
        change.newColumnName = newColumnName
        change.newTableCatalogName = newTableCatalogName
        change.newTableName = newTableName
        change.newTableSchemaName = newTableSchemaName
        changeSetSupport.addChange(change)
    }

    fun delete(
        catalogName: String? = null,
        schemaName: String? = null,
        tableName: String,
        block: ColumnDsl.() -> Unit,
    ) {
        val change = changeSetSupport.createChange("delete") as DeleteDataChange
        change.catalogName = catalogName
        change.schemaName = schemaName
        change.tableName = tableName
        change.toColumnDsl(block).forEach {
            change.addWhereParam(it)
        }
        changeSetSupport.addChange(change)
    }

    fun insert(
        catalogName: String? = null,
        dbms: String? = null,
        schemaName: String? = null,
        tableName: String,
        block: ColumnDsl.() -> Unit,
    ) {
        val change = changeSetSupport.createChange("insert") as InsertDataChange
        change.catalogName = catalogName
        change.dbms = dbms
        change.schemaName = schemaName
        change.tableName = tableName
        change.columns = change.toColumnDsl(block)
        changeSetSupport.addChange(change)
    }

    fun loadData(
        catalogName: String? = null,
        commentLineStartsWith: String? = null,
        encoding: String? = null,
        file: String,
        quotchar: String? = null,
        relativeToChangelogFile: Boolean? = null,
        schemaName: String? = null,
        separator: String? = null,
        tableName: String,
        usePreparedStatements: Boolean? = null,
        block: LoadDataColumnDsl.() -> Unit,
    ) {
        val change = changeSetSupport.createChange("loadData") as LoadDataChange
        change.catalogName = catalogName
        change.commentLineStartsWith = commentLineStartsWith
        change.encoding = encoding
        change.file = file
        change.quotchar = quotchar
        change.isRelativeToChangelogFile = relativeToChangelogFile
        change.schemaName = schemaName
        change.separator = separator
        change.tableName = tableName
        change.usePreparedStatements = usePreparedStatements
        change.columns = change.toColumnDsl(block)
        changeSetSupport.addChange(change)
    }

    fun loadUpdateData(
        catalogName: String? = null,
        commentLineStartsWith: String? = null,
        encoding: String? = null,
        file: String,
        onlyUpdate: Boolean? = null,
        primaryKey: String,
        quotchar: String? = null,
        relativeToChangelogFile: Boolean? = null,
        schemaName: String? = null,
        separator: String? = null,
        tableName: String,
        usePreparedStatements: Boolean? = null,
        block: LoadDataColumnDsl.() -> Unit,
    ) {
        val change = changeSetSupport.createChange("loadUpdateData") as LoadUpdateDataChange
        change.catalogName = catalogName
        change.commentLineStartsWith = commentLineStartsWith
        change.encoding = encoding
        change.file = file
        change.onlyUpdate = onlyUpdate
        change.primaryKey = primaryKey
        change.quotchar = quotchar
        change.isRelativeToChangelogFile = relativeToChangelogFile
        change.schemaName = schemaName
        change.separator = separator
        change.tableName = tableName
        change.usePreparedStatements = usePreparedStatements
        change.columns = change.toColumnDsl(block)
        changeSetSupport.addChange(change)
    }

    fun mergeColumns(
        catalogName: String? = null,
        column1Name: String,
        column2Name: String,
        finalColumnName: String,
        finalColumnType: String,
        joinString: String? = null,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("mergeColumns") as MergeColumnChange
        change.catalogName = catalogName
        change.column1Name = column1Name
        change.column2Name = column2Name
        change.finalColumnName = finalColumnName
        change.finalColumnType = finalColumnType
        change.joinString = joinString
        change.schemaName = schemaName
        change.tableName = tableName
        changeSetSupport.addChange(change)
    }

    /**
     * https://docs.liquibase.com/change-types/modify-sql.html
     */
    fun modifySql(
        dbms: String? = null,
        contextFilter: String? = null,
        context: String? = null,
        applyToRollback: Boolean? = false,
        block: ModifySqlDsl.() -> Unit,
    ) {
        val dsl =
            ModifySqlDsl.build(
                changeLog = changeLog,
                dbms = dbms,
                contextFilter = contextFilter ?: context,
                applyToRollback = applyToRollback,
            )
        block(dsl)
        dsl.sqlVisitors.forEach {
            this.context.changeSet.addSqlVisitor(it)
        }
    }

    fun update(
        catalogName: String? = null,
        schemaName: String? = null,
        tableName: String,
        block: ColumnDsl.() -> Unit,
    ) {
        val change = changeSetSupport.createChange("update") as UpdateDataChange
        change.catalogName = catalogName
        change.schemaName = schemaName
        change.tableName = tableName
        change.columns = change.toColumnDsl(block)
        changeSetSupport.addChange(change)
    }

    // Miscellaneous
    fun customChange(
        `class`: String? = null,
        clazz: String? = null, // syntax sugar(Not official)
        className: String? = null, // syntax sugar(Not official)
        block: (KeyValueDsl.() -> Unit)? = null,
    ) {
        val classOrClazz =
            `class` ?: clazz ?: className ?: throw ChangeLogParseException(
                "Should be specify `class` or clazz",
            )
        // TODO: create KotlinCustomChangeWrapper
        val change = changeSetSupport.createChange("customChange") as CustomChangeWrapper
//        change.classLoader = block?.javaClass?.javaClass?.classLoader ?: this::class.java.classLoader
        change.setClass(classOrClazz.expandExpressions(changeLog))

        block?.let {
            val dsl = KeyValueDsl()
            it(dsl)
            dsl.map.forEach { (key, value) ->
                change.setParam(key, value.expandExpressions(changeLog))
            }
        }

        changeSetSupport.addChange(change)
    }

    fun empty() {
    }

    fun executeCommand(
        executable: String,
        os: String? = null,
        timeout: String? = null,
        block: ArgumentDsl.() -> Unit,
    ) {
        val change = changeSetSupport.createChange("executeCommand") as ExecuteShellCommandChange
        change.executable = executable
        change.setOs(os)
        change.timeout = timeout
        val dsl = ArgumentDsl()
        block(dsl)
        dsl.args.forEach {
            change.args.add(it.expandExpressions(changeLog))
        }
        changeSetSupport.addChange(change)
    }

    // for pro
//    fun markUnused(
//        catalogName: String? = null,
//        columnName: String,
//        schemaName: String? = null,
//        tableName: String,
//    ) {
//        val change = changeSetSupport.createChange("markUnused") as MarkUnusedChange
//        change.catalogName = catalogName
//        change.columnName = columnName
//        change.schemaName = schemaName
//        change.tableName = tableName
//        changeSetSupport.addChange(change)
//    }

    fun output(
        message: String? = null,
        target: String? = null,
    ) {
        val change = changeSetSupport.createChange("output") as OutputChange
        change.message = message
        change.target = target ?: "STDERR"
        changeSetSupport.addChange(change)
    }

    fun sql(
        dbms: String? = null,
        endDelimiter: String? = null,
        splitStatements: Boolean? = null,
        stripComments: Boolean? = null,
        block: CommentDsl.() -> String,
    ) {
        val change = changeSetSupport.createChange("sql") as RawSQLChange
        change.dbms = dbms
        change.endDelimiter = endDelimiter
        change.isSplitStatements = splitStatements
        change.isStripComments = stripComments
        val dsl = CommentDsl()
        val sql = block(dsl)
        change.sql = sql.expandExpressions(changeLog)
        change.comment = dsl.comment?.expandExpressions(changeLog)
        changeSetSupport.addChange(change)
    }

    fun sqlFile(
        dbms: String? = null,
        encoding: String? = null,
        endDelimiter: String? = null,
        path: String,
        relativeToChangelogFile: Boolean? = null,
        splitStatements: Boolean? = null,
        stripComments: Boolean? = null,
    ) {
        val change = changeSetSupport.createChange("sqlFile") as SQLFileChange
        change.dbms = dbms
        change.encoding = encoding
        change.endDelimiter = endDelimiter
        change.path = path
        change.isRelativeToChangelogFile = relativeToChangelogFile
        change.isSplitStatements = splitStatements
        change.isStripComments = stripComments
        change.finishInitialization() // check for path in liquibase.change.core.SQLFileChange
        changeSetSupport.addChange(change)
    }

    fun stop(message: String? = null) {
        val change = changeSetSupport.createChange("stop") as StopChange
        change.message = message.expandExpressions(changeLog)
        changeSetSupport.addChange(change)
    }

    fun tagDatabase(tag: String) {
        val change = changeSetSupport.createChange("tagDatabase") as TagDatabaseChange
        change.tag = tag.expandExpressions(changeLog)
        changeSetSupport.addChange(change)
    }

    private inline fun <reified COLUMN_CONFIG : ColumnConfig> Change.toColumnDsl(
        block: IColumnDsl<COLUMN_CONFIG>.() -> Unit,
    ): List<COLUMN_CONFIG> {
        val columnDsl = IColumnDsl(
            changeLog = changeLog,
            columnConfigClass = COLUMN_CONFIG::class,
            changeSetId = changeSet.id,
            changeName = this.serializedObjectName,
            change = this,
        )
        block(columnDsl)
        return columnDsl.columns
    }
}

class ChangeSetContext(
    val changeSet: ChangeSet,
    val inRollback: Boolean = false,
) {
    fun <E> withChangeSetContext(block: ChangeSetContext.() -> E): E = this.block()
}
