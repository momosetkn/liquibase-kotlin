package momosetkn.liquibase.kotlin.dsl

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import liquibase.changelog.ChangeSet
import liquibase.exception.ChangeLogParseException

class ChangeSetSupportSpec : FunSpec({
    val changeSet = mockk<ChangeSet>()
    every { changeSet.id } returns "changeSetId1"
    val changeSetSupport = ChangeSetSupport(changeSet)

    context("invalid changeName") {
        test("should throw an exception") {
            val exception = shouldThrow<ChangeLogParseException> {
                changeSetSupport.createChange("hoge")
            }
            exception.message shouldBe "ChangeSet 'changeSetId1': 'hoge' is not a valid element of a ChangeSet"
        }
    }
    context("valid changeName") {
        test("not throw an exception") {
            changeSetSupport.createChange("addColumn")
        }
    }
})
