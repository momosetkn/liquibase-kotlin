package momosetkn.liquibase.kotlin.change.custom.komapper

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
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
import java.lang.IllegalStateException
import java.sql.Connection
import java.util.logging.Logger
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

    @Suppress("MaxLineLength")
    val errorMessage = "I could not find the `org.komapper.jdbc.JdbcDialects` that should be used. Please set the `momosetkn.liquibase.kotlin.change.custom.komapper.LiquibaseKomapperJdbcConfig.provideJdbcDatabase`."

    context("CockroachDatabase") {
        test("not supported") {
            val exception = shouldThrow<IllegalStateException> {
                subject(CockroachDatabase::class)
            }
            exception.message shouldBe errorMessage
        }
    }

    context("DB2Database") {
        test("not supported") {
            val exception = shouldThrow<IllegalStateException> {
                subject(DB2Database::class)
            }
            exception.message shouldBe errorMessage
        }
    }

    context("Db2zDatabase") {
        test("not supported") {
            val exception = shouldThrow<IllegalStateException> {
                subject(Db2zDatabase::class)
            }
            exception.message shouldBe errorMessage
        }
    }

    context("DerbyDatabase") {
        test("not supported") {
            val exception = shouldThrow<IllegalStateException> {
                subject(DerbyDatabase::class)
            }
            exception.message shouldBe errorMessage
        }
    }

    context("EnterpriseDBDatabase") {
        test("not supported") {
            val exception = shouldThrow<IllegalStateException> {
                subject(EnterpriseDBDatabase::class)
            }
            exception.message shouldBe errorMessage
        }
    }

    context("FirebirdDatabase") {
        test("not supported") {
            val exception = shouldThrow<IllegalStateException> {
                subject(FirebirdDatabase::class)
            }
            exception.message shouldBe errorMessage
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
            val exception = shouldThrow<IllegalStateException> {
                subject(HsqlDatabase::class)
            }
            exception.message shouldBe errorMessage
        }
    }

    context("InformixDatabase") {
        test("not supported") {
            val exception = shouldThrow<IllegalStateException> {
                subject(InformixDatabase::class)
            }
            exception.message shouldBe errorMessage
        }
    }

    context("Ingres9Database") {
        test("not supported") {
            val exception = shouldThrow<IllegalStateException> {
                subject(Ingres9Database::class)
            }
            exception.message shouldBe errorMessage
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
            val exception = shouldThrow<IllegalStateException> {
                subject(MSSQLDatabase::class)
            }
            exception.message shouldBe errorMessage
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
            val exception = shouldThrow<IllegalStateException> {
                subject(SnowflakeDatabase::class)
            }
            exception.message shouldBe errorMessage
        }
    }

    context("SQLiteDatabase") {
        test("not supported") {
            val exception = shouldThrow<IllegalStateException> {
                subject(SQLiteDatabase::class)
            }
            exception.message shouldBe errorMessage
        }
    }

    context("SybaseASADatabase") {
        test("not supported") {
            val exception = shouldThrow<IllegalStateException> {
                subject(SybaseASADatabase::class)
            }
            exception.message shouldBe errorMessage
        }
    }

    context("SybaseDatabase") {
        test("not supported") {
            val exception = shouldThrow<IllegalStateException> {
                subject(SybaseDatabase::class)
            }
            exception.message shouldBe errorMessage
        }
    }
})

val StubDataSource = object : javax.sql.DataSource {
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
