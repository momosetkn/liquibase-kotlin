package momosetkn.utils

import momosetkn.liquibase.client.ConfigureLiquibaseDslBlock
import momosetkn.liquibase.client.LiquibaseClient
import java.io.File
import java.net.URL
import java.net.URLClassLoader

object JarUtils {
    private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)

    private val rootDir = File("../")
    private val projectDir = File(".")
    private var buildComplete = false

    @Synchronized
    fun build() {
//        if (this.buildComplete) return
//
//        // FIXME: remove
//        log.info("file exists1: " + File("D:\\a\\liquibase-kotlin\\liquibase-kotlin\\gradlew.bat").exists())
//        log.info("file exists2: " + File("D:\\a\\liquibase-kotlin\\liquibase-kotlin\\fat-jar-integration-test\\..\\gradlew.bat").exists())
//        log.info("file exists3: " + File("D:\\a\\liquibase-kotlin\\gradlew.bat").exists())
//
//        //        val os = System.getProperty("os.name").lowercase()
// //        val gradleCommand = if (os.contains("win")) arrayOf(System.getenv("SHELL"), "./gradlew") else arrayOf("./gradlew")
// //        val dire = if (os.contains("win")) File("D:\\a\\liquibase-kotlin\\liquibase-kotlin\\") else rootDir
//        fun getDefaultShell(): String {
//            val os = System.getProperty("os.name").lowercase()
//            return if (os.contains("win")) {
//                "powershell"
//            } else {
//                "/bin/sh"
//            }
//        }
//
//        val command = listOfNotNull(getDefaultShell(), "./gradlew", "shadowjar")
//        log.info("execute command: `{}`. in directory : `{}`", command.joinToString(" "), rootDir.absolutePath)
//        val process = ProcessBuilder(*command.toTypedArray())
//            .directory(rootDir)
//            .redirectErrorStream(false)
//            .let {
//                it.environment()["JAVA_HOME"] = System.getProperty("java.home")
//                it.start()
//            }
//
//        val logThreads = logRealtimeProcessOutput(process)
//        val notTimeout = process.waitFor(90, TimeUnit.SECONDS)
//        logThreads.forEach { it.join() }
//        require(notTimeout) {
//            "timeout. command: `${command.joinToString(" ")}`"
//        }
//        require(process.exitValue() == 0) {
//            "process exited with non-zero value: `${process.exitValue()}`, command: `${command.joinToString(" ")}`"
//        }
//
//        buildComplete = true
    }

    @Synchronized
    fun run(
        configureBlock: ConfigureLiquibaseDslBlock,
    ): LiquibaseClient {
        val jarFilePath = "build/libs/fat-jar-integration-test-all.jar"
        val className = "momosetkn.liquibase.client.LiquibaseClient"

        val jarFileUrl = URL("file:" + jarFilePath)
        val classLoader = URLClassLoader(arrayOf(jarFileUrl))

        val clazz = classLoader.loadClass(className)
        val constructor = clazz.constructors[0]
        return constructor.newInstance(configureBlock) as momosetkn.liquibase.client.LiquibaseClient
    }

//    @Synchronized
//    fun run(vararg args: String) {
//        val command = arrayOf(
//            "java",
//            "-jar",
//            "build/libs/fat-jar-integration-test-all.jar",
//            "\"${args.joinToString(",")}\""
//        )
//        log.info("execute command: `{}`. in directory : `{}`", command.joinToString(" "), rootDir.absolutePath)
//        val process = ProcessBuilder(*command)
//            .directory(projectDir)
//            .redirectErrorStream(false)
//            .let {
//                it.environment()["JAVA_HOME"] = System.getProperty("java.home")
//                it.start()
//            }
//
//        val logThreads = logRealtimeProcessOutput(process)
//        val notTimeout = process.waitFor(20, TimeUnit.SECONDS)
//        logThreads.forEach { it.join() }
//        require(notTimeout) {
//            "timeout. command: `${command.joinToString(" ")}`"
//        }
//        require(process.exitValue() == 0) {
//            "process exited with non-zero value: `${process.exitValue()}`, command: `${command.joinToString(" ")}`"
//        }
//    }

    private fun logRealtimeProcessOutput(
        process: Process,
    ): List<Thread> {
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
        return listOf(
            inputStreamThread,
            errorStreamThread
        )
    }
}

/*
     // JARファイルのパス
        String jarFilePath = "path/to/yourfile.jar";
        // 実行したいクラス名
        String className = "com.example.MainClass";

        // JARファイルをクラスローダに追加
        URL jarFileUrl = new URL("file:" + jarFilePath);
        URLClassLoader classLoader = new URLClassLoader(new URL[] { jarFileUrl });

        // クラスをロードしてmainメソッドを実行
        Class<?> clazz = classLoader.loadClass(className);
        Method mainMethod = clazz.getDeclaredMethod("main", String[].class);
        String[] params = null; // 必要な引数をここに指定
        mainMethod.invoke(null, (Object) params);
 */
