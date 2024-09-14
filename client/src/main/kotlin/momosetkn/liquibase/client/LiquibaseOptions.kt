@file:Suppress("CyclomaticComplexMethod", "LongMethod")

package momosetkn.liquibase.client

import java.time.temporal.TemporalAccessor

interface LiquibaseArgs {
    fun serialize(): List<String>
}

@Suppress("LargeClass", "TooManyFunctions")
data class LiquibaseInfoArgs0(
    val help: Boolean? = null,
    val version: Boolean? = null,
) : LiquibaseArgs {
    override fun serialize(): List<String> {
        return listOfNotNull(
            help?.let { "-h=$it" },
            version?.let { "-v=$it" },
        )
    }
}

data class LiquibaseGlobalArgs(
    val allowDuplicatedChangesetIdentifiers: Boolean? = null,
    val alwaysDropInsteadOfReplace: Boolean? = null,
    val alwaysOverrideStoredLogicSchema: String? = null,
    val autoReorg: Boolean? = null,
    val changelogLockPollRate: Int? = null,
    val changelogLockWaitTimeInMinutes: Int? = null,
    val changelogParseMode: String? = null,
    val classpath: String? = null,
    val convertDataTypes: Boolean? = null,
    val databaseChangelogLockTableName: String? = null,
    val databaseChangelogTableName: String? = null,
    val databaseClass: String? = null,
    val ddlLockTimeout: Int? = null,
    val defaultsFile: String? = null,
    val diffColumnOrder: Boolean? = null,
    val driver: String? = null,
    val driverPropertiesFile: String? = null,
    val duplicateFileMode: String? = null,
    val errorOnCircularIncludeAll: Boolean? = null,
    val fileEncoding: String? = null,
    val generateChangesetCreatedValues: Boolean? = null,
    val generatedChangesetIdsContainsDescription: String? = null,
    val headless: String? = null,
    val includeCatalogInSpecification: Boolean? = null,
    val includeRelationsForComputedColumns: Boolean? = null,
    val includeSystemClasspath: Boolean? = null,
    val licenseKey: String? = null,
    val liquibaseCatalogName: String? = null,
    val liquibaseSchemaName: String? = null,
    val liquibaseTablespaceName: String? = null,
    val logChannels: String? = null,
    val logFile: String? = null,
    val logFormat: String? = null,
    val logLevel: String? = null,
    val mirrorConsoleMessagesToLog: Boolean? = null,
    val missingPropertyMode: String? = null,
    val monitorPerformance: Boolean? = null,
    val onMissingIncludeChangelog: String? = null,
    val outputFile: String? = null,
    val outputFileEncoding: String? = null,
    val outputLineSeparator: String? = null,
    val preserveSchemaCase: Boolean? = null,
    val proGlobalEndDelimiter: String? = null,
    val proGlobalEndDelimiterPrioritized: Boolean? = null,
    val proMarkUnusedNotDrop: Boolean? = null,
    val proSqlInline: Boolean? = null,
    val proStrict: Boolean? = null,
    val proSynonymsDropPublic: Boolean? = null,
    val promptForNonLocalDatabase: Boolean? = null,
    val propertyProviderClass: String? = null,
    val searchPath: String? = null,
    val secureParsing: Boolean? = null,
    val shouldRun: Boolean? = null,
    val shouldSnapshotData: Boolean? = null,
    val showBanner: Boolean? = null,
    val sqlLogLevel: String? = null,
    val sqlShowSqlWarnings: Boolean? = null,
    val strict: Boolean? = null,
    val supportPropertyEscaping: Boolean? = null,
    val supportsMethodValidationLevel: String? = null,
    val trimLoadDataFileHeader: Boolean? = null,
    val uiService: String? = null,
    val useProcedureSchema: Boolean? = null,
    val validateXmlChangelogFiles: Boolean? = null,
) : LiquibaseArgs {
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
            generateChangesetCreatedValues?.let { "--generate-changeset-created-values=$it" },
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
            supportsMethodValidationLevel?.let { "--supports-method-validation-level=$it" },
            trimLoadDataFileHeader?.let { "--trim-load-data-file-header=$it" },
            uiService?.let { "--ui-service=$it" },
            useProcedureSchema?.let { "--use-procedure-schema=$it" },
            validateXmlChangelogFiles?.let { "--validate-xml-changelog-files=$it" },
        )
    }
}

data class LiquibaseDbclhistoryArgs(
    // Args
    val dbclhistoryCaptureExtensions: Boolean? = null,
    val dbclhistoryCaptureSql: Boolean? = null,
    val dbclhistoryEnabled: Boolean? = null,
    val dbclhistorySeverity: String? = null,
) : LiquibaseArgs {
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
    // Args
    val psqlArgs: String? = null,
    val psqlKeepTemp: Boolean? = null,
    val psqlKeepTempName: String? = null,
    val psqlKeepTempPath: String? = null,
    val psqlLogFile: String? = null,
    val psqlPath: String? = null,
    val psqlTimeout: Int? = null,
    val sqlcmdArgs: String? = null,
    val sqlcmdCatalogName: String? = null,
    val sqlcmdKeepTemp: Boolean? = null,
    val sqlcmdKeepTempName: String? = null,
    val sqlcmdKeepTempOverwrite: Boolean? = null,
    val sqlcmdKeepTempPath: String? = null,
    val sqlcmdLogFile: String? = null,
    val sqlcmdPath: String? = null,
    val sqlcmdTimeout: Int? = null,
    val sqlplusArgs: String? = null,
    val sqlplusCreateSpool: Boolean? = null,
    val sqlplusKeepTemp: Boolean? = null,
    val sqlplusKeepTempName: String? = null,
    val sqlplusKeepTempOverwrite: Boolean? = null,
    val sqlplusKeepTempPath: String? = null,
    val sqlplusPassword: String? = null,
    val sqlplusPath: String? = null,
    val sqlplusSqlerror: String? = null,
    val sqlplusTimeout: Int? = null,
    val sqlplusUsername: String? = null,
) : LiquibaseArgs {
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

data class LiquibaseCustomLoggingArgs(
    // Args
    val customLogDataFile: String? = null,
    val customLogDataFrequency: String? = null,
) : LiquibaseArgs {
    override fun serialize(): List<String> {
        return listOfNotNull(
            customLogDataFile?.let { "--custom-log-data-file=$it" },
            customLogDataFrequency?.let { "--custom-log-data-frequency=$it" },
        )
    }
}

data class LiquibaseExtensionsArgs(
    // Args
    val dynamodbTrackingTablesBillingMode: String? = null,
    val dynamodbTrackingTablesProvisionedThroughputReadCapacityUnits: Int? = null,
    val dynamodbTrackingTablesProvisionedThroughputWriteCapacityUnits: Int? = null,
    val dynamodbWaitersEnabled: Boolean? = null,
    val dynamodbWaitersFailOnTimeout: Boolean? = null,
    val dynamodbWaitersLogNotificationEnabled: Boolean? = null,
    val dynamodbWaitersLogNotificationInterval: Int? = null,
    val dynamodbWaiterCreateFixedDelayBackoffStrategyDuration: Int? = null,
    val dynamodbWaiterCreateMaxAttempts: Int? = null,
    val dynamodbWaiterCreateTotalTimeout: Int? = null,
    val dynamodbWaiterDeleteFixedDelayBackoffStrategyDuration: Int? = null,
    val dynamodbWaiterDeleteMaxAttempts: Int? = null,
    val dynamodbWaiterDeleteTotalTimeout: Int? = null,
    val dynamodbWaiterUpdateFixedDelayBackoffStrategyDuration: Int? = null,
    val dynamodbWaiterUpdateMaxAttempts: Int? = null,
    val dynamodbWaiterUpdateTotalTimeout: Int? = null,
) : LiquibaseArgs {
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
            dynamodbWaitersLogNotificationInterval?.let { "--dynamodb-waiters-log-notification-interval=$it" },
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
    // Args
    val adjustTrackingTablesOnStartup: Boolean? = null,
    val retryWrites: Boolean? = null,
    val supportsValidator: Boolean? = null,
) : LiquibaseArgs {
    override fun serialize(): List<String> {
        return listOfNotNull(
            adjustTrackingTablesOnStartup?.let { "--adjust-tracking-tables-on-startup=$it" },
            retryWrites?.let { "--retry-writes=$it" },
            supportsValidator?.let { "--supports-validator=$it" },
        )
    }
}

data class LiquibaseConfigurationArgs(
    // Args
    val liquibaseHome: String? = null,
    val liquibaseLauncherDebug: String? = null,
    val liquibaseLauncherParentClassloader: String? = null,
) : LiquibaseArgs {
    override fun serialize(): List<String> {
        return listOfNotNull(
            liquibaseHome?.let { "liquibase.home=$it" },
            liquibaseLauncherDebug?.let { "liquibase.launcher.debug=$it" },
            liquibaseLauncherParentClassloader?.let { "liquibase.launcher.parent.classloader=$it" },
        )
    }
}

data class LiquibaseConnectionArgs(
    // Args
    val changelogFile: String? = null,
    val driver: String? = null,
    val driverPropertiesFile: String? = null,
    val password: String? = null,
    val referenceDefaultCatalogName: String? = null,
    val referenceDefaultSchemaName: String? = null,
    val referenceDriver: String? = null,
    val referenceDriverPropertiesFile: String? = null,
    val referencePassword: String? = null,
    val referenceSchemas: String? = null,
    val referenceUrl: String? = null,
    val referenceUsername: String? = null,
    val url: String? = null,
    val username: String? = null,
) : LiquibaseArgs {
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
    // Args
    val recursive: Boolean? = null,
    val source: String? = null,
    val target: String? = null,
    val changelogFile: String? = null,
    val format: String? = null,
    val keepTempFiles: Boolean? = null,
    val projectDefaultsFile: String? = null,
    val projectDir: String? = null,
    val projectGuide: String? = null,
    val url: String? = null,
    val bindAddress: String? = null,
    val dbPort: String? = null,
    val launchBrowser: Boolean? = null,
    val password: String? = null,
    val username: String? = null,
    val webPort: String? = null,
) : LiquibaseArgs {
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
    // Args
    val flowFile: String? = null,
    val flowFileStrictParsing: Boolean? = null,
    val flowShellInterpreter: String? = null,
    val flowShellKeepTempFiles: Boolean? = null,
) : LiquibaseArgs {
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
    // Args
    val autoEnableNewChecks: Boolean? = null,
    val autoUpdate: String? = null,
    val cacheChangelogFileContents: Boolean? = null,
    val changelogFile: String? = null,
    val changesetFilter: String? = null,
    val checkName: String? = null,
    val checkRollbacks: String? = null,
    val checkStatus: String? = null,
    val checksOutput: String? = null,
    val checksPackages: String? = null,
    val checksScope: String? = null,
    val checksSettingsFile: String? = null,
    val disable: Boolean? = null,
    val enable: Boolean? = null,
    val force: Boolean? = null,
    val format: String? = null,
    val packageContents: String? = null,
    val packageFile: String? = null,
    val packageName: String? = null,
    val propertySubstitutionEnabled: Boolean? = null,
    val schemas: String? = null,
    val severity: String? = null,
    val showCols: String? = null,
    val sqlParserFailSeverity: String? = null,
    val url: String? = null,
) : LiquibaseArgs {
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
    // Args
    val driftSeverity: String? = null,
    val driftSeverityChanged: String? = null,
    val driftSeverityMissing: String? = null,
    val driftSeverityUnexpected: String? = null,
    val openReport: Boolean? = null,
    val reportEnabled: Boolean? = null,
    val reportName: String? = null,
    val reportPath: String? = null,
) : LiquibaseArgs {
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

data class LiquibaseGeneralArgs(
    // Args
    val addRow: Boolean? = null,
    val changeExecListenerClass: String? = null,
    val changeExecListenerPropertiesFile: String? = null,
    val changesetAuthor: String? = null,
    val changesetId: String? = null,
    val changesetIdentifier: String? = null,
    val changesetPath: String? = null,
    val contextFilter: String? = null,
    val count: Int? = null,
    val dataOutputDirectory: String? = null,
    val date: TemporalAccessor? = null,
    val dropDbclhistory: Boolean? = null,
    val dbms: String? = null,
    val defaultCatalogName: String? = null,
    val defaultSchemaName: String? = null,
    val diffTypes: String? = null,
    val dropAllRequireForce: Boolean? = null,
    val excludeObjects: String? = null,
    val forceOnPartialChanges: Boolean? = null,
    val includeCatalog: Boolean? = null,
    val includeObjects: String? = null,
    val includeSchema: Boolean? = null,
    val includeTablespace: Boolean? = null,
    val labels: String? = null,
    val outputDirectory: String? = null,
    val outputSchemas: String? = null,
    val overwriteOutputFile: Boolean? = null,
    val referenceLiquibaseCatalogName: String? = null,
    val referenceLiquibaseSchemaName: String? = null,
    val replaceIfExistsTypes: String? = null,
    val rollbackOnError: Boolean? = null,
    val rollbackScript: String? = null,
    val runOnChangeTypes: String? = null,
    val schemas: String? = null,
    val showSummary: String? = null,
    val showSummaryOutput: String? = null,
    val skipObjectSorting: Boolean? = null,
    val snapshotFilters: String? = null,
    val snapshotFormat: String? = null,
    val sql: String? = null,
    val sqlFile: String? = null,
    val tag: String? = null,
    val tagVersion: String? = null,
    val verbose: Boolean? = null,
) : LiquibaseArgs {
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
