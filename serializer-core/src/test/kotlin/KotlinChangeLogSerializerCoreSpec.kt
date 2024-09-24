import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import liquibase.changelog.ChangeSet
import liquibase.changelog.DatabaseChangeLog
import momosetkn.liquibase.kotlin.serializer.KotlinChangeLogSerializerCore

class KotlinChangeLogSerializerCoreSpec : FunSpec({
    context("include double-quote and line-break") {
        test("can serialize") {
            val serializer = KotlinChangeLogSerializerCore()

            val changeLog = DatabaseChangeLog()
            val changeSet =
                ChangeSet(
                    "id",
                    """
                        "momose"
                        "momo"
                    """.trimIndent(),
                    false,
                    false,
                    "filepath",
                    null,
                    null,
                    null,
                    null,
                    true,
                    null,
                    changeLog,
                )
            val serialized = serializer.serializeLiquibaseSerializable(changeSet)
            serialized shouldBe """
                changeSet(author = "\"momose\"\n\"momo\"", id = "id")
            """.trimIndent()
        }
    }
})
