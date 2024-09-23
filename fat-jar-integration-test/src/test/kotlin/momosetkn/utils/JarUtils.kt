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

        logRealtimeProcessOutput(process, 30, TimeUnit.SECONDS)

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

        logRealtimeProcessOutput(process, 10, TimeUnit.SECONDS)
    }

    private fun logRealtimeProcessOutput(
        process: Process,
        timeout: Long = 10L,
        unit: TimeUnit = TimeUnit.SECONDS
    ) {
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
        process.waitFor(timeout, unit)
        inputStreamThread.join()
        errorStreamThread.join()
    }
}
