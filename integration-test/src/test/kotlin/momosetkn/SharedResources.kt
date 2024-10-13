package momosetkn

import momosetkn.utils.DatabaseServer
import kotlin.concurrent.getOrSet

object SharedResources {
    private val targetDatabaseServerThreadLocal = ThreadLocal<DatabaseServer>()
    fun getTargetDatabaseServer(): DatabaseServer {
        return targetDatabaseServerThreadLocal.getOrSet {
                DatabaseServer()
            }
    }

    private val referenceDatabaseServerThreadLocal = ThreadLocal<DatabaseServer>()
    fun getReferenceDatabaseServer(): DatabaseServer {
        return referenceDatabaseServerThreadLocal.getOrSet {
            DatabaseServer()
        }
    }

}
