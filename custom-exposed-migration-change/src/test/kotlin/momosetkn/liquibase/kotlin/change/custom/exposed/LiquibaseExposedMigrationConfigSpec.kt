package momosetkn.liquibase.kotlin.change.custom.exposed

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
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
import org.jetbrains.exposed.v1.core.vendors.H2Dialect
import org.jetbrains.exposed.v1.core.vendors.MariaDBDialect
import org.jetbrains.exposed.v1.core.vendors.MysqlDialect
import org.jetbrains.exposed.v1.core.vendors.OracleDialect
import org.jetbrains.exposed.v1.core.vendors.PostgreSQLDialect
import org.jetbrains.exposed.v1.core.vendors.SQLiteDialect
import org.jetbrains.exposed.v1.core.vendors.VendorDialect
import java.io.PrintWriter
import java.sql.Connection
import java.util.logging.Logger
import kotlin.reflect.KClass
import liquibase.database.Database as LiquibaseDatabase

class LiquibaseExposedMigrationConfigSpec : FunSpec({
    fun subject(clazz: KClass<out LiquibaseDatabase>): VendorDialect? {
        val liquibaseDatabase = clazz.constructors.find { it.parameters.isEmpty() }!!.call()
        return LiquibaseExposedMigrationConfig.getDialect(
            liquibaseDatabase.shortName
        )
    }

    context("CockroachDatabase") {
        test("not supported") {
            val actual = subject(CockroachDatabase::class)
            actual shouldBe null
        }
    }

    context("DB2Database") {
        test("not supported") {
            val actual = subject(DB2Database::class)
            actual shouldBe null
        }
    }

    context("Db2zDatabase") {
        test("not supported") {
            val actual = subject(Db2zDatabase::class)
            actual shouldBe null
        }
    }

    context("DerbyDatabase") {
        test("supported") {
            val actual = subject(DerbyDatabase::class)
            actual shouldBe null
        }
    }

    context("EnterpriseDBDatabase") {
        test("not supported") {
            val actual = subject(EnterpriseDBDatabase::class)
            actual shouldBe null
        }
    }

    context("FirebirdDatabase") {
        test("not supported") {
            val actual = subject(FirebirdDatabase::class)
            actual shouldBe null
        }
    }

    context("H2Database") {
        test("supported") {
            val actual = subject(H2Database::class)
            actual.shouldBeTypeOf<H2Dialect>()
        }
    }

    context("HsqlDatabase") {
        test("supported") {
            val actual = subject(HsqlDatabase::class)
            actual shouldBe null
        }
    }

    context("InformixDatabase") {
        test("not supported") {
            val actual = subject(InformixDatabase::class)
            actual shouldBe null
        }
    }

    context("Ingres9Database") {
        test("not supported") {
            val actual = subject(Ingres9Database::class)
            actual shouldBe null
        }
    }

    context("MariaDBDatabase") {
        test("supported") {
            val actual = subject(MariaDBDatabase::class)
            actual.shouldBeTypeOf<MariaDBDialect>()
        }
    }

    context("MSSQLDatabase") {
        test("not supported") {
            val actual = subject(MSSQLDatabase::class)
            actual shouldBe null
        }
    }

    context("MySQLDatabase") {
        test("supported") {
            val actual = subject(MySQLDatabase::class)
            actual.shouldBeTypeOf<MysqlDialect>()
        }
    }

    context("OracleDatabase") {
        test("supported") {
            val actual = subject(OracleDatabase::class)
            actual.shouldBeTypeOf<OracleDialect>()
        }
    }

    context("PostgresDatabase") {
        test("supported") {
            val actual = subject(PostgresDatabase::class)
            actual.shouldBeTypeOf<PostgreSQLDialect>()
        }
    }

    context("SnowflakeDatabase") {
        test("not supported") {
            val actual = subject(SnowflakeDatabase::class)
            actual shouldBe null
        }
    }

    context("SQLiteDatabase") {
        test("not supported") {
            val actual = subject(SQLiteDatabase::class)
            actual.shouldBeTypeOf<SQLiteDialect>()
        }
    }

    context("SybaseASADatabase") {
        test("not supported") {
            val actual =
                subject(SybaseASADatabase::class)
            actual shouldBe null
        }
    }

    context("SybaseDatabase") {
        test("not supported") {
            val actual = subject(SybaseDatabase::class)
            actual shouldBe null
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
