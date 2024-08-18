// Entities
            fun createTable(
                catalogName : String? = null,
ifNotExists : String? = null,
remarks : String? = null,
schemaName : String? = null,
tableName : String,
tablespace : String? = null,
            ) {
                val change = lookupChange("createTable")
                change["catalogName"] = catalogName
change["ifNotExists"] = ifNotExists
change["remarks"] = remarks
change["schemaName"] = schemaName
change["tableName"] = tableName
change["tablespace"] = tablespace
                addChange(change)
            }
            fun dropTable(
                cascadeConstraints : String? = null,
catalogName : String? = null,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("dropTable")
                change["cascadeConstraints"] = cascadeConstraints
change["catalogName"] = catalogName
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun setTableRemarks(
                catalogName : String? = null,
remarks : String,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("setTableRemarks")
                change["catalogName"] = catalogName
change["remarks"] = remarks
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun renameTable(
                catalogName : String? = null,
newTableName : String,
oldTableName : String,
schemaName : String? = null,
            ) {
                val change = lookupChange("renameTable")
                change["catalogName"] = catalogName
change["newTableName"] = newTableName
change["oldTableName"] = oldTableName
change["schemaName"] = schemaName
                addChange(change)
            }
            fun addColumn(
                catalogName : String? = null,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("addColumn")
                change["catalogName"] = catalogName
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun dropColumn(
                catalogName : String? = null,
columnName : String? = null,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("dropColumn")
                change["catalogName"] = catalogName
change["columnName"] = columnName
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun renameColumn(
                catalogName : String? = null,
columnDataType : String? = null,
newColumnName : String,
oldColumnName : String,
remarks : String? = null,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("renameColumn")
                change["catalogName"] = catalogName
change["columnDataType"] = columnDataType
change["newColumnName"] = newColumnName
change["oldColumnName"] = oldColumnName
change["remarks"] = remarks
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun modifyDataType(
                catalogName : String? = null,
columnName : String,
newDataType : String,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("modifyDataType")
                change["catalogName"] = catalogName
change["columnName"] = columnName
change["newDataType"] = newDataType
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun setColumnRemarks(
                catalogName : String? = null,
columnName : String,
remarks : String,
schemaName : String? = null,
tableName : String,
columnDataType : String? = null,
columnParentType : String? = null,
            ) {
                val change = lookupChange("setColumnRemarks")
                change["catalogName"] = catalogName
change["columnName"] = columnName
change["remarks"] = remarks
change["schemaName"] = schemaName
change["tableName"] = tableName
change["columnDataType"] = columnDataType
change["columnParentType"] = columnParentType
                addChange(change)
            }
            fun addAutoIncrement(
                catalogName : String? = null,
columnDataType : String? = null,
columnName : String,
defaultOnNull : String? = null,
generationType : String? = null,
incrementBy : String? = null,
schemaName : String? = null,
startWith : String? = null,
tableName : String,
            ) {
                val change = lookupChange("addAutoIncrement")
                change["catalogName"] = catalogName
change["columnDataType"] = columnDataType
change["columnName"] = columnName
change["defaultOnNull"] = defaultOnNull
change["generationType"] = generationType
change["incrementBy"] = incrementBy
change["schemaName"] = schemaName
change["startWith"] = startWith
change["tableName"] = tableName
                addChange(change)
            }
            fun createIndex(
                associatedWith : String? = null,
catalogName : String? = null,
clustered : String? = null,
indexName : String? = null,
schemaName : String? = null,
tableName : String,
tablespace : String? = null,
unique : String? = null,
            ) {
                val change = lookupChange("createIndex")
                change["associatedWith"] = associatedWith
change["catalogName"] = catalogName
change["clustered"] = clustered
change["indexName"] = indexName
change["schemaName"] = schemaName
change["tableName"] = tableName
change["tablespace"] = tablespace
change["unique"] = unique
                addChange(change)
            }
            fun dropIndex(
                catalogName : String? = null,
indexName : String,
schemaName : String? = null,
tableName : String? = null,
            ) {
                val change = lookupChange("dropIndex")
                change["catalogName"] = catalogName
change["indexName"] = indexName
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun createView(
                catalogName : String? = null,
encoding : String? = null,
fullDefinition : String? = null,
path : String? = null,
relativeToChangelogFile : String? = null,
remarks : String? = null,
replaceIfExists : String? = null,
schemaName : String? = null,
selectQuery : String? = null,
viewName : String,
            ) {
                val change = lookupChange("createView")
                change["catalogName"] = catalogName
change["encoding"] = encoding
change["fullDefinition"] = fullDefinition
change["path"] = path
change["relativeToChangelogFile"] = relativeToChangelogFile
change["remarks"] = remarks
change["replaceIfExists"] = replaceIfExists
change["schemaName"] = schemaName
change["selectQuery"] = selectQuery
change["viewName"] = viewName
                addChange(change)
            }
            fun dropView(
                catalogName : String? = null,
ifExists : String? = null,
schemaName : String? = null,
viewName : String,
            ) {
                val change = lookupChange("dropView")
                change["catalogName"] = catalogName
change["ifExists"] = ifExists
change["schemaName"] = schemaName
change["viewName"] = viewName
                addChange(change)
            }
            fun renameView(
                catalogName : String? = null,
newViewName : String,
oldViewName : String,
schemaName : String? = null,
            ) {
                val change = lookupChange("renameView")
                change["catalogName"] = catalogName
change["newViewName"] = newViewName
change["oldViewName"] = oldViewName
change["schemaName"] = schemaName
                addChange(change)
            }
            fun createProcedure(
                catalogName : String? = null,
dbms : String? = null,
encoding : String? = null,
path : String,
procedureText : String,
procedureName : String? = null,
relativeToChangelogFile : String? = null,
replaceIfExists : String? = null,
schemaName : String? = null,
            ) {
                val change = lookupChange("createProcedure")
                change["catalogName"] = catalogName
change["dbms"] = dbms
change["encoding"] = encoding
change["path"] = path
change["procedureText"] = procedureText
change["procedureName"] = procedureName
change["relativeToChangelogFile"] = relativeToChangelogFile
change["replaceIfExists"] = replaceIfExists
change["schemaName"] = schemaName
                addChange(change)
            }
            fun dropProcedure(
                catalogName : String? = null,
procedureName : String,
schemaName : String? = null,
            ) {
                val change = lookupChange("dropProcedure")
                change["catalogName"] = catalogName
change["procedureName"] = procedureName
change["schemaName"] = schemaName
                addChange(change)
            }
            fun createSequence(
                cacheSize : String? = null,
catalogName : String? = null,
cycle : String? = null,
dataType : String? = null,
incrementBy : String? = null,
maxValue : String? = null,
minValue : String? = null,
ordered : String? = null,
schemaName : String? = null,
sequenceName : String,
startValue : String? = null,
            ) {
                val change = lookupChange("createSequence")
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
                addChange(change)
            }
            fun dropSequence(
                catalogName : String? = null,
schemaName : String? = null,
sequenceName : String,
            ) {
                val change = lookupChange("dropSequence")
                change["catalogName"] = catalogName
change["schemaName"] = schemaName
change["sequenceName"] = sequenceName
                addChange(change)
            }
            fun renameSequence(
                catalogName : String? = null,
newSequenceName : String,
oldSequenceName : String,
schemaName : String? = null,
            ) {
                val change = lookupChange("renameSequence")
                change["catalogName"] = catalogName
change["newSequenceName"] = newSequenceName
change["oldSequenceName"] = oldSequenceName
change["schemaName"] = schemaName
                addChange(change)
            }
            fun alterSequence(
                cacheSize : String? = null,
catalogName : String? = null,
cycle : String? = null,
dataType : String? = null,
incrementBy : String? = null,
maxValue : String? = null,
minValue : String? = null,
ordered : String? = null,
schemaName : String? = null,
sequenceName : String,
            ) {
                val change = lookupChange("alterSequence")
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
                addChange(change)
            }
            fun createFunction(
                catalogName : String? = null,
dbms : String? = null,
encoding : String? = null,
functionBody : String,
functionName : String,
path : String,
procedureText : String,
relativeTo Changelog File : String? = null,
replaceIfExists : String? = null,
schemaName : String? = null,
            ) {
                val change = lookupChange("createFunction")
                change["catalogName"] = catalogName
change["dbms"] = dbms
change["encoding"] = encoding
change["functionBody"] = functionBody
change["functionName"] = functionName
change["path"] = path
change["procedureText"] = procedureText
change["relativeTo Changelog File"] = relativeTo Changelog File
change["replaceIfExists"] = replaceIfExists
change["schemaName"] = schemaName
                addChange(change)
            }
            fun dropFunction(
                catalogName : String? = null,
functionName : String,
schemaName : String? = null,
            ) {
                val change = lookupChange("dropFunction")
                change["catalogName"] = catalogName
change["functionName"] = functionName
change["schemaName"] = schemaName
                addChange(change)
            }
            fun createPackage(
                catalogName : String? = null,
dbms : String? = null,
encoding : String? = null,
packageName : String,
packageText : String,
path : String,
procedureText : String,
relativeToChange logFile : String? = null,
replaceIfExists : String? = null,
schemaName : String? = null,
            ) {
                val change = lookupChange("createPackage")
                change["catalogName"] = catalogName
change["dbms"] = dbms
change["encoding"] = encoding
change["packageName"] = packageName
change["packageText"] = packageText
change["path"] = path
change["procedureText"] = procedureText
change["relativeToChange logFile"] = relativeToChange logFile
change["replaceIfExists"] = replaceIfExists
change["schemaName"] = schemaName
                addChange(change)
            }
            fun createPackageBody(
                catalogName : String? = null,
dbms : String? = null,
encoding : String? = null,
packageBodyName : String,
packageBodyText : String,
path : String,
procedureText : String,
relativeToChange logFile : String? = null,
replaceIfExists : String? = null,
schemaName : String? = null,
            ) {
                val change = lookupChange("createPackageBody")
                change["catalogName"] = catalogName
change["dbms"] = dbms
change["encoding"] = encoding
change["packageBodyName"] = packageBodyName
change["packageBodyText"] = packageBodyText
change["path"] = path
change["procedureText"] = procedureText
change["relativeToChange logFile"] = relativeToChange logFile
change["replaceIfExists"] = replaceIfExists
change["schemaName"] = schemaName
                addChange(change)
            }
            fun dropPackage(
                catalogName : String? = null,
packageName : String,
schemaName : String? = null,
            ) {
                val change = lookupChange("dropPackage")
                change["catalogName"] = catalogName
change["packageName"] = packageName
change["schemaName"] = schemaName
                addChange(change)
            }
            fun dropPackageBody(
                catalogName : String? = null,
packageBodyName : String,
schemaName : String? = null,
            ) {
                val change = lookupChange("dropPackageBody")
                change["catalogName"] = catalogName
change["packageBodyName"] = packageBodyName
change["schemaName"] = schemaName
                addChange(change)
            }
            fun createSynonym(
                objectCatalogName : String? = null,
objectName : String,
objectSchemaName : String? = null,
objectType : String? = null,
private : String? = null,
replaceIfExists : String? = null,
synonymCatalogName : String? = null,
synonymName : String,
synonymSchemaName : String? = null,
            ) {
                val change = lookupChange("createSynonym")
                change["objectCatalogName"] = objectCatalogName
change["objectName"] = objectName
change["objectSchemaName"] = objectSchemaName
change["objectType"] = objectType
change["private"] = private
change["replaceIfExists"] = replaceIfExists
change["synonymCatalogName"] = synonymCatalogName
change["synonymName"] = synonymName
change["synonymSchemaName"] = synonymSchemaName
                addChange(change)
            }
            fun dropSynonym(
                objectType : String? = null,
private : String? = null,
synonymCatalogName : String? = null,
synonymName : String,
synonymSchemaName : String? = null,
            ) {
                val change = lookupChange("dropSynonym")
                change["objectType"] = objectType
change["private"] = private
change["synonymCatalogName"] = synonymCatalogName
change["synonymName"] = synonymName
change["synonymSchemaName"] = synonymSchemaName
                addChange(change)
            }
            fun createTrigger(
                catalogName : String? = null,
dbms : String? = null,
disabled : String? = null,
encoding : String? = null,
path : String,
procedureText : String,
relativeToChangelogFile : String? = null,
replaceIfExists : String? = null,
schemaName : String? = null,
scope : String? = null,
tableName : String? = null,
triggerBody : String,
triggerName : String,
            ) {
                val change = lookupChange("createTrigger")
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
                addChange(change)
            }
            fun enableTrigger(
                catalogName : String? = null,
schemaName : String? = null,
scope : String? = null,
tableName : String? = null,
triggerName : String,
            ) {
                val change = lookupChange("enableTrigger")
                change["catalogName"] = catalogName
change["schemaName"] = schemaName
change["scope"] = scope
change["tableName"] = tableName
change["triggerName"] = triggerName
                addChange(change)
            }
            fun dropTrigger(
                catalogName : String? = null,
schemaName : String? = null,
scope : String? = null,
tableName : String? = null,
triggerName : String,
            ) {
                val change = lookupChange("dropTrigger")
                change["catalogName"] = catalogName
change["schemaName"] = schemaName
change["scope"] = scope
change["tableName"] = tableName
change["triggerName"] = triggerName
                addChange(change)
            }
            fun disableTrigger(
                catalogName : String? = null,
schemaName : String? = null,
scope : String? = null,
tableName : String? = null,
triggerName : String,
            ) {
                val change = lookupChange("disableTrigger")
                change["catalogName"] = catalogName
change["schemaName"] = schemaName
change["scope"] = scope
change["tableName"] = tableName
change["triggerName"] = triggerName
                addChange(change)
            }
            fun renameTrigger(
                catalogName : String? = null,
newTriggerName : String,
oldTriggerName : String,
schemaName : String? = null,
tableName : String? = null,
            ) {
                val change = lookupChange("renameTrigger")
                change["catalogName"] = catalogName
change["newTriggerName"] = newTriggerName
change["oldTriggerName"] = oldTriggerName
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
// Constraints
            fun addCheckConstraint(
                catalogName : String? = null,
constraintBody : String,
constraintName : String,
disabled : String? = null,
schemaName : String? = null,
tableName : String,
validate : String? = null,
            ) {
                val change = lookupChange("addCheckConstraint")
                change["catalogName"] = catalogName
change["constraintBody"] = constraintBody
change["constraintName"] = constraintName
change["disabled"] = disabled
change["schemaName"] = schemaName
change["tableName"] = tableName
change["validate"] = validate
                addChange(change)
            }
            fun enableCheckConstraint(
                catalogName : String? = null,
constraintName : String,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("enableCheckConstraint")
                change["catalogName"] = catalogName
change["constraintName"] = constraintName
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun dropCheckConstraint(
                catalogName : String? = null,
constraintName : String,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("dropCheckConstraint")
                change["catalogName"] = catalogName
change["constraintName"] = constraintName
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun disableCheckConstraint(
                catalogName : String? = null,
constraintName : String,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("disableCheckConstraint")
                change["catalogName"] = catalogName
change["constraintName"] = constraintName
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun addDefaultValue(
                catalogName : String? = null,
columnDataType : String? = null,
columnName : String,
defaultValue : String? = null,
defaultValueBoolean : String? = null,
defaultValueComputed : String? = null,
defaultValueConstraintName : String? = null,
defaultValueDate : String? = null,
defaultValueNumeric : String? = null,
defaultValueSequenceNext : String? = null,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("addDefaultValue")
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
                addChange(change)
            }
            fun dropDefaultValue(
                catalogName : String? = null,
columnDataType : String? = null,
columnName : String,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("dropDefaultValue")
                change["catalogName"] = catalogName
change["columnDataType"] = columnDataType
change["columnName"] = columnName
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun addForeignKeyConstraint(
                baseColumnNames : String,
baseTableCatalogName : String? = null,
baseTableName : String,
baseTableSchemaName : String? = null,
constraintName : String,
deferrable : String? = null,
initiallyDeferred : String? = null,
onDelete : String? = null,
onUpdate : String? = null,
referencedColumnNames : String,
referencedTableCatalogName : String? = null,
referencedTableName : String,
referencedTableSchemaName : String? = null,
validate : String? = null,
referencesUniqueColumn : String? = null,
            ) {
                val change = lookupChange("addForeignKeyConstraint")
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
change["referencesUniqueColumn"] = referencesUniqueColumn
                addChange(change)
            }
            fun dropForeignKeyConstraint(
                baseTableCatalogName : String? = null,
baseTableName : String,
baseTableSchemaName : String? = null,
constraintName : String,
            ) {
                val change = lookupChange("dropForeignKeyConstraint")
                change["baseTableCatalogName"] = baseTableCatalogName
change["baseTableName"] = baseTableName
change["baseTableSchemaName"] = baseTableSchemaName
change["constraintName"] = constraintName
                addChange(change)
            }
            fun dropAllForeignKeyConstraints(
                baseTableCatalogName : String? = null,
baseTableName : String,
baseTableSchemaName : String? = null,
            ) {
                val change = lookupChange("dropAllForeignKeyConstraints")
                change["baseTableCatalogName"] = baseTableCatalogName
change["baseTableName"] = baseTableName
change["baseTableSchemaName"] = baseTableSchemaName
                addChange(change)
            }
            fun addNotNullConstraint(
                catalogName : String? = null,
columnDataType : String? = null,
columnName : String,
constraintName : String? = null,
defaultNullValue : String? = null,
schemaName : String? = null,
tableName : String,
validate : String? = null,
            ) {
                val change = lookupChange("addNotNullConstraint")
                change["catalogName"] = catalogName
change["columnDataType"] = columnDataType
change["columnName"] = columnName
change["constraintName"] = constraintName
change["defaultNullValue"] = defaultNullValue
change["schemaName"] = schemaName
change["tableName"] = tableName
change["validate"] = validate
                addChange(change)
            }
            fun dropNotNullConstraint(
                catalogName : String? = null,
columnDataType : String? = null,
columnName : String,
constraintName : String? = null,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("dropNotNullConstraint")
                change["catalogName"] = catalogName
change["columnDataType"] = columnDataType
change["columnName"] = columnName
change["constraintName"] = constraintName
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun addPrimaryKey(
                catalogName : String? = null,
clustered : String? = null,
columnNames : String,
constraintName : String? = null,
forIndexCatalogName : String? = null,
forIndexName : String? = null,
forIndexSchemaName : String? = null,
schemaName : String? = null,
tableName : String,
tablespace : String? = null,
validate : String? = null,
            ) {
                val change = lookupChange("addPrimaryKey")
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
                addChange(change)
            }
            fun dropPrimaryKey(
                catalogName : String? = null,
constraintName : String? = null,
dropIndex : String? = null,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("dropPrimaryKey")
                change["catalogName"] = catalogName
change["constraintName"] = constraintName
change["dropIndex"] = dropIndex
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun addUniqueConstraint(
                catalogName : String? = null,
clustered : String? = null,
columnNames : String,
constraintName : String? = null,
deferrable : String? = null,
disabled : String? = null,
forIndexCatalogName : String? = null,
forIndexName : String? = null,
forIndexSchemaName : String? = null,
initiallyDeferred : String? = null,
schemaName : String? = null,
tableName : String,
tablespace : String? = null,
validate : String? = null,
            ) {
                val change = lookupChange("addUniqueConstraint")
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
                addChange(change)
            }
            fun dropUniqueConstraint(
                catalogName : String? = null,
constraintName : String,
schemaName : String? = null,
tableName : String,
uniqueColumns : String? = null,
            ) {
                val change = lookupChange("dropUniqueConstraint")
                change["catalogName"] = catalogName
change["constraintName"] = constraintName
change["schemaName"] = schemaName
change["tableName"] = tableName
change["uniqueColumns"] = uniqueColumns
                addChange(change)
            }
// Data
            fun addLookupTable(
                constraintName : String? = null,
existingColumnName : String,
existingTableCatalogName : String? = null,
existingTableName : String,
existingTableSchemaName : String? = null,
newColumnDataType : String? = null,
newColumnName : String,
newTableCatalogName : String? = null,
newTableName : String,
newTableSchemaName : String? = null,
            ) {
                val change = lookupChange("addLookupTable")
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
                addChange(change)
            }
            fun delete(
                catalogName : String? = null,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("delete")
                change["catalogName"] = catalogName
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun insert(
                catalogName : String? = null,
dbms : String? = null,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("insert")
                change["catalogName"] = catalogName
change["dbms"] = dbms
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun loadData(
                catalogName : String? = null,
commentLineStartsWith : String? = null,
encoding : String? = null,
file : String,
quotchar : String? = null,
relativeToChangelogFile : String? = null,
schemaName : String? = null,
separator : String? = null,
tableName : String,
usePreparedStatements : String? = null,
            ) {
                val change = lookupChange("loadData")
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
                addChange(change)
            }
            fun loadUpdateData(
                catalogName : String? = null,
commentLineStartsWith : String? = null,
encoding : String? = null,
file : String,
onlyUpdate : String? = null,
primaryKey : String,
quotchar : String? = null,
relativeToChangelogFile : String? = null,
schemaName : String? = null,
separator : String? = null,
tableName : String,
usePreparedStatements : String? = null,
            ) {
                val change = lookupChange("loadUpdateData")
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
                addChange(change)
            }
            fun mergeColumns(
                catalogName : String? = null,
column1Name : String,
column2Name : String,
finalColumnName : String,
finalColumnType : String,
joinString : String? = null,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("mergeColumns")
                change["catalogName"] = catalogName
change["column1Name"] = column1Name
change["column2Name"] = column2Name
change["finalColumnName"] = finalColumnName
change["finalColumnType"] = finalColumnType
change["joinString"] = joinString
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun update(
                catalogName : String? = null,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("update")
                change["catalogName"] = catalogName
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
// Miscellaneous
            fun customChange(
                DB2/LUW : String? = null,
DB2/z : String? = null,
Derby : String? = null,
Firebird : String? = null,
H2 : String? = null,
HyperSQL : String? = null,
INGRES : String? = null,
Informix : String? = null,
MariaDB : String? = null,
MySQL : String? = null,
Oracle : String? = null,
PostgreSQL : String? = null,
Snowflake : String? = null,
SQL Server : String? = null,
SQLite : String? = null,
Sybase : String? = null,
Sybase Anywhere : String? = null,
            ) {
                val change = lookupChange("customChange")
                change["DB2/LUW"] = DB2/LUW
change["DB2/z"] = DB2/z
change["Derby"] = Derby
change["Firebird"] = Firebird
change["H2"] = H2
change["HyperSQL"] = HyperSQL
change["INGRES"] = INGRES
change["Informix"] = Informix
change["MariaDB"] = MariaDB
change["MySQL"] = MySQL
change["Oracle"] = Oracle
change["PostgreSQL"] = PostgreSQL
change["Snowflake"] = Snowflake
change["SQL Server"] = SQL Server
change["SQLite"] = SQLite
change["Sybase"] = Sybase
change["Sybase Anywhere"] = Sybase Anywhere
                addChange(change)
            }
            fun empty(
                DB2/LUW : String? = null,
DB2/z : String? = null,
Derby : String? = null,
Firebird : String? = null,
H2 : String? = null,
HyperSQL : String? = null,
INGRES : String? = null,
Informix : String? = null,
MariaDB : String? = null,
MySQL : String? = null,
Oracle : String? = null,
PostgreSQL : String? = null,
Snowflake : String? = null,
SQL Server : String? = null,
SQLite : String? = null,
Sybase : String? = null,
Sybase Anywhere : String? = null,
            ) {
                val change = lookupChange("empty")
                change["DB2/LUW"] = DB2/LUW
change["DB2/z"] = DB2/z
change["Derby"] = Derby
change["Firebird"] = Firebird
change["H2"] = H2
change["HyperSQL"] = HyperSQL
change["INGRES"] = INGRES
change["Informix"] = Informix
change["MariaDB"] = MariaDB
change["MySQL"] = MySQL
change["Oracle"] = Oracle
change["PostgreSQL"] = PostgreSQL
change["Snowflake"] = Snowflake
change["SQL Server"] = SQL Server
change["SQLite"] = SQLite
change["Sybase"] = Sybase
change["Sybase Anywhere"] = Sybase Anywhere
                addChange(change)
            }
            fun executeCommand(
                executable : String,
os : String? = null,
timeout : String? = null,
            ) {
                val change = lookupChange("executeCommand")
                change["executable"] = executable
change["os"] = os
change["timeout"] = timeout
                addChange(change)
            }
            fun markUnused(
                catalogName : String? = null,
columnName : String,
schemaName : String? = null,
tableName : String,
            ) {
                val change = lookupChange("markUnused")
                change["catalogName"] = catalogName
change["columnName"] = columnName
change["schemaName"] = schemaName
change["tableName"] = tableName
                addChange(change)
            }
            fun output(
                message : String? = null,
target : String? = null,
            ) {
                val change = lookupChange("output")
                change["message"] = message
change["target"] = target
                addChange(change)
            }
            fun sql(
                dbms : String? = null,
endDelimiter : String? = null,
splitStatements : String? = null,
sql : String,
stripComments : String? = null,
            ) {
                val change = lookupChange("sql")
                change["dbms"] = dbms
change["endDelimiter"] = endDelimiter
change["splitStatements"] = splitStatements
change["sql"] = sql
change["stripComments"] = stripComments
                addChange(change)
            }
            fun sqlFile(
                dbms : String? = null,
encoding : String? = null,
endDelimiter : String? = null,
path : String,
relativeToChangelogFile : String? = null,
splitStatements : String? = null,
stripComments : String? = null,
            ) {
                val change = lookupChange("sqlFile")
                change["dbms"] = dbms
change["encoding"] = encoding
change["endDelimiter"] = endDelimiter
change["path"] = path
change["relativeToChangelogFile"] = relativeToChangelogFile
change["splitStatements"] = splitStatements
change["stripComments"] = stripComments
                addChange(change)
            }
fun stop(
    message : String? = null,
) {
    val change = lookupChange("stop")
    change["message"] = message
    addChange(change)
}
fun tagDatabase(
    tag : String,
) {
    val change = lookupChange("tagDatabase")
    change["tag"] = tag
    addChange(change)
}
// Other tags
            fun include(
                file : String? = null,
contextFilter : String? = null,
errorIfMissing : String? = null,
ignore : String? = null,
labels : String? = null,
relativeToChangelogFile : String? = null,
            ) {
                val change = lookupChange("include")
                change["file"] = file
change["contextFilter"] = contextFilter
change["errorIfMissing"] = errorIfMissing
change["ignore"] = ignore
change["labels"] = labels
change["relativeToChangelogFile"] = relativeToChangelogFile
                addChange(change)
            }
            fun includeAll(
                path : String? = null,
contextFilter : String? = null,
endsWithFilter : String? = null,
errorIfMissingOrEmpty : String? = null,
filter : String? = null,
ignore : String? = null,
labels : String? = null,
maxDepth : String? = null,
minDepth : String? = null,
relativeToChangelogFile : String? = null,
resourceComparator : String? = null,
            ) {
                val change = lookupChange("includeAll")
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
                addChange(change)
            }
            fun removeChangeSetProperty(
                change : String,
dbms : String,
remove : String,
            ) {
                val change = lookupChange("removeChangeSetProperty")
                change["change"] = change
change["dbms"] = dbms
change["remove"] = remove
                addChange(change)
            }
