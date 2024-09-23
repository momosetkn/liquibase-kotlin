import momosetkn.liquibase.client.LiquibaseClient
import kotlin.system.exitProcess

@Suppress("DestructuringDeclarationWithTooManyEntries")
fun main(args: Array<String>) {
    val exitCode = runCatching {
        executeCommand(args)
    }.fold(
        onSuccess = {
            println("success")
            0
        },
        onFailure = {
            println("failure")
            it.printStackTrace()
            1
        },
    )
    exitProcess(exitCode)
}

private fun executeCommand(args: Array<String>) {
    var i = 0
    val splitedArgs = args[0].removeSurrounding("\"").split(",")
    val command = splitedArgs[i++]
    when (command) {
        "update" -> client.update(
            driver = splitedArgs[i++],
            url = splitedArgs[i++],
            username = splitedArgs[i++],
            password = splitedArgs[i++],
            changelogFile = splitedArgs[i++],
        )

        "rollback" -> client.rollback(
            driver = splitedArgs[i++],
            url = splitedArgs[i++],
            username = splitedArgs[i++],
            password = splitedArgs[i++],
            changelogFile = splitedArgs[i++],
            tag = splitedArgs[i],
        )

        "generateChangelog" -> client.generateChangelog(
            driver = splitedArgs[i++],
            url = splitedArgs[i++],
            username = splitedArgs[i++],
            password = splitedArgs[i++],
            changelogFile = splitedArgs[i++],
        )

        else -> TODO()
    }
}

private val client = LiquibaseClient {
    globalArgs {
        general {
            showBanner = false
            logLevel = "debug"
        }
    }
}
