package momosetkn.utils

import java.io.File
import java.util.concurrent.TimeUnit

object JarUtils {
    private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)

    private val rootDir = File("../")
    private val projectDir = File(".")
    private var buildComplete = false

    @Synchronized
    fun build() {
        if (this.buildComplete) return

        // FIXME: remove
        log.info("file exists1: " + File("D:\\a\\liquibase-kotlin\\liquibase-kotlin\\gradlew.bat").exists())
        log.info("file exists2: " + File("D:\\a\\liquibase-kotlin\\liquibase-kotlin\\fat-jar-integration-test\\..\\gradlew.bat").exists())
        log.info("file exists3: " + File("D:\\a\\liquibase-kotlin\\gradlew.bat").exists())

        //        val os = System.getProperty("os.name").lowercase()
//        val gradleCommand = if (os.contains("win")) arrayOf(System.getenv("SHELL"), "./gradlew") else arrayOf("./gradlew")
//        val dire = if (os.contains("win")) File("D:\\a\\liquibase-kotlin\\liquibase-kotlin\\") else rootDir
        fun getDefaultShell(): String {
            val os = System.getProperty("os.name").lowercase()
            return if (os.contains("win")) {
                System.getenv("ComSpec") ?: "powershell"
            } else {
                System.getenv("SHELL") ?: "/bin/sh"
            }
        }

        val command = listOfNotNull(getDefaultShell(), "./gradlew", "shadowjar")
        log.info("execute command: `{}`. in directory : `{}`", command.joinToString(" "), rootDir.absolutePath)
        val process = ProcessBuilder(*command.toTypedArray())
            .directory(rootDir)
            .redirectErrorStream(false)
            .let {
                it.environment()["JAVA_HOME"] = System.getProperty("java.home")
                it.start()
            }

        val notTimeout = logRealtimeProcessOutput(process, 90, TimeUnit.SECONDS)
        require(notTimeout) {
            "timeout. command: `${command.joinToString(" ")}`"
        }
        require(process.exitValue() == 0) {
            "process exited with non-zero value: `${process.exitValue()}`, command: `${command.joinToString(" ")}`"
        }

        buildComplete = true
    }

    @Synchronized
    fun run(vararg args: String) {
        val command = arrayOf(
            "java",
            "-jar",
            "build/libs/fat-jar-integration-test-all.jar",
            "\"${args.joinToString(",")}\""
        )
        log.info("execute command: `{}`. in directory : `{}`", command.joinToString(" "), rootDir.absolutePath)
        val process = ProcessBuilder(*command)
            .directory(projectDir)
            .redirectErrorStream(false)
            .let {
                it.environment()["JAVA_HOME"] = System.getProperty("java.home")
                it.start()
            }

        val notTimeout = logRealtimeProcessOutput(process, 20, TimeUnit.SECONDS)
        require(notTimeout) {
            "timeout. command: `${command.joinToString(" ")}`"
        }
        require(process.exitValue() == 0) {
            "process exited with non-zero value: `${process.exitValue()}`, command: `${command.joinToString(" ")}`"
        }
    }

    private fun logRealtimeProcessOutput(
        process: Process,
        timeout: Long = 15L,
        unit: TimeUnit = TimeUnit.SECONDS,
    ): Boolean {
        val inputStreamThread = Thread {
            process.inputStream.bufferedReader().use {
                while (it.ready()) {
                    log.info("> : {}", it.readLine())
                }
            }
        }
        val errorStreamThread = Thread {
            process.errorStream.bufferedReader().use {
                while (it.ready()) {
                    log.warn("> : {}", it.readLine())
                }
            }
        }
        inputStreamThread.start()
        errorStreamThread.start()
        val waitForResult = process.waitFor(timeout, unit)
        inputStreamThread.join()
        errorStreamThread.join()
        return waitForResult
    }
}
