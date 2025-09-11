import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import liquibase.Liquibase
import momosetkn.liquibase.client.LiquibaseClient
import java.lang.Deprecated
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KVisibility
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions

class LiquibaseClientSpec : FunSpec({
    context("LiquibaseClient") {
        // subject
        val liquibaseClientPublicMethods =
            LiquibaseClient::class.members.filter { it.visibility == KVisibility.PUBLIC }
        // original class
        val liquibasePublicMethods =
            Liquibase::class.functions.filter { it.visibility == KVisibility.PUBLIC }

        // confirm a deprecated method
        run {
            val deprecatedLiquibasePublicMethods = liquibasePublicMethods
                .map {
                    it.name to it.findAnnotation<Deprecated>()
                }
                .groupBy({ it.first }, { it.second })
                .filter { it.value.filterNotNull().isNotEmpty() }
            println(deprecatedLiquibasePublicMethods)
        }

        context("check public methods") {
            val sortedLiquibaseClientPublicMethods = liquibaseClientPublicMethods
                .map { it.name }
                .distinct()
                .sorted()
                .filter { it !in sortedLiquibaseClientPublicMethodsExcludes }
            val sortedLiquibasePublicMethods = liquibasePublicMethods
                .map { it.name }
                .distinct()
                .sorted()
                .filter { it !in sortedLiquibasePublicMethodsExcludes }

            test("public methods are identical to liquibase.Liquibase") {
                sortedLiquibaseClientPublicMethods shouldBe sortedLiquibasePublicMethods
            }
        }

        context("Check what `check public methods` can't check") {
            val setterLiquibasePublicMethodNames = sortedLiquibasePublicMethodsExcludes
                .filter { it.startsWith("set") }
                .map {
                    it.substring(3).replaceFirstChar { it.lowercase() }
                }
            context("setter method is exist with mutable in the LiquibaseClient") {
                setterLiquibasePublicMethodNames.forEach { setterProperty ->
                    val finedMethod = liquibaseClientPublicMethods.find { it.name == setterProperty }
                    context(setterProperty) {
                        test("should be mutable property") {
                            checkNotNull(finedMethod) {
                                "method `$setterProperty` is not found in ${LiquibaseClient::class.qualifiedName}"
                            }
                            (finedMethod is KMutableProperty1<*, *>) shouldBe true
                        }
                    }
                }
            }
            context("getter only method is exist with not mutable in the LiquibaseClient") {
                val getterLiquibasePublicMethodNames = sortedLiquibasePublicMethodsExcludes
                    .filter { it.startsWith("get") }
                    .map {
                        it.substring(3).replaceFirstChar { it.lowercase() }
                    }
                val getterOnlyProperties = getterLiquibasePublicMethodNames
                    .filter {
                        it !in setterLiquibasePublicMethodNames
                    }
                getterOnlyProperties.forEach { getterOnlyProperty ->
                    val finedMethod = liquibaseClientPublicMethods.find { it.name == getterOnlyProperty }
                    context(getterOnlyProperty) {
                        test("should be mutable property") {
                            checkNotNull(finedMethod) {
                                "method `$getterOnlyProperty` is not found in ${LiquibaseClient::class.qualifiedName}"
                            }
                            (finedMethod is KMutableProperty1<*, *>) shouldBe false
                        }
                    }
                }
            }
        }
    }
})

private val sortedLiquibaseClientPublicMethodsExcludes = listOf(
    "showSummaryOutput",
    "showSummary",
    "changeLogFile",
    "database",
    "resourceAccessor",
    "changeLogParameters",
    "changeExecListener",
    "defaultChangeExecListener",
    "liquibase",
    "diffTypes",
    "diffChangeLog",
    "log",
)

private val sortedLiquibasePublicMethodsExcludes = listOf(
    // because deprecated
    "outputHeader",
    // because provided by property
    "getLog",
    "getDatabase",
    "getChangeLogFile",
    "setShowSummaryOutput",
    "getShowSummary",
    "setShowSummary",
    "getResourceAccessor",
    "getChangeLogParameters",
    "setChangeExecListener",
    "getDefaultChangeExecListener",
)
