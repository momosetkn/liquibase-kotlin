@file:Suppress("ktlint:standard:no-consecutive-comments", "CyclomaticComplexMethod")

package momosetkn.liquibase.client

import momosetkn.liquibase.client.LiquibaseCommandExecutor.executeCommand
import java.time.temporal.TemporalAccessor

@Suppress("LargeClass", "TooManyFunctions")
class LiquibaseClient(
    override val configureBlock: ConfigureLiquibaseDslBlock,
) : ConfigureLiquibase {
    init {
        setSystemEnvArgs()
    }

    // memo
    private val globalArgs = getGlobalArgs()
    private val commandArgs = getCommandArgs()

    // init

    /**
     * [init copy](https://docs.liquibase.com/commands/init/copy.html)
     */
    fun initCopy(
        recursive: Boolean? = null,
        source: String? = null,
        target: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                recursive?.let { "recursive" to it },
                source?.let { "source" to it },
                target?.let { "target" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("init", "copy"),
            args = argsList,
        )
    }

    /**
     * [init project](https://docs.liquibase.com/commands/init/project.html)
     */
    fun initProject(
        changelogFile: String? = null,
        format: String? = null,
        keepTempFiles: Boolean? = null,
        password: String? = null,
        projectDefaultsFile: String? = null,
        projectDir: String? = null,
        projectGuide: String? = null,
        url: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                changelogFile?.let { "changelog-file" to it },
                format?.let { "format" to it },
                keepTempFiles?.let { "keep-temp-files" to it },
                password?.let { "password" to it },
                projectDefaultsFile?.let { "project-defaults-file" to it },
                projectDir?.let { "project-dir" to it },
                projectGuide?.let { "project-guide" to it },
                url?.let { "url" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("init", "project"),
            args = argsList,
        )
    }

    /**
     * [init start-h2](https://docs.liquibase.com/commands/init/start-h2.html)
     */
    fun initStartH2(
        bindAddress: String? = null,
        dbPort: String? = null,
        detached: Boolean? = null,
        launchBrowser: Boolean? = null,
        password: String? = null,
        username: String? = null,
        webPort: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                bindAddress?.let { "bind-address" to it },
                dbPort?.let { "db-port" to it },
                detached?.let { "detached" to it },
                launchBrowser?.let { "launch-browser" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
                webPort?.let { "web-port" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("init", "startH2"),
            args = argsList,
        )
    }

    // update

    /**
     * [update](https://docs.liquibase.com/commands/update/update.html)
     */
    fun update(
        liquibaseCatalogName: String? = null,
        liquibaseSchemaName: String? = null,
        changelogFile: String,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        forceOnPartialChanges: Boolean? = null,
        labelFilter: String? = null,
        password: String? = null,
        reportEnabled: Boolean? = null,
        reportName: String? = null,
        reportPath: String? = null,
        rollbackOnError: Boolean? = null,
        showSummary: String? = null,
        showSummaryOutput: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                liquibaseCatalogName?.let { "liquibase-catalog-name" to it },
                liquibaseSchemaName?.let { "liquibase-schema-name" to it },
                "changelog-file" to changelogFile,
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                forceOnPartialChanges?.let { "force-on-partial-changes" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                reportEnabled?.let { "report-enabled" to it },
                reportName?.let { "report-name" to it },
                reportPath?.let { "report-path" to it },
                rollbackOnError?.let { "rollback-on-error" to it },
                showSummary?.let { "show-summary" to it },
                showSummaryOutput?.let { "show-summary-output" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("update"),
            args = argsList,
        )
    }

    /**
     * [update-sql](https://docs.liquibase.com/commands/update/update-sql.html)
     */
    fun updateSql(
        outputFile: String? = null,
        changelogFile: String,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        outputDefaultCatalog: Boolean? = null,
        outputDefaultSchema: Boolean? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                outputFile?.let { "output-file" to it },
                "changelog-file" to changelogFile,
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                outputDefaultCatalog?.let { "output-default-catalog" to it },
                outputDefaultSchema?.let { "output-default-schema" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("updateSql"),
            args = argsList,
        )
    }

    /**
     * [update-count](https://docs.liquibase.com/commands/update/update-count.html)
     */
    fun updateCount(
        changelogFile: String,
        count: Int,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        forceOnPartialChanges: Boolean? = null,
        labelFilter: String? = null,
        password: String? = null,
        reportEnabled: Boolean? = null,
        reportName: String? = null,
        reportPath: String? = null,
        rollbackOnError: Boolean? = null,
        showSummary: String? = null,
        showSummaryOutput: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "changelog-file" to changelogFile,
                "count" to count,
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                forceOnPartialChanges?.let { "force-on-partial-changes" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                reportEnabled?.let { "report-enabled" to it },
                reportName?.let { "report-name" to it },
                reportPath?.let { "report-path" to it },
                rollbackOnError?.let { "rollback-on-error" to it },
                showSummary?.let { "show-summary" to it },
                showSummaryOutput?.let { "show-summary-output" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("updateCount"),
            args = argsList,
        )
    }

    /**
     * [update-count-sql](https://docs.liquibase.com/commands/update/update-count-sql.html)
     */
    fun updateCountSql(
        outputFile: String? = null,
        changelogFile: String,
        count: Int,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        outputDefaultCatalog: Boolean? = null,
        outputDefaultSchema: Boolean? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                outputFile?.let { "output-file" to it },
                "changelog-file" to changelogFile,
                "count" to count,
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                outputDefaultCatalog?.let { "output-default-catalog" to it },
                outputDefaultSchema?.let { "output-default-schema" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("updateCountSql"),
            args = argsList,
        )
    }

    /**
     * [update-to-tag](https://docs.liquibase.com/commands/update/update-to-tag.html)
     */
    fun updateToTag(
        changelogFile: String,
        tag: String,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        forceOnPartialChanges: Boolean? = null,
        labelFilter: String? = null,
        password: String? = null,
        reportEnabled: Boolean? = null,
        reportName: String? = null,
        reportPath: String? = null,
        rollbackOnError: Boolean? = null,
        showSummary: String? = null,
        showSummaryOutput: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "changelog-file" to changelogFile,
                "tag" to tag,
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                forceOnPartialChanges?.let { "force-on-partial-changes" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                reportEnabled?.let { "report-enabled" to it },
                reportName?.let { "report-name" to it },
                reportPath?.let { "report-path" to it },
                rollbackOnError?.let { "rollback-on-error" to it },
                showSummary?.let { "show-summary" to it },
                showSummaryOutput?.let { "show-summary-output" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("updateToTag"),
            args = argsList,
        )
    }

    /**
     * [update-to-tag-sql](https://docs.liquibase.com/commands/update/update-to-tag-sql.html)
     */
    fun updateToTagSql(
        outputFile: String? = null,
        changelogFile: String,
        tag: String,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        outputDefaultCatalog: Boolean? = null,
        outputDefaultSchema: Boolean? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                outputFile?.let { "output-file" to it },
                "changelog-file" to changelogFile,
                "tag" to tag,
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                outputDefaultCatalog?.let { "output-default-catalog" to it },
                outputDefaultSchema?.let { "output-default-schema" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("updateToTagSql"),
            args = argsList,
        )
    }

    /**
     * [update-testing-rollback](https://docs.liquibase.com/commands/update/update-testing-rollback.html)
     */
    fun updateTestingRollback(
        changelogFile: String,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        forceOnPartialChanges: Boolean? = null,
        labelFilter: String? = null,
        password: String? = null,
        reportEnabled: Boolean? = null,
        reportName: String? = null,
        reportPath: String? = null,
        rollbackOnError: Boolean? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "changelog-file" to changelogFile,
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                forceOnPartialChanges?.let { "force-on-partial-changes" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                reportEnabled?.let { "report-enabled" to it },
                reportName?.let { "report-name" to it },
                reportPath?.let { "report-path" to it },
                rollbackOnError?.let { "rollback-on-error" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("updateTestingRollback"),
            args = argsList,
        )
    }

    /**
     * [update-one-changeset](https://docs.liquibase.com/commands/update/update-one-changeset.html)
     */
    fun updateOneChangeset(
        licenseKey: String,
        changelogFile: String,
        changesetAuthor: String,
        changesetId: String,
        changesetPath: String,
        force: Boolean,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        forceOnPartialChanges: Boolean? = null,
        labelFilter: String? = null,
        password: String? = null,
        reportEnabled: Boolean? = null,
        reportName: String? = null,
        reportPath: String? = null,
        rollbackOnError: Boolean? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                "changelog-file" to changelogFile,
                "changeset-author" to changesetAuthor,
                "changeset-id" to changesetId,
                "changeset-path" to changesetPath,
                "force" to force,
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                forceOnPartialChanges?.let { "force-on-partial-changes" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                reportEnabled?.let { "report-enabled" to it },
                reportName?.let { "report-name" to it },
                reportPath?.let { "report-path" to it },
                rollbackOnError?.let { "rollback-on-error" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("updateOneChangeset"),
            args = argsList,
        )
    }

    /**
     * [update-one-changeset-sql](https://docs.liquibase.com/commands/update/update-one-changeset-sql.html)
     */
    fun updateOneChangesetSql(
        licenseKey: String,
        outputFile: String? = null,
        changelogFile: String,
        changesetAuthor: String,
        changesetId: String,
        changesetPath: String,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                outputFile?.let { "output-file" to it },
                "changelog-file" to changelogFile,
                "changeset-author" to changesetAuthor,
                "changeset-id" to changesetId,
                "changeset-path" to changesetPath,
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("updateOneChangesetSql"),
            args = argsList,
        )
    }

    // rollback

    /**
     * [rollback](https://docs.liquibase.com/commands/rollback/rollback.html)
     */
    fun rollback(
        changelogFile: String,
        tag: String,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        password: String? = null,
        reportEnabled: Boolean? = null,
        reportName: String? = null,
        reportPath: String? = null,
        rollbackScript: String? = null,
        tagVersion: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "changelog-file" to changelogFile,
                "tag" to tag,
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                reportEnabled?.let { "report-enabled" to it },
                reportName?.let { "report-name" to it },
                reportPath?.let { "report-path" to it },
                rollbackScript?.let { "rollback-script" to it },
                tagVersion?.let { "tag-version" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("rollback"),
            args = argsList,
        )
    }

    /**
     * [rollback-sql](https://docs.liquibase.com/commands/rollback/rollback-sql.html)
     */
    fun rollbackSql(
        outputFile: String? = null,
        changelogFile: String,
        tag: String,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        outputDefaultCatalog: Boolean? = null,
        outputDefaultSchema: Boolean? = null,
        password: String? = null,
        rollbackScript: String? = null,
        tagVersion: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                outputFile?.let { "output-file" to it },
                "changelog-file" to changelogFile,
                "tag" to tag,
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                outputDefaultCatalog?.let { "output-default-catalog" to it },
                outputDefaultSchema?.let { "output-default-schema" to it },
                password?.let { "password" to it },
                rollbackScript?.let { "rollback-script" to it },
                tagVersion?.let { "tag-version" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("rollbackSql"),
            args = argsList,
        )
    }

    /**
     * [rollback-to-date](https://docs.liquibase.com/commands/rollback/rollback-to-date.html)
     */
    fun rollbackToDate(
        changelogFile: String,
        date: TemporalAccessor,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        password: String? = null,
        reportEnabled: Boolean? = null,
        reportName: String? = null,
        reportPath: String? = null,
        rollbackScript: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "changelog-file" to changelogFile,
                "date" to LiquibaseClientConfig.dateTimeFormatter.format(date),
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                reportEnabled?.let { "report-enabled" to it },
                reportName?.let { "report-name" to it },
                reportPath?.let { "report-path" to it },
                rollbackScript?.let { "rollback-script" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("rollbackToDate"),
            args = argsList,
        )
    }

    /**
     * [rollback-to-date-sql](https://docs.liquibase.com/commands/rollback/rollback-to-date-sql.html)
     */
    fun rollbackToDateSql(
        outputFile: String? = null,
        changelogFile: String,
        date: TemporalAccessor,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        outputDefaultCatalog: Boolean? = null,
        outputDefaultSchema: Boolean? = null,
        password: String? = null,
        rollbackScript: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                outputFile?.let { "output-file" to it },
                "changelog-file" to changelogFile,
                "date" to LiquibaseClientConfig.dateTimeFormatter.format(date),
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                outputDefaultCatalog?.let { "output-default-catalog" to it },
                outputDefaultSchema?.let { "output-default-schema" to it },
                password?.let { "password" to it },
                rollbackScript?.let { "rollback-script" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("rollbackToDateSql"),
            args = argsList,
        )
    }

    /**
     * [rollback-count](https://docs.liquibase.com/commands/rollback/rollback-count.html)
     */
    fun rollbackCount(
        changelogFile: String,
        count: Int,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        password: String? = null,
        reportEnabled: Boolean? = null,
        reportName: String? = null,
        reportPath: String? = null,
        rollbackScript: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "changelog-file" to changelogFile,
                "count" to count,
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                reportEnabled?.let { "report-enabled" to it },
                reportName?.let { "report-name" to it },
                reportPath?.let { "report-path" to it },
                rollbackScript?.let { "rollback-script" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("rollbackCount"),
            args = argsList,
        )
    }

    /**
     * [rollback-count-sql](https://docs.liquibase.com/commands/rollback/rollback-count-sql.html)
     */
    fun rollbackCountSql(
        outputFile: String? = null,
        changelogFile: String,
        count: Int,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        outputDefaultCatalog: Boolean? = null,
        outputDefaultSchema: Boolean? = null,
        password: String? = null,
        rollbackScript: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                outputFile?.let { "output-file" to it },
                "changelog-file" to changelogFile,
                "count" to count,
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                outputDefaultCatalog?.let { "output-default-catalog" to it },
                outputDefaultSchema?.let { "output-default-schema" to it },
                password?.let { "password" to it },
                rollbackScript?.let { "rollback-script" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("rollbackCountSql"),
            args = argsList,
        )
    }

    /**
     * [future-rollback-sql](https://docs.liquibase.com/commands/rollback/future-rollback-sql.html)
     */
    fun futureRollbackSql(
        outputFile: String? = null,
        changelogFile: String,
        url: String,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        outputDefaultCatalog: Boolean? = null,
        outputDefaultSchema: Boolean? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                outputFile?.let { "output-file" to it },
                "changelog-file" to changelogFile,
                "url" to url,
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                outputDefaultCatalog?.let { "output-default-catalog" to it },
                outputDefaultSchema?.let { "output-default-schema" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("futureRollbackSql"),
            args = argsList,
        )
    }

    /**
     * [future-rollback-from-tag-sql](https://docs.liquibase.com/commands/rollback/future-rollback-from-tag-sql.html)
     */
    fun futureRollbackFromTagSql(
        outputFile: String? = null,
        changelogFile: String,
        tag: String,
        url: String,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        outputDefaultCatalog: Boolean? = null,
        outputDefaultSchema: Boolean? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                outputFile?.let { "output-file" to it },
                "changelog-file" to changelogFile,
                "tag" to tag,
                "url" to url,
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                outputDefaultCatalog?.let { "output-default-catalog" to it },
                outputDefaultSchema?.let { "output-default-schema" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("futureRollbackFromTagSql"),
            args = argsList,
        )
    }

    /**
     * [future-rollback-count-sql](https://docs.liquibase.com/commands/rollback/future-rollback-count-sql.html)
     */
    fun futureRollbackCountSql(
        outputFile: String? = null,
        changelogFile: String,
        count: Int,
        url: String,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        outputDefaultCatalog: Boolean? = null,
        outputDefaultSchema: Boolean? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                outputFile?.let { "output-file" to it },
                "changelog-file" to changelogFile,
                "count" to count,
                "url" to url,
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                outputDefaultCatalog?.let { "output-default-catalog" to it },
                outputDefaultSchema?.let { "output-default-schema" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("futureRollbackCountSql"),
            args = argsList,
        )
    }

    /**
     * [rollback-one-changeset](https://docs.liquibase.com/commands/rollback/rollback-one-changeset.html)
     */
    fun rollbackOneChangeset(
        licenseKey: String,
        changelogFile: String,
        changesetAuthor: String,
        changesetId: String,
        changesetPath: String,
        force: Boolean,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        password: String? = null,
        reportEnabled: Boolean? = null,
        reportName: String? = null,
        reportPath: String? = null,
        rollbackScript: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                "changelog-file" to changelogFile,
                "changeset-author" to changesetAuthor,
                "changeset-id" to changesetId,
                "changeset-path" to changesetPath,
                "force" to force,
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                password?.let { "password" to it },
                reportEnabled?.let { "report-enabled" to it },
                reportName?.let { "report-name" to it },
                reportPath?.let { "report-path" to it },
                rollbackScript?.let { "rollback-script" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("rollbackOneChangeset"),
            args = argsList,
        )
    }

    /**
     * [rollback-one-changeset-sql](https://docs.liquibase.com/commands/rollback/rollback-one-changeset-sql.html)
     */
    fun rollbackOneChangesetSql(
        licenseKey: String,
        outputFile: String? = null,
        changelogFile: String,
        changesetAuthor: String,
        changesetId: String,
        changesetPath: String,
        url: String,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        outputDefaultCatalog: Boolean? = null,
        outputDefaultSchema: Boolean? = null,
        password: String? = null,
        rollbackScript: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                outputFile?.let { "output-file" to it },
                "changelog-file" to changelogFile,
                "changeset-author" to changesetAuthor,
                "changeset-id" to changesetId,
                "changeset-path" to changesetPath,
                "url" to url,
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                outputDefaultCatalog?.let { "output-default-catalog" to it },
                outputDefaultSchema?.let { "output-default-schema" to it },
                password?.let { "password" to it },
                rollbackScript?.let { "rollback-script" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("rollbackOneChangesetSql"),
            args = argsList,
        )
    }

    /**
     * [rollback-one-update](https://docs.liquibase.com/commands/rollback/rollback-one-update.html)
     */
    fun rollbackOneUpdate(
        licenseKey: String,
        changelogFile: String,
        force: Boolean,
        url: String,
        changeExecListenerClass: String? = null,
        changeExecListenerPropertiesFile: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        deploymentId: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        password: String? = null,
        reportEnabled: Boolean? = null,
        reportName: String? = null,
        reportPath: String? = null,
        rollbackScript: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                "changelog-file" to changelogFile,
                "force" to force,
                "url" to url,
                changeExecListenerClass?.let { "change-exec-listener-class" to it },
                changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                deploymentId?.let { "deployment-id" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                password?.let { "password" to it },
                reportEnabled?.let { "report-enabled" to it },
                reportName?.let { "report-name" to it },
                reportPath?.let { "report-path" to it },
                rollbackScript?.let { "rollback-script" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("rollbackOneUpdate"),
            args = argsList,
        )
    }

    /**
     * [rollback-one-update-sql](https://docs.liquibase.com/commands/rollback/rollback-one-update-sql.html)
     */
    fun rollbackOneUpdateSql(
        licenseKey: String,
        outputFile: String? = null,
        changelogFile: String,
        url: String,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        deploymentId: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        outputDefaultCatalog: Boolean? = null,
        outputDefaultSchema: Boolean? = null,
        password: String? = null,
        rollbackScript: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                outputFile?.let { "output-file" to it },
                "changelog-file" to changelogFile,
                "url" to url,
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                deploymentId?.let { "deployment-id" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                outputDefaultCatalog?.let { "output-default-catalog" to it },
                outputDefaultSchema?.let { "output-default-schema" to it },
                password?.let { "password" to it },
                rollbackScript?.let { "rollback-script" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("rollbackOneUpdateSql"),
            args = argsList,
        )
    }

// inspection
    /**
     * [diff](https://docs.liquibase.com/commands/inspection/diff.html)
     */
    fun diff(
        referenceUrl: String,
        url: String,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        diffTypes: String? = null,
        driftSeverity: String? = null,
        driftSeverityChanged: String? = null,
        driftSeverityMissing: String? = null,
        driftSeverityUnexpected: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        excludeObjects: String? = null,
        format: String? = null,
        includeObjects: String? = null,
        reportOpen: Boolean? = null,
        outputSchemas: String? = null,
        password: String? = null,
        referenceDefaultCatalogName: String? = null,
        referenceDefaultSchemaName: String? = null,
        referenceDriver: String? = null,
        referenceDriverPropertiesFile: String? = null,
        referenceLiquibaseCatalogName: String? = null,
        referenceLiquibaseSchemaName: String? = null,
        referencePassword: String? = null,
        referenceSchemas: String? = null,
        referenceUsername: String? = null,
        reportEnabled: Boolean? = null,
        reportName: String? = null,
        reportPath: String? = null,
        schemas: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "reference-url" to referenceUrl,
                "url" to url,
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                diffTypes?.let { "diff-types" to it },
                driftSeverity?.let { "drift-severity" to it },
                driftSeverityChanged?.let { "drift-severity-changed" to it },
                driftSeverityMissing?.let { "drift-severity-missing" to it },
                driftSeverityUnexpected?.let { "drift-severity-unexpected" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                excludeObjects?.let { "exclude-objects" to it },
                format?.let { "format" to it },
                includeObjects?.let { "include-objects" to it },
                reportOpen?.let { "report-open" to it },
                outputSchemas?.let { "output-schemas" to it },
                password?.let { "password" to it },
                referenceDefaultCatalogName?.let { "reference-default-catalog-name" to it },
                referenceDefaultSchemaName?.let { "reference-default-schema-name" to it },
                referenceDriver?.let { "reference-driver" to it },
                referenceDriverPropertiesFile?.let { "reference-driver-properties-file" to it },
                referenceLiquibaseCatalogName?.let { "reference-liquibase-catalog-name" to it },
                referenceLiquibaseSchemaName?.let { "reference-liquibase-schema-name" to it },
                referencePassword?.let { "reference-password" to it },
                referenceSchemas?.let { "reference-schemas" to it },
                referenceUsername?.let { "reference-username" to it },
                reportEnabled?.let { "report-enabled" to it },
                reportName?.let { "report-name" to it },
                reportPath?.let { "report-path" to it },
                schemas?.let { "schemas" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("diff"),
            args = argsList,
        )
    }

    /**
     * [diff-changelog](https://docs.liquibase.com/commands/inspection/diff-changelog.html)
     */
    fun diffChangelog(
        changelogFile: String,
        referenceUrl: String,
        url: String,
        author: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        diffTypes: String? = null,
        driftSeverity: String? = null,
        driftSeverityChanged: String? = null,
        driftSeverityMissing: String? = null,
        driftSeverityUnexpected: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        excludeObjects: String? = null,
        includeCatalog: Boolean? = null,
        includeObjects: String? = null,
        includeSchema: Boolean? = null,
        includeTablespace: Boolean? = null,
        labelFilter: String? = null,
        reportOpen: Boolean? = null,
        outputSchemas: String? = null,
        password: String? = null,
        referenceDefaultCatalogName: String? = null,
        referenceDefaultSchemaName: String? = null,
        referenceDriver: String? = null,
        referenceDriverPropertiesFile: String? = null,
        referenceLiquibaseCatalogName: String? = null,
        referenceLiquibaseSchemaName: String? = null,
        referencePassword: String? = null,
        referenceSchemas: String? = null,
        referenceUsername: String? = null,
        replaceIfExistsTypes: String? = null,
        reportEnabled: Boolean? = null,
        reportName: String? = null,
        reportPath: String? = null,
        runOnChangeTypes: String? = null,
        schemas: String? = null,
        skipObjectSorting: Boolean? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "changelog-file" to changelogFile,
                "reference-url" to referenceUrl,
                "url" to url,
                author?.let { "author" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                diffTypes?.let { "diff-types" to it },
                driftSeverity?.let { "drift-severity" to it },
                driftSeverityChanged?.let { "drift-severity-changed" to it },
                driftSeverityMissing?.let { "drift-severity-missing" to it },
                driftSeverityUnexpected?.let { "drift-severity-unexpected" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                excludeObjects?.let { "exclude-objects" to it },
                includeCatalog?.let { "include-catalog" to it },
                includeObjects?.let { "include-objects" to it },
                includeSchema?.let { "include-schema" to it },
                includeTablespace?.let { "include-tablespace" to it },
                labelFilter?.let { "label-filter" to it },
                reportOpen?.let { "report-open" to it },
                outputSchemas?.let { "output-schemas" to it },
                password?.let { "password" to it },
                referenceDefaultCatalogName?.let { "reference-default-catalog-name" to it },
                referenceDefaultSchemaName?.let { "reference-default-schema-name" to it },
                referenceDriver?.let { "reference-driver" to it },
                referenceDriverPropertiesFile?.let { "reference-driver-properties-file" to it },
                referenceLiquibaseCatalogName?.let { "reference-liquibase-catalog-name" to it },
                referenceLiquibaseSchemaName?.let { "reference-liquibase-schema-name" to it },
                referencePassword?.let { "reference-password" to it },
                referenceSchemas?.let { "reference-schemas" to it },
                referenceUsername?.let { "reference-username" to it },
                replaceIfExistsTypes?.let { "replace-if-exists-types" to it },
                reportEnabled?.let { "report-enabled" to it },
                reportName?.let { "report-name" to it },
                reportPath?.let { "report-path" to it },
                runOnChangeTypes?.let { "run-on-change-types" to it },
                schemas?.let { "schemas" to it },
                skipObjectSorting?.let { "skip-object-sorting" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("diffChangelog"),
            args = argsList,
        )
    }

    /**
     * [snapshot](https://docs.liquibase.com/commands/inspection/snapshot.html)
     */
    fun snapshot(
        outputFile: String? = null,
        url: String,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        password: String? = null,
        schemas: String? = null,
        snapshotFilters: String? = null,
        snapshotFormat: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                outputFile?.let { "output-file" to it },
                "url" to url,
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                password?.let { "password" to it },
                schemas?.let { "schemas" to it },
                snapshotFilters?.let { "snapshot-filters" to it },
                snapshotFormat?.let { "snapshot-format" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("snapshot"),
            args = argsList,
        )
    }

    /**
     * [snapshot-reference](https://docs.liquibase.com/commands/inspection/snapshot-reference.html)
     */
    fun snapshotReference(
        outputFile: String? = null,
        referenceUrl: String,
        referenceDefaultCatalogName: String? = null,
        referenceDefaultSchemaName: String? = null,
        referenceDriver: String? = null,
        referenceDriverPropertiesFile: String? = null,
        referenceLiquibaseCatalogName: String? = null,
        referenceLiquibaseSchemaName: String? = null,
        referencePassword: String? = null,
        referenceUsername: String? = null,
        snapshotFilters: String? = null,
        snapshotFormat: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                outputFile?.let { "output-file" to it },
                "reference-url" to referenceUrl,
                referenceDefaultCatalogName?.let { "reference-default-catalog-name" to it },
                referenceDefaultSchemaName?.let { "reference-default-schema-name" to it },
                referenceDriver?.let { "reference-driver" to it },
                referenceDriverPropertiesFile?.let { "reference-driver-properties-file" to it },
                referenceLiquibaseCatalogName?.let { "reference-liquibase-catalog-name" to it },
                referenceLiquibaseSchemaName?.let { "reference-liquibase-schema-name" to it },
                referencePassword?.let { "reference-password" to it },
                referenceUsername?.let { "reference-username" to it },
                snapshotFilters?.let { "snapshot-filters" to it },
                snapshotFormat?.let { "snapshot-format" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("snapshotReference"),
            args = argsList,
        )
    }

    /**
     * [generate-changelog](https://docs.liquibase.com/commands/inspection/generate-changelog.html)
     */
    fun generateChangelog(
        licenseKey: String? = null, // Unlike the document, optional
        url: String,
        author: String? = null,
        changelogFile: String? = null,
        contextFilter: String? = null,
        dataOutputDirectory: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        diffTypes: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        excludeObjects: String? = null,
        includeCatalog: Boolean? = null,
        includeObjects: String? = null,
        includeSchema: Boolean? = null,
        includeTablespace: Boolean? = null,
        labelFilter: String? = null,
        outputSchemas: String? = null,
        overwriteOutputFile: Boolean? = null,
        password: String? = null,
        replaceIfExistsTypes: String? = null,
        runOnChangeTypes: String? = null,
        schemas: String? = null,
        skipObjectSorting: Boolean? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                licenseKey?.let { "license-key" to it },
                "url" to url,
                author?.let { "author" to it },
                changelogFile?.let { "changelog-file" to it },
                contextFilter?.let { "context-filter" to it },
                dataOutputDirectory?.let { "data-output-directory" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                diffTypes?.let { "diff-types" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                excludeObjects?.let { "exclude-objects" to it },
                includeCatalog?.let { "include-catalog" to it },
                includeObjects?.let { "include-objects" to it },
                includeSchema?.let { "include-schema" to it },
                includeTablespace?.let { "include-tablespace" to it },
                labelFilter?.let { "label-filter" to it },
                outputSchemas?.let { "output-schemas" to it },
                overwriteOutputFile?.let { "overwrite-output-file" to it },
                password?.let { "password" to it },
                replaceIfExistsTypes?.let { "replace-if-exists-types" to it },
                runOnChangeTypes?.let { "run-on-change-types" to it },
                schemas?.let { "schemas" to it },
                skipObjectSorting?.let { "skip-object-sorting" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("generateChangelog"),
            args = argsList,
        )
    }

    /**
     * [diff JSON](https://docs.liquibase.com/commands/inspection/diff-json.html)
     */
    fun diffJSON(
        referenceUrl: String,
        url: String,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        diffTypes: String? = null,
        driftSeverity: String? = null,
        driftSeverityChanged: String? = null,
        driftSeverityMissing: String? = null,
        driftSeverityUnexpected: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        excludeObjects: String? = null,
        format: String? = null,
        includeObjects: String? = null,
        reportOpen: Boolean? = null,
        outputSchemas: String? = null,
        password: String? = null,
        referenceDefaultCatalogName: String? = null,
        referenceDefaultSchemaName: String? = null,
        referenceDriver: String? = null,
        referenceDriverPropertiesFile: String? = null,
        referenceLiquibaseCatalogName: String? = null,
        referenceLiquibaseSchemaName: String? = null,
        referencePassword: String? = null,
        referenceSchemas: String? = null,
        referenceUsername: String? = null,
        reportEnabled: Boolean? = null,
        reportName: String? = null,
        reportPath: String? = null,
        schemas: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "reference-url" to referenceUrl,
                "url" to url,
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                diffTypes?.let { "diff-types" to it },
                driftSeverity?.let { "drift-severity" to it },
                driftSeverityChanged?.let { "drift-severity-changed" to it },
                driftSeverityMissing?.let { "drift-severity-missing" to it },
                driftSeverityUnexpected?.let { "drift-severity-unexpected" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                excludeObjects?.let { "exclude-objects" to it },
                format?.let { "format" to it },
                includeObjects?.let { "include-objects" to it },
                reportOpen?.let { "report-open" to it },
                outputSchemas?.let { "output-schemas" to it },
                password?.let { "password" to it },
                referenceDefaultCatalogName?.let { "reference-default-catalog-name" to it },
                referenceDefaultSchemaName?.let { "reference-default-schema-name" to it },
                referenceDriver?.let { "reference-driver" to it },
                referenceDriverPropertiesFile?.let { "reference-driver-properties-file" to it },
                referenceLiquibaseCatalogName?.let { "reference-liquibase-catalog-name" to it },
                referenceLiquibaseSchemaName?.let { "reference-liquibase-schema-name" to it },
                referencePassword?.let { "reference-password" to it },
                referenceSchemas?.let { "reference-schemas" to it },
                referenceUsername?.let { "reference-username" to it },
                reportEnabled?.let { "report-enabled" to it },
                reportName?.let { "report-name" to it },
                reportPath?.let { "report-path" to it },
                schemas?.let { "schemas" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("diff", "JSON"),
            args = argsList,
        )
    }

// change-tracking
    /**
     * [history](https://docs.liquibase.com/commands/change-tracking/history.html)
     */
    fun history(
        outputFile: String? = null,
        url: String,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        format: String? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                outputFile?.let { "output-file" to it },
                "url" to url,
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                format?.let { "format" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("history"),
            args = argsList,
        )
    }

    /**
     * [status](https://docs.liquibase.com/commands/change-tracking/status.html)
     */
    fun status(
        changelogFile: String,
        url: String,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        password: String? = null,
        username: String? = null,
        verbose: Boolean? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "changelog-file" to changelogFile,
                "url" to url,
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
                verbose?.let { "verbose" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("status"),
            args = argsList,
        )
    }

    /**
     * [unexpected-changesets](https://docs.liquibase.com/commands/change-tracking/unexpected-changesets.html)
     */
    fun unexpectedChangesets(
        changelogFile: String,
        url: String,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        password: String? = null,
        username: String? = null,
        verbose: Boolean? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "changelog-file" to changelogFile,
                "url" to url,
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
                verbose?.let { "verbose" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("unexpectedChangesets"),
            args = argsList,
        )
    }

    /**
     * [connect](https://docs.liquibase.com/commands/change-tracking/connect.html)
     */
    fun connect(
        url: String,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "url" to url,
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("connect"),
            args = argsList,
        )
    }

    /**
     * [dbcl-history](https://docs.liquibase.com/commands/change-tracking/dbcl-history.html)
     */
    fun dbclHistory(
        licenseKey: String,
        dbclhistoryCaptureExtensions: Boolean? = null,
        dbclhistoryCaptureSql: Boolean? = null,
        dbclhistoryEnabled: Boolean? = null,
        dbclhistorySeverity: String? = null,
        outputFile: String? = null,
        url: String,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        format: String? = null,
        password: String? = null,
        username: String? = null,
        verbose: Boolean? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                dbclhistoryCaptureExtensions?.let { "dbclhistory-capture-extensions" to it },
                dbclhistoryCaptureSql?.let { "dbclhistory-capture-sql" to it },
                dbclhistoryEnabled?.let { "dbclhistory-enabled" to it },
                dbclhistorySeverity?.let { "dbclhistory-severity" to it },
                outputFile?.let { "output-file" to it },
                "url" to url,
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                format?.let { "format" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
                verbose?.let { "verbose" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("dbclHistory"),
            args = argsList,
        )
    }

// utility
    /**
     * [db-doc](https://docs.liquibase.com/commands/utility/db-doc.html)
     */
    fun dbDoc(
        changelogFile: String,
        outputDirectory: String,
        url: String,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        password: String? = null,
        schemas: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "changelog-file" to changelogFile,
                "output-directory" to outputDirectory,
                "url" to url,
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                schemas?.let { "schemas" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("dbDoc"),
            args = argsList,
        )
    }

    /**
     * [tag](https://docs.liquibase.com/commands/utility/tag.html)
     */
    fun tag(
        tag: String,
        url: String,
        addRow: Boolean? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "tag" to tag,
                "url" to url,
                addRow?.let { "add-row" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("tag"),
            args = argsList,
        )
    }

    /**
     * [tag-exists](https://docs.liquibase.com/commands/utility/tag-exists.html)
     */
    fun tagExists(
        tag: String,
        url: String,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "tag" to tag,
                "url" to url,
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("tagExists"),
            args = argsList,
        )
    }

    /**
     * [validate](https://docs.liquibase.com/commands/utility/validate.html)
     */
    fun validate(
        changelogFile: String,
        url: String,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "changelog-file" to changelogFile,
                "url" to url,
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("validate"),
            args = argsList,
        )
    }

    /**
     * [calculate-checksum](https://docs.liquibase.com/commands/utility/calculate-checksum.html)
     */
    fun calculateChecksum(
        changelogFile: String,
        url: String,
        changesetAuthor: String? = null,
        changesetId: String? = null,
        changesetIdentifier: String? = null,
        changesetPath: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "changelog-file" to changelogFile,
                "url" to url,
                changesetAuthor?.let { "changeset-author" to it },
                changesetId?.let { "changeset-id" to it },
                changesetIdentifier?.let { "changeset-identifier" to it },
                changesetPath?.let { "changeset-path" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("calculateChecksum"),
            args = argsList,
        )
    }

    /**
     * [clear-checksums](https://docs.liquibase.com/commands/utility/clear-checksums.html)
     */
    fun clearChecksums(
        url: String,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "url" to url,
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("clearChecksums"),
            args = argsList,
        )
    }

    /**
     * [list-locks](https://docs.liquibase.com/commands/utility/list-locks.html)
     */
    fun listLocks(
        url: String,
        changelogFile: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "url" to url,
                changelogFile?.let { "changelog-file" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("listLocks"),
            args = argsList,
        )
    }

    /**
     * [release-locks](https://docs.liquibase.com/commands/utility/release-locks.html)
     */
    fun releaseLocks(
        url: String,
        changelogFile: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "url" to url,
                changelogFile?.let { "changelog-file" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("releaseLocks"),
            args = argsList,
        )
    }

    /**
     * [changelog-sync](https://docs.liquibase.com/commands/utility/changelog-sync.html)
     */
    fun changelogSync(
        changelogFile: String,
        url: String,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "changelog-file" to changelogFile,
                "url" to url,
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("changelogSync"),
            args = argsList,
        )
    }

    /**
     * [changelog-sync-sql](https://docs.liquibase.com/commands/utility/changelog-sync-sql.html)
     */
    fun changelogSyncSql(
        outputFile: String? = null,
        changelogFile: String,
        url: String,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        outputDefaultCatalog: Boolean? = null,
        outputDefaultSchema: Boolean? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                outputFile?.let { "output-file" to it },
                "changelog-file" to changelogFile,
                "url" to url,
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                outputDefaultCatalog?.let { "output-default-catalog" to it },
                outputDefaultSchema?.let { "output-default-schema" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("changelogSyncSql"),
            args = argsList,
        )
    }

    /**
     * [changelog-sync-to-tag](https://docs.liquibase.com/commands/utility/changelog-sync-to-tag.html)
     */
    fun changelogSyncToTag(
        changelogFile: String,
        tag: String,
        url: String,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "changelog-file" to changelogFile,
                "tag" to tag,
                "url" to url,
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("changelogSyncToTag"),
            args = argsList,
        )
    }

    /**
     * [changelog-sync-to-tag-sql](https://docs.liquibase.com/commands/utility/changelog-sync-to-tag-sql.html)
     */
    fun changelogSyncToTagSql(
        outputFile: String? = null,
        changelogFile: String,
        tag: String,
        url: String,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        outputDefaultCatalog: Boolean? = null,
        outputDefaultSchema: Boolean? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                outputFile?.let { "output-file" to it },
                "changelog-file" to changelogFile,
                "tag" to tag,
                "url" to url,
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                outputDefaultCatalog?.let { "output-default-catalog" to it },
                outputDefaultSchema?.let { "output-default-schema" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("changelogSyncToTagSql"),
            args = argsList,
        )
    }

    /**
     * [mark-next-changeset-ran](https://docs.liquibase.com/commands/utility/mark-next-changeset-ran.html)
     */
    fun markNextChangesetRan(
        changelogFile: String,
        url: String,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "changelog-file" to changelogFile,
                "url" to url,
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("markNextChangesetRan"),
            args = argsList,
        )
    }

    /**
     * [mark-next-changeset-ran-sql](https://docs.liquibase.com/commands/utility/mark-next-changeset-ran-sql.html)
     */
    fun markNextChangesetRanSql(
        outputFile: String? = null,
        changelogFile: String,
        url: String,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        labelFilter: String? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                outputFile?.let { "output-file" to it },
                "changelog-file" to changelogFile,
                "url" to url,
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("markNextChangesetRanSql"),
            args = argsList,
        )
    }

    /**
     * [drop-all](https://docs.liquibase.com/commands/utility/drop-all.html)
     */
    fun dropAll(
        force: Boolean,
        url: String,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        dropDbclhistory: Boolean? = null,
        password: String? = null,
        requireForce: Boolean? = null,
        schemas: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "force" to force,
                "url" to url,
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                dropDbclhistory?.let { "drop-dbclhistory" to it },
                password?.let { "password" to it },
                requireForce?.let { "require-force" to it },
                schemas?.let { "schemas" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("dropAll"),
            args = argsList,
        )
    }

    /**
     * [execute-sql](https://docs.liquibase.com/commands/utility/execute-sql.html)
     */
    fun executeSql(
        outputFile: String? = null,
        sql: String,
        sqlFile: String,
        url: String,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        delimiter: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        password: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                outputFile?.let { "output-file" to it },
                "sql" to sql,
                "sql-file" to sqlFile,
                "url" to url,
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                delimiter?.let { "delimiter" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                password?.let { "password" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("executeSql"),
            args = argsList,
        )
    }

    /**
     * [set-contexts](https://docs.liquibase.com/commands/utility/set-contexts.html)
     */
    fun setContexts(
        licenseKey: String,
        changelogFile: String,
        setAs: String,
        changesetAuthor: String? = null,
        changesetId: String? = null,
        changesetPath: String? = null,
        contextFilter: String? = null,
        dbms: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        forceReplace: Boolean? = null,
        labelFilter: String? = null,
        password: String? = null,
        url: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                "changelog-file" to changelogFile,
                "set-as" to setAs,
                changesetAuthor?.let { "changeset-author" to it },
                changesetId?.let { "changeset-id" to it },
                changesetPath?.let { "changeset-path" to it },
                contextFilter?.let { "context-filter" to it },
                dbms?.let { "dbms" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                forceReplace?.let { "force-replace" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                url?.let { "url" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("setContexts"),
            args = argsList,
        )
    }

    /**
     * [set-labels](https://docs.liquibase.com/commands/utility/set-labels.html)
     */
    fun setLabels(
        licenseKey: String,
        changelogFile: String,
        setAs: String,
        changesetAuthor: String? = null,
        changesetId: String? = null,
        changesetPath: String? = null,
        contextFilter: String? = null,
        dbms: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        forceReplace: Boolean? = null,
        labelFilter: String? = null,
        password: String? = null,
        url: String? = null,
        username: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                "changelog-file" to changelogFile,
                "set-as" to setAs,
                changesetAuthor?.let { "changeset-author" to it },
                changesetId?.let { "changeset-id" to it },
                changesetPath?.let { "changeset-path" to it },
                contextFilter?.let { "context-filter" to it },
                dbms?.let { "dbms" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                forceReplace?.let { "force-replace" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                url?.let { "url" to it },
                username?.let { "username" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("setLabels"),
            args = argsList,
        )
    }

// checks
    /**
     * [check bulk-set](https://docs.liquibase.com/commands/quality-checks/subcommands/bulk-set.html)
     */
    fun checkBulkSet(
        licenseKey: String,
        disable: Boolean? = null,
        enable: Boolean? = null,
        autoEnableNewChecks: Boolean? = null,
        autoUpdate: String? = null,
        checkName: String? = null,
        checksSettingsFile: String? = null,
        force: Boolean? = null,
        severity: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                disable?.let { "disable" to it },
                enable?.let { "enable" to it },
                autoEnableNewChecks?.let { "auto-enable-new-checks" to it },
                autoUpdate?.let { "auto-update" to it },
                checkName?.let { "check-name" to it },
                checksSettingsFile?.let { "checks-settings-file" to it },
                force?.let { "force" to it },
                severity?.let { "severity" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("check", "bulkSet"),
            args = argsList,
        )
    }

    /**
     * [check copy](https://docs.liquibase.com/commands/quality-checks/subcommands/copy.html)
     */
    fun checkCopy(
        licenseKey: String,
        checkName: String,
        autoEnableNewChecks: Boolean? = null,
        autoUpdate: String? = null,
        checksSettingsFile: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                "check-name" to checkName,
                autoEnableNewChecks?.let { "auto-enable-new-checks" to it },
                autoUpdate?.let { "auto-update" to it },
                checksSettingsFile?.let { "checks-settings-file" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("check", "copy"),
            args = argsList,
        )
    }

    /**
     * [check create](https://docs.liquibase.com/commands/quality-checks/subcommands/create.html)
     */
    fun checkCreate(
        licenseKey: String,
        packageContents: String,
        packageName: String,
        packageFile: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                "package-contents" to packageContents,
                "package-name" to packageName,
                packageFile?.let { "package-file" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("check", "create"),
            args = argsList,
        )
    }

    /**
     * [check customize](https://docs.liquibase.com/commands/quality-checks/subcommands/customize.html)
     */
    fun checkCustomize(
        licenseKey: String,
        checkName: String,
        autoEnableNewChecks: Boolean? = null,
        autoUpdate: String? = null,
        checksSettingsFile: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                "check-name" to checkName,
                autoEnableNewChecks?.let { "auto-enable-new-checks" to it },
                autoUpdate?.let { "auto-update" to it },
                checksSettingsFile?.let { "checks-settings-file" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("check", "customize"),
            args = argsList,
        )
    }

    /**
     * [check delete](https://docs.liquibase.com/commands/quality-checks/subcommands/delete.html)
     */
    fun checkDelete(
        licenseKey: String,
        checkName: String,
        autoEnableNewChecks: Boolean? = null,
        autoUpdate: String? = null,
        checksSettingsFile: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                "check-name" to checkName,
                autoEnableNewChecks?.let { "auto-enable-new-checks" to it },
                autoUpdate?.let { "auto-update" to it },
                checksSettingsFile?.let { "checks-settings-file" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("check", "delete"),
            args = argsList,
        )
    }

    /**
     * [check disable](https://docs.liquibase.com/commands/quality-checks/subcommands/disable.html)
     */
    fun checkDisable(
        licenseKey: String,
        checkName: String,
        autoEnableNewChecks: Boolean? = null,
        autoUpdate: String? = null,
        checksSettingsFile: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                "check-name" to checkName,
                autoEnableNewChecks?.let { "auto-enable-new-checks" to it },
                autoUpdate?.let { "auto-update" to it },
                checksSettingsFile?.let { "checks-settings-file" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("check", "disable"),
            args = argsList,
        )
    }

    /**
     * [check enable](https://docs.liquibase.com/commands/quality-checks/subcommands/enable.html)
     */
    fun checkEnable(
        licenseKey: String,
        checkName: String,
        autoEnableNewChecks: Boolean? = null,
        autoUpdate: String? = null,
        checksSettingsFile: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                "check-name" to checkName,
                autoEnableNewChecks?.let { "auto-enable-new-checks" to it },
                autoUpdate?.let { "auto-update" to it },
                checksSettingsFile?.let { "checks-settings-file" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("check", "enable"),
            args = argsList,
        )
    }

    /**
     * [check reset](https://docs.liquibase.com/commands/quality-checks/subcommands/reset.html)
     */
    fun checkReset(
        licenseKey: String,
        checkName: String,
        autoEnableNewChecks: Boolean? = null,
        autoUpdate: String? = null,
        checksSettingsFile: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                "check-name" to checkName,
                autoEnableNewChecks?.let { "auto-enable-new-checks" to it },
                autoUpdate?.let { "auto-update" to it },
                checksSettingsFile?.let { "checks-settings-file" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("check", "reset"),
            args = argsList,
        )
    }

    /**
     * [check run](https://docs.liquibase.com/commands/quality-checks/subcommands/run.html)
     */
    fun checkRun(
        licenseKey: String,
        changelogFile: String? = null,
        url: String? = null,
        autoEnableNewChecks: Boolean? = null,
        autoUpdate: String? = null,
        cacheChangelogFileContents: Boolean? = null,
        changesetFilter: String? = null,
        checkName: String? = null,
        checkRollbacks: Boolean? = null,
        checksOutput: String? = null,
        checksPackages: String? = null,
        checksScope: String? = null,
        checksScriptsEnabled: Boolean? = null,
        checksScriptsPath: String? = null,
        checksSettingsFile: String? = null,
        contextFilter: String? = null,
        defaultCatalogName: String? = null,
        defaultSchemaName: String? = null,
        driver: String? = null,
        driverPropertiesFile: String? = null,
        format: String? = null,
        labelFilter: String? = null,
        password: String? = null,
        propertySubstitutionEnabled: Boolean? = null,
        reportEnabled: Boolean? = null,
        reportName: String? = null,
        reportPath: String? = null,
        schemas: String? = null,
        sqlParserFailSeverity: String? = null,
        username: String? = null,
        verbose: Boolean? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                changelogFile?.let { "changelog-file" to it },
                url?.let { "url" to it },
                autoEnableNewChecks?.let { "auto-enable-new-checks" to it },
                autoUpdate?.let { "auto-update" to it },
                cacheChangelogFileContents?.let { "cache-changelog-file-contents" to it },
                changesetFilter?.let { "changeset-filter" to it },
                checkName?.let { "check-name" to it },
                checkRollbacks?.let { "check-rollbacks" to it },
                checksOutput?.let { "checks-output" to it },
                checksPackages?.let { "checks-packages" to it },
                checksScope?.let { "checks-scope" to it },
                checksScriptsEnabled?.let { "checks-scripts-enabled" to it },
                checksScriptsPath?.let { "checks-scripts-path" to it },
                checksSettingsFile?.let { "checks-settings-file" to it },
                contextFilter?.let { "context-filter" to it },
                defaultCatalogName?.let { "default-catalog-name" to it },
                defaultSchemaName?.let { "default-schema-name" to it },
                driver?.let { "driver" to it },
                driverPropertiesFile?.let { "driver-properties-file" to it },
                format?.let { "format" to it },
                labelFilter?.let { "label-filter" to it },
                password?.let { "password" to it },
                propertySubstitutionEnabled?.let { "property-substitution-enabled" to it },
                reportEnabled?.let { "report-enabled" to it },
                reportName?.let { "report-name" to it },
                reportPath?.let { "report-path" to it },
                schemas?.let { "schemas" to it },
                sqlParserFailSeverity?.let { "sql-parser-fail-severity" to it },
                username?.let { "username" to it },
                verbose?.let { "verbose" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("check", "run"),
            args = argsList,
        )
    }

    /**
     * [check show](https://docs.liquibase.com/commands/quality-checks/subcommands/show.html)
     */
    fun checkShow(
        licenseKey: String,
        autoEnableNewChecks: Boolean? = null,
        autoUpdate: String? = null,
        checkName: String? = null,
        checkStatus: String? = null,
        checksPackages: String? = null,
        checksSettingsFile: String? = null,
        showCols: String? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                autoEnableNewChecks?.let { "auto-enable-new-checks" to it },
                autoUpdate?.let { "auto-update" to it },
                checkName?.let { "check-name" to it },
                checkStatus?.let { "check-status" to it },
                checksPackages?.let { "checks-packages" to it },
                checksSettingsFile?.let { "checks-settings-file" to it },
                showCols?.let { "show-cols" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("check", "show"),
            args = argsList,
        )
    }

    // Flow

    /**
     * [flow](https://docs.liquibase.com/commands/flow/flow.html)
     */
    fun flow(
        licenseKey: String,
        flowFile: String? = null,
        flowFileStrictParsing: Boolean? = null,
        flowShellInterpreter: String? = null,
        flowShellKeepTempFiles: Boolean? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                flowFile?.let { "flow-file" to it },
                flowFileStrictParsing?.let { "flow-file-strict-parsing" to it },
                flowShellInterpreter?.let { "flow-shell-interpreter" to it },
                flowShellKeepTempFiles?.let { "flow-shell-keep-temp-files" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("flow"),
            args = argsList,
        )
    }

    /**
     * [flow validate](https://docs.liquibase.com/commands/flow/flow-validate.html)
     */
    fun flowValidate(
        licenseKey: String,
        flowFile: String? = null,
        flowFileStrictParsing: Boolean? = null,
        flowShellInterpreter: String? = null,
        flowShellKeepTempFiles: Boolean? = null,
    ) {
        val argsList = listOf(
            globalArgs,
            listOfNotNullStringToString(
                "license-key" to licenseKey,
                flowFile?.let { "flow-file" to it },
                flowFileStrictParsing?.let { "flow-file-strict-parsing" to it },
                flowShellInterpreter?.let { "flow-shell-interpreter" to it },
                flowShellKeepTempFiles?.let { "flow-shell-keep-temp-files" to it },
            ),
            commandArgs,
        ).flatten()
        executeCommand(
            command = listOf("flow", "validate"),
            args = argsList,
        )
    }

    private fun listOfNotNullStringToString(vararg keyValue: Pair<String, Any>?): List<Pair<String, String>> {
        return keyValue.filterNotNull().map { (k, v) -> k to v.toString() }
    }
}
