package momosetkn.liquibase.kotlin.parser

import io.github.classgraph.ClassGraph
import liquibase.ContextExpression
import liquibase.Labels
import liquibase.Scope
import liquibase.changelog.DatabaseChangeLog
import liquibase.exception.LiquibaseException
import liquibase.exception.SetupException
import liquibase.parser.ChangeLogParserFactory
import liquibase.precondition.core.PreconditionContainer
import liquibase.resource.ResourceAccessor
import momosetkn.liquibase.kotlin.dsl.overridable.ChangeLogDslOverride
import java.util.Collections

@Suppress("TooGenericExceptionCaught")
class KotlinCompiledChangeLogDslOverride(
    private val sourceChangeLog: DatabaseChangeLog,
    private val resourceAccessor: ResourceAccessor,
) : ChangeLogDslOverride {
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
        maxDepth?.also {
            TODO("not implemented in kotlin-compiled")
        }
        minDepth?.also {
            TODO("not implemented in kotlin-compiled")
        }
        val resources = getResources(
            pathName = path,
            isRelativeToChangelogFile = relativeToChangelogFile,
            endsWithFilter = endsWithFilter,
        )
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
        )
    }

    @Throws(LiquibaseException::class)
    private fun innerInclude(
        fileName: String,
        isRelativePath: Boolean,
        errorIfMissing: Boolean,
        includeContextFilter: ContextExpression?,
        labels: Labels?,
        ignore: Boolean?,
    ): Boolean {
        val normalizedFilePath = getPathWithRelative(fileName, isRelativePath)
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
            } catch (e: Exception) {
                Scope.getCurrentScope().getLog(javaClass).warning("Include file '$normalizedFilePath' not found")
                if (errorIfMissing) {
                    throw LiquibaseException(e)
                }
                return false
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

    @Throws(SetupException::class)
    private fun getResources(
        pathName: String,
        isRelativeToChangelogFile: Boolean,
        endsWithFilter: String?,
        // TODO
//        minDepth: Int,
        // TODO
//        maxDepth: Int,
    ): List<String> {
        val normalizedPathName = getPathWithRelative(pathName, isRelativeToChangelogFile)
        val scanResult = ClassGraph()
            .acceptPackages(normalizedPathName)
            .scan()
        val files = scanResult.allClasses
            .map { it.name }
            .filter { name ->
                endsWithFilter?.let { name.endsWith(it) }
                    ?: true
            }
            .sorted() // same to includeAll order
        return files
    }

    private fun getPathWithRelative(
        fileName: String,
        isRelativePath: Boolean,
    ) = if (isRelativePath) {
        val packageName = this.sourceChangeLog.filePath.substringBeforeLast(".")
        "$packageName.$fileName"
    } else {
        fileName
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
