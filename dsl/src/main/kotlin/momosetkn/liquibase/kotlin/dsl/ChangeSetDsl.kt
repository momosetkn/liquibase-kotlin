package momosetkn.liquibase.kotlin.dsl

import liquibase.Scope
import liquibase.change.Change
import liquibase.change.ColumnConfig
import liquibase.change.core.ExecuteShellCommandChange
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
        ifNotExists: String? = null,
        remarks: String? = null,
        schemaName: String? = null,
        tableName: String,
        tablespace: String? = null,
        block: ColumnDsl.() -> Unit,
    ) {
        val change = changeSetSupport.createChange("createTable")
        change["catalogName"] = catalogName
        change["ifNotExists"] = ifNotExists
        change["remarks"] = remarks
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        change["tablespace"] = tablespace
        block(change.toColumnDsl())
        changeSetSupport.addChange(change)
    }

    fun dropTable(
        cascadeConstraints: String? = null,
        catalogName: String? = null,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("dropTable")
        change["cascadeConstraints"] = cascadeConstraints
        change["catalogName"] = catalogName
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        changeSetSupport.addChange(change)
    }

    fun setTableRemarks(
        catalogName: String? = null,
        remarks: String,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("setTableRemarks")
        change["catalogName"] = catalogName
        change["remarks"] = remarks
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        changeSetSupport.addChange(change)
    }

    fun renameTable(
        catalogName: String? = null,
        newTableName: String,
        oldTableName: String,
        schemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("renameTable")
        change["catalogName"] = catalogName
        change["newTableName"] = newTableName
        change["oldTableName"] = oldTableName
        change["schemaName"] = schemaName
        changeSetSupport.addChange(change)
    }

    fun addColumn(
        catalogName: String? = null,
        schemaName: String? = null,
        tableName: String,
        block: AddColumnDsl.() -> Unit,
    ) {
        val change = changeSetSupport.createChange("addColumn")
        change["catalogName"] = catalogName
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        block(change.toColumnDsl())
        changeSetSupport.addChange(change)
    }

    fun dropColumn(
        catalogName: String? = null,
        columnName: String? = null,
        schemaName: String? = null,
        tableName: String,
        block: ColumnDsl.() -> Unit,
    ) {
        val change = changeSetSupport.createChange("dropColumn")
        change["catalogName"] = catalogName
        change["columnName"] = columnName
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        block(change.toColumnDsl())
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
        val change = changeSetSupport.createChange("renameColumn")
        change["catalogName"] = catalogName
        change["columnDataType"] = columnDataType
        change["newColumnName"] = newColumnName
        change["oldColumnName"] = oldColumnName
        change["remarks"] = remarks
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        changeSetSupport.addChange(change)
    }

    fun modifyDataType(
        catalogName: String? = null,
        columnName: String,
        newDataType: String,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("modifyDataType")
        change["catalogName"] = catalogName
        change["columnName"] = columnName
        change["newDataType"] = newDataType
        change["schemaName"] = schemaName
        change["tableName"] = tableName
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
        val change = changeSetSupport.createChange("setColumnRemarks")
        change["catalogName"] = catalogName
        change["columnName"] = columnName
        change["remarks"] = remarks
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        change["columnDataType"] = columnDataType
        change["columnParentType"] = columnParentType
        changeSetSupport.addChange(change)
    }

    fun addAutoIncrement(
        catalogName: String? = null,
        columnDataType: String? = null,
        columnName: String,
        defaultOnNull: String? = null,
        generationType: String? = null,
        incrementBy: String? = null,
        schemaName: String? = null,
        startWith: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("addAutoIncrement")
        change["catalogName"] = catalogName
        change["columnDataType"] = columnDataType
        change["columnName"] = columnName
        change["defaultOnNull"] = defaultOnNull
        change["generationType"] = generationType
        change["incrementBy"] = incrementBy
        change["schemaName"] = schemaName
        change["startWith"] = startWith
        change["tableName"] = tableName
        changeSetSupport.addChange(change)
    }

    fun createIndex(
        associatedWith: String? = null,
        catalogName: String? = null,
        clustered: String? = null,
        indexName: String? = null,
        schemaName: String? = null,
        tableName: String,
        tablespace: String? = null,
        unique: String? = null,
    ) {
        val change = changeSetSupport.createChange("createIndex")
        change["associatedWith"] = associatedWith
        change["catalogName"] = catalogName
        change["clustered"] = clustered
        change["indexName"] = indexName
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        change["tablespace"] = tablespace
        change["unique"] = unique
        changeSetSupport.addChange(change)
    }

    fun dropIndex(
        catalogName: String? = null,
        indexName: String,
        schemaName: String? = null,
        tableName: String? = null,
    ) {
        val change = changeSetSupport.createChange("dropIndex")
        change["catalogName"] = catalogName
        change["indexName"] = indexName
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        changeSetSupport.addChange(change)
    }

    fun createView(
        catalogName: String? = null,
        encoding: String? = null,
        fullDefinition: String? = null,
        path: String? = null,
        relativeToChangelogFile: String? = null,
        remarks: String? = null,
        replaceIfExists: String? = null,
        schemaName: String? = null,
        viewName: String,
        selectQuery: () -> String,
    ) {
        val change = changeSetSupport.createChange("createView")
        change["catalogName"] = catalogName
        change["encoding"] = encoding
        change["fullDefinition"] = fullDefinition
        change["path"] = path
        change["relativeToChangelogFile"] = relativeToChangelogFile
        change["remarks"] = remarks
        change["replaceIfExists"] = replaceIfExists
        change["schemaName"] = schemaName
        change["selectQuery"] = selectQuery().expandExpressions(changeLog)
        change["viewName"] = viewName
        changeSetSupport.addChange(change)
    }

    fun dropView(
        catalogName: String? = null,
        ifExists: String? = null,
        schemaName: String? = null,
        viewName: String,
    ) {
        val change = changeSetSupport.createChange("dropView")
        change["catalogName"] = catalogName
        change["ifExists"] = ifExists
        change["schemaName"] = schemaName
        change["viewName"] = viewName
        changeSetSupport.addChange(change)
    }

    fun renameView(
        catalogName: String? = null,
        newViewName: String,
        oldViewName: String,
        schemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("renameView")
        change["catalogName"] = catalogName
        change["newViewName"] = newViewName
        change["oldViewName"] = oldViewName
        change["schemaName"] = schemaName
        changeSetSupport.addChange(change)
    }

    fun createProcedure(
        catalogName: String? = null,
        dbms: String? = null,
        encoding: String? = null,
        path: String,
        procedureName: String? = null,
        relativeToChangelogFile: String? = null,
        replaceIfExists: String? = null,
        schemaName: String? = null,
        procedureText: () -> String,
    ) {
        val change = changeSetSupport.createChange("createProcedure")
        change["catalogName"] = catalogName
        change["dbms"] = dbms
        change["encoding"] = encoding
        change["path"] = path
        change["procedureText"] = procedureText().expandExpressions(changeLog)
        change["procedureName"] = procedureName
        change["relativeToChangelogFile"] = relativeToChangelogFile
        change["replaceIfExists"] = replaceIfExists
        change["schemaName"] = schemaName
        changeSetSupport.addChange(change)
    }

    fun dropProcedure(
        catalogName: String? = null,
        procedureName: String,
        schemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("dropProcedure")
        change["catalogName"] = catalogName
        change["procedureName"] = procedureName
        change["schemaName"] = schemaName
        changeSetSupport.addChange(change)
    }

    fun createSequence(
        cacheSize: String? = null,
        catalogName: String? = null,
        cycle: String? = null,
        dataType: String? = null,
        incrementBy: String? = null,
        maxValue: String? = null,
        minValue: String? = null,
        ordered: String? = null,
        schemaName: String? = null,
        sequenceName: String,
        startValue: String? = null,
    ) {
        val change = changeSetSupport.createChange("createSequence")
        change["cacheSize"] = cacheSize
        change["catalogName"] = catalogName
        change["cycle"] = cycle
        change["dataType"] = dataType
        change["incrementBy"] = incrementBy
        change["maxValue"] = maxValue
        change["minValue"] = minValue
        change["ordered"] = ordered
        change["schemaName"] = schemaName
        change["sequenceName"] = sequenceName
        change["startValue"] = startValue
        changeSetSupport.addChange(change)
    }

    fun dropSequence(
        catalogName: String? = null,
        schemaName: String? = null,
        sequenceName: String,
    ) {
        val change = changeSetSupport.createChange("dropSequence")
        change["catalogName"] = catalogName
        change["schemaName"] = schemaName
        change["sequenceName"] = sequenceName
        changeSetSupport.addChange(change)
    }

    fun renameSequence(
        catalogName: String? = null,
        newSequenceName: String,
        oldSequenceName: String,
        schemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("renameSequence")
        change["catalogName"] = catalogName
        change["newSequenceName"] = newSequenceName
        change["oldSequenceName"] = oldSequenceName
        change["schemaName"] = schemaName
        changeSetSupport.addChange(change)
    }

    fun alterSequence(
        cacheSize: String? = null,
        catalogName: String? = null,
        cycle: String? = null,
        dataType: String? = null,
        incrementBy: String? = null,
        maxValue: String? = null,
        minValue: String? = null,
        ordered: String? = null,
        schemaName: String? = null,
        sequenceName: String,
    ) {
        val change = changeSetSupport.createChange("alterSequence")
        change["cacheSize"] = cacheSize
        change["catalogName"] = catalogName
        change["cycle"] = cycle
        change["dataType"] = dataType
        change["incrementBy"] = incrementBy
        change["maxValue"] = maxValue
        change["minValue"] = minValue
        change["ordered"] = ordered
        change["schemaName"] = schemaName
        change["sequenceName"] = sequenceName
        changeSetSupport.addChange(change)
    }

    fun createFunction(
        catalogName: String? = null,
        dbms: String? = null,
        encoding: String? = null,
        functionBody: String,
        functionName: String,
        path: String,
        procedureText: String,
        relativeToChangelogFile: String? = null,
        replaceIfExists: String? = null,
        schemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("createFunction")
        change["catalogName"] = catalogName
        change["dbms"] = dbms
        change["encoding"] = encoding
        change["functionBody"] = functionBody
        change["functionName"] = functionName
        change["path"] = path
        change["procedureText"] = procedureText
        change["relativeToChangelogFile"] = relativeToChangelogFile
        change["replaceIfExists"] = replaceIfExists
        change["schemaName"] = schemaName
        changeSetSupport.addChange(change)
    }

    fun dropFunction(
        catalogName: String? = null,
        functionName: String,
        schemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("dropFunction")
        change["catalogName"] = catalogName
        change["functionName"] = functionName
        change["schemaName"] = schemaName
        changeSetSupport.addChange(change)
    }

    fun createPackage(
        catalogName: String? = null,
        dbms: String? = null,
        encoding: String? = null,
        packageName: String,
        packageText: String,
        path: String,
        procedureText: String,
        relativeToChangelogFile: String? = null,
        replaceIfExists: String? = null,
        schemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("createPackage")
        change["catalogName"] = catalogName
        change["dbms"] = dbms
        change["encoding"] = encoding
        change["packageName"] = packageName
        change["packageText"] = packageText
        change["path"] = path
        change["procedureText"] = procedureText
        change["relativeToChangelogFile"] = relativeToChangelogFile
        change["replaceIfExists"] = replaceIfExists
        change["schemaName"] = schemaName
        changeSetSupport.addChange(change)
    }

    fun createPackageBody(
        catalogName: String? = null,
        dbms: String? = null,
        encoding: String? = null,
        packageBodyName: String,
        packageBodyText: String,
        path: String,
        procedureText: String,
        relativeToChangelogFile: String? = null,
        replaceIfExists: String? = null,
        schemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("createPackageBody")
        change["catalogName"] = catalogName
        change["dbms"] = dbms
        change["encoding"] = encoding
        change["packageBodyName"] = packageBodyName
        change["packageBodyText"] = packageBodyText
        change["path"] = path
        change["procedureText"] = procedureText
        change["relativeToChangelogFile"] = relativeToChangelogFile
        change["replaceIfExists"] = replaceIfExists
        change["schemaName"] = schemaName
        changeSetSupport.addChange(change)
    }

    fun dropPackage(
        catalogName: String? = null,
        packageName: String,
        schemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("dropPackage")
        change["catalogName"] = catalogName
        change["packageName"] = packageName
        change["schemaName"] = schemaName
        changeSetSupport.addChange(change)
    }

    fun dropPackageBody(
        catalogName: String? = null,
        packageBodyName: String,
        schemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("dropPackageBody")
        change["catalogName"] = catalogName
        change["packageBodyName"] = packageBodyName
        change["schemaName"] = schemaName
        changeSetSupport.addChange(change)
    }

    fun createSynonym(
        objectCatalogName: String? = null,
        objectName: String,
        objectSchemaName: String? = null,
        objectType: String? = null,
        private: String? = null,
        replaceIfExists: String? = null,
        synonymCatalogName: String? = null,
        synonymName: String,
        synonymSchemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("createSynonym")
        change["objectCatalogName"] = objectCatalogName
        change["objectName"] = objectName
        change["objectSchemaName"] = objectSchemaName
        change["objectType"] = objectType
        change["private"] = private
        change["replaceIfExists"] = replaceIfExists
        change["synonymCatalogName"] = synonymCatalogName
        change["synonymName"] = synonymName
        change["synonymSchemaName"] = synonymSchemaName
        changeSetSupport.addChange(change)
    }

    fun dropSynonym(
        objectType: String? = null,
        private: String? = null,
        synonymCatalogName: String? = null,
        synonymName: String,
        synonymSchemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("dropSynonym")
        change["objectType"] = objectType
        change["private"] = private
        change["synonymCatalogName"] = synonymCatalogName
        change["synonymName"] = synonymName
        change["synonymSchemaName"] = synonymSchemaName
        changeSetSupport.addChange(change)
    }

    fun createTrigger(
        catalogName: String? = null,
        dbms: String? = null,
        disabled: String? = null,
        encoding: String? = null,
        path: String,
        procedureText: String,
        relativeToChangelogFile: String? = null,
        replaceIfExists: String? = null,
        schemaName: String? = null,
        scope: String? = null,
        tableName: String? = null,
        triggerBody: String,
        triggerName: String,
    ) {
        val change = changeSetSupport.createChange("createTrigger")
        change["catalogName"] = catalogName
        change["dbms"] = dbms
        change["disabled"] = disabled
        change["encoding"] = encoding
        change["path"] = path
        change["procedureText"] = procedureText
        change["relativeToChangelogFile"] = relativeToChangelogFile
        change["replaceIfExists"] = replaceIfExists
        change["schemaName"] = schemaName
        change["scope"] = scope
        change["tableName"] = tableName
        change["triggerBody"] = triggerBody
        change["triggerName"] = triggerName
        changeSetSupport.addChange(change)
    }

    fun enableTrigger(
        catalogName: String? = null,
        schemaName: String? = null,
        scope: String? = null,
        tableName: String? = null,
        triggerName: String,
    ) {
        val change = changeSetSupport.createChange("enableTrigger")
        change["catalogName"] = catalogName
        change["schemaName"] = schemaName
        change["scope"] = scope
        change["tableName"] = tableName
        change["triggerName"] = triggerName
        changeSetSupport.addChange(change)
    }

    fun dropTrigger(
        catalogName: String? = null,
        schemaName: String? = null,
        scope: String? = null,
        tableName: String? = null,
        triggerName: String,
    ) {
        val change = changeSetSupport.createChange("dropTrigger")
        change["catalogName"] = catalogName
        change["schemaName"] = schemaName
        change["scope"] = scope
        change["tableName"] = tableName
        change["triggerName"] = triggerName
        changeSetSupport.addChange(change)
    }

    fun disableTrigger(
        catalogName: String? = null,
        schemaName: String? = null,
        scope: String? = null,
        tableName: String? = null,
        triggerName: String,
    ) {
        val change = changeSetSupport.createChange("disableTrigger")
        change["catalogName"] = catalogName
        change["schemaName"] = schemaName
        change["scope"] = scope
        change["tableName"] = tableName
        change["triggerName"] = triggerName
        changeSetSupport.addChange(change)
    }

    fun renameTrigger(
        catalogName: String? = null,
        newTriggerName: String,
        oldTriggerName: String,
        schemaName: String? = null,
        tableName: String? = null,
    ) {
        val change = changeSetSupport.createChange("renameTrigger")
        change["catalogName"] = catalogName
        change["newTriggerName"] = newTriggerName
        change["oldTriggerName"] = oldTriggerName
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        changeSetSupport.addChange(change)
    }

    // Constraints
    fun addCheckConstraint(
        catalogName: String? = null,
        constraintBody: String,
        constraintName: String,
        disabled: String? = null,
        schemaName: String? = null,
        tableName: String,
        validate: String? = null,
    ) {
        val change = changeSetSupport.createChange("addCheckConstraint")
        change["catalogName"] = catalogName
        change["constraintBody"] = constraintBody
        change["constraintName"] = constraintName
        change["disabled"] = disabled
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        change["validate"] = validate
        changeSetSupport.addChange(change)
    }

    fun enableCheckConstraint(
        catalogName: String? = null,
        constraintName: String,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("enableCheckConstraint")
        change["catalogName"] = catalogName
        change["constraintName"] = constraintName
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        changeSetSupport.addChange(change)
    }

    fun dropCheckConstraint(
        catalogName: String? = null,
        constraintName: String,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("dropCheckConstraint")
        change["catalogName"] = catalogName
        change["constraintName"] = constraintName
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        changeSetSupport.addChange(change)
    }

    fun disableCheckConstraint(
        catalogName: String? = null,
        constraintName: String,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("disableCheckConstraint")
        change["catalogName"] = catalogName
        change["constraintName"] = constraintName
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        changeSetSupport.addChange(change)
    }

    fun addDefaultValue(
        catalogName: String? = null,
        columnDataType: String? = null,
        columnName: String,
        defaultValue: String? = null,
        defaultValueBoolean: String? = null,
        defaultValueComputed: String? = null,
        defaultValueConstraintName: String? = null,
        defaultValueDate: String? = null,
        defaultValueNumeric: String? = null,
        defaultValueSequenceNext: String? = null,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("addDefaultValue")
        change["catalogName"] = catalogName
        change["columnDataType"] = columnDataType
        change["columnName"] = columnName
        change["defaultValue"] = defaultValue
        change["defaultValueBoolean"] = defaultValueBoolean
        change["defaultValueComputed"] = defaultValueComputed
        change["defaultValueConstraintName"] = defaultValueConstraintName
        change["defaultValueDate"] = defaultValueDate
        change["defaultValueNumeric"] = defaultValueNumeric
        change["defaultValueSequenceNext"] = defaultValueSequenceNext
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        changeSetSupport.addChange(change)
    }

    fun dropDefaultValue(
        catalogName: String? = null,
        columnDataType: String? = null,
        columnName: String,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("dropDefaultValue")
        change["catalogName"] = catalogName
        change["columnDataType"] = columnDataType
        change["columnName"] = columnName
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        changeSetSupport.addChange(change)
    }

    fun addForeignKeyConstraint(
        baseColumnNames: String,
        baseTableCatalogName: String? = null,
        baseTableName: String,
        baseTableSchemaName: String? = null,
        constraintName: String,
        deferrable: String? = null,
        initiallyDeferred: String? = null,
        onDelete: String? = null,
        onUpdate: String? = null,
        referencedColumnNames: String,
        referencedTableCatalogName: String? = null,
        referencedTableName: String,
        referencedTableSchemaName: String? = null,
        validate: String? = null,
        referencesUniqueColumn: String? = null,
    ) {
        val change = changeSetSupport.createChange("addForeignKeyConstraint")
        change["baseColumnNames"] = baseColumnNames
        change["baseTableCatalogName"] = baseTableCatalogName
        change["baseTableName"] = baseTableName
        change["baseTableSchemaName"] = baseTableSchemaName
        change["constraintName"] = constraintName
        change["deferrable"] = deferrable
        change["initiallyDeferred"] = initiallyDeferred
        change["onDelete"] = onDelete
        change["onUpdate"] = onUpdate
        change["referencedColumnNames"] = referencedColumnNames
        change["referencedTableCatalogName"] = referencedTableCatalogName
        change["referencedTableName"] = referencedTableName
        change["referencedTableSchemaName"] = referencedTableSchemaName
        change["validate"] = validate
        referencesUniqueColumn?.also {
            log.warning("referencesUniqueColumn is deprecated")
        }
        change["referencesUniqueColumn"] = referencesUniqueColumn
        changeSetSupport.addChange(change)
    }

    fun dropForeignKeyConstraint(
        baseTableCatalogName: String? = null,
        baseTableName: String,
        baseTableSchemaName: String? = null,
        constraintName: String,
    ) {
        val change = changeSetSupport.createChange("dropForeignKeyConstraint")
        change["baseTableCatalogName"] = baseTableCatalogName
        change["baseTableName"] = baseTableName
        change["baseTableSchemaName"] = baseTableSchemaName
        change["constraintName"] = constraintName
        changeSetSupport.addChange(change)
    }

    fun dropAllForeignKeyConstraints(
        baseTableCatalogName: String? = null,
        baseTableName: String,
        baseTableSchemaName: String? = null,
    ) {
        val change = changeSetSupport.createChange("dropAllForeignKeyConstraints")
        change["baseTableCatalogName"] = baseTableCatalogName
        change["baseTableName"] = baseTableName
        change["baseTableSchemaName"] = baseTableSchemaName
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
        validate: String? = null,
    ) {
        val change = changeSetSupport.createChange("addNotNullConstraint")
        change["catalogName"] = catalogName
        change["columnDataType"] = columnDataType
        change["columnName"] = columnName
        change["constraintName"] = constraintName
        change["defaultNullValue"] = defaultNullValue
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        change["validate"] = validate
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
        val change = changeSetSupport.createChange("dropNotNullConstraint")
        change["catalogName"] = catalogName
        change["columnDataType"] = columnDataType
        change["columnName"] = columnName
        change["constraintName"] = constraintName
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        changeSetSupport.addChange(change)
    }

    fun addPrimaryKey(
        catalogName: String? = null,
        clustered: String? = null,
        columnNames: String,
        constraintName: String? = null,
        forIndexCatalogName: String? = null,
        forIndexName: String? = null,
        forIndexSchemaName: String? = null,
        schemaName: String? = null,
        tableName: String,
        tablespace: String? = null,
        validate: String? = null,
    ) {
        val change = changeSetSupport.createChange("addPrimaryKey")
        change["catalogName"] = catalogName
        change["clustered"] = clustered
        change["columnNames"] = columnNames
        change["constraintName"] = constraintName
        change["forIndexCatalogName"] = forIndexCatalogName
        change["forIndexName"] = forIndexName
        change["forIndexSchemaName"] = forIndexSchemaName
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        change["tablespace"] = tablespace
        change["validate"] = validate
        changeSetSupport.addChange(change)
    }

    fun dropPrimaryKey(
        catalogName: String? = null,
        constraintName: String? = null,
        dropIndex: String? = null,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("dropPrimaryKey")
        change["catalogName"] = catalogName
        change["constraintName"] = constraintName
        change["dropIndex"] = dropIndex
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        changeSetSupport.addChange(change)
    }

    fun addUniqueConstraint(
        catalogName: String? = null,
        clustered: String? = null,
        columnNames: String,
        constraintName: String? = null,
        deferrable: String? = null,
        disabled: String? = null,
        forIndexCatalogName: String? = null,
        forIndexName: String? = null,
        forIndexSchemaName: String? = null,
        initiallyDeferred: String? = null,
        schemaName: String? = null,
        tableName: String,
        tablespace: String? = null,
        validate: String? = null,
    ) {
        val change = changeSetSupport.createChange("addUniqueConstraint")
        change["catalogName"] = catalogName
        change["clustered"] = clustered
        change["columnNames"] = columnNames
        change["constraintName"] = constraintName
        change["deferrable"] = deferrable
        change["disabled"] = disabled
        change["forIndexCatalogName"] = forIndexCatalogName
        change["forIndexName"] = forIndexName
        change["forIndexSchemaName"] = forIndexSchemaName
        change["initiallyDeferred"] = initiallyDeferred
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        change["tablespace"] = tablespace
        change["validate"] = validate
        changeSetSupport.addChange(change)
    }

    fun dropUniqueConstraint(
        catalogName: String? = null,
        constraintName: String,
        schemaName: String? = null,
        tableName: String,
        uniqueColumns: String? = null,
    ) {
        val change = changeSetSupport.createChange("dropUniqueConstraint")
        change["catalogName"] = catalogName
        change["constraintName"] = constraintName
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        change["uniqueColumns"] = uniqueColumns
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
        val change = changeSetSupport.createChange("addLookupTable")
        change["constraintName"] = constraintName
        change["existingColumnName"] = existingColumnName
        change["existingTableCatalogName"] = existingTableCatalogName
        change["existingTableName"] = existingTableName
        change["existingTableSchemaName"] = existingTableSchemaName
        change["newColumnDataType"] = newColumnDataType
        change["newColumnName"] = newColumnName
        change["newTableCatalogName"] = newTableCatalogName
        change["newTableName"] = newTableName
        change["newTableSchemaName"] = newTableSchemaName
        changeSetSupport.addChange(change)
    }

    fun delete(
        catalogName: String? = null,
        schemaName: String? = null,
        tableName: String,
        block: ColumnDsl.() -> Unit,
    ) {
        val change = changeSetSupport.createChange("delete")
        change["catalogName"] = catalogName
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        block(change.toColumnDsl())
        changeSetSupport.addChange(change)
    }

    fun insert(
        catalogName: String? = null,
        dbms: String? = null,
        schemaName: String? = null,
        tableName: String,
        block: ColumnDsl.() -> Unit,
    ) {
        val change = changeSetSupport.createChange("insert")
        change["catalogName"] = catalogName
        change["dbms"] = dbms
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        block(change.toColumnDsl())
        changeSetSupport.addChange(change)
    }

    fun loadData(
        catalogName: String? = null,
        commentLineStartsWith: String? = null,
        encoding: String? = null,
        file: String,
        quotchar: String? = null,
        relativeToChangelogFile: String? = null,
        schemaName: String? = null,
        separator: String? = null,
        tableName: String,
        usePreparedStatements: String? = null,
        block: LoadDataColumnDsl.() -> Unit,
    ) {
        val change = changeSetSupport.createChange("loadData")
        change["catalogName"] = catalogName
        change["commentLineStartsWith"] = commentLineStartsWith
        change["encoding"] = encoding
        change["file"] = file
        change["quotchar"] = quotchar
        change["relativeToChangelogFile"] = relativeToChangelogFile
        change["schemaName"] = schemaName
        change["separator"] = separator
        change["tableName"] = tableName
        change["usePreparedStatements"] = usePreparedStatements
        block(change.toColumnDsl())
        changeSetSupport.addChange(change)
    }

    fun loadUpdateData(
        catalogName: String? = null,
        commentLineStartsWith: String? = null,
        encoding: String? = null,
        file: String,
        onlyUpdate: String? = null,
        primaryKey: String,
        quotchar: String? = null,
        relativeToChangelogFile: String? = null,
        schemaName: String? = null,
        separator: String? = null,
        tableName: String,
        usePreparedStatements: String? = null,
        block: LoadDataColumnDsl.() -> Unit,
    ) {
        val change = changeSetSupport.createChange("loadUpdateData")
        change["catalogName"] = catalogName
        change["commentLineStartsWith"] = commentLineStartsWith
        change["encoding"] = encoding
        change["file"] = file
        change["onlyUpdate"] = onlyUpdate
        change["primaryKey"] = primaryKey
        change["quotchar"] = quotchar
        change["relativeToChangelogFile"] = relativeToChangelogFile
        change["schemaName"] = schemaName
        change["separator"] = separator
        change["tableName"] = tableName
        change["usePreparedStatements"] = usePreparedStatements
        block(change.toColumnDsl())
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
        val change = changeSetSupport.createChange("mergeColumns")
        change["catalogName"] = catalogName
        change["column1Name"] = column1Name
        change["column2Name"] = column2Name
        change["finalColumnName"] = finalColumnName
        change["finalColumnType"] = finalColumnType
        change["joinString"] = joinString
        change["schemaName"] = schemaName
        change["tableName"] = tableName
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
        val change = changeSetSupport.createChange("update")
        change["catalogName"] = catalogName
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        block(change.toColumnDsl())
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
        val change = changeSetSupport.createChange("customChange")
        change as CustomChangeWrapper
        change["classLoader"] = block?.javaClass?.classLoader ?: this::class.java.classLoader
        change["class"] = classOrClazz.expandExpressions(changeLog)

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
        val change = changeSetSupport.createChange("executeCommand")
        change as ExecuteShellCommandChange
        change["executable"] = executable
        change["os"] = os
        change["timeout"] = timeout
        val dsl = ArgumentDsl()
        block(dsl)
        dsl.args.forEach {
            change.args.add(it.expandExpressions(changeLog))
        }
        changeSetSupport.addChange(change)
    }

    fun markUnused(
        catalogName: String? = null,
        columnName: String,
        schemaName: String? = null,
        tableName: String,
    ) {
        val change = changeSetSupport.createChange("markUnused")
        change["catalogName"] = catalogName
        change["columnName"] = columnName
        change["schemaName"] = schemaName
        change["tableName"] = tableName
        changeSetSupport.addChange(change)
    }

    fun output(
        message: String? = null,
        target: String? = null,
    ) {
        val change = changeSetSupport.createChange("output")
        change["message"] = message
        change["target"] = target ?: "STDERR"
        changeSetSupport.addChange(change)
    }

    fun sql(
        dbms: String? = null,
        endDelimiter: String? = null,
        splitStatements: String? = null,
        stripComments: String? = null,
        block: CommentDsl.() -> String,
    ) {
        val change = changeSetSupport.createChange("sql")
        change["dbms"] = dbms
        change["endDelimiter"] = endDelimiter
        change["splitStatements"] = splitStatements
        change["stripComments"] = stripComments
        val dsl = CommentDsl()
        val sql = block(dsl)
        change["sql"] = sql.expandExpressions(changeLog)
        change["comment"] = dsl.comment.expandExpressions(changeLog)
        changeSetSupport.addChange(change)
    }

    fun sqlFile(
        dbms: String? = null,
        encoding: String? = null,
        endDelimiter: String? = null,
        path: String,
        relativeToChangelogFile: String? = null,
        splitStatements: String? = null,
        stripComments: String? = null,
    ) {
        val change = changeSetSupport.createChange("sqlFile")
        change["dbms"] = dbms
        change["encoding"] = encoding
        change["endDelimiter"] = endDelimiter
        change["path"] = path
        change["relativeToChangelogFile"] = relativeToChangelogFile
        change["splitStatements"] = splitStatements
        change["stripComments"] = stripComments
        change.finishInitialization() // check for path in liquibase.change.core.SQLFileChange
        changeSetSupport.addChange(change)
    }

    fun stop(message: String? = null) {
        val change = changeSetSupport.createChange("stop")
        change["message"] = message.expandExpressions(changeLog)
        changeSetSupport.addChange(change)
    }

    fun tagDatabase(tag: String) {
        val change = changeSetSupport.createChange("tagDatabase")
        change["tag"] = tag.expandExpressions(changeLog)
        changeSetSupport.addChange(change)
    }

    // Other tags
    fun include(
        file: String? = null,
        contextFilter: String? = null,
        errorIfMissing: String? = null,
        ignore: String? = null,
        labels: String? = null,
        relativeToChangelogFile: String? = null,
    ) {
        val change = changeSetSupport.createChange("include")
        change["file"] = file
        change["contextFilter"] = contextFilter
        change["errorIfMissing"] = errorIfMissing
        change["ignore"] = ignore
        change["labels"] = labels
        change["relativeToChangelogFile"] = relativeToChangelogFile
        changeSetSupport.addChange(change)
    }

    fun includeAll(
        path: String? = null,
        contextFilter: String? = null,
        endsWithFilter: String? = null,
        errorIfMissingOrEmpty: String? = null,
        filter: String? = null,
        ignore: String? = null,
        labels: String? = null,
        maxDepth: String? = null,
        minDepth: String? = null,
        relativeToChangelogFile: String? = null,
        resourceComparator: String? = null,
    ) {
        val change = changeSetSupport.createChange("includeAll")
        change["path"] = path
        change["contextFilter"] = contextFilter
        change["endsWithFilter"] = endsWithFilter
        change["errorIfMissingOrEmpty"] = errorIfMissingOrEmpty
        change["filter"] = filter
        change["ignore"] = ignore
        change["labels"] = labels
        change["maxDepth"] = maxDepth
        change["minDepth"] = minDepth
        change["relativeToChangelogFile"] = relativeToChangelogFile
        change["resourceComparator"] = resourceComparator
        changeSetSupport.addChange(change)
    }

    fun removeChangeSetProperty(
        change: String,
        dbms: String,
        remove: String,
    ) {
        @Suppress("ktlint:standard:property-naming")
        val change_ = changeSetSupport.createChange("removeChangeSetProperty")
        change_["change"] = change
        change_["dbms"] = dbms
        change_["remove"] = remove
        changeSetSupport.addChange(change_)
    }

    private inline fun <reified COLUMN_CONFIG : ColumnConfig> Change.toColumnDsl() =
        IColumnDsl(
            changeLog = changeLog,
            columnConfigClass = COLUMN_CONFIG::class,
            changeSetId = changeSet.id,
            changeName = this.serializedObjectName,
            change = this,
        )
}

class ChangeSetContext(
    val changeSet: ChangeSet,
    val inRollback: Boolean = false,
) {
    fun <E> withChangeSetContext(block: ChangeSetContext.() -> E): E = this.block()
}
