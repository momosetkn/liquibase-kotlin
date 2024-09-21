package momosetkn.utils

import org.komapper.jdbc.JdbcDatabase

object DatabaseKomapperExtensions {
    fun Database.komapperDb() = JdbcDatabase(
        url = this.startedContainer.jdbcUrl,
        user = this.startedContainer.username,
        password = this.startedContainer.password,
    )
}
