@file:Suppress("CyclomaticComplexMethod", "LongMethod")

package momosetkn.liquibase.command.client

import java.time.temporal.TemporalAccessor

sealed interface LiquibaseGlobalArgs {
    fun serialize(): List<Pair<String, String>>
}
sealed interface LiquibaseSystemEnvArgs {
    fun serialize(): List<Pair<String, String>>
}
sealed interface LiquibaseCommandArgs {
    fun serialize(): List<Pair<String, String>>
}

@ConfigureLiquibaseDslMarker
data class LiquibaseInfoArgs(
    var help: Boolean? = null,
    var version: Boolean? = null,
) : LiquibaseGlobalArgs {
    override fun serialize(): List<Pair<String, String>> {
        return listOfNotNull(
            help?.let { "h" to it },
            version?.let { "v" to it },
        ).map { (k, v) -> k to v.toString() }
    }
}

@ConfigureLiquibaseDslMarker
data class LiquibaseGeneralGlobalArgs(
    var allowDuplicatedChangesetIdentifiers: Boolean? = null,
    var alwaysDropInsteadOfReplace: Boolean? = null,
    var alwaysOverrideStoredLogicSchema: String? = null,
    var autoReorg: Boolean? = null,
    var changelogLockPollRate: Int? = null,
    var changelogLockWaitTimeInMinutes: Int? = null,
    var changelogParseMode: String? = null,
    var classpath: String? = null,
    var convertDataTypes: Boolean? = null,
    var databaseChangelogLockTableName: String? = null,
    var databaseChangelogTableName: String? = null,
    var databaseClass: String? = null,
    var ddlLockTimeout: Int? = null,
    var defaultsFile: String? = null,
    var diffColumnOrder: Boolean? = null,
    var driver: String? = null,
    var driverPropertiesFile: String? = null,
    var duplicateFileMode: String? = null,
    var errorOnCircularIncludeAll: Boolean? = null,
    var fileEncoding: String? = null,
    var generateChangesetCreatedvarues: Boolean? = null,
    var generatedChangesetIdsContainsDescription: String? = null,
    var headless: String? = null,
    var includeCatalogInSpecification: Boolean? = null,
    var includeRelationsForComputedColumns: Boolean? = null,
    var includeSystemClasspath: Boolean? = null,
    var licenseKey: String? = null,
    var liquibaseCatalogName: String? = null,
    var liquibaseSchemaName: String? = null,
    var liquibaseTablespaceName: String? = null,
    var logChannels: String? = null,
    var logFile: String? = null,
    var logFormat: String? = null,
    var logLevel: String? = null,
    var mirrorConsoleMessagesToLog: Boolean? = null,
    var missingPropertyMode: String? = null,
    var monitorPerformance: Boolean? = null,
    var onMissingIncludeChangelog: String? = null,
    var outputFile: String? = null,
    var outputFileEncoding: String? = null,
    var outputLineSeparator: String? = null,
    var preserveSchemaCase: Boolean? = null,
    var proGlobalEndDelimiter: String? = null,
    var proGlobalEndDelimiterPrioritized: Boolean? = null,
    var proMarkUnusedNotDrop: Boolean? = null,
    var proSqlInline: Boolean? = null,
    var proStrict: Boolean? = null,
    var proSynonymsDropPublic: Boolean? = null,
    var promptForNonLocalDatabase: Boolean? = null,
    var propertyProviderClass: String? = null,
    var searchPath: String? = null,
    var secureParsing: Boolean? = null,
    var shouldRun: Boolean? = null,
    var shouldSnapshotData: Boolean? = null,
    var showBanner: Boolean? = null,
    var sqlLogLevel: String? = null,
    var sqlShowSqlWarnings: Boolean? = null,
    var strict: Boolean? = null,
    var supportPropertyEscaping: Boolean? = null,
    var supportsMethodvaridationLevel: String? = null,
    var trimLoadDataFileHeader: Boolean? = null,
    var uiService: String? = null,
    var useProcedureSchema: Boolean? = null,
    var validateXmlChangelogFiles: Boolean? = null,
) : LiquibaseGlobalArgs {
    override fun serialize(): List<Pair<String, String>> {
        return listOfNotNull(
            allowDuplicatedChangesetIdentifiers?.let { "allow-duplicated-changeset-identifiers" to it },
            alwaysDropInsteadOfReplace?.let { "always-drop-instead-of-replace" to it },
            alwaysOverrideStoredLogicSchema?.let { "always-override-stored-logic-schema" to it },
            autoReorg?.let { "auto-reorg" to it },
            changelogLockPollRate?.let { "changelog-lock-poll-rate" to it },
            changelogLockWaitTimeInMinutes?.let { "changelog-lock-wait-time-in-minutes" to it },
            changelogParseMode?.let { "changelog-parse-mode" to it },
            classpath?.let { "classpath" to it },
            convertDataTypes?.let { "convert-data-types" to it },
            databaseChangelogLockTableName?.let { "database-changelog-lock-table-name" to it },
            databaseChangelogTableName?.let { "database-changelog-table-name" to it },
            databaseClass?.let { "database-class" to it },
            ddlLockTimeout?.let { "ddl-lock-timeout" to it },
            defaultsFile?.let { "defaults-file" to it },
            diffColumnOrder?.let { "diff-column-order" to it },
            driver?.let { "driver" to it },
            driverPropertiesFile?.let { "driver-properties-file" to it },
            duplicateFileMode?.let { "duplicate-file-mode" to it },
            errorOnCircularIncludeAll?.let { "error-on-circular-include-all" to it },
            fileEncoding?.let { "file-encoding" to it },
            generateChangesetCreatedvarues?.let { "generate-changeset-created-varues" to it },
            generatedChangesetIdsContainsDescription?.let { "generated-changeset-ids-contains-description" to it },
            headless?.let { "headless" to it },
            includeCatalogInSpecification?.let { "include-catalog-in-specification" to it },
            includeRelationsForComputedColumns?.let { "include-relations-for-computed-columns" to it },
            includeSystemClasspath?.let { "include-system-classpath" to it },
            licenseKey?.let { "license-key" to it },
            liquibaseCatalogName?.let { "liquibase-catalog-name" to it },
            liquibaseSchemaName?.let { "liquibase-schema-name" to it },
            liquibaseTablespaceName?.let { "liquibase-tablespace-name" to it },
            logChannels?.let { "log-channels" to it },
            logFile?.let { "log-file" to it },
            logFormat?.let { "log-format" to it },
            logLevel?.let { "log-level" to it },
            mirrorConsoleMessagesToLog?.let { "mirror-console-messages-to-log" to it },
            missingPropertyMode?.let { "missing-property-mode" to it },
            monitorPerformance?.let { "monitor-performance" to it },
            onMissingIncludeChangelog?.let { "on-missing-include-changelog" to it },
            outputFile?.let { "output-file" to it },
            outputFileEncoding?.let { "output-file-encoding" to it },
            outputLineSeparator?.let { "output-line-separator" to it },
            preserveSchemaCase?.let { "preserve-schema-case" to it },
            proGlobalEndDelimiter?.let { "pro-global-end-delimiter" to it },
            proGlobalEndDelimiterPrioritized?.let { "pro-global-end-delimiter-prioritized" to it },
            proMarkUnusedNotDrop?.let { "pro-mark-unused-not-drop" to it },
            proSqlInline?.let { "pro-sql-inline" to it },
            proStrict?.let { "pro-strict" to it },
            proSynonymsDropPublic?.let { "pro-synonyms-drop-public" to it },
            promptForNonLocalDatabase?.let { "prompt-for-non-local-database" to it },
            propertyProviderClass?.let { "property-provider-class" to it },
            searchPath?.let { "search-path" to it },
            secureParsing?.let { "secure-parsing" to it },
            shouldRun?.let { "should-run" to it },
            shouldSnapshotData?.let { "should-snapshot-data" to it },
            showBanner?.let { "show-banner" to it },
            sqlLogLevel?.let { "sql-log-level" to it },
            sqlShowSqlWarnings?.let { "sql-show-sql-warnings" to it },
            strict?.let { "strict" to it },
            supportPropertyEscaping?.let { "support-property-escaping" to it },
            supportsMethodvaridationLevel?.let { "supports-method-varidation-level" to it },
            trimLoadDataFileHeader?.let { "trim-load-data-file-header" to it },
            uiService?.let { "ui-service" to it },
            useProcedureSchema?.let { "use-procedure-schema" to it },
            validateXmlChangelogFiles?.let { "validate-xml-changelog-files" to it },
        ).map { (k, v) -> k to v.toString() }
    }
}

@ConfigureLiquibaseDslMarker
data class LiquibaseDbclhistoryArgs(
    var dbclhistoryCaptureExtensions: Boolean? = null,
    var dbclhistoryCaptureSql: Boolean? = null,
    var dbclhistoryEnabled: Boolean? = null,
    var dbclhistorySeverity: String? = null,
) : LiquibaseGlobalArgs {
    override fun serialize(): List<Pair<String, String>> {
        return listOfNotNull(
            dbclhistoryCaptureExtensions?.let { "dbclhistory-capture-extensions" to it },
            dbclhistoryCaptureSql?.let { "dbclhistory-capture-sql" to it },
            dbclhistoryEnabled?.let { "dbclhistory-enabled" to it },
            dbclhistorySeverity?.let { "dbclhistory-severity" to it },
        ).map { (k, v) -> k to v.toString() }
    }
}

@ConfigureLiquibaseDslMarker
data class LiquibaseExecutorsArgs(
    var psqlArgs: String? = null,
    var psqlKeepTemp: Boolean? = null,
    var psqlKeepTempName: String? = null,
    var psqlKeepTempPath: String? = null,
    var psqlLogFile: String? = null,
    var psqlPath: String? = null,
    var psqlTimeout: Int? = null,
    var sqlcmdArgs: String? = null,
    var sqlcmdCatalogName: String? = null,
    var sqlcmdKeepTemp: Boolean? = null,
    var sqlcmdKeepTempName: String? = null,
    var sqlcmdKeepTempOverwrite: Boolean? = null,
    var sqlcmdKeepTempPath: String? = null,
    var sqlcmdLogFile: String? = null,
    var sqlcmdPath: String? = null,
    var sqlcmdTimeout: Int? = null,
    var sqlplusArgs: String? = null,
    var sqlplusCreateSpool: Boolean? = null,
    var sqlplusKeepTemp: Boolean? = null,
    var sqlplusKeepTempName: String? = null,
    var sqlplusKeepTempOverwrite: Boolean? = null,
    var sqlplusKeepTempPath: String? = null,
    var sqlplusPassword: String? = null,
    var sqlplusPath: String? = null,
    var sqlplusSqlerror: String? = null,
    var sqlplusTimeout: Int? = null,
    var sqlplusUsername: String? = null,
) : LiquibaseGlobalArgs {
    override fun serialize(): List<Pair<String, String>> {
        return listOfNotNull(
            psqlArgs?.let { "psql-args" to it },
            psqlKeepTemp?.let { "psql-keep-temp" to it },
            psqlKeepTempName?.let { "psql-keep-temp-name" to it },
            psqlKeepTempPath?.let { "psql-keep-temp-path" to it },
            psqlLogFile?.let { "psql-log-file" to it },
            psqlPath?.let { "psql-path" to it },
            psqlTimeout?.let { "psql-timeout" to it },
            sqlcmdArgs?.let { "sqlcmd-args" to it },
            sqlcmdCatalogName?.let { "sqlcmd-catalog-name" to it },
            sqlcmdKeepTemp?.let { "sqlcmd-keep-temp" to it },
            sqlcmdKeepTempName?.let { "sqlcmd-keep-temp-name" to it },
            sqlcmdKeepTempOverwrite?.let { "sqlcmd-keep-temp-overwrite" to it },
            sqlcmdKeepTempPath?.let { "sqlcmd-keep-temp-path" to it },
            sqlcmdLogFile?.let { "sqlcmd-log-file" to it },
            sqlcmdPath?.let { "sqlcmd-path" to it },
            sqlcmdTimeout?.let { "sqlcmd-timeout" to it },
            sqlplusArgs?.let { "sqlplus-args" to it },
            sqlplusCreateSpool?.let { "sqlplus-create-spool" to it },
            sqlplusKeepTemp?.let { "sqlplus-keep-temp" to it },
            sqlplusKeepTempName?.let { "sqlplus-keep-temp-name" to it },
            sqlplusKeepTempOverwrite?.let { "sqlplus-keep-temp-overwrite" to it },
            sqlplusKeepTempPath?.let { "sqlplus-keep-temp-path" to it },
            sqlplusPassword?.let { "sqlplus-password" to it },
            sqlplusPath?.let { "sqlplus-path" to it },
            sqlplusSqlerror?.let { "sqlplus-sqlerror" to it },
            sqlplusTimeout?.let { "sqlplus-timeout" to it },
            sqlplusUsername?.let { "sqlplus-username" to it },
        ).map { (k, v) -> k to v.toString() }
    }
}

@ConfigureLiquibaseDslMarker
data class LiquibaseCustomLoggingCommandArgs(
    var customLogDataFile: String? = null,
    var customLogDataFrequency: String? = null,
) : LiquibaseCommandArgs {
    override fun serialize(): List<Pair<String, String>> {
        return listOfNotNull(
            customLogDataFile?.let { "custom-log-data-file" to it },
            customLogDataFrequency?.let { "custom-log-data-frequency" to it },
        ).map { (k, v) -> k to v.toString() }
    }
}

@ConfigureLiquibaseDslMarker
data class LiquibaseExtensionsArgs(
    var dynamodbTrackingTablesBillingMode: String? = null,
    var dynamodbTrackingTablesProvisionedThroughputReadCapacityUnits: Int? = null,
    var dynamodbTrackingTablesProvisionedThroughputWriteCapacityUnits: Int? = null,
    var dynamodbWaitersEnabled: Boolean? = null,
    var dynamodbWaitersFailOnTimeout: Boolean? = null,
    var dynamodbWaitersLogNotificationEnabled: Boolean? = null,
    var dynamodbWaitersLogNotificationIntervar: Int? = null,
    var dynamodbWaiterCreateFixedDelayBackoffStrategyDuration: Int? = null,
    var dynamodbWaiterCreateMaxAttempts: Int? = null,
    var dynamodbWaiterCreateTotalTimeout: Int? = null,
    var dynamodbWaiterDeleteFixedDelayBackoffStrategyDuration: Int? = null,
    var dynamodbWaiterDeleteMaxAttempts: Int? = null,
    var dynamodbWaiterDeleteTotalTimeout: Int? = null,
    var dynamodbWaiterUpdateFixedDelayBackoffStrategyDuration: Int? = null,
    var dynamodbWaiterUpdateMaxAttempts: Int? = null,
    var dynamodbWaiterUpdateTotalTimeout: Int? = null,
) : LiquibaseGlobalArgs {
    override fun serialize(): List<Pair<String, String>> {
        return listOfNotNull(
            dynamodbTrackingTablesBillingMode?.let { "dynamodb-tracking-tables-billing-mode" to it },
            dynamodbTrackingTablesProvisionedThroughputReadCapacityUnits?.let {
                "dynamodb-tracking-tables-provisioned-throughput-read-capacity-units" to it
            },
            dynamodbTrackingTablesProvisionedThroughputWriteCapacityUnits?.let {
                "dynamodb-tracking-tables-provisioned-throughput-write-capacity-units" to it
            },
            dynamodbWaitersEnabled?.let { "dynamodb-waiters-enabled" to it },
            dynamodbWaitersFailOnTimeout?.let { "dynamodb-waiters-fail-on-timeout" to it },
            dynamodbWaitersLogNotificationEnabled?.let { "dynamodb-waiters-log-notification-enabled" to it },
            dynamodbWaitersLogNotificationIntervar?.let { "dynamodb-waiters-log-notification-intervar" to it },
            dynamodbWaiterCreateFixedDelayBackoffStrategyDuration?.let {
                "dynamodb-waiter-create-fixed-delay-backoff-strategy-duration" to it
            },
            dynamodbWaiterCreateMaxAttempts?.let { "dynamodb-waiter-create-max-attempts" to it },
            dynamodbWaiterCreateTotalTimeout?.let { "dynamodb-waiter-create-total-timeout" to it },
            dynamodbWaiterDeleteFixedDelayBackoffStrategyDuration?.let {
                "dynamodb-waiter-delete-fixed-delay-backoff-strategy-duration" to it
            },
            dynamodbWaiterDeleteMaxAttempts?.let { "dynamodb-waiter-delete-max-attempts" to it },
            dynamodbWaiterDeleteTotalTimeout?.let { "dynamodb-waiter-delete-total-timeout" to it },
            dynamodbWaiterUpdateFixedDelayBackoffStrategyDuration?.let {
                "dynamodb-waiter-update-fixed-delay-backoff-strategy-duration" to it
            },
            dynamodbWaiterUpdateMaxAttempts?.let { "dynamodb-waiter-update-max-attempts" to it },
            dynamodbWaiterUpdateTotalTimeout?.let { "dynamodb-waiter-update-total-timeout" to it },
        ).map { (k, v) -> k to v.toString() }
    }
}

@ConfigureLiquibaseDslMarker
data class LiquibaseMongoDBArgs(
    var adjustTrackingTablesOnStartup: Boolean? = null,
    var retryWrites: Boolean? = null,
    var supportsvaridator: Boolean? = null,
) : LiquibaseGlobalArgs {
    override fun serialize(): List<Pair<String, String>> {
        return listOfNotNull(
            adjustTrackingTablesOnStartup?.let { "adjust-tracking-tables-on-startup" to it },
            retryWrites?.let { "retry-writes" to it },
            supportsvaridator?.let { "supports-varidator" to it },
        ).map { (k, v) -> k to v.toString() }
    }
}

@ConfigureLiquibaseDslMarker
data class LiquibaseConfigurationArgs(
    var liquibaseHome: String? = null,
    var liquibaseLauncherDebug: String? = null,
    var liquibaseLauncherParentClassloader: String? = null,
) : LiquibaseSystemEnvArgs {
    override fun serialize(): List<Pair<String, String>> {
        return listOfNotNull(
            liquibaseHome?.let { "liquibase.home" to it },
            liquibaseLauncherDebug?.let { "liquibase.launcher.debug" to it },
            liquibaseLauncherParentClassloader?.let { "liquibase.launcher.parent.classloader" to it },
        )
    }
}

@ConfigureLiquibaseDslMarker
data class LiquibaseConnectionArgs(
    var changelogFile: String? = null,
    var driver: String? = null,
    var driverPropertiesFile: String? = null,
    var password: String? = null,
    var referenceDefaultCatalogName: String? = null,
    var referenceDefaultSchemaName: String? = null,
    var referenceDriver: String? = null,
    var referenceDriverPropertiesFile: String? = null,
    var referencePassword: String? = null,
    var referenceSchemas: String? = null,
    var referenceUrl: String? = null,
    var referenceUsername: String? = null,
    var url: String? = null,
    var username: String? = null,
) : LiquibaseCommandArgs {
    override fun serialize(): List<Pair<String, String>> {
        return listOfNotNull(
            changelogFile?.let { "changelog-file" to it },
            driver?.let { "driver" to it },
            driverPropertiesFile?.let { "driver-properties-file" to it },
            password?.let { "password" to it },
            referenceDefaultCatalogName?.let { "reference-default-catalog-name" to it },
            referenceDefaultSchemaName?.let { "reference-default-schema-name" to it },
            referenceDriver?.let { "reference-driver" to it },
            referenceDriverPropertiesFile?.let { "reference-driver-properties-file" to it },
            referencePassword?.let { "reference-password" to it },
            referenceSchemas?.let { "reference-schemas" to it },
            referenceUrl?.let { "reference-url" to it },
            referenceUsername?.let { "reference-username" to it },
            url?.let { "url" to it },
            username?.let { "username" to it },
        ).map { (k, v) -> k to v.toString() }
    }
}

@ConfigureLiquibaseDslMarker
data class LiquibaseInitArgs(
    var recursive: Boolean? = null,
    var source: String? = null,
    var target: String? = null,
    var changelogFile: String? = null,
    var format: String? = null,
    var keepTempFiles: Boolean? = null,
    var projectDefaultsFile: String? = null,
    var projectDir: String? = null,
    var projectGuide: String? = null,
    var url: String? = null,
    var bindAddress: String? = null,
    var dbPort: String? = null,
    var launchBrowser: Boolean? = null,
    var password: String? = null,
    var username: String? = null,
    var webPort: String? = null,
) : LiquibaseCommandArgs {
    override fun serialize(): List<Pair<String, String>> {
        return listOfNotNull(
            recursive?.let { "recursive" to it },
            source?.let { "source" to it },
            target?.let { "target" to it },
            changelogFile?.let { "changelog-file" to it },
            format?.let { "format" to it },
            keepTempFiles?.let { "keep-temp-files" to it },
            projectDefaultsFile?.let { "project-defaults-file" to it },
            projectDir?.let { "project-dir" to it },
            projectGuide?.let { "project-guide" to it },
            url?.let { "url" to it },
            bindAddress?.let { "bind-address" to it },
            dbPort?.let { "db-port" to it },
            launchBrowser?.let { "launch-browser" to it },
            password?.let { "password" to it },
            username?.let { "username" to it },
            webPort?.let { "web-port" to it },
        ).map { (k, v) -> k to v.toString() }
    }
}

@ConfigureLiquibaseDslMarker
data class LiquibaseFlowFilesArgs(
    var flowFile: String? = null,
    var flowFileStrictParsing: Boolean? = null,
    var flowShellInterpreter: String? = null,
    var flowShellKeepTempFiles: Boolean? = null,
) : LiquibaseCommandArgs {
    override fun serialize(): List<Pair<String, String>> {
        return listOfNotNull(
            flowFile?.let { "flow-file" to it },
            flowFileStrictParsing?.let { "flow-file-strict-parsing" to it },
            flowShellInterpreter?.let { "flow-shell-interpreter" to it },
            flowShellKeepTempFiles?.let { "flow-shell-keep-temp-files" to it },
        ).map { (k, v) -> k to v.toString() }
    }
}

@ConfigureLiquibaseDslMarker
data class LiquibasePolicyChecksArgs(
    var autoEnableNewChecks: Boolean? = null,
    var autoUpdate: String? = null,
    var cacheChangelogFileContents: Boolean? = null,
    var changelogFile: String? = null,
    var changesetFilter: String? = null,
    var checkName: String? = null,
    var checkRollbacks: String? = null,
    var checkStatus: String? = null,
    var checksOutput: String? = null,
    var checksPackages: String? = null,
    var checksScope: String? = null,
    var checksSettingsFile: String? = null,
    var disable: Boolean? = null,
    var enable: Boolean? = null,
    var force: Boolean? = null,
    var format: String? = null,
    var packageContents: String? = null,
    var packageFile: String? = null,
    var packageName: String? = null,
    var propertySubstitutionEnabled: Boolean? = null,
    var schemas: String? = null,
    var severity: String? = null,
    var showCols: String? = null,
    var sqlParserFailSeverity: String? = null,
    var url: String? = null,
) : LiquibaseCommandArgs {
    override fun serialize(): List<Pair<String, String>> {
        return listOfNotNull(
            autoEnableNewChecks?.let { "auto-enable-new-checks" to it },
            autoUpdate?.let { "auto-update" to it },
            cacheChangelogFileContents?.let { "cache-changelog-file-contents" to it },
            changelogFile?.let { "changelog-file" to it },
            changesetFilter?.let { "changeset-filter" to it },
            checkName?.let { "check-name" to it },
            checkRollbacks?.let { "check-rollbacks" to it },
            checkStatus?.let { "check-status" to it },
            checksOutput?.let { "checks-output" to it },
            checksPackages?.let { "checks-packages" to it },
            checksScope?.let { "checks-scope" to it },
            checksSettingsFile?.let { "checks-settings-file" to it },
            disable?.let { "disable" to it },
            enable?.let { "enable" to it },
            force?.let { "force" to it },
            format?.let { "format" to it },
            packageContents?.let { "package-contents" to it },
            packageFile?.let { "package-file" to it },
            packageName?.let { "package-name" to it },
            propertySubstitutionEnabled?.let { "property-substitution-enabled" to it },
            schemas?.let { "schemas" to it },
            severity?.let { "severity" to it },
            showCols?.let { "show-cols" to it },
            sqlParserFailSeverity?.let { "sql-parser-fail-severity" to it },
            url?.let { "url" to it },
        ).map { (k, v) -> k to v.toString() }
    }
}

@ConfigureLiquibaseDslMarker
data class LiquibaseOperationReportsArgs(
    var driftSeverity: String? = null,
    var driftSeverityChanged: String? = null,
    var driftSeverityMissing: String? = null,
    var driftSeverityUnexpected: String? = null,
    var openReport: Boolean? = null,
    var reportEnabled: Boolean? = null,
    var reportName: String? = null,
    var reportPath: String? = null,
) : LiquibaseCommandArgs {
    override fun serialize(): List<Pair<String, String>> {
        return listOfNotNull(
            driftSeverity?.let { "drift-severity" to it },
            driftSeverityChanged?.let { "drift-severity-changed" to it },
            driftSeverityMissing?.let { "drift-severity-missing" to it },
            driftSeverityUnexpected?.let { "drift-severity-unexpected" to it },
            openReport?.let { "open-report" to it },
            reportEnabled?.let { "report-enabled" to it },
            reportName?.let { "report-name" to it },
            reportPath?.let { "report-path" to it },
        ).map { (k, v) -> k to v.toString() }
    }
}

@ConfigureLiquibaseDslMarker
data class LiquibaseGeneralCommandArgs(
    var addRow: Boolean? = null,
    var changeExecListenerClass: String? = null,
    var changeExecListenerPropertiesFile: String? = null,
    var changesetAuthor: String? = null,
    var changesetId: String? = null,
    var changesetIdentifier: String? = null,
    var changesetPath: String? = null,
    var contextFilter: String? = null,
    var count: Int? = null,
    var dataOutputDirectory: String? = null,
    var date: TemporalAccessor? = null,
    var dropDbclhistory: Boolean? = null,
    var dbms: String? = null,
    var defaultCatalogName: String? = null,
    var defaultSchemaName: String? = null,
    var diffTypes: String? = null,
    var dropAllRequireForce: Boolean? = null,
    var excludeObjects: String? = null,
    var forceOnPartialChanges: Boolean? = null,
    var includeCatalog: Boolean? = null,
    var includeObjects: String? = null,
    var includeSchema: Boolean? = null,
    var includeTablespace: Boolean? = null,
    var labels: String? = null,
    var outputDirectory: String? = null,
    var outputSchemas: String? = null,
    var overwriteOutputFile: Boolean? = null,
    var referenceLiquibaseCatalogName: String? = null,
    var referenceLiquibaseSchemaName: String? = null,
    var replaceIfExistsTypes: String? = null,
    var rollbackOnError: Boolean? = null,
    var rollbackScript: String? = null,
    var runOnChangeTypes: String? = null,
    var schemas: String? = null,
    var showSummary: String? = null,
    var showSummaryOutput: String? = null,
    var skipObjectSorting: Boolean? = null,
    var snapshotFilters: String? = null,
    var snapshotFormat: String? = null,
    var sql: String? = null,
    var sqlFile: String? = null,
    var tag: String? = null,
    var tagVersion: String? = null,
    var verbose: Boolean? = null,
) : LiquibaseCommandArgs {
    override fun serialize(): List<Pair<String, String>> {
        return listOfNotNull(
            addRow?.let { "add-row" to it },
            changeExecListenerClass?.let { "change-exec-listener-class" to it },
            changeExecListenerPropertiesFile?.let { "change-exec-listener-properties-file" to it },
            changesetAuthor?.let { "changeset-author" to it },
            changesetId?.let { "changeset-id" to it },
            changesetIdentifier?.let { "changeset-identifier" to it },
            changesetPath?.let { "changeset-path" to it },
            contextFilter?.let { "context-filter" to it },
            count?.let { "count" to it },
            dataOutputDirectory?.let { "data-output-directory" to it },
            date?.let { "date" to LiquibaseCommandClientConfig.dateTimeFormatter.format(it) },
            dropDbclhistory?.let { "drop-dbclhistory" to it },
            dbms?.let { "dbms" to it },
            defaultCatalogName?.let { "default-catalog-name" to it },
            defaultSchemaName?.let { "default-schema-name" to it },
            diffTypes?.let { "diff-types" to it },
            dropAllRequireForce?.let { "drop-all-require-force" to it },
            excludeObjects?.let { "exclude-objects" to it },
            forceOnPartialChanges?.let { "force-on-partial-changes" to it },
            includeCatalog?.let { "include-catalog" to it },
            includeObjects?.let { "include-objects" to it },
            includeSchema?.let { "include-schema" to it },
            includeTablespace?.let { "include-tablespace" to it },
            labels?.let { "labels" to it },
            outputDirectory?.let { "output-directory" to it },
            outputSchemas?.let { "output-schemas" to it },
            overwriteOutputFile?.let { "overwrite-output-file" to it },
            referenceLiquibaseCatalogName?.let { "reference-liquibase-catalog-name" to it },
            referenceLiquibaseSchemaName?.let { "reference-liquibase-schema-name" to it },
            replaceIfExistsTypes?.let { "replace-if-exists-types" to it },
            rollbackOnError?.let { "rollback-on-error" to it },
            rollbackScript?.let { "rollback-script" to it },
            runOnChangeTypes?.let { "run-on-change-types" to it },
            schemas?.let { "schemas" to it },
            showSummary?.let { "show-summary" to it },
            showSummaryOutput?.let { "show-summary-output" to it },
            skipObjectSorting?.let { "skip-object-sorting" to it },
            snapshotFilters?.let { "snapshot-filters" to it },
            snapshotFormat?.let { "snapshot-format" to it },
            sql?.let { "sql" to it },
            sqlFile?.let { "sql-file" to it },
            tag?.let { "tag" to it },
            tagVersion?.let { "tag-version" to it },
            verbose?.let { "verbose" to it },
        ).map { (k, v) -> k to v.toString() }
    }
}
