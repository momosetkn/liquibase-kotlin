package momosetkn.utils

interface DatabaseServer {
    val startedServer: DatabaseConfig

    fun startAndClear()

    fun clear()

    fun generateDdl(): String
}

data class DatabaseConfig(
    val driver: String,
    val jdbcUrl: String,
    val username: String,
    val password: String,
)
