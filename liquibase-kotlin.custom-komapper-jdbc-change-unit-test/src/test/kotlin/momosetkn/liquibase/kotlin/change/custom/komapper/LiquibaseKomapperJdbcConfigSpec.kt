package momosetkn.liquibase.kotlin.change.custom.komapper

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import liquibase.database.core.H2Database
import org.komapper.dialect.postgresql.jdbc.PostgreSqlJdbcDialect
import org.komapper.jdbc.JdbcDatabase
import java.io.PrintWriter
import java.sql.Connection
import java.util.logging.Logger
import javax.sql.DataSource
import kotlin.reflect.KClass
import liquibase.database.Database as LiquibaseDatabase

class LiquibaseKomapperJdbcConfigSpec : FunSpec({
    fun subject(clazz: KClass<out LiquibaseDatabase>): JdbcDatabase {
        val liquibaseDatabase = clazz.constructors.find { it.parameters.isEmpty() }!!.call()
        return LiquibaseKomapperJdbcConfig.defaultProvideJdbcDatabase(
            javaxSqlDataSource = MockDataSource,
            liquibaseDatabaseShortName = liquibaseDatabase.shortName
        )
    }

    context("not supported and dialect is one") {
        test("can use loaded dialect") {
            val actual = subject(H2Database::class)
            actual.config.dialect.shouldBeInstanceOf<PostgreSqlJdbcDialect>()
        }
    }
})

val MockDataSource = object : DataSource {
    override fun getLogWriter(): PrintWriter {
        TODO("Not yet implemented")
    }

    override fun setLogWriter(out: PrintWriter?) {
        TODO("Not yet implemented")
    }

    override fun setLoginTimeout(seconds: Int) {
        TODO("Not yet implemented")
    }

    override fun getLoginTimeout(): Int {
        TODO("Not yet implemented")
    }

    override fun getParentLogger(): Logger {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> unwrap(iface: Class<T>?): T {
        TODO("Not yet implemented")
    }

    override fun isWrapperFor(iface: Class<*>?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getConnection(): Connection {
        TODO("Not yet implemented")
    }

    override fun getConnection(username: String?, password: String?): Connection {
        TODO("Not yet implemented")
    }
}
