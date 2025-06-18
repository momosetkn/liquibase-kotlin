package momosetkn.liquibase.extension

import liquibase.change.Change
import liquibase.changelog.ChangeSet
import liquibase.changelog.DatabaseChangeLog
import liquibase.changelog.visitor.ChangeExecListener
import liquibase.database.Database
import liquibase.exception.PreconditionErrorException
import liquibase.exception.PreconditionFailedException
import liquibase.precondition.core.PreconditionContainer
import java.lang.Exception

@Suppress("TooManyFunctions")
class SlowLogChangeExecListener(
    val thresholdMillis: Long = 3_000,
    private val log: org.slf4j.Logger = org.slf4j.LoggerFactory.getLogger(SlowLogChangeExecListener::class.java)
) : ChangeExecListener {
    private val startTimes = mutableMapOf<String, Long>()

    override fun willRun(
        changeSet: ChangeSet,
        databaseChangeLog: DatabaseChangeLog,
        database: Database,
        runStatus: ChangeSet.RunStatus
    ) {
        resetStartTimes(changeSet)
    }

    override fun ran(
        changeSet: ChangeSet,
        databaseChangeLog: DatabaseChangeLog,
        database: Database,
        execType: ChangeSet.ExecType
    ) {
        logElapsedTime(changeSet, "executed")
    }

    override fun willRollback(
        changeSet: ChangeSet,
        databaseChangeLog: DatabaseChangeLog,
        database: Database
    ) {
        resetStartTimes(changeSet)
    }

    override fun rolledBack(
        changeSet: ChangeSet,
        databaseChangeLog: DatabaseChangeLog,
        database: Database
    ) {
        logElapsedTime(changeSet, "rolled back")
    }

    override fun preconditionFailed(
        error: PreconditionFailedException,
        onFail: PreconditionContainer.FailOption
    ) {
        // no-op
    }

    override fun preconditionErrored(
        error: PreconditionErrorException,
        onError: PreconditionContainer.ErrorOption
    ) {
        // no-op
    }

    override fun willRun(
        change: Change,
        changeSet: ChangeSet,
        changeLog: DatabaseChangeLog,
        database: Database
    ) {
        // no-op
    }

    override fun ran(
        change: Change,
        changeSet: ChangeSet,
        changeLog: DatabaseChangeLog,
        database: Database
    ) {
        // no-op
    }

    override fun runFailed(
        changeSet: ChangeSet,
        databaseChangeLog: DatabaseChangeLog,
        database: Database,
        exception: Exception
    ) {
        // no-op
    }

    override fun rollbackFailed(
        changeSet: ChangeSet,
        databaseChangeLog: DatabaseChangeLog,
        database: Database,
        exception: Exception
    ) {
        // no-op
    }

    private fun resetStartTimes(changeSet: ChangeSet) {
        startTimes[changeSet.id] = System.currentTimeMillis()
    }

    private fun logElapsedTime(changeSet: ChangeSet, action: String) {
        val startTime = startTimes[changeSet.id] ?: return
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        if (duration >= thresholdMillis) {
            log.warn("Slow ChangeSet: {} {} took {}ms", action, changeSet, duration)
        } else {
            log.debug("ChangeSet: {} {} took {}ms", action, changeSet, duration)
        }
    }
}
