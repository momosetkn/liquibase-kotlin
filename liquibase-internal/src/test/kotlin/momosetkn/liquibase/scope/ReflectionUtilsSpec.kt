package momosetkn.liquibase.scope

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import liquibase.command.CommandScope

class ReflectionUtilsSpec : FunSpec({
    val classloader = ReflectionUtils::getURLClassloaderByClass
    context("getInstanceByAntherLoader") {
        context("java class") {
            val command = arrayOf("update")
            test("can create instance") {
                val actual = ReflectionUtils.getInstanceWithClassLoader(CommandScope::class.java, classloader, *command)
                actual.shouldBeInstanceOf<CommandScope>()
            }
        }
        context("kotlin class") {
            val command = arrayOf("update")
            test("can create instance") {
                val actual = ReflectionUtils.getInstanceWithClassLoader(KotlinClass::class.java, classloader, *command)
                actual.shouldBeInstanceOf<KotlinClass>()
            }
        }
    }
})

data class KotlinClass(
    val data: String,
)
