import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import liquibase.Liquibase
import momosetkn.liquibase.client.LiquibaseClient
import kotlin.reflect.KCallable
import kotlin.reflect.KVisibility
import kotlin.reflect.full.functions

class LiquibaseClientSpec : FunSpec({
    context("LiquibaseClient") {
        test("public methods are identical to liquibase.Liquibase") {
            val liquibaseClientPublicMethods =
                LiquibaseClient::class.members.filter { it.visibility == KVisibility.PUBLIC }
            val liquibasePublicMethods =
                Liquibase::class.functions.filter { it.visibility == KVisibility.PUBLIC }

            val customComparator = Comparator<KCallable<*>> { p1, p2 ->
                compareValuesBy(p1, p2, { it.name }, { it.parameters.size })
            }
            val sortedLiquibaseClientPublicMethods = liquibaseClientPublicMethods.sortedWith(customComparator)
                .filter { it.name !in sortedLiquibaseClientPublicMethodsExcludes }
            val sortedLiquibasePublicMethods = liquibasePublicMethods.sortedWith(customComparator)
                .filter { it.name !in sortedLiquibasePublicMethodsExcludes }

            sortedLiquibaseClientPublicMethods.zip(sortedLiquibasePublicMethods).forEach { (m1, m2) ->
                Pair(m1.name, m1.parameters.size) shouldBe Pair(m2.name, m2.parameters.size)
            }

            sortedLiquibaseClientPublicMethods
                .size shouldBe sortedLiquibasePublicMethods
                .filter { it.name !in sortedLiquibasePublicMethodsExcludes }.size
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
)

private val sortedLiquibasePublicMethodsExcludes = listOf(
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
