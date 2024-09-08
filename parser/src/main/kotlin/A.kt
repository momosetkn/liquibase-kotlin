import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.ScriptEvaluationConfiguration
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

class MyCompilationConfiguration :
    ScriptCompilationConfiguration({
        jvm {
            dependenciesFromCurrentContext(wholeClasspath = true)
        }
    })

class LiquibaseDslCompilationConfiguration3 :
    ScriptCompilationConfiguration({
        jvm {
            dependenciesFromCurrentContext(wholeClasspath = true)
        }
    })

fun evaluateWithConfiguration2() {
    val compilationConfiguration = MyCompilationConfiguration()
    val evaluationConfiguration = ScriptEvaluationConfiguration()

    val host = BasicJvmScriptingHost()
    val result = host.eval(
        """
            println("aaaaaaaaaaaaaaa")
            if( i == 0) {
                i++
                evaluateWithConfiguration3()
            } else {
                println("else")
            }
            println("end")
        """.trimIndent().toScriptSource(),
        compilationConfiguration,
        evaluationConfiguration
    )
    result.reports.forEach { println(it.toString()) }
    println(result)
}

fun evaluateWithConfiguration3() {
    val compilationConfiguration = MyCompilationConfiguration()
    val evaluationConfiguration = ScriptEvaluationConfiguration {}

    val host = BasicJvmScriptingHost()

    val result = host.eval(
        """
            println("bbbbbbbbbbbbbbb")
        """.trimIndent().toScriptSource("evaluateWithConfiguration3"), // nameをつけることがポイント
        compilationConfiguration,
        evaluationConfiguration
    )
    result.reports.forEach { println(it.toString()) }
    println(result)
}

fun main(args: Array<String>) = evaluateWithConfiguration2()

var i = 0
