package momosetkn.utils

import java.io.File

object JarUtils {
    private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)

    private val rootDir = File("../")
    private val projectDir = File(".")
    private var buildComplete = false

    @Synchronized fun build() {
        if (this.buildComplete) return

        val command = arrayOf("./gradlew", "shadowjar")
        log.info("execute command: {}", command.joinToString(" "))
        val process = ProcessBuilder(*command)
            .directory(rootDir)
            .redirectErrorStream(true)
            .let {
                it.environment()["JAVA_HOME"] = System.getProperty("java.home")
                it.start()
            }

        val result = process.inputStream.bufferedReader().readText()
        process.waitFor(20, java.util.concurrent.TimeUnit.SECONDS)

        log.info(result)
        buildComplete = true
    }

    @Synchronized fun run(vararg args: String) {
        val command = arrayOf(
            "java",
            "-jar",
            "build/libs/fat-jar-integration-test-all.jar",
            "\"${args.joinToString(",")}\""
        )
        log.info("execute command: {}", command.joinToString(" "))
        val process = ProcessBuilder(*command)
            .directory(projectDir)
            .redirectErrorStream(false)
            .let {
                it.environment()["JAVA_HOME"] = System.getProperty("java.home")
                it.start()
            }

//        val result = result_.readText()
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
        process.waitFor(10, java.util.concurrent.TimeUnit.SECONDS)
        inputStreamThread.join()
        errorStreamThread.join()
    }
}
