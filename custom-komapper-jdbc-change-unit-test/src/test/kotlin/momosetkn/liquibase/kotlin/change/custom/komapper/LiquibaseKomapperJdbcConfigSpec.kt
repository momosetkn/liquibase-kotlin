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
            javaxSqlDataSource = StubDataSource,
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

val StubDataSource = object : DataSource {
    override fun getLogWriter(): PrintWriter {
        throw UnsupportedOperationException()
    }

    override fun setLogWriter(out: PrintWriter?) {
        throw UnsupportedOperationException()
    }

    override fun setLoginTimeout(seconds: Int) {
        throw UnsupportedOperationException()
    }

    override fun getLoginTimeout(): Int {
        throw UnsupportedOperationException()
    }

    override fun getParentLogger(): Logger {
        throw UnsupportedOperationException()
    }

    override fun <T : Any?> unwrap(iface: Class<T>?): T {
        throw UnsupportedOperationException()
    }

    override fun isWrapperFor(iface: Class<*>?): Boolean {
        throw UnsupportedOperationException()
    }

    override fun getConnection(): Connection {
        throw UnsupportedOperationException()
    }

    override fun getConnection(username: String?, password: String?): Connection {
        throw UnsupportedOperationException()
    }
}
