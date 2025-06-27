package momosetkn.liquibase.kotlin.change.custom.jooq

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
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
import org.jooq.DSLContext
import org.jooq.SQLDialect
import java.io.PrintWriter
import java.sql.Connection
import java.util.logging.Logger
import kotlin.reflect.KClass
import liquibase.database.Database as LiquibaseDatabase

class LiquibaseJooqConfigSpec : FunSpec({
    fun subject(clazz: KClass<out LiquibaseDatabase>): DSLContext {
        val liquibaseDatabase = clazz.constructors.find { it.parameters.isEmpty() }!!.call()
        return LiquibaseJooqConfig.defaultProvideDSLContext(
            javaxSqlDataSource = StubDataSource,
            liquibaseDatabaseShortName = liquibaseDatabase.shortName
        )
    }

    context("CockroachDatabase") {
        test("not supported") {
            val actual = subject(CockroachDatabase::class)
            actual.dialect() shouldBe SQLDialect.DEFAULT
        }
    }

    context("DB2Database") {
        test("not supported") {
            val actual = subject(DB2Database::class)
            actual.dialect() shouldBe SQLDialect.DEFAULT
        }
    }

    context("Db2zDatabase") {
        test("not supported") {
            val actual = subject(Db2zDatabase::class)
            actual.dialect() shouldBe SQLDialect.DEFAULT
        }
    }

    context("DerbyDatabase") {
        test("supported") {
            val actual = subject(DerbyDatabase::class)
            actual.dialect() shouldBe SQLDialect.DERBY
        }
    }

    context("EnterpriseDBDatabase") {
        test("not supported") {
            val actual = subject(EnterpriseDBDatabase::class)
            actual.dialect() shouldBe SQLDialect.DEFAULT
        }
    }

    context("FirebirdDatabase") {
        test("supported") {
            val actual = subject(FirebirdDatabase::class)
            actual.dialect() shouldBe SQLDialect.FIREBIRD
        }
    }

    context("H2Database") {
        test("supported") {
            val actual = subject(H2Database::class)
            actual.dialect() shouldBe SQLDialect.H2
        }
    }

    context("HsqlDatabase") {
        test("supported") {
            val actual = subject(HsqlDatabase::class)
            actual.dialect() shouldBe SQLDialect.HSQLDB
        }
    }

    context("InformixDatabase") {
        test("not supported") {
            val actual = subject(InformixDatabase::class)
            actual.dialect() shouldBe SQLDialect.DEFAULT
        }
    }

    context("Ingres9Database") {
        test("not supported") {
            val actual = subject(Ingres9Database::class)
            actual.dialect() shouldBe SQLDialect.DEFAULT
        }
    }

    context("MariaDBDatabase") {
        test("supported") {
            val actual = subject(MariaDBDatabase::class)
            actual.dialect() shouldBe SQLDialect.MARIADB
        }
    }

    context("MSSQLDatabase") {
        test("not supported") {
            val actual = subject(MSSQLDatabase::class)
            actual.dialect() shouldBe SQLDialect.DEFAULT
        }
    }

    context("MySQLDatabase") {
        test("supported") {
            val actual = subject(MySQLDatabase::class)
            actual.dialect() shouldBe SQLDialect.MYSQL
        }
    }

    context("OracleDatabase") {
        test("supported") {
            val actual = subject(OracleDatabase::class)
            actual.dialect() shouldBe SQLDialect.DEFAULT
        }
    }

    context("PostgresDatabase") {
        test("supported") {
            val actual = subject(PostgresDatabase::class)
            actual.dialect() shouldBe SQLDialect.POSTGRES
        }
    }

    context("SnowflakeDatabase") {
        test("not supported") {
            val actual = subject(SnowflakeDatabase::class)
            actual.dialect() shouldBe SQLDialect.DEFAULT
        }
    }

    context("SQLiteDatabase") {
        test("not supported") {
            val actual = subject(SQLiteDatabase::class)
            actual.dialect() shouldBe SQLDialect.SQLITE
        }
    }

    context("SybaseASADatabase") {
        test("not supported") {
            val actual =
                subject(SybaseASADatabase::class)
            actual.dialect() shouldBe SQLDialect.DEFAULT
        }
    }

    context("SybaseDatabase") {
        test("not supported") {
            val actual = subject(SybaseDatabase::class)
            actual.dialect() shouldBe SQLDialect.DEFAULT
        }
    }
})

val StubDataSource = object : javax.sql.DataSource {
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
