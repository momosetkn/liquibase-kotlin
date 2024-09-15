@file:Suppress("CyclomaticComplexMethod", "LongMethod")

package momosetkn.liquibase.client

import java.time.temporal.TemporalAccessor

sealed interface LiquibaseGlobalArgs {
    fun serialize(): List<String>
}
sealed interface LiquibaseSystemEnvArgs {
    fun serialize(): List<Pair<String, String>>
}
sealed interface LiquibaseCommandArgs {
    fun serialize(): List<String>
}

@Suppress("LargeClass", "TooManyFunctions")
data class LiquibaseInfoArgs(
    var help: Boolean? = null,
    var version: Boolean? = null,
) : LiquibaseGlobalArgs {
    override fun serialize(): List<String> {
        return listOfNotNull(
            help?.let { "-h=$it" },
            version?.let { "-v=$it" },
        )
    }
}

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
    var varidateXmlChangelogFiles: Boolean? = null,
) : LiquibaseGlobalArgs {
    override fun serialize(): List<String> {
        return listOfNotNull(
            allowDuplicatedChangesetIdentifiers?.let { "--allow-duplicated-changeset-identifiers=$it" },
            alwaysDropInsteadOfReplace?.let { "--always-drop-instead-of-replace=$it" },
            alwaysOverrideStoredLogicSchema?.let { "--always-override-stored-logic-schema=$it" },
            autoReorg?.let { "--auto-reorg=$it" },
            changelogLockPollRate?.let { "--changelog-lock-poll-rate=$it" },
            changelogLockWaitTimeInMinutes?.let { "--changelog-lock-wait-time-in-minutes=$it" },
            changelogParseMode?.let { "--changelog-parse-mode=$it" },
            classpath?.let { "--classpath=$it" },
            convertDataTypes?.let { "--convert-data-types=$it" },
            databaseChangelogLockTableName?.let { "--database-changelog-lock-table-name=$it" },
            databaseChangelogTableName?.let { "--database-changelog-table-name=$it" },
            databaseClass?.let { "--database-class=$it" },
            ddlLockTimeout?.let { "--ddl-lock-timeout=$it" },
            defaultsFile?.let { "--defaults-file=$it" },
            diffColumnOrder?.let { "--diff-column-order=$it" },
            driver?.let { "--driver=$it" },
            driverPropertiesFile?.let { "--driver-properties-file=$it" },
            duplicateFileMode?.let { "--duplicate-file-mode=$it" },
            errorOnCircularIncludeAll?.let { "--error-on-circular-include-all=$it" },
            fileEncoding?.let { "--file-encoding=$it" },
            generateChangesetCreatedvarues?.let { "--generate-changeset-created-varues=$it" },
            generatedChangesetIdsContainsDescription?.let { "--generated-changeset-ids-contains-description=$it" },
            headless?.let { "--headless=$it" },
            includeCatalogInSpecification?.let { "--include-catalog-in-specification=$it" },
            includeRelationsForComputedColumns?.let { "--include-relations-for-computed-columns=$it" },
            includeSystemClasspath?.let { "--include-system-classpath=$it" },
            licenseKey?.let { "--license-key=$it" },
            liquibaseCatalogName?.let { "--liquibase-catalog-name=$it" },
            liquibaseSchemaName?.let { "--liquibase-schema-name=$it" },
            liquibaseTablespaceName?.let { "--liquibase-tablespace-name=$it" },
            logChannels?.let { "--log-channels=$it" },
            logFile?.let { "--log-file=$it" },
            logFormat?.let { "--log-format=$it" },
            logLevel?.let { "--log-level=$it" },
            mirrorConsoleMessagesToLog?.let { "--mirror-console-messages-to-log=$it" },
            missingPropertyMode?.let { "--missing-property-mode=$it" },
            monitorPerformance?.let { "--monitor-performance=$it" },
            onMissingIncludeChangelog?.let { "--on-missing-include-changelog=$it" },
            outputFile?.let { "--output-file=$it" },
            outputFileEncoding?.let { "--output-file-encoding=$it" },
            outputLineSeparator?.let { "--output-line-separator=$it" },
            preserveSchemaCase?.let { "--preserve-schema-case=$it" },
            proGlobalEndDelimiter?.let { "--pro-global-end-delimiter=$it" },
            proGlobalEndDelimiterPrioritized?.let { "--pro-global-end-delimiter-prioritized=$it" },
            proMarkUnusedNotDrop?.let { "--pro-mark-unused-not-drop=$it" },
            proSqlInline?.let { "--pro-sql-inline=$it" },
            proStrict?.let { "--pro-strict=$it" },
            proSynonymsDropPublic?.let { "--pro-synonyms-drop-public=$it" },
            promptForNonLocalDatabase?.let { "--prompt-for-non-local-database=$it" },
            propertyProviderClass?.let { "--property-provider-class=$it" },
            searchPath?.let { "--search-path=$it" },
            secureParsing?.let { "--secure-parsing=$it" },
            shouldRun?.let { "--should-run=$it" },
            shouldSnapshotData?.let { "--should-snapshot-data=$it" },
            showBanner?.let { "--show-banner=$it" },
            sqlLogLevel?.let { "--sql-log-level=$it" },
            sqlShowSqlWarnings?.let { "--sql-show-sql-warnings=$it" },
            strict?.let { "--strict=$it" },
            supportPropertyEscaping?.let { "--support-property-escaping=$it" },
            supportsMethodvaridationLevel?.let { "--supports-method-varidation-level=$it" },
            trimLoadDataFileHeader?.let { "--trim-load-data-file-header=$it" },
            uiService?.let { "--ui-service=$it" },
            useProcedureSchema?.let { "--use-procedure-schema=$it" },
            varidateXmlChangelogFiles?.let { "--varidate-xml-changelog-files=$it" },
        )
    }
}

data class LiquibaseDbclhistoryArgs(
    var dbclhistoryCaptureExtensions: Boolean? = null,
    var dbclhistoryCaptureSql: Boolean? = null,
    var dbclhistoryEnabled: Boolean? = null,
    var dbclhistorySeverity: String? = null,
) : LiquibaseGlobalArgs {
    override fun serialize(): List<String> {
        return listOfNotNull(
            dbclhistoryCaptureExtensions?.let { "--dbclhistory-capture-extensions=$it" },
            dbclhistoryCaptureSql?.let { "--dbclhistory-capture-sql=$it" },
            dbclhistoryEnabled?.let { "--dbclhistory-enabled=$it" },
            dbclhistorySeverity?.let { "--dbclhistory-severity=$it" },
        )
    }
}

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
    override fun serialize(): List<String> {
        return listOfNotNull(
            psqlArgs?.let { "--psql-args=$it" },
            psqlKeepTemp?.let { "--psql-keep-temp=$it" },
            psqlKeepTempName?.let { "--psql-keep-temp-name=$it" },
            psqlKeepTempPath?.let { "--psql-keep-temp-path=$it" },
            psqlLogFile?.let { "--psql-log-file=$it" },
            psqlPath?.let { "--psql-path=$it" },
            psqlTimeout?.let { "--psql-timeout=$it" },
            sqlcmdArgs?.let { "--sqlcmd-args=$it" },
            sqlcmdCatalogName?.let { "--sqlcmd-catalog-name=$it" },
            sqlcmdKeepTemp?.let { "--sqlcmd-keep-temp=$it" },
            sqlcmdKeepTempName?.let { "--sqlcmd-keep-temp-name=$it" },
            sqlcmdKeepTempOverwrite?.let { "--sqlcmd-keep-temp-overwrite=$it" },
            sqlcmdKeepTempPath?.let { "--sqlcmd-keep-temp-path=$it" },
            sqlcmdLogFile?.let { "--sqlcmd-log-file=$it" },
            sqlcmdPath?.let { "--sqlcmd-path=$it" },
            sqlcmdTimeout?.let { "--sqlcmd-timeout=$it" },
            sqlplusArgs?.let { "--sqlplus-args=$it" },
            sqlplusCreateSpool?.let { "--sqlplus-create-spool=$it" },
            sqlplusKeepTemp?.let { "--sqlplus-keep-temp=$it" },
            sqlplusKeepTempName?.let { "--sqlplus-keep-temp-name=$it" },
            sqlplusKeepTempOverwrite?.let { "--sqlplus-keep-temp-overwrite=$it" },
            sqlplusKeepTempPath?.let { "--sqlplus-keep-temp-path=$it" },
            sqlplusPassword?.let { "--sqlplus-password=$it" },
            sqlplusPath?.let { "--sqlplus-path=$it" },
            sqlplusSqlerror?.let { "--sqlplus-sqlerror=$it" },
            sqlplusTimeout?.let { "--sqlplus-timeout=$it" },
            sqlplusUsername?.let { "--sqlplus-username=$it" },
        )
    }
}

data class LiquibaseCustomLoggingCommandArgs(
    var customLogDataFile: String? = null,
    var customLogDataFrequency: String? = null,
) : LiquibaseCommandArgs {
    override fun serialize(): List<String> {
        return listOfNotNull(
            customLogDataFile?.let { "--custom-log-data-file=$it" },
            customLogDataFrequency?.let { "--custom-log-data-frequency=$it" },
        )
    }
}

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
    override fun serialize(): List<String> {
        return listOfNotNull(
            dynamodbTrackingTablesBillingMode?.let { "--dynamodb-tracking-tables-billing-mode=$it" },
            dynamodbTrackingTablesProvisionedThroughputReadCapacityUnits?.let {
                "--dynamodb-tracking-tables-provisioned-throughput-read-capacity-units=$it"
            },
            dynamodbTrackingTablesProvisionedThroughputWriteCapacityUnits?.let {
                "--dynamodb-tracking-tables-provisioned-throughput-write-capacity-units=$it"
            },
            dynamodbWaitersEnabled?.let { "--dynamodb-waiters-enabled=$it" },
            dynamodbWaitersFailOnTimeout?.let { "--dynamodb-waiters-fail-on-timeout=$it" },
            dynamodbWaitersLogNotificationEnabled?.let { "--dynamodb-waiters-log-notification-enabled=$it" },
            dynamodbWaitersLogNotificationIntervar?.let { "--dynamodb-waiters-log-notification-intervar=$it" },
            dynamodbWaiterCreateFixedDelayBackoffStrategyDuration?.let {
                "--dynamodb-waiter-create-fixed-delay-backoff-strategy-duration=$it"
            },
            dynamodbWaiterCreateMaxAttempts?.let { "--dynamodb-waiter-create-max-attempts=$it" },
            dynamodbWaiterCreateTotalTimeout?.let { "--dynamodb-waiter-create-total-timeout=$it" },
            dynamodbWaiterDeleteFixedDelayBackoffStrategyDuration?.let {
                "--dynamodb-waiter-delete-fixed-delay-backoff-strategy-duration=$it"
            },
            dynamodbWaiterDeleteMaxAttempts?.let { "--dynamodb-waiter-delete-max-attempts=$it" },
            dynamodbWaiterDeleteTotalTimeout?.let { "--dynamodb-waiter-delete-total-timeout=$it" },
            dynamodbWaiterUpdateFixedDelayBackoffStrategyDuration?.let {
                "--dynamodb-waiter-update-fixed-delay-backoff-strategy-duration=$it"
            },
            dynamodbWaiterUpdateMaxAttempts?.let { "--dynamodb-waiter-update-max-attempts=$it" },
            dynamodbWaiterUpdateTotalTimeout?.let { "--dynamodb-waiter-update-total-timeout=$it" },
        )
    }
}

data class LiquibaseMongoDBArgs(
    var adjustTrackingTablesOnStartup: Boolean? = null,
    var retryWrites: Boolean? = null,
    var supportsvaridator: Boolean? = null,
) : LiquibaseGlobalArgs {
    override fun serialize(): List<String> {
        return listOfNotNull(
            adjustTrackingTablesOnStartup?.let { "--adjust-tracking-tables-on-startup=$it" },
            retryWrites?.let { "--retry-writes=$it" },
            supportsvaridator?.let { "--supports-varidator=$it" },
        )
    }
}

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
    override fun serialize(): List<String> {
        return listOfNotNull(
            changelogFile?.let { "--changelog-file=$it" },
            driver?.let { "--driver=$it" },
            driverPropertiesFile?.let { "--driver-properties-file=$it" },
            password?.let { "--password=$it" },
            referenceDefaultCatalogName?.let { "--reference-default-catalog-name=$it" },
            referenceDefaultSchemaName?.let { "--reference-default-schema-name=$it" },
            referenceDriver?.let { "--reference-driver=$it" },
            referenceDriverPropertiesFile?.let { "--reference-driver-properties-file=$it" },
            referencePassword?.let { "--reference-password=$it" },
            referenceSchemas?.let { "--reference-schemas=$it" },
            referenceUrl?.let { "--reference-url=$it" },
            referenceUsername?.let { "--reference-username=$it" },
            url?.let { "--url=$it" },
            username?.let { "--username=$it" },
        )
    }
}

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
    override fun serialize(): List<String> {
        return listOfNotNull(
            recursive?.let { "--recursive=$it" },
            source?.let { "--source=$it" },
            target?.let { "--target=$it" },
            changelogFile?.let { "--changelog-file=$it" },
            format?.let { "--format=$it" },
            keepTempFiles?.let { "--keep-temp-files=$it" },
            projectDefaultsFile?.let { "--project-defaults-file=$it" },
            projectDir?.let { "--project-dir=$it" },
            projectGuide?.let { "--project-guide=$it" },
            url?.let { "--url=$it" },
            bindAddress?.let { "--bind-address=$it" },
            dbPort?.let { "--db-port=$it" },
            launchBrowser?.let { "--launch-browser=$it" },
            password?.let { "--password=$it" },
            username?.let { "--username=$it" },
            webPort?.let { "--web-port=$it" },
        )
    }
}

data class LiquibaseFlowFilesArgs(
    var flowFile: String? = null,
    var flowFileStrictParsing: Boolean? = null,
    var flowShellInterpreter: String? = null,
    var flowShellKeepTempFiles: Boolean? = null,
) : LiquibaseCommandArgs {
    override fun serialize(): List<String> {
        return listOfNotNull(
            flowFile?.let { "--flow-file=$it" },
            flowFileStrictParsing?.let { "--flow-file-strict-parsing=$it" },
            flowShellInterpreter?.let { "--flow-shell-interpreter=$it" },
            flowShellKeepTempFiles?.let { "--flow-shell-keep-temp-files=$it" },
        )
    }
}

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
    override fun serialize(): List<String> {
        return listOfNotNull(
            autoEnableNewChecks?.let { "--auto-enable-new-checks=$it" },
            autoUpdate?.let { "--auto-update=$it" },
            cacheChangelogFileContents?.let { "--cache-changelog-file-contents=$it" },
            changelogFile?.let { "--changelog-file=$it" },
            changesetFilter?.let { "--changeset-filter=$it" },
            checkName?.let { "--check-name=$it" },
            checkRollbacks?.let { "--check-rollbacks=$it" },
            checkStatus?.let { "--check-status=$it" },
            checksOutput?.let { "--checks-output=$it" },
            checksPackages?.let { "--checks-packages=$it" },
            checksScope?.let { "--checks-scope=$it" },
            checksSettingsFile?.let { "--checks-settings-file=$it" },
            disable?.let { "--disable=$it" },
            enable?.let { "--enable=$it" },
            force?.let { "--force=$it" },
            format?.let { "--format=$it" },
            packageContents?.let { "--package-contents=$it" },
            packageFile?.let { "--package-file=$it" },
            packageName?.let { "--package-name=$it" },
            propertySubstitutionEnabled?.let { "--property-substitution-enabled=$it" },
            schemas?.let { "--schemas=$it" },
            severity?.let { "--severity=$it" },
            showCols?.let { "--show-cols=$it" },
            sqlParserFailSeverity?.let { "--sql-parser-fail-severity=$it" },
            url?.let { "--url=$it" },
        )
    }
}

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
    override fun serialize(): List<String> {
        return listOfNotNull(
            driftSeverity?.let { "--drift-severity=$it" },
            driftSeverityChanged?.let { "--drift-severity-changed=$it" },
            driftSeverityMissing?.let { "--drift-severity-missing=$it" },
            driftSeverityUnexpected?.let { "--drift-severity-unexpected=$it" },
            openReport?.let { "--open-report=$it" },
            reportEnabled?.let { "--report-enabled=$it" },
            reportName?.let { "--report-name=$it" },
            reportPath?.let { "--report-path=$it" },
        )
    }
}

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
    override fun serialize(): List<String> {
        return listOfNotNull(
            addRow?.let { "--add-row=$it" },
            changeExecListenerClass?.let { "--change-exec-listener-class=$it" },
            changeExecListenerPropertiesFile?.let { "--change-exec-listener-properties-file=$it" },
            changesetAuthor?.let { "--changeset-author=$it" },
            changesetId?.let { "--changeset-id=$it" },
            changesetIdentifier?.let { "--changeset-identifier=$it" },
            changesetPath?.let { "--changeset-path=$it" },
            contextFilter?.let { "--context-filter=$it" },
            count?.let { "--count=$it" },
            dataOutputDirectory?.let { "--data-output-directory=$it" },
            date?.let { "--date=${LiquibaseClientConfig.dateTimeFormatter.format(it)}" },
            dropDbclhistory?.let { "--drop-dbclhistory=$it" },
            dbms?.let { "--dbms=$it" },
            defaultCatalogName?.let { "--default-catalog-name=$it" },
            defaultSchemaName?.let { "--default-schema-name=$it" },
            diffTypes?.let { "--diff-types=$it" },
            dropAllRequireForce?.let { "--drop-all-require-force=$it" },
            excludeObjects?.let { "--exclude-objects=$it" },
            forceOnPartialChanges?.let { "--force-on-partial-changes=$it" },
            includeCatalog?.let { "--include-catalog=$it" },
            includeObjects?.let { "--include-objects=$it" },
            includeSchema?.let { "--include-schema=$it" },
            includeTablespace?.let { "--include-tablespace=$it" },
            labels?.let { "--labels=$it" },
            outputDirectory?.let { "--output-directory=$it" },
            outputSchemas?.let { "--output-schemas=$it" },
            overwriteOutputFile?.let { "--overwrite-output-file=$it" },
            referenceLiquibaseCatalogName?.let { "--reference-liquibase-catalog-name=$it" },
            referenceLiquibaseSchemaName?.let { "--reference-liquibase-schema-name=$it" },
            replaceIfExistsTypes?.let { "--replace-if-exists-types=$it" },
            rollbackOnError?.let { "--rollback-on-error=$it" },
            rollbackScript?.let { "--rollback-script=$it" },
            runOnChangeTypes?.let { "--run-on-change-types=$it" },
            schemas?.let { "--schemas=$it" },
            showSummary?.let { "--show-summary=$it" },
            showSummaryOutput?.let { "--show-summary-output=$it" },
            skipObjectSorting?.let { "--skip-object-sorting=$it" },
            snapshotFilters?.let { "--snapshot-filters=$it" },
            snapshotFormat?.let { "--snapshot-format=$it" },
            sql?.let { "--sql=$it" },
            sqlFile?.let { "--sql-file=$it" },
            tag?.let { "--tag=$it" },
            tagVersion?.let { "--tag-version=$it" },
            verbose?.let { "--verbose=$it" },
        )
    }
}
