package momosetkn

import momosetkn.utils.DatabaseServer
import momosetkn.utils.DefaultDatabaseServer
import kotlin.concurrent.getOrSet

object SharedResources {
    private val targetDatabaseServerThreadLocal = ThreadLocal<DatabaseServer>()
    fun getTargetDatabaseServer(): DatabaseServer {
        return targetDatabaseServerThreadLocal.getOrSet {
            DefaultDatabaseServer()
        }
    }

    private val referenceDatabaseServerThreadLocal = ThreadLocal<DatabaseServer>()
    fun getReferenceDatabaseServer(): DatabaseServer {
        return referenceDatabaseServerThreadLocal.getOrSet {
            DefaultDatabaseServer()
        }
    }
}
