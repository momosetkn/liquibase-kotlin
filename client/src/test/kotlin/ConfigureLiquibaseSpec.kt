import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import momosetkn.liquibase.client.configureLiquibase

class ConfigureLiquibaseSpec : FunSpec({
    context("ConfigureLiquibase") {
        configureLiquibase {
            global {
                general {
                    showBanner = true
                    sqlLogLevel = "INFO"
                }
            }
            system {
                liquibaseHome = "~/home"
                liquibaseLauncherDebug = "true"
            }
        }
        configureLiquibase {
            global {
                general {
                    showBanner = true
                    searchPath = "./path"
                }
            }
            system {
                liquibaseHome = "~/home"
                liquibaseLauncherParentClassloader = "Aaaaa"
            }
        }
        test("system-property was override") {
            System.getProperty("show-banner") shouldBe "true"
            System.getProperty("sql-log-level") shouldBe null
            System.getProperty("search-path") shouldBe "./path"
            System.getProperty("liquibase.home") shouldBe "~/home"
            System.getProperty("liquibase.launcher.parent.classloader") shouldBe "Aaaaa"
        }
    }
})
