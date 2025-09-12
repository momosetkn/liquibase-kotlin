package momosetkn.utils

import org.slf4j.LoggerFactory
import java.io.File

class GradlewUtils(
    // root directory
    private val directory: File = File("../")
) {
    private val log = LoggerFactory.getLogger(this.javaClass.name)
    fun executeCommand(
        command: List<String>,
    ): () -> Unit {
        log.info("execute command: `{}`. in directory : `{}`", command.joinToString(" "), directory.absolutePath)
        @Suppress("SpreadOperator")
        val process = ProcessBuilder(*command.toTypedArray())
            .directory(directory)
            .redirectErrorStream(false)
            .let {
                it.environment()["JAVA_HOME"] = System.getProperty("java.home")
                it.start()
            }
        logRealtimeProcessOutput(process)
        return {
            process.destroy()
        }
    }

    fun getDefaultShell(): String {
        val os = System.getProperty("os.name").lowercase()
        return if (os.contains("win")) {
            "powershell"
        } else {
            "/bin/sh"
        }
    }

    private fun logRealtimeProcessOutput(
        process: Process,
    ): List<Thread> {
        val inputStreamThread = Thread {
            process.inputStream.bufferedReader().use {
                while (true) {
                    log.info("> : {}", it.readLine() ?: break)
                }
            }
        }
        val errorStreamThread = Thread {
            process.errorStream.bufferedReader().use {
                while (true) {
                    log.warn("> : {}", it.readLine() ?: break)
                }
            }
        }
        inputStreamThread.start()
        errorStreamThread.start()
        return listOf(
            inputStreamThread,
            errorStreamThread
        )
    }
}
