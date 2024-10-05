package momosetkn.liquibase.kotlin.change

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import liquibase.database.core.CockroachDatabase
import liquibase.database.core.DB2Database
import liquibase.database.core.Db2zDatabase
import liquibase.database.core.DerbyDatabase
import liquibase.database.core.EnterpriseDBDatabase
import liquibase.database.core.FirebirdDatabase
import liquibase.database.core.H2Database
import liquibase.database.core.HsqlDatabase
import liquibase.database.core.InformixDatabase
import liquibase.database.core.Ingres9Database
import liquibase.database.core.MSSQLDatabase
import liquibase.database.core.MariaDBDatabase
import liquibase.database.core.MySQLDatabase
import liquibase.database.core.OracleDatabase
import liquibase.database.core.PostgresDatabase
import liquibase.database.core.SQLiteDatabase
import liquibase.database.core.SnowflakeDatabase
import liquibase.database.core.SybaseASADatabase
import liquibase.database.core.SybaseDatabase
import org.komapper.dialect.h2.jdbc.H2JdbcDialect
import org.komapper.dialect.mariadb.jdbc.MariaDbJdbcDialect
import org.komapper.dialect.mysql.jdbc.MySqlJdbcDialect
import org.komapper.dialect.oracle.jdbc.OracleJdbcDialect
import org.komapper.dialect.postgresql.jdbc.PostgreSqlJdbcDialect
import org.komapper.jdbc.JdbcDatabase
import java.io.PrintWriter
import java.lang.IllegalArgumentException
import java.sql.Connection
import java.util.logging.Logger
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

    context("CockroachDatabase") {
        test("not supported") {
            shouldThrow<IllegalArgumentException> {
                subject(CockroachDatabase::class)
            }
        }
    }

    context("DB2Database") {
        test("not supported") {
            shouldThrow<IllegalArgumentException> {
                subject(DB2Database::class)
            }
        }
    }

    context("Db2zDatabase") {
        test("not supported") {
            shouldThrow<IllegalArgumentException> {
                subject(Db2zDatabase::class)
            }
        }
    }

    context("DerbyDatabase") {
        test("not supported") {
            shouldThrow<IllegalArgumentException> {
                subject(DerbyDatabase::class)
            }
        }
    }

    context("EnterpriseDBDatabase") {
        test("not supported") {
            shouldThrow<IllegalArgumentException> {
                subject(EnterpriseDBDatabase::class)
            }
        }
    }

    context("FirebirdDatabase") {
        test("not supported") {
            shouldThrow<IllegalArgumentException> {
                subject(FirebirdDatabase::class)
            }
        }
    }

    context("H2Database") {
        test("supported") {
            val actual = subject(H2Database::class)
            actual.config.dialect.shouldBeInstanceOf<H2JdbcDialect>()
        }
    }

    context("HsqlDatabase") {
        test("not supported") {
            shouldThrow<IllegalArgumentException> {
                subject(HsqlDatabase::class)
            }
        }
    }

    context("InformixDatabase") {
        test("not supported") {
            shouldThrow<IllegalArgumentException> {
                subject(InformixDatabase::class)
            }
        }
    }

    context("Ingres9Database") {
        test("not supported") {
            shouldThrow<IllegalArgumentException> {
                subject(Ingres9Database::class)
            }
        }
    }

    context("MariaDBDatabase") {
        test("supported") {
            val actual = subject(MariaDBDatabase::class)
            actual.config.dialect.shouldBeInstanceOf<MariaDbJdbcDialect>()
        }
    }

    context("MSSQLDatabase") {
        test("not supported") {
            shouldThrow<IllegalArgumentException> {
                subject(MSSQLDatabase::class)
            }
        }
    }

    context("MySQLDatabase") {
        test("supported") {
            val actual = subject(MySQLDatabase::class)
            actual.config.dialect.shouldBeInstanceOf<MySqlJdbcDialect>()
        }
    }

    context("OracleDatabase") {
        test("supported") {
            val actual = subject(OracleDatabase::class)
            actual.config.dialect.shouldBeInstanceOf<OracleJdbcDialect>()
        }
    }

    context("PostgresDatabase") {
        test("supported") {
            val actual = subject(PostgresDatabase::class)
            actual.config.dialect.shouldBeInstanceOf<PostgreSqlJdbcDialect>()
        }
    }

    context("SnowflakeDatabase") {
        test("not supported") {
            shouldThrow<IllegalArgumentException> {
                subject(SnowflakeDatabase::class)
            }
        }
    }

    context("SQLiteDatabase") {
        test("not supported") {
            shouldThrow<IllegalArgumentException> {
                subject(SQLiteDatabase::class)
            }
        }
    }

    context("SybaseASADatabase") {
        test("not supported") {
            shouldThrow<IllegalArgumentException> {
                subject(SybaseASADatabase::class)
            }
        }
    }

    context("SybaseDatabase") {
        test("not supported") {
            shouldThrow<IllegalArgumentException> {
                subject(SybaseDatabase::class)
            }
        }
    }
})

val MockDataSource = object : javax.sql.DataSource {
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
