@file:Suppress("CyclomaticComplexMethod", "LongMethod")

package momosetkn.liquibase.client

sealed interface LiquibaseGlobalArgs {
    fun serialize(): List<Pair<String, String?>>
}

sealed interface LiquibaseSystemEnvArgs {
    fun serialize(): List<Pair<String, String?>>
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
    override fun serialize(): List<Pair<String, String?>> {
        return listOf(
            "allow-duplicated-changeset-identifiers" to allowDuplicatedChangesetIdentifiers,
            "always-drop-instead-of-replace" to alwaysDropInsteadOfReplace,
            "always-override-stored-logic-schema" to alwaysOverrideStoredLogicSchema,
            "auto-reorg" to autoReorg,
            "changelog-lock-poll-rate" to changelogLockPollRate,
            "changelog-lock-wait-time-in-minutes" to changelogLockWaitTimeInMinutes,
            "changelog-parse-mode" to changelogParseMode,
            "classpath" to classpath,
            "convert-data-types" to convertDataTypes,
            "database-changelog-lock-table-name" to databaseChangelogLockTableName,
            "database-changelog-table-name" to databaseChangelogTableName,
            "database-class" to databaseClass,
            "ddl-lock-timeout" to ddlLockTimeout,
            "defaults-file" to defaultsFile,
            "diff-column-order" to diffColumnOrder,
            "driver" to driver,
            "driver-properties-file" to driverPropertiesFile,
            "duplicate-file-mode" to duplicateFileMode,
            "error-on-circular-include-all" to errorOnCircularIncludeAll,
            "file-encoding" to fileEncoding,
            "generate-changeset-created-varues" to generateChangesetCreatedvarues,
            "generated-changeset-ids-contains-description" to generatedChangesetIdsContainsDescription,
            "headless" to headless,
            "include-catalog-in-specification" to includeCatalogInSpecification,
            "include-relations-for-computed-columns" to includeRelationsForComputedColumns,
            "include-system-classpath" to includeSystemClasspath,
            "license-key" to licenseKey,
            "liquibase-catalog-name" to liquibaseCatalogName,
            "liquibase-schema-name" to liquibaseSchemaName,
            "liquibase-tablespace-name" to liquibaseTablespaceName,
            "log-channels" to logChannels,
            "log-file" to logFile,
            "log-format" to logFormat,
            "log-level" to logLevel,
            "mirror-console-messages-to-log" to mirrorConsoleMessagesToLog,
            "missing-property-mode" to missingPropertyMode,
            "monitor-performance" to monitorPerformance,
            "on-missing-include-changelog" to onMissingIncludeChangelog,
            "output-file" to outputFile,
            "output-file-encoding" to outputFileEncoding,
            "output-line-separator" to outputLineSeparator,
            "preserve-schema-case" to preserveSchemaCase,
            "pro-global-end-delimiter" to proGlobalEndDelimiter,
            "pro-global-end-delimiter-prioritized" to proGlobalEndDelimiterPrioritized,
            "pro-mark-unused-not-drop" to proMarkUnusedNotDrop,
            "pro-sql-inline" to proSqlInline,
            "pro-strict" to proStrict,
            "pro-synonyms-drop-public" to proSynonymsDropPublic,
            "prompt-for-non-local-database" to promptForNonLocalDatabase,
            "property-provider-class" to propertyProviderClass,
            "search-path" to searchPath,
            "secure-parsing" to secureParsing,
            "should-run" to shouldRun,
            "should-snapshot-data" to shouldSnapshotData,
            "show-banner" to showBanner,
            "sql-log-level" to sqlLogLevel,
            "sql-show-sql-warnings" to sqlShowSqlWarnings,
            "strict" to strict,
            "support-property-escaping" to supportPropertyEscaping,
            "supports-method-varidation-level" to supportsMethodvaridationLevel,
            "trim-load-data-file-header" to trimLoadDataFileHeader,
            "ui-service" to uiService,
            "use-procedure-schema" to useProcedureSchema,
            "validate-xml-changelog-files" to validateXmlChangelogFiles,
        ).map { (k, v) -> k to v?.toString() }
    }
}

@ConfigureLiquibaseDslMarker
data class LiquibaseDbclhistoryArgs(
    var dbclhistoryCaptureExtensions: Boolean? = null,
    var dbclhistoryCaptureSql: Boolean? = null,
    var dbclhistoryEnabled: Boolean? = null,
    var dbclhistorySeverity: String? = null,
) : LiquibaseGlobalArgs {
    override fun serialize(): List<Pair<String, String?>> {
        return listOf(
            "dbclhistory-capture-extensions" to dbclhistoryCaptureExtensions,
            "dbclhistory-capture-sql" to dbclhistoryCaptureSql,
            "dbclhistory-enabled" to dbclhistoryEnabled,
            "dbclhistory-severity" to dbclhistorySeverity,
        ).map { (k, v) -> k to v?.toString() }
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
    override fun serialize(): List<Pair<String, String?>> {
        return listOf(
            "psql-args" to psqlArgs,
            "psql-keep-temp" to psqlKeepTemp,
            "psql-keep-temp-name" to psqlKeepTempName,
            "psql-keep-temp-path" to psqlKeepTempPath,
            "psql-log-file" to psqlLogFile,
            "psql-path" to psqlPath,
            "psql-timeout" to psqlTimeout,
            "sqlcmd-args" to sqlcmdArgs,
            "sqlcmd-catalog-name" to sqlcmdCatalogName,
            "sqlcmd-keep-temp" to sqlcmdKeepTemp,
            "sqlcmd-keep-temp-name" to sqlcmdKeepTempName,
            "sqlcmd-keep-temp-overwrite" to sqlcmdKeepTempOverwrite,
            "sqlcmd-keep-temp-path" to sqlcmdKeepTempPath,
            "sqlcmd-log-file" to sqlcmdLogFile,
            "sqlcmd-path" to sqlcmdPath,
            "sqlcmd-timeout" to sqlcmdTimeout,
            "sqlplus-args" to sqlplusArgs,
            "sqlplus-create-spool" to sqlplusCreateSpool,
            "sqlplus-keep-temp" to sqlplusKeepTemp,
            "sqlplus-keep-temp-name" to sqlplusKeepTempName,
            "sqlplus-keep-temp-overwrite" to sqlplusKeepTempOverwrite,
            "sqlplus-keep-temp-path" to sqlplusKeepTempPath,
            "sqlplus-password" to sqlplusPassword,
            "sqlplus-path" to sqlplusPath,
            "sqlplus-sqlerror" to sqlplusSqlerror,
            "sqlplus-timeout" to sqlplusTimeout,
            "sqlplus-username" to sqlplusUsername,
        ).map { (k, v) -> k to v?.toString() }
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
    @Suppress("MaxLineLength")
    override fun serialize(): List<Pair<String, String?>> {
        return listOf(
            "dynamodb-tracking-tables-billing-mode" to dynamodbTrackingTablesBillingMode,
            "dynamodb-tracking-tables-provisioned-throughput-read-capacity-units" to
                dynamodbTrackingTablesProvisionedThroughputReadCapacityUnits,
            "dynamodb-tracking-tables-provisioned-throughput-write-capacity-units" to
                dynamodbTrackingTablesProvisionedThroughputWriteCapacityUnits,
            "dynamodb-waiters-enabled" to dynamodbWaitersEnabled,
            "dynamodb-waiters-fail-on-timeout" to dynamodbWaitersFailOnTimeout,
            "dynamodb-waiters-log-notification-enabled" to dynamodbWaitersLogNotificationEnabled,
            "dynamodb-waiters-log-notification-intervar" to dynamodbWaitersLogNotificationIntervar,
            "dynamodb-waiter-create-fixed-delay-backoff-strategy-duration" to dynamodbWaiterCreateFixedDelayBackoffStrategyDuration,
            "dynamodb-waiter-create-max-attempts" to dynamodbWaiterCreateMaxAttempts,
            "dynamodb-waiter-create-total-timeout" to dynamodbWaiterCreateTotalTimeout,
            "dynamodb-waiter-delete-fixed-delay-backoff-strategy-duration" to dynamodbWaiterDeleteFixedDelayBackoffStrategyDuration,
            "dynamodb-waiter-delete-max-attempts" to dynamodbWaiterDeleteMaxAttempts,
            "dynamodb-waiter-delete-total-timeout" to dynamodbWaiterDeleteTotalTimeout,
            "dynamodb-waiter-update-fixed-delay-backoff-strategy-duration" to dynamodbWaiterUpdateFixedDelayBackoffStrategyDuration,
            "dynamodb-waiter-update-max-attempts" to dynamodbWaiterUpdateMaxAttempts,
            "dynamodb-waiter-update-total-timeout" to dynamodbWaiterUpdateTotalTimeout,
        ).map { (k, v) -> k to v?.toString() }
    }
}

@ConfigureLiquibaseDslMarker
data class LiquibaseMongoDBArgs(
    var adjustTrackingTablesOnStartup: Boolean? = null,
    var retryWrites: Boolean? = null,
    var supportsvaridator: Boolean? = null,
) : LiquibaseGlobalArgs {
    override fun serialize(): List<Pair<String, String?>> {
        return listOf(
            "adjust-tracking-tables-on-startup" to adjustTrackingTablesOnStartup,
            "retry-writes" to retryWrites,
            "supports-varidator" to supportsvaridator,
        ).map { (k, v) -> k to v?.toString() }
    }
}

@ConfigureLiquibaseDslMarker
data class LiquibaseSystemArgs(
    var liquibaseHome: String? = null,
    var liquibaseLauncherDebug: String? = null,
    var liquibaseLauncherParentClassloader: String? = null,
) : LiquibaseSystemEnvArgs {
    override fun serialize(): List<Pair<String, String?>> {
        return listOf(
            "liquibase.home" to liquibaseHome,
            "liquibase.launcher.debug" to liquibaseLauncherDebug,
            "liquibase.launcher.parent.classloader" to liquibaseLauncherParentClassloader,
        )
    }
}
