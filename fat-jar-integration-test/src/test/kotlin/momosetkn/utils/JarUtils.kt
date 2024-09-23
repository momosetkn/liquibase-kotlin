package momosetkn.utils

import java.io.File

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

        val os = System.getProperty("os.name").lowercase()
        val gradleCommand = if (os.contains("win")) "gradlew.bat" else "./gradlew"
        val command = arrayOf(gradleCommand, "shadowjar")
        log.info("execute command: `{}`. in directory : `{}`", command.joinToString(" "), rootDir.absolutePath)
        val process = ProcessBuilder(*command)
            .directory(File("D:\\a\\liquibase-kotlin\\liquibase-kotlin\\"))
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
