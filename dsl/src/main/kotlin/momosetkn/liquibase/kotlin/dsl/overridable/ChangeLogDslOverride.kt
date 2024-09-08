package momosetkn.liquibase.kotlin.dsl.overridable

interface ChangeLogDslOverride {
    fun includeAll(
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
    )

    fun include(
        file: String,
        contextFilter: String?,
        context: String?, // same to contextFilter
        errorIfMissing: Boolean,
        ignore: Boolean,
        labels: String?,
        relativeToChangelogFile: Boolean,
    )
}
