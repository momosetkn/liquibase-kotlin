package momosetkn

import momosetkn.utils.DatabaseServer

object SharedResources {
    private val targetDatabaseServerInstance = DatabaseServer()
    fun getTargetDatabaseServer() = targetDatabaseServerInstance

    private val referenceDatabaseServerInstance = DatabaseServer()
    fun getReferenceDatabaseServer() = referenceDatabaseServerInstance
}
