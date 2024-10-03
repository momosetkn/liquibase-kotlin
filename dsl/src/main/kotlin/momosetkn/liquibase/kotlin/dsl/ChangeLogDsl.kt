@file:Suppress("CyclomaticComplexMethod")

package momosetkn.liquibase.kotlin.dsl

import liquibase.ContextExpression
import liquibase.Labels
import liquibase.change.visitor.AddColumnChangeVisitor
import liquibase.change.visitor.ChangeVisitorFactory
import liquibase.changelog.ChangeSet
import liquibase.changelog.DatabaseChangeLog
import liquibase.changelog.DatabaseChangeLog.OnUnknownFileFormat
import liquibase.changelog.IncludeAllFilter
import liquibase.database.DatabaseList
import liquibase.database.ObjectQuotingStrategy
import liquibase.exception.ChangeLogParseException
import liquibase.resource.ResourceAccessor
import momosetkn.liquibase.kotlin.dsl.Expressions.evalExpressions
import momosetkn.liquibase.kotlin.dsl.util.ReflectionUtils.loadKClass
import momosetkn.liquibase.kotlin.dsl.util.ReflectionUtils.new
import momosetkn.liquibase.kotlin.dsl.util.StringsUtil.splitAndTrim
import java.util.Properties
import kotlin.reflect.KClass

@ChangeLogDslMarker
class ChangeLogDsl(
    private val changeLog: DatabaseChangeLog,
    private val resourceAccessor: ResourceAccessor,
) {
    /**
     * Adds a new ChangeSet to the current ChangeLog.
     * [official-document](https://docs.liquibase.com/concepts/changelogs/changeset.html)
     *
     * @param author The author of the ChangeSet. Required.
     * @param id The unique identifier for the ChangeSet. Required.
     * @param contextFilter Optional context filter for the ChangeSet.
     * @param context Optional context for the ChangeSet.
     * @param created Optional timestamp when the ChangeSet was created.
     * @param dbms Optional DBMS-specific flag for executing the ChangeSet.
     * @param failOnError Optional flag indicating whether to fail if an error occurs.
     * @param ignore Optional flag indicating whether to ignore the ChangeSet.
     * @param labels Optional labels for the ChangeSet.
     * @param logicalFilePath Optional logical file path for the ChangeSet.
     * @param objectQuotingStrategy Optional quoting strategy for the ChangeSet.
     * @param onValidationFail Optional strategy on validation failure.
     * @param runAlways Optional flag indicating whether to run the ChangeSet always.
     * @param runInTransaction Optional flag indicating whether to run the ChangeSet in a transaction.
     * @param runOnChange Optional flag indicating whether to run the ChangeSet on change.
     * @param runOrder Optional run order for the ChangeSet.
     * @param runWith Optional RunWith strategy for the ChangeSet.
     * @param runWithSpoolFile Optional RunWithSpoolFile strategy for the ChangeSet.
     * @param comment Optional comment for the ChangeSet.
     * @param block The block containing ChangeSet DSL.
     */
    fun changeSet(
        author: String,
        id: String,
        contextFilter: String? = null, // same to context
        context: String? = null,
        created: String? = null,
        dbms: String? = null,
        failOnError: Boolean? = null,
        ignore: Boolean? = null,
        labels: String? = null,
        logicalFilePath: String? = null,
        objectQuotingStrategy: String? = null,
        onValidationFail: String? = null,
        runAlways: Boolean? = null,
        runInTransaction: Boolean? = null,
        runOnChange: Boolean? = null,
        runOrder: String? = null,
        runWith: String? = null,
        runWithSpoolFile: String? = null,
        comment: String? = null, // not official args
        block: ChangeSetDsl.() -> Unit,
    ) {
        val enumObjectQuotingStrategy =
            objectQuotingStrategy?.let {
                try {
                    ObjectQuotingStrategy.valueOf(it)
                } catch (e: IllegalArgumentException) {
                    throw ChangeLogParseException(
                        "ChangeSet '$id': $it is not a supported ChangeSet ObjectQuotingStrategy",
                        e
                    )
                }
            }
        val filePath = logicalFilePath?.evalExpressions(changeLog) ?: changeLog.filePath
        val contextFilterOrContext = contextFilter ?: context
        val changeSet =
            ChangeSet(
                id.evalExpressions(changeLog),
                author.evalExpressions(changeLog),
                runAlways ?: false,
                runOnChange ?: false,
                filePath.toString(),
                contextFilterOrContext?.evalExpressions(changeLog),
                dbms?.evalExpressions(changeLog),
                runWith?.evalExpressions(changeLog),
                runWithSpoolFile?.evalExpressions(changeLog),
                runInTransaction ?: true,
                enumObjectQuotingStrategy,
                changeLog,
            )
        changeSet.changeLogParameters = changeLog.changeLogParameters
        failOnError?.also { changeSet.failOnError = it }
        onValidationFail?.also { changeSet.onValidationFail = ChangeSet.ValidationFailOption.valueOf(it) }
        labels?.also { changeSet.labels = Labels(it) }
        created?.also { changeSet.created = it }
        runOrder?.also { changeSet.runOrder = it }
        ignore?.also { changeSet.isIgnore = it }
        comment?.also { changeSet.comments = it }
        val changeSetcontext =
            ChangeSetContext(
                changeSet = changeSet,
            )
        val dsl =
            ChangeSetDsl(
                changeLog = changeLog,
                context = changeSetcontext,
            )
        block(dsl)
        changeLog.addChangeSet(changeSet)
    }

    /**
     * Includes and processes the specified file in the change log.
     * [official-document](https://docs.liquibase.com/change-types/include.html)
     *
     * @param file The path to the file that needs to be included. This path is evaluated for expressions. Required.
     * @param contextFilter Optional context filter to be applied for the inclusion.
     * @param context Optional context for the inclusion. This is the same as contextFilter.
     * @param errorIfMissing Flag indicating whether an error should be raised if the file is missing. Defaults to true.
     * @param ignore Flag indicating whether this inclusion should be ignored. Defaults to false.
     * @param labels Optional labels to be applied for the inclusion.
     * @param relativeToChangelogFile Flag indicating whether the file path is relative to the changelog file.
     * Defaults to false.
     */
    fun include(
        file: String,
        contextFilter: String? = null,
        context: String? = null, // same to contextFilter
        errorIfMissing: Boolean = true,
        ignore: Boolean = false,
        labels: String? = null,
        relativeToChangelogFile: Boolean = false,
    ) {
        val fileName = file.evalExpressions(changeLog)
        val contextFilterOrContext = contextFilter ?: context
        val includeContexts = ContextExpression(contextFilterOrContext?.evalExpressions(changeLog))
        val typedLabels = labels?.let { Labels(it.evalExpressions(changeLog)) }

        changeLog.include(
            fileName,
            relativeToChangelogFile,
            errorIfMissing,
            resourceAccessor,
            includeContexts,
            typedLabels,
            ignore,
            onUnknownFileFormat,
        )
    }

    /**
     * Includes all resources from a specified path into the change log, optionally filtering them by various criteria.
     * [official-document](https://docs.liquibase.com/change-types/includeall.html)
     *
     * @param path The path from which to include all resources. This path is evaluated for expressions. Required.
     * @param contextFilter Optional context filter to be applied for the inclusion.
     * @param context Optional context for the inclusion. This is the same as contextFilter.
     * @param endsWithFilter Optional filter to include only resources with a specific ending.
     * @param errorIfMissingOrEmpty Flag indicating whether an error should be raised if no resources are found.
     * Defaults to true.
     * @param filter Optional custom filter function to include or exclude specific resources based on their path.
     * @param ignore Flag indicating whether this inclusion should be ignored. Defaults to false.
     * @param labels Optional labels to be applied for the inclusion.
     * @param maxDepth Optional maximum depth for recursively including resources.
     * @param minDepth Optional minimum depth for recursively including resources.
     * @param relativeToChangelogFile Flag indicating whether the path is relative to the changelog file.
     * Defaults to false.
     * @param resourceComparator Optional comparator for ordering the resources.
     */
    fun includeAll(
        path: String,
        contextFilter: String? = null,
        context: String? = null, // same to contextFilter
        endsWithFilter: String? = null,
        errorIfMissingOrEmpty: Boolean = true, // optional
        filter: ((String) -> Boolean)? = null,
        ignore: Boolean = false, // optional
        labels: String? = null,
        maxDepth: Int? = null,
        minDepth: Int? = null,
        relativeToChangelogFile: Boolean = false, // optional
        resourceComparator: Any? = null,
    ) {
        val typedResourceComparator: Comparator<String> = getComparatorByAny(resourceComparator)
        val contextFilterOrContext = contextFilter ?: context
        val includeContexts = ContextExpression(contextFilterOrContext?.evalExpressions(changeLog))
        val typedLabels = labels?.let { Labels(it.evalExpressions(changeLog)) }
        val includeAllFilter = filter?.let {
            IncludeAllFilter { changeLogPath -> filter(changeLogPath) }
        }
        changeLog.includeAll(
            path.evalExpressions(changeLog),
            relativeToChangelogFile,
            includeAllFilter,
            errorIfMissingOrEmpty,
            typedResourceComparator,
            resourceAccessor,
            includeContexts,
            typedLabels,
            ignore,
            minDepth ?: 0,
            maxDepth ?: Integer.MAX_VALUE,
            endsWithFilter?.evalExpressions(changeLog) ?: "",
            null,
        )
    }

    /**
     * Defines preconditions for the ChangeLog.
     * [official-document](https://docs.liquibase.com/concepts/changelogs/preconditions.html)
     *
     * @param onError Indicates the behavior when an error occurs.
     * @param onErrorMessage Custom message to be displayed when an error occurs.
     * @param onFail Indicates the behavior when a failure occurs.
     * @param onFailMessage Custom message to be displayed when a failure occurs.
     * @param onSqlOutput Custom SQL output settings.
     * @param block The block of code containing preconditions.
     */
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
                onFailMessage = onFailMessage?.evalExpressions(changeLog),
                onErrorMessage = onErrorMessage?.evalExpressions(changeLog),
                onSqlOutput = onSqlOutput,
            )
        val dsl =
            PreConditionDsl.build(
                changeLog = changeLog,
                preconditionContainerContext = preconditionContainerContext,
            )
        runCatching {
            dsl(block)
        }.fold(
            onSuccess = {
                changeLog.preconditions = it
            },
            onFailure = {
                throw ChangeLogParseException(
                    "changeLogId: ${changeLog.filePath} ${it.message}",
                    it,
                )
            }
        )
    }

    /**
     * Sets a property in the change log with a specified name and value, optionally from an external file.
     * [official-document](https://docs.liquibase.com/concepts/changelogs/property-substitution.html)
     *
     * @param name The name of the property. Required.
     * @param value The value of the property. Required.
     * @param file Optional file path from which to load additional properties. The path is evaluated for expressions.
     * @param relativeToChangelogFile Optional flag indicating whether the file path is relative to the changelog file.
     * @param context Optional context for which the property is valid.
     * @param contextFilter Optional filter to apply to the context.
     * @param labels Optional labels for the property.
     * @param dbms Optional DBMS-specific flag for the property.
     * @param global Flag indicating whether the property is global across all change logs. Defaults to true.
     */
    fun property(
        name: String,
        value: String,
        file: String? = null,
        relativeToChangelogFile: Boolean? = null,
        context: String? = null,
        contextFilter: String? = null,
        labels: String? = null, // todo: nothing document
        dbms: String? = null,
        global: Boolean = true,
    ) {
        val contextFilterOrContext = contextFilter ?: context
        val typedContext = ContextExpression(contextFilterOrContext?.evalExpressions(changeLog))

        val typedLabels = labels?.let { Labels(it.evalExpressions(changeLog)) }

        // https://docs.liquibase.com/concepts/changelogs/property-substitution.html#xml_example
        val changeLogParameters = changeLog.changeLogParameters

        if (file == null) {
            changeLogParameters.set(
                name,
                value,
                typedContext,
                typedLabels,
                dbms,
                global,
                changeLog,
            )
        } else {
            val propFile = file.evalExpressions(changeLog)
            val relativeTo =
                if (relativeToChangelogFile == true) {
                    changeLog.physicalFilePath
                } else {
                    null
                }
            resourceAccessor
                .get(
                    relativeTo,
                ).resolveSibling(propFile)
                .openInputStream()
                .use { stream ->
                    val props = Properties()
                    props.load(stream)
                    props.forEach { (k, v) ->
                        changeLogParameters.set(
                            k.toString(),
                            v.toString(),
                            typedContext,
                            typedLabels,
                            dbms,
                            global,
                            changeLog,
                        )
                    }
                }
        }
    }

    /**
     * Removes a specific property from a ChangeSet in the change log based on the given parameters.
     * [official-document](https://docs.liquibase.com/concepts/changelogs/property-substitution.html)
     *
     * @param change The identifier of the change to modify.
     * @param dbms The database management system for which the property is being removed.
     * @param remove The property to be removed from the ChangeSet.
     */
    fun removeChangeSetProperty(
        change: String,
        dbms: String,
        remove: String,
    ) {
        val currentDatabase = changeLog.changeLogParameters.database
        val evaluatedDbms = dbms.evalExpressions(changeLog)
        if (!DatabaseList.definitionMatches(evaluatedDbms, currentDatabase, false)) {
            return
        }

        val changeVisitor =
            ChangeVisitorFactory.getInstance().create(change.evalExpressions(changeLog))
                ?: throw ChangeLogParseException(
                    "DatabaseChangeLog: $change is not a valid change type",
                )
        val overrideChangeVisitor =
            (changeVisitor as? AddColumnChangeVisitor)?.let {
                // cannot use kotlin by(delegate).
                // because AddColumnChangeVisitor is an abstract class.
                object : AddColumnChangeVisitor() {
                    override fun getRemove() = remove

                    override fun getDbms(): Set<String?> = evaluatedDbms.splitAndTrim().toSet()
                }
            } ?: throw ChangeLogParseException(
                "changeVisitor: $changeVisitor is not a valid changeVisitor type",
            )

        changeLog.changeVisitors.add(overrideChangeVisitor)
    }

    private fun getComparatorByAny(resourceComparator: Any?): Comparator<String> {
        @Suppress("UNCHECKED_CAST")
        val typedResourceComparator: Comparator<String> = resourceComparator?.let {
            when (it) {
                is KClass<*> -> (it as KClass<Comparator<String>>).new()
                is Class<*> -> (it as Class<Comparator<String>>).kotlin.new()
                is Comparator<*> -> it as Comparator<String>
                is String -> run {
                    val clazz: KClass<Comparator<String>> = loadKClass(it.evalExpressions(changeLog))
                    clazz.new()
                }

                else -> error("KClass<*> or Class<*> or Comparator<*> or String required. but was $it")
            }
        } ?: Comparator.comparing { it.replace("WEB-INF/classes/", "") }
        return typedResourceComparator
    }

    companion object {
        var onUnknownFileFormat = OnUnknownFileFormat.WARN
    }
}
