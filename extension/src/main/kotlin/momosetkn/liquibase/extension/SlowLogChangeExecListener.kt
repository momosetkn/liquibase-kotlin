package momosetkn.liquibase.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import liquibase.change.Change
import liquibase.changelog.ChangeSet
import liquibase.changelog.DatabaseChangeLog
import liquibase.changelog.visitor.ChangeExecListener
import liquibase.database.Database
import liquibase.exception.PreconditionErrorException
import liquibase.exception.PreconditionFailedException
import liquibase.precondition.core.PreconditionContainer
import java.lang.Exception
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Suppress("TooManyFunctions")
class SlowLogChangeExecListener(
    val threshold: Duration = 3.seconds,
    private val log: org.slf4j.Logger = org.slf4j.LoggerFactory.getLogger(SlowLogChangeExecListener::class.java)
) : ChangeExecListener {
    // not care for concurrent
    private val reportSlowLogJobs = mutableMapOf<ChangeSet, TimedJob>()

    override fun willRun(
        changeSet: ChangeSet,
        databaseChangeLog: DatabaseChangeLog,
        database: Database,
        runStatus: ChangeSet.RunStatus
    ) {
        launchSlowLogJob(changeSet, "executed")
    }

    override fun ran(
        changeSet: ChangeSet,
        databaseChangeLog: DatabaseChangeLog,
        database: Database,
        execType: ChangeSet.ExecType
    ) {
        logElapsedTimeAndCancelSlowLog(changeSet, "executed")
    }

    override fun willRollback(
        changeSet: ChangeSet,
        databaseChangeLog: DatabaseChangeLog,
        database: Database
    ) {
        launchSlowLogJob(changeSet, "rolled back")
    }

    override fun rolledBack(
        changeSet: ChangeSet,
        databaseChangeLog: DatabaseChangeLog,
        database: Database
    ) {
        logElapsedTimeAndCancelSlowLog(changeSet, "rolled back")
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

    private fun logElapsedTimeAndCancelSlowLog(changeSet: ChangeSet, action: String) {
        val timedJob = reportSlowLogJobs[changeSet]
        checkNotNull(timedJob)
        timedJob.job.cancel()

        val elapsedTime = System.currentTimeMillis() - timedJob.startTime
        log.debug("ChangeSet: {} {} took {}ms", action, changeSet, elapsedTime)
    }

    private fun launchSlowLogJob(changeSet: ChangeSet, action: String) {
        val job = CoroutineScope(Dispatchers.IO).launch {
            delay(threshold)
            log.warn("Slow ChangeSet: {} {} threshold-time is {}", action, changeSet, threshold)
        }
        reportSlowLogJobs[changeSet] = TimedJob.create(job)
    }
}

private data class TimedJob(
    val job: Job,
    val startTime: Long
) {
    companion object {
        fun create(job: Job): TimedJob = TimedJob(job, System.currentTimeMillis())
    }
}
