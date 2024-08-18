package momosetkn.liquibase.parser.ext

import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.api.ScriptEvaluationConfiguration
import kotlin.script.experimental.api.implicitReceivers
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

class LiquibaseDslCompilationConfiguration :
    ScriptCompilationConfiguration({
        implicitReceivers(momosetkn.liquibase.kotlin.dsl.ChangeLogBuilderDsl::class)
        jvm {
            dependenciesFromCurrentContext(wholeClasspath = true)
        }
    })

object EvaluateLiquibaseDsl {
    private val log = liquibase.Scope.getCurrentScope().getLog(javaClass)

    fun evaluate(
        changeLogScript: String,
        context: momosetkn.liquibase.kotlin.dsl.ChangeLogBuilderDsl,
    ) {
        val result = evaluateWithConfiguration(changeLogScript, context)
        logResult(result)
    }

    private fun evaluateWithConfiguration(
        script: String,
        context: momosetkn.liquibase.kotlin.dsl.ChangeLogBuilderDsl,
    ): ResultWithDiagnostics<EvaluationResult> {
        val compilationConfiguration = LiquibaseDslCompilationConfiguration()
        val evaluationConfiguration =
            ScriptEvaluationConfiguration {
                implicitReceivers(context)
            }

        val host = BasicJvmScriptingHost()

        return host.eval(script.toScriptSource(), compilationConfiguration, evaluationConfiguration)
    }

    private fun logResult(result: ResultWithDiagnostics<EvaluationResult>) {
        result.reports.forEach { report ->
            when (report.severity) {
                ScriptDiagnostic.Severity.DEBUG -> log.fine(report.message)
                ScriptDiagnostic.Severity.INFO -> log.info(report.message)
                ScriptDiagnostic.Severity.WARNING -> log.warning(report.message)
                ScriptDiagnostic.Severity.ERROR -> log.severe(report.message)
                ScriptDiagnostic.Severity.FATAL -> log.severe(report.message)
            }
        }
    }
}
