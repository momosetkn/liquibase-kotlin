package momosetkn.liquibase.kotlin.parser

import liquibase.exception.LiquibaseParseException
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultValue
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.api.ScriptEvaluationConfiguration
import kotlin.script.experimental.api.defaultImports
import kotlin.script.experimental.api.implicitReceivers
import kotlin.script.experimental.api.valueOrNull
import kotlin.script.experimental.api.valueOrThrow
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

class LiquibaseDslCompilationConfiguration(
    private val imports: List<String> = emptyList()
) : ScriptCompilationConfiguration({
        implicitReceivers(momosetkn.liquibase.kotlin.dsl.ChangeLogBuilderDsl::class)
        jvm {
            dependenciesFromCurrentContext(wholeClasspath = true)
        }
        @Suppress("SpreadOperator")
        defaultImports(*imports.toTypedArray())
    })

object EvaluateLiquibaseDsl {
    private val log = liquibase.Scope.getCurrentScope().getLog(javaClass)

    fun evaluate(
        filePath: String,
        changeLogScript: String,
        imports: List<String>,
        changeLogBuilderDsl: momosetkn.liquibase.kotlin.dsl.ChangeLogBuilderDsl,
    ) {
        val result = evaluateWithConfiguration(
            filePath = filePath,
            script = changeLogScript,
            imports = imports,
            context = changeLogBuilderDsl,
        )
        logResult(filePath = filePath, result = result)
    }

    private fun evaluateWithConfiguration(
        filePath: String,
        script: String,
        imports: List<String>,
        context: momosetkn.liquibase.kotlin.dsl.ChangeLogBuilderDsl,
    ): ResultWithDiagnostics<EvaluationResult> {
        val scriptSource = script.toScriptSource(filePath)
        val compilationConfiguration = LiquibaseDslCompilationConfiguration(imports)
        val evaluationConfiguration =
            ScriptEvaluationConfiguration {
                implicitReceivers(context)
            }

        val host = BasicJvmScriptingHost()

        return host.eval(scriptSource, compilationConfiguration, evaluationConfiguration)
    }

    private fun logResult(
        filePath: String,
        result: ResultWithDiagnostics<EvaluationResult>,
    ) {
        var existsError = false
        result.reports.forEach { report ->
            val message = "$filePath $report"
            when (report.severity) {
                ScriptDiagnostic.Severity.DEBUG -> log.fine(message)
                ScriptDiagnostic.Severity.INFO -> log.info(message)
                ScriptDiagnostic.Severity.WARNING -> log.warning(message)
                ScriptDiagnostic.Severity.ERROR -> {
                    log.severe(message)
                    existsError = true
                }

                ScriptDiagnostic.Severity.FATAL -> {
                    log.severe(message)
                    existsError = true
                }
            }
        }
        val value = result.valueOrNull()
        when (val returnValue = value?.returnValue) {
            is ResultValue.Value -> log.fine(
                "$filePath name is ${returnValue.name}. value is ${returnValue.value}. type is ${returnValue.type}."
            )
            is ResultValue.Unit -> log.fine("$filePath returnValue is Unit")
            is ResultValue.Error -> {
                log.severe("$filePath error", returnValue.error)
                log.severe("$filePath wrappingException", returnValue.wrappingException)
                existsError = true
            }

            is ResultValue.NotEvaluated -> log.fine("$filePath not evaluated")
            null -> log.warning("returnValue is null")
        }
        if (existsError) {
            val errorReports = result.reports.filter { it.severity >= ScriptDiagnostic.Severity.ERROR }
            val valueOrThrow = result.valueOrThrow()

            val returnValue = valueOrThrow.returnValue
            val errorReturnValue = returnValue as? ResultValue.Error
            throw LiquibaseParseException(
                errorReports.joinToString(separator = "\n") { it.message },
                errorReturnValue?.error
            )
        }
    }
}
