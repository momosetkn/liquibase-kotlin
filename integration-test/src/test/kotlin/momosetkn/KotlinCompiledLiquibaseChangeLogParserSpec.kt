package momosetkn

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import liquibase.resource.ResourceAccessor
import momosetkn.liquibase.kotlin.parser.KotlinCompiledLiquibaseChangeLogParser

class KotlinCompiledLiquibaseChangeLogParserSpec : FunSpec({
    val resourceAccessor = mockk<ResourceAccessor>()
    context("supports") {
        context("kt file") {
            val changeLogFile = "momosetkn/liquibase.changelogs.CompiledDatabaseChangelogAll.kt"
            test("supports kt") {
                val parser = KotlinCompiledLiquibaseChangeLogParser()
                val changeLog = parser.supports(changeLogFile, resourceAccessor)
                changeLog shouldBe true
            }
        }
        context("class file") {
            val changeLogFile = "momosetkn/liquibase.changelogs.CompiledDatabaseChangelogAll.class"
            test("supports kt") {
                val parser = KotlinCompiledLiquibaseChangeLogParser()
                val changeLog = parser.supports(changeLogFile, resourceAccessor)
                changeLog shouldBe true
            }
        }
        context("class without extension") {
            val changeLogFile = "momosetkn/liquibase.changelogs.CompiledDatabaseChangelogAll"
            test("supports kt") {
                val parser = KotlinCompiledLiquibaseChangeLogParser()
                val changeLog = parser.supports(changeLogFile, resourceAccessor)
                changeLog shouldBe true
            }
        }
        context("other") {
            test("not supports") {
                val parser = KotlinCompiledLiquibaseChangeLogParser()
                val changeLog = parser.supports("momosetkn/liquibase.kotlin.parser.Hogehoge.java", resourceAccessor)
                changeLog shouldBe false
            }
        }
    }
})
