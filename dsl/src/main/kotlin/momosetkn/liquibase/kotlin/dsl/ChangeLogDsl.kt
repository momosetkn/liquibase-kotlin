package momosetkn.liquibase.kotlin.dsl

import liquibase.ContextExpression
import liquibase.Labels
import liquibase.change.visitor.AddColumnChangeVisitor
import liquibase.change.visitor.ChangeVisitorFactory
import liquibase.changelog.ChangeSet
import liquibase.changelog.DatabaseChangeLog
import liquibase.changelog.IncludeAllFilter
import liquibase.database.DatabaseList
import liquibase.database.ObjectQuotingStrategy
import liquibase.exception.ChangeLogParseException
import liquibase.resource.ResourceAccessor
import momosetkn.liquibase.kotlin.dsl.util.StringsUtil.splitAndTrim
import java.util.Properties

@ChangeLogDslMarker
class ChangeLogDsl(
    private val changeLog: DatabaseChangeLog,
    private val resourceAccessor: ResourceAccessor,
) {
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
        val filePath = logicalFilePath ?: changeLog.filePath
        val contextFilterOrContext = contextFilter ?: context
        val changeSet =
            ChangeSet(
                id.expandExpressions(changeLog),
                author.expandExpressions(changeLog),
                runAlways ?: false,
                runOnChange ?: false,
                filePath.toString(),
                contextFilterOrContext.expandExpressions(changeLog),
                dbms.expandExpressions(changeLog),
                runWith.expandExpressions(changeLog),
                runWithSpoolFile.expandExpressions(changeLog),
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

    fun include(
        file: String,
        contextFilter: String? = null,
        context: String? = null, // same to contextFilter
        errorIfMissing: Boolean = true,
        ignore: Boolean = false,
        labels: String? = null,
        relativeToChangelogFile: Boolean = false,
    ) {
        val fileName =
            changeLog
                .changeLogParameters
                .expandExpressions(file, changeLog)
        val contextFilterOrContext = contextFilter ?: context
        val includeContexts = ContextExpression(contextFilterOrContext)
        val typedLabels = labels?.let { Labels(it) }

        changeLog.include(
            fileName,
            relativeToChangelogFile ?: false,
            errorIfMissing ?: false,
            resourceAccessor,
            includeContexts,
            typedLabels,
            ignore ?: false,
            DatabaseChangeLog.OnUnknownFileFormat.WARN, // TODO: set by user
        )
    }

    fun includeAll(
        path: String,
        contextFilter: String? = null,
        context: String? = null, // same to contextFilter
        endsWithFilter: String? = null,
        errorIfMissingOrEmpty: Boolean = true, // optional
        filter: String? = null,
        ignore: Boolean = false, // optional
        labels: String? = null,
        maxDepth: Int? = null,
        minDepth: Int? = null,
        relativeToChangelogFile: Boolean = false, // optional
        resourceComparator: String? = null,
    ) {
        @Suppress("UNCHECKED_CAST")
        val typedResourceComparator =
            resourceComparator?.let {
                // TODO: confirm
                this::class.java.classLoader
                    .loadClass(
                        it,
                    ).getDeclaredConstructor()
                    .newInstance() as Comparator<String>
            } ?: Comparator.comparing { it.replace("WEB-INF/classes/", "") }
        val contextFilterOrContext = contextFilter ?: context
        val includeContexts = ContextExpression(contextFilterOrContext)
        val typedLabels = labels?.let { Labels(it) }
        changeLog.includeAll(
            path,
            relativeToChangelogFile,
            filter as IncludeAllFilter?, // FIXME
            errorIfMissingOrEmpty,
            typedResourceComparator,
            resourceAccessor,
            includeContexts,
            typedLabels,
            ignore,
            minDepth ?: 0,
            maxDepth ?: Integer.MAX_VALUE,
            endsWithFilter ?: "",
            null, // TODO: confirm
        )
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
        kotlin.runCatching {
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

    fun property(
        name: String,
        value: String,
        file: String? = null,
        relativeToChangelogFile: Boolean? = null,
        context: String? = null,
        contextFilter: String? = null,
        labels: String? = null, // todo: nothing document
        dbms: String? = null,
        global: Boolean? = true,
    ) {
        val contextFilterOrContext = contextFilter ?: context
        val typedContext = ContextExpression(contextFilterOrContext)

        val typedLabels = labels?.let { Labels(it) }

        val global = global ?: true

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
            val propFile = file.toString()
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

    fun removeChangeSetProperty(
        change: String,
        dbms: String,
        remove: String,
    ) {
        val currentDatabase = changeLog.changeLogParameters.database
        if (!DatabaseList.definitionMatches(dbms, currentDatabase, false)) {
            return
        }

        val changeVisitor =
            ChangeVisitorFactory.getInstance().create(change)
                ?: throw ChangeLogParseException(
                    "DatabaseChangeLog: $change is not a valid change type",
                )
        val overrideChangeVisitor =
            (changeVisitor as? AddColumnChangeVisitor)?.let {
                // cannot use kotlin by(delegate).
                // because AddColumnChangeVisitor is an abstract class.
                object : AddColumnChangeVisitor() {
                    override fun getRemove() = remove

                    override fun getDbms(): Set<String?> = dbms.splitAndTrim().toSet()
                }
            } ?: throw ChangeLogParseException(
                "changeVisitor: $changeVisitor is not a valid changeVisitor type",
            )

        changeLog.changeVisitors.add(overrideChangeVisitor)
    }
}
