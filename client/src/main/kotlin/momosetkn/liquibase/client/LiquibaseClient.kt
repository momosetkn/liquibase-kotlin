@file:Suppress("ktlint:standard:no-consecutive-comments", "CyclomaticComplexMethod")

package momosetkn.liquibase.client

import momosetkn.liquibase.client.LiquibaseCommandExecutor.executeLiquibaseCommandLine
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
            listOfNotNull(
                "init",
                "copy",
                recursive?.let { "--recursive=$it" },
                source?.let { "--source=$it" },
                target?.let { "--target=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "init",
                "project",
                changelogFile?.let { "--changelog-file=$it" },
                format?.let { "--format=$it" },
                keepTempFiles?.let { "--keep-temp-files=$it" },
                password?.let { "--password=$it" },
                projectDefaultsFile?.let { "--project-defaults-file=$it" },
                projectDir?.let { "--project-dir=$it" },
                projectGuide?.let { "--project-guide=$it" },
                url?.let { "--url=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "init",
                "start-h2",
                bindAddress?.let { "--bind-address=$it" },
                dbPort?.let { "--db-port=$it" },
                detached?.let { "--detached=$it" },
                launchBrowser?.let { "--launch-browser=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
                webPort?.let { "--web-port=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "update",
                liquibaseCatalogName?.let { "--liquibase-catalog-name=$it" },
                liquibaseSchemaName?.let { "--liquibase-schema-name=$it" },
                "--changelog-file=$changelogFile",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                forceOnPartialChanges?.let { "--force-on-partial-changes=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                reportEnabled?.let { "--report-enabled=$it" },
                reportName?.let { "--report-name=$it" },
                reportPath?.let { "--report-path=$it" },
                rollbackOnError?.let { "--rollback-on-error=$it" },
                showSummary?.let { "--show-summary=$it" },
                showSummaryOutput?.let { "--show-summary-output=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "update-sql",
                outputFile?.let { "--output-file=$it" },
                "--changelog-file=$changelogFile",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                outputDefaultCatalog?.let { "--output-default-catalog=$it" },
                outputDefaultSchema?.let { "--output-default-schema=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "update-count",
                "--changelog-file=$changelogFile",
                "--count=$count",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                forceOnPartialChanges?.let { "--force-on-partial-changes=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                reportEnabled?.let { "--report-enabled=$it" },
                reportName?.let { "--report-name=$it" },
                reportPath?.let { "--report-path=$it" },
                rollbackOnError?.let { "--rollback-on-error=$it" },
                showSummary?.let { "--show-summary=$it" },
                showSummaryOutput?.let { "--show-summary-output=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "update-count-sql",
                outputFile?.let { "--output-file=$it" },
                "--changelog-file=$changelogFile",
                "--count=$count",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                outputDefaultCatalog?.let { "--output-default-catalog=$it" },
                outputDefaultSchema?.let { "--output-default-schema=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "update-to-tag",
                "--changelog-file=$changelogFile",
                "--tag=$tag",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                forceOnPartialChanges?.let { "--force-on-partial-changes=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                reportEnabled?.let { "--report-enabled=$it" },
                reportName?.let { "--report-name=$it" },
                reportPath?.let { "--report-path=$it" },
                rollbackOnError?.let { "--rollback-on-error=$it" },
                showSummary?.let { "--show-summary=$it" },
                showSummaryOutput?.let { "--show-summary-output=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "update-to-tag-sql",
                outputFile?.let { "--output-file=$it" },
                "--changelog-file=$changelogFile",
                "--tag=$tag",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                outputDefaultCatalog?.let { "--output-default-catalog=$it" },
                outputDefaultSchema?.let { "--output-default-schema=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "update-testing-rollback",
                "--changelog-file=$changelogFile",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                forceOnPartialChanges?.let { "--force-on-partial-changes=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                reportEnabled?.let { "--report-enabled=$it" },
                reportName?.let { "--report-name=$it" },
                reportPath?.let { "--report-path=$it" },
                rollbackOnError?.let { "--rollback-on-error=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "update-one-changeset",
                "--license-key=$licenseKey",
                "--changelog-file=$changelogFile",
                "--changeset-author=$changesetAuthor",
                "--changeset-id=$changesetId",
                "--changeset-path=$changesetPath",
                "--force=$force",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                forceOnPartialChanges?.let { "--force-on-partial-changes=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                reportEnabled?.let { "--report-enabled=$it" },
                reportName?.let { "--report-name=$it" },
                reportPath?.let { "--report-path=$it" },
                rollbackOnError?.let { "--rollback-on-error=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "update-one-changeset-sql",
                "--license-key=$licenseKey",
                outputFile?.let { "--output-file=$it" },
                "--changelog-file=$changelogFile",
                "--changeset-author=$changesetAuthor",
                "--changeset-id=$changesetId",
                "--changeset-path=$changesetPath",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "rollback",
                "--changelog-file=$changelogFile",
                "--tag=$tag",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                reportEnabled?.let { "--report-enabled=$it" },
                reportName?.let { "--report-name=$it" },
                reportPath?.let { "--report-path=$it" },
                rollbackScript?.let { "--rollback-script=$it" },
                tagVersion?.let { "--tag-version=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "rollback-sql",
                outputFile?.let { "--output-file=$it" },
                "--changelog-file=$changelogFile",
                "--tag=$tag",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                outputDefaultCatalog?.let { "--output-default-catalog=$it" },
                outputDefaultSchema?.let { "--output-default-schema=$it" },
                password?.let { "--password=$it" },
                rollbackScript?.let { "--rollback-script=$it" },
                tagVersion?.let { "--tag-version=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "rollback-to-date",
                "--changelog-file=$changelogFile",
                "--date=${LiquibaseClientConfig.dateTimeFormatter.format(date)}",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                reportEnabled?.let { "--report-enabled=$it" },
                reportName?.let { "--report-name=$it" },
                reportPath?.let { "--report-path=$it" },
                rollbackScript?.let { "--rollback-script=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "rollback-to-date-sql",
                outputFile?.let { "--output-file=$it" },
                "--changelog-file=$changelogFile",
                "--date=${LiquibaseClientConfig.dateTimeFormatter.format(date)}",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                outputDefaultCatalog?.let { "--output-default-catalog=$it" },
                outputDefaultSchema?.let { "--output-default-schema=$it" },
                password?.let { "--password=$it" },
                rollbackScript?.let { "--rollback-script=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "rollback-count",
                "--changelog-file=$changelogFile",
                "--count=$count",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                reportEnabled?.let { "--report-enabled=$it" },
                reportName?.let { "--report-name=$it" },
                reportPath?.let { "--report-path=$it" },
                rollbackScript?.let { "--rollback-script=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "rollback-count-sql",
                outputFile?.let { "--output-file=$it" },
                "--changelog-file=$changelogFile",
                "--count=$count",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                outputDefaultCatalog?.let { "--output-default-catalog=$it" },
                outputDefaultSchema?.let { "--output-default-schema=$it" },
                password?.let { "--password=$it" },
                rollbackScript?.let { "--rollback-script=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "future-rollback-sql",
                outputFile?.let { "--output-file=$it" },
                "--changelog-file=$changelogFile",
                "--url=$url",
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                outputDefaultCatalog?.let { "--output-default-catalog=$it" },
                outputDefaultSchema?.let { "--output-default-schema=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "future-rollback-from-tag-sql",
                outputFile?.let { "--output-file=$it" },
                "--changelog-file=$changelogFile",
                "--tag=$tag",
                "--url=$url",
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                outputDefaultCatalog?.let { "--output-default-catalog=$it" },
                outputDefaultSchema?.let { "--output-default-schema=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "future-rollback-count-sql",
                outputFile?.let { "--output-file=$it" },
                "--changelog-file=$changelogFile",
                "--count=$count",
                "--url=$url",
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                outputDefaultCatalog?.let { "--output-default-catalog=$it" },
                outputDefaultSchema?.let { "--output-default-schema=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "rollback-one-changeset",
                "--license-key=$licenseKey",
                "--changelog-file=$changelogFile",
                "--changeset-author=$changesetAuthor",
                "--changeset-id=$changesetId",
                "--changeset-path=$changesetPath",
                "--force=$force",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                password?.let { "--password=$it" },
                reportEnabled?.let { "--report-enabled=$it" },
                reportName?.let { "--report-name=$it" },
                reportPath?.let { "--report-path=$it" },
                rollbackScript?.let { "--rollback-script=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "rollback-one-changeset-sql",
                "--license-key=$licenseKey",
                outputFile?.let { "--output-file=$it" },
                "--changelog-file=$changelogFile",
                "--changeset-author=$changesetAuthor",
                "--changeset-id=$changesetId",
                "--changeset-path=$changesetPath",
                "--url=$url",
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                outputDefaultCatalog?.let { "--output-default-catalog=$it" },
                outputDefaultSchema?.let { "--output-default-schema=$it" },
                password?.let { "--password=$it" },
                rollbackScript?.let { "--rollback-script=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "rollback-one-update",
                "--license-key=$licenseKey",
                "--changelog-file=$changelogFile",
                "--force=$force",
                "--url=$url",
                changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
                changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                deploymentId?.let { "--deployment-id=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                password?.let { "--password=$it" },
                reportEnabled?.let { "--report-enabled=$it" },
                reportName?.let { "--report-name=$it" },
                reportPath?.let { "--report-path=$it" },
                rollbackScript?.let { "--rollback-script=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "rollback-one-update-sql",
                "--license-key=$licenseKey",
                outputFile?.let { "--output-file=$it" },
                "--changelog-file=$changelogFile",
                "--url=$url",
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                deploymentId?.let { "--deployment-id=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                outputDefaultCatalog?.let { "--output-default-catalog=$it" },
                outputDefaultSchema?.let { "--output-default-schema=$it" },
                password?.let { "--password=$it" },
                rollbackScript?.let { "--rollback-script=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "diff",
                "--reference-url=$referenceUrl",
                "--url=$url",
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                diffTypes?.let { "--diff-types=$it" },
                driftSeverity?.let { "--drift-severity=$it" },
                driftSeverityChanged?.let { "--drift-severity-changed=$it" },
                driftSeverityMissing?.let { "--drift-severity-missing=$it" },
                driftSeverityUnexpected?.let { "--drift-severity-unexpected=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                excludeObjects?.let { "--exclude-objects=$it" },
                format?.let { "--format=$it" },
                includeObjects?.let { "--include-objects=$it" },
                reportOpen?.let { "--report-open=$it" },
                outputSchemas?.let { "--output-schemas=$it" },
                password?.let { "--password=$it" },
                referenceDefaultCatalogName?.let { "--reference-default-catalog-name=$it" },
                referenceDefaultSchemaName?.let { "--reference-default-schema-name=$it" },
                referenceDriver?.let { "--reference-driver=$it" },
                referenceDriverPropertiesFile?.let { "--reference-driver-properties-file=$it" },
                referenceLiquibaseCatalogName?.let { "--reference-liquibase-catalog-name=$it" },
                referenceLiquibaseSchemaName?.let { "--reference-liquibase-schema-name=$it" },
                referencePassword?.let { "--reference-password=$it" },
                referenceSchemas?.let { "--reference-schemas=$it" },
                referenceUsername?.let { "--reference-username=$it" },
                reportEnabled?.let { "--report-enabled=$it" },
                reportName?.let { "--report-name=$it" },
                reportPath?.let { "--report-path=$it" },
                schemas?.let { "--schemas=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "diff-changelog",
                "--changelog-file=$changelogFile",
                "--reference-url=$referenceUrl",
                "--url=$url",
                author?.let { "--author=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                diffTypes?.let { "--diff-types=$it" },
                driftSeverity?.let { "--drift-severity=$it" },
                driftSeverityChanged?.let { "--drift-severity-changed=$it" },
                driftSeverityMissing?.let { "--drift-severity-missing=$it" },
                driftSeverityUnexpected?.let { "--drift-severity-unexpected=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                excludeObjects?.let { "--exclude-objects=$it" },
                includeCatalog?.let { "--include-catalog=$it" },
                includeObjects?.let { "--include-objects=$it" },
                includeSchema?.let { "--include-schema=$it" },
                includeTablespace?.let { "--include-tablespace=$it" },
                labelFilter?.let { "--label-filter=$it" },
                reportOpen?.let { "--report-open=$it" },
                outputSchemas?.let { "--output-schemas=$it" },
                password?.let { "--password=$it" },
                referenceDefaultCatalogName?.let { "--reference-default-catalog-name=$it" },
                referenceDefaultSchemaName?.let { "--reference-default-schema-name=$it" },
                referenceDriver?.let { "--reference-driver=$it" },
                referenceDriverPropertiesFile?.let { "--reference-driver-properties-file=$it" },
                referenceLiquibaseCatalogName?.let { "--reference-liquibase-catalog-name=$it" },
                referenceLiquibaseSchemaName?.let { "--reference-liquibase-schema-name=$it" },
                referencePassword?.let { "--reference-password=$it" },
                referenceSchemas?.let { "--reference-schemas=$it" },
                referenceUsername?.let { "--reference-username=$it" },
                replaceIfExistsTypes?.let { "--replace-if-exists-types=$it" },
                reportEnabled?.let { "--report-enabled=$it" },
                reportName?.let { "--report-name=$it" },
                reportPath?.let { "--report-path=$it" },
                runOnChangeTypes?.let { "--run-on-change-types=$it" },
                schemas?.let { "--schemas=$it" },
                skipObjectSorting?.let { "--skip-object-sorting=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "snapshot",
                outputFile?.let { "--output-file=$it" },
                "--url=$url",
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                password?.let { "--password=$it" },
                schemas?.let { "--schemas=$it" },
                snapshotFilters?.let { "--snapshot-filters=$it" },
                snapshotFormat?.let { "--snapshot-format=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "snapshot-reference",
                outputFile?.let { "--output-file=$it" },
                "--reference-url=$referenceUrl",
                referenceDefaultCatalogName?.let { "--reference-default-catalog-name=$it" },
                referenceDefaultSchemaName?.let { "--reference-default-schema-name=$it" },
                referenceDriver?.let { "--reference-driver=$it" },
                referenceDriverPropertiesFile?.let { "--reference-driver-properties-file=$it" },
                referenceLiquibaseCatalogName?.let { "--reference-liquibase-catalog-name=$it" },
                referenceLiquibaseSchemaName?.let { "--reference-liquibase-schema-name=$it" },
                referencePassword?.let { "--reference-password=$it" },
                referenceUsername?.let { "--reference-username=$it" },
                snapshotFilters?.let { "--snapshot-filters=$it" },
                snapshotFormat?.let { "--snapshot-format=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "generate-changelog",
                licenseKey?.let { "--license-key=$it" },
                "--url=$url",
                author?.let { "--author=$it" },
                changelogFile?.let { "--changelog-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                dataOutputDirectory?.let { "--data-output-directory=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                diffTypes?.let { "--diff-types=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                excludeObjects?.let { "--exclude-objects=$it" },
                includeCatalog?.let { "--include-catalog=$it" },
                includeObjects?.let { "--include-objects=$it" },
                includeSchema?.let { "--include-schema=$it" },
                includeTablespace?.let { "--include-tablespace=$it" },
                labelFilter?.let { "--label-filter=$it" },
                outputSchemas?.let { "--output-schemas=$it" },
                overwriteOutputFile?.let { "--overwrite-output-file=$it" },
                password?.let { "--password=$it" },
                replaceIfExistsTypes?.let { "--replace-if-exists-types=$it" },
                runOnChangeTypes?.let { "--run-on-change-types=$it" },
                schemas?.let { "--schemas=$it" },
                skipObjectSorting?.let { "--skip-object-sorting=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "diff",
                "JSON",
                "--reference-url=$referenceUrl",
                "--url=$url",
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                diffTypes?.let { "--diff-types=$it" },
                driftSeverity?.let { "--drift-severity=$it" },
                driftSeverityChanged?.let { "--drift-severity-changed=$it" },
                driftSeverityMissing?.let { "--drift-severity-missing=$it" },
                driftSeverityUnexpected?.let { "--drift-severity-unexpected=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                excludeObjects?.let { "--exclude-objects=$it" },
                format?.let { "--format=$it" },
                includeObjects?.let { "--include-objects=$it" },
                reportOpen?.let { "--report-open=$it" },
                outputSchemas?.let { "--output-schemas=$it" },
                password?.let { "--password=$it" },
                referenceDefaultCatalogName?.let { "--reference-default-catalog-name=$it" },
                referenceDefaultSchemaName?.let { "--reference-default-schema-name=$it" },
                referenceDriver?.let { "--reference-driver=$it" },
                referenceDriverPropertiesFile?.let { "--reference-driver-properties-file=$it" },
                referenceLiquibaseCatalogName?.let { "--reference-liquibase-catalog-name=$it" },
                referenceLiquibaseSchemaName?.let { "--reference-liquibase-schema-name=$it" },
                referencePassword?.let { "--reference-password=$it" },
                referenceSchemas?.let { "--reference-schemas=$it" },
                referenceUsername?.let { "--reference-username=$it" },
                reportEnabled?.let { "--report-enabled=$it" },
                reportName?.let { "--report-name=$it" },
                reportPath?.let { "--report-path=$it" },
                schemas?.let { "--schemas=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "history",
                outputFile?.let { "--output-file=$it" },
                "--url=$url",
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                format?.let { "--format=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "status",
                "--changelog-file=$changelogFile",
                "--url=$url",
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
                verbose?.let { "--verbose=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "unexpected-changesets",
                "--changelog-file=$changelogFile",
                "--url=$url",
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
                verbose?.let { "--verbose=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "connect",
                "--url=$url",
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "dbcl-history",
                "--license-key=$licenseKey",
                dbclhistoryCaptureExtensions?.let { "--dbclhistory-capture-extensions=$it" },
                dbclhistoryCaptureSql?.let { "--dbclhistory-capture-sql=$it" },
                dbclhistoryEnabled?.let { "--dbclhistory-enabled=$it" },
                dbclhistorySeverity?.let { "--dbclhistory-severity=$it" },
                outputFile?.let { "--output-file=$it" },
                "--url=$url",
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                format?.let { "--format=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
                verbose?.let { "--verbose=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "db-doc",
                "--changelog-file=$changelogFile",
                "--output-directory=$outputDirectory",
                "--url=$url",
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                schemas?.let { "--schemas=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "tag",
                "--tag=$tag",
                "--url=$url",
                addRow?.let { "--add-row=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "tag-exists",
                "--tag=$tag",
                "--url=$url",
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "validate",
                "--changelog-file=$changelogFile",
                "--url=$url",
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "calculate-checksum",
                "--changelog-file=$changelogFile",
                "--url=$url",
                changesetAuthor?.let { "--changeset-author=$it" },
                changesetId?.let { "--changeset-id=$it" },
                changesetIdentifier?.let { "--changeset-identifier=$it" },
                changesetPath?.let { "--changeset-path=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "clear-checksums",
                "--url=$url",
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "list-locks",
                "--url=$url",
                changelogFile?.let { "--changelog-file=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "release-locks",
                "--url=$url",
                changelogFile?.let { "--changelog-file=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "changelog-sync",
                "--changelog-file=$changelogFile",
                "--url=$url",
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "changelog-sync-sql",
                outputFile?.let { "--output-file=$it" },
                "--changelog-file=$changelogFile",
                "--url=$url",
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                outputDefaultCatalog?.let { "--output-default-catalog=$it" },
                outputDefaultSchema?.let { "--output-default-schema=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "changelog-sync-to-tag",
                "--changelog-file=$changelogFile",
                "--tag=$tag",
                "--url=$url",
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "changelog-sync-to-tag-sql",
                outputFile?.let { "--output-file=$it" },
                "--changelog-file=$changelogFile",
                "--tag=$tag",
                "--url=$url",
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                outputDefaultCatalog?.let { "--output-default-catalog=$it" },
                outputDefaultSchema?.let { "--output-default-schema=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "mark-next-changeset-ran",
                "--changelog-file=$changelogFile",
                "--url=$url",
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "mark-next-changeset-ran-sql",
                outputFile?.let { "--output-file=$it" },
                "--changelog-file=$changelogFile",
                "--url=$url",
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "drop-all",
                "--force=$force",
                "--url=$url",
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                dropDbclhistory?.let { "--drop-dbclhistory=$it" },
                password?.let { "--password=$it" },
                requireForce?.let { "--require-force=$it" },
                schemas?.let { "--schemas=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "execute-sql",
                outputFile?.let { "--output-file=$it" },
                "--sql=$sql",
                "--sql-file=$sqlFile",
                "--url=$url",
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                delimiter?.let { "--delimiter=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                password?.let { "--password=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "set-contexts",
                "--license-key=$licenseKey",
                "--changelog-file=$changelogFile",
                "--set-as=$setAs",
                changesetAuthor?.let { "--changeset-author=$it" },
                changesetId?.let { "--changeset-id=$it" },
                changesetPath?.let { "--changeset-path=$it" },
                contextFilter?.let { "--context-filter=$it" },
                dbms?.let { "--dbms=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                forceReplace?.let { "--force-replace=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                url?.let { "--url=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "set-labels",
                "--license-key=$licenseKey",
                "--changelog-file=$changelogFile",
                "--set-as=$setAs",
                changesetAuthor?.let { "--changeset-author=$it" },
                changesetId?.let { "--changeset-id=$it" },
                changesetPath?.let { "--changeset-path=$it" },
                contextFilter?.let { "--context-filter=$it" },
                dbms?.let { "--dbms=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                forceReplace?.let { "--force-replace=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                url?.let { "--url=$it" },
                username?.let { "--username=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "check",
                "bulk-set",
                "--license-key=$licenseKey",
                disable?.let { "--disable=$it" },
                enable?.let { "--enable=$it" },
                autoEnableNewChecks?.let { "--auto-enable-new-checks=$it" },
                autoUpdate?.let { "--auto-update=$it" },
                checkName?.let { "--check-name=$it" },
                checksSettingsFile?.let { "--checks-settings-file=$it" },
                force?.let { "--force=$it" },
                severity?.let { "--severity=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "check",
                "copy",
                "--license-key=$licenseKey",
                "--check-name=$checkName",
                autoEnableNewChecks?.let { "--auto-enable-new-checks=$it" },
                autoUpdate?.let { "--auto-update=$it" },
                checksSettingsFile?.let { "--checks-settings-file=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "check",
                "create",
                "--license-key=$licenseKey",
                "--package-contents=$packageContents",
                "--package-name=$packageName",
                packageFile?.let { "--package-file=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "check",
                "customize",
                "--license-key=$licenseKey",
                "--check-name=$checkName",
                autoEnableNewChecks?.let { "--auto-enable-new-checks=$it" },
                autoUpdate?.let { "--auto-update=$it" },
                checksSettingsFile?.let { "--checks-settings-file=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "check",
                "delete",
                "--license-key=$licenseKey",
                "--check-name=$checkName",
                autoEnableNewChecks?.let { "--auto-enable-new-checks=$it" },
                autoUpdate?.let { "--auto-update=$it" },
                checksSettingsFile?.let { "--checks-settings-file=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "check",
                "disable",
                "--license-key=$licenseKey",
                "--check-name=$checkName",
                autoEnableNewChecks?.let { "--auto-enable-new-checks=$it" },
                autoUpdate?.let { "--auto-update=$it" },
                checksSettingsFile?.let { "--checks-settings-file=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "check",
                "enable",
                "--license-key=$licenseKey",
                "--check-name=$checkName",
                autoEnableNewChecks?.let { "--auto-enable-new-checks=$it" },
                autoUpdate?.let { "--auto-update=$it" },
                checksSettingsFile?.let { "--checks-settings-file=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "check",
                "reset",
                "--license-key=$licenseKey",
                "--check-name=$checkName",
                autoEnableNewChecks?.let { "--auto-enable-new-checks=$it" },
                autoUpdate?.let { "--auto-update=$it" },
                checksSettingsFile?.let { "--checks-settings-file=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "check",
                "run",
                "--license-key=$licenseKey",
                changelogFile?.let { "--changelog-file=$it" },
                url?.let { "--url=$it" },
                autoEnableNewChecks?.let { "--auto-enable-new-checks=$it" },
                autoUpdate?.let { "--auto-update=$it" },
                cacheChangelogFileContents?.let { "--cache-changelog-file-contents=$it" },
                changesetFilter?.let { "--changeset-filter=$it" },
                checkName?.let { "--check-name=$it" },
                checkRollbacks?.let { "--check-rollbacks=$it" },
                checksOutput?.let { "--checks-output=$it" },
                checksPackages?.let { "--checks-packages=$it" },
                checksScope?.let { "--checks-scope=$it" },
                checksScriptsEnabled?.let { "--checks-scripts-enabled=$it" },
                checksScriptsPath?.let { "--checks-scripts-path=$it" },
                checksSettingsFile?.let { "--checks-settings-file=$it" },
                contextFilter?.let { "--context-filter=$it" },
                defaultCatalogName?.let { "--default-catalog-name=$it" },
                defaultSchemaName?.let { "--default-schema-name=$it" },
                driver?.let { "--driver=$it" },
                driverPropertiesFile?.let { "--driver-properties-file=$it" },
                format?.let { "--format=$it" },
                labelFilter?.let { "--label-filter=$it" },
                password?.let { "--password=$it" },
                propertySubstitutionEnabled?.let { "--property-substitution-enabled=$it" },
                reportEnabled?.let { "--report-enabled=$it" },
                reportName?.let { "--report-name=$it" },
                reportPath?.let { "--report-path=$it" },
                schemas?.let { "--schemas=$it" },
                sqlParserFailSeverity?.let { "--sql-parser-fail-severity=$it" },
                username?.let { "--username=$it" },
                verbose?.let { "--verbose=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "check",
                "show",
                "--license-key=$licenseKey",
                autoEnableNewChecks?.let { "--auto-enable-new-checks=$it" },
                autoUpdate?.let { "--auto-update=$it" },
                checkName?.let { "--check-name=$it" },
                checkStatus?.let { "--check-status=$it" },
                checksPackages?.let { "--checks-packages=$it" },
                checksSettingsFile?.let { "--checks-settings-file=$it" },
                showCols?.let { "--show-cols=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "flow",
                "--license-key=$licenseKey",
                flowFile?.let { "--flow-file=$it" },
                flowFileStrictParsing?.let { "--flow-file-strict-parsing=$it" },
                flowShellInterpreter?.let { "--flow-shell-interpreter=$it" },
                flowShellKeepTempFiles?.let { "--flow-shell-keep-temp-files=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
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
            listOfNotNull(
                "flow",
                "validate",
                "--license-key=$licenseKey",
                flowFile?.let { "--flow-file=$it" },
                flowFileStrictParsing?.let { "--flow-file-strict-parsing=$it" },
                flowShellInterpreter?.let { "--flow-shell-interpreter=$it" },
                flowShellKeepTempFiles?.let { "--flow-shell-keep-temp-files=$it" },
            ),
            commandArgs,
        ).flatten()
        executeLiquibaseCommandLine(argsList)
    }
}
