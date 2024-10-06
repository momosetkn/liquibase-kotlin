package momosetkn.liquibase.kotlin.change.custom.ktorm

import io.kotest.core.spec.style.FunSpec
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
import org.ktorm.database.SqlDialect
import org.ktorm.support.mysql.MySqlDialect
import org.ktorm.support.oracle.OracleDialect
import org.ktorm.support.postgresql.PostgreSqlDialect
import org.ktorm.support.sqlite.SQLiteDialect
import kotlin.reflect.KClass
import liquibase.database.Database as LiquibaseDatabase

class LiquibaseKtormConfigSpec : FunSpec({
    fun subject(clazz: KClass<out LiquibaseDatabase>): SqlDialect {
        val liquibaseDatabase = clazz.constructors.find { it.parameters.isEmpty() }!!.call()
        return LiquibaseKtormConfig.getDialect(
            liquibaseDatabase.shortName
        )
    }

    context("CockroachDatabase") {
        test("not supported") {
            val actual = subject(CockroachDatabase::class)
            actual.shouldBeTypeOf<StandardSqlDialect>()
        }
    }

    context("DB2Database") {
        test("not supported") {
            val actual = subject(DB2Database::class)
            actual.shouldBeTypeOf<StandardSqlDialect>()
        }
    }

    context("Db2zDatabase") {
        test("not supported") {
            val actual = subject(Db2zDatabase::class)
            actual.shouldBeTypeOf<StandardSqlDialect>()
        }
    }

    context("DerbyDatabase") {
        test("supported") {
            val actual = subject(DerbyDatabase::class)
            actual.shouldBeTypeOf<StandardSqlDialect>()
        }
    }

    context("EnterpriseDBDatabase") {
        test("not supported") {
            val actual = subject(EnterpriseDBDatabase::class)
            actual.shouldBeTypeOf<StandardSqlDialect>()
        }
    }

    context("FirebirdDatabase") {
        test("not supported") {
            val actual = subject(FirebirdDatabase::class)
            actual.shouldBeTypeOf<StandardSqlDialect>()
        }
    }

    context("H2Database") {
        test("supported") {
            val actual = subject(H2Database::class)
            actual.shouldBeTypeOf<StandardSqlDialect>()
        }
    }

    context("HsqlDatabase") {
        test("supported") {
            val actual = subject(HsqlDatabase::class)
            actual.shouldBeTypeOf<StandardSqlDialect>()
        }
    }

    context("InformixDatabase") {
        test("not supported") {
            val actual = subject(InformixDatabase::class)
            actual.shouldBeTypeOf<StandardSqlDialect>()
        }
    }

    context("Ingres9Database") {
        test("not supported") {
            val actual = subject(Ingres9Database::class)
            actual.shouldBeTypeOf<StandardSqlDialect>()
        }
    }

    context("MariaDBDatabase") {
        test("supported") {
            val actual = subject(MariaDBDatabase::class)
            actual.shouldBeTypeOf<StandardSqlDialect>()
        }
    }

    context("MSSQLDatabase") {
        test("not supported") {
            val actual = subject(MSSQLDatabase::class)
            actual.shouldBeTypeOf<StandardSqlDialect>()
        }
    }

    context("MySQLDatabase") {
        test("supported") {
            val actual = subject(MySQLDatabase::class)
            actual.shouldBeTypeOf<MySqlDialect>()
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
            actual.shouldBeTypeOf<PostgreSqlDialect>()
        }
    }

    context("SnowflakeDatabase") {
        test("not supported") {
            val actual = subject(SnowflakeDatabase::class)
            actual.shouldBeTypeOf<StandardSqlDialect>()
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
            actual.shouldBeTypeOf<StandardSqlDialect>()
        }
    }

    context("SybaseDatabase") {
        test("not supported") {
            val actual = subject(SybaseDatabase::class)
            actual.shouldBeTypeOf<StandardSqlDialect>()
        }
    }
})
