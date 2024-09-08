package momosetkn.liquibase.kotlin.parser

import io.github.classgraph.ClassGraph
import liquibase.ContextExpression
import liquibase.Labels
import liquibase.Scope
import liquibase.changelog.DatabaseChangeLog
import liquibase.changelog.DatabaseChangeLog.OnUnknownFileFormat
import liquibase.changelog.ModifyChangeSets
import liquibase.exception.LiquibaseException
import liquibase.exception.SetupException
import liquibase.parser.ChangeLogParserFactory
import liquibase.precondition.core.PreconditionContainer
import liquibase.resource.ResourceAccessor
import momosetkn.liquibase.kotlin.dsl.overridable.ChangeLogDslOverride
import java.util.Collections

class KotlinTypesafeChangeLogDslOverride(
    private val sourceChangeLog: DatabaseChangeLog,
    private val resourceAccessor: ResourceAccessor,
) : ChangeLogDslOverride {
    private val onUnknownFileFormat = OnUnknownFileFormat.WARN

    @Throws(SetupException::class)
    override fun includeAll(
        path: String,
        contextFilter: String?,
        context: String?, // same to contextFilter
        endsWithFilter: String?,
        errorIfMissingOrEmpty: Boolean, // optional
        filter: String?,
        ignore: Boolean, // optional
        labels: String?,
        maxDepth: Int?,
        minDepth: Int?,
        relativeToChangelogFile: Boolean, // optional
        resourceComparator: String?,
    ) {
        val contextFilterOrContext = contextFilter ?: context
        val includeContexts = ContextExpression(contextFilterOrContext)
        val typedLabels = labels?.let { Labels(it) }
        val modifyChangeSets = null
        val resources = getResources(path)

        if (resources.isEmpty() && errorIfMissingOrEmpty) {
            throw SetupException("Specify path is empty class `$path`")
        }
        try {
            val seenChangelogPaths: Set<String> =
                Scope.getCurrentScope().get(DatabaseChangeLog.SEEN_CHANGELOGS_PATHS_SCOPE_KEY, HashSet())
            Scope.child(
                Collections.singletonMap<String, Any>(
                    DatabaseChangeLog.SEEN_CHANGELOGS_PATHS_SCOPE_KEY,
                    seenChangelogPaths
                ),
                {
                    for (resource in resources) {
                        Scope.getCurrentScope().getLog(javaClass).info("Reading resource: $resource")
                        innerInclude(
                            fileName = resource,
                            isRelativePath = false,
                            errorIfMissing = errorIfMissingOrEmpty,
                            includeContextFilter = includeContexts,
                            labels = typedLabels,
                            ignore = ignore,
                            onUnknownFileFormat = onUnknownFileFormat,
                            modifyChangeSets = modifyChangeSets
                        )
                    }
                }
            )
        } catch (e: Exception) {
            throw SetupException(e)
        }
    }

    override fun include(
        file: String,
        contextFilter: String?,
        context: String?, // same to contextFilter
        errorIfMissing: Boolean,
        ignore: Boolean,
        labels: String?,
        relativeToChangelogFile: Boolean,
    ) {
        innerInclude(
            fileName = file,
            isRelativePath = relativeToChangelogFile,
            errorIfMissing = errorIfMissing,
            includeContextFilter = contextFilter?.let { ContextExpression(it) },
            labels = labels?.let { Labels(it) },
            ignore = ignore,
            onUnknownFileFormat = onUnknownFileFormat,
            modifyChangeSets = null,
        )
    }

//
    @Throws(LiquibaseException::class)
    private fun innerInclude(
        fileName: String,
        isRelativePath: Boolean,
        errorIfMissing: Boolean,
        includeContextFilter: ContextExpression?,
        labels: Labels?,
        ignore: Boolean?,
        onUnknownFileFormat: OnUnknownFileFormat,
        modifyChangeSets: ModifyChangeSets?
    ): Boolean {
        // TODO: relative
//        if (isRelativePath) {
//            //
//        }
        val normalizedFilePath = fileName

        try {
            val rootChangeLog = rootChangeLogThreadLocal.get()
            if (rootChangeLog == null) {
                rootChangeLogThreadLocal.set(sourceChangeLog)
            }
            val parentChangeLog = parentChangeLogThreadLocal.get()
            parentChangeLogThreadLocal.set(sourceChangeLog)
            val currentChangeLog = try {
                val parser = ChangeLogParserFactory.getInstance().getParser(normalizedFilePath, resourceAccessor)

                // not support modifyChangeLog
                val currentChangeLog =
                    parser.parse(normalizedFilePath, sourceChangeLog.changeLogParameters, resourceAccessor)
                currentChangeLog.includeContextFilter = includeContextFilter
                currentChangeLog.includeLabels = labels
                currentChangeLog.isIncludeIgnore = ignore ?: false
                currentChangeLog
            } finally {
                if (rootChangeLog == null) {
                    rootChangeLogThreadLocal.remove()
                }
                if (parentChangeLog == null) {
                    parentChangeLogThreadLocal.remove()
                } else {
                    parentChangeLogThreadLocal.set(parentChangeLog)
                }
            }
            val preconditions = currentChangeLog.preconditions
            if (preconditions != null) {
                if (null == sourceChangeLog.preconditions) {
                    sourceChangeLog.preconditions = PreconditionContainer()
                }
                sourceChangeLog.preconditions.addNestedPrecondition(preconditions)
            }
            for (changeSet in currentChangeLog.changeSets) {
                // not support modifyChangeLog
//            if (modifyChangeSets != null) {
                // private method
//                sourceChangeLog.modifyChangeSets(modifyChangeSets, changeSet)
//            }
                sourceChangeLog.addChangeSet(changeSet)
            }
            sourceChangeLog.skippedChangeSets.addAll(currentChangeLog.skippedChangeSets)
        } catch (e: java.lang.Exception) {
            throw LiquibaseException(e.message, e)
        }

        return true
    }

    /**
     *
     * TODO: same way to [liquibase.changelog.DatabaseChangeLog.findResources]
     */
    private fun getResources(path: String): List<String> {
        val scanResult = ClassGraph()
            .acceptPackages(path)
            .scan()
        val files = scanResult.allClasses
            .map { it.name + ".kt" }
            .sorted() // same to includeAll order
        return files
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        val rootChangeLogThreadLocal = run {
            val rootChangeLogThreadLocalProperty = DatabaseChangeLog::class.java
                .getDeclaredField("ROOT_CHANGE_LOG")
                .apply { isAccessible = true }
            rootChangeLogThreadLocalProperty.get(DatabaseChangeLog()) as ThreadLocal<DatabaseChangeLog>
        }

        @Suppress("UNCHECKED_CAST")
        val parentChangeLogThreadLocal = run {
            val parentChangeLogThreadLocalProperty = DatabaseChangeLog::class.java
                .getDeclaredField("PARENT_CHANGE_LOG")
                .apply { isAccessible = true }
            parentChangeLogThreadLocalProperty.get(DatabaseChangeLog()) as ThreadLocal<DatabaseChangeLog>
        }
    }
}
