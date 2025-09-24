package momosetkn.liquibase.kotlin.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import liquibase.resource.ClassLoaderResourceAccessor

class KotlinCompiledResourceAccessorSpec : FunSpec({
    val mockClassLoaderResourceAccessor = mockk<ClassLoaderResourceAccessor>(relaxed = true)
    val kotlinCompiledResourceAccessor = KotlinCompiledResourceAccessor(mockClassLoaderResourceAccessor)
    beforeEach {
        clearMocks(mockClassLoaderResourceAccessor)
        every {
            mockClassLoaderResourceAccessor.search(any(), any<Boolean>())
        } returns emptyList()
        every {
            mockClassLoaderResourceAccessor.getAll(any())
        } returns emptyList()
    }

    context("search") {
        fun subject(inputPath: String) {
            kotlinCompiledResourceAccessor.search(inputPath, true)
        }
        context("directory + packageName + className") {
            val inputPath = "aaa/bbb/com.example.hoge/Hogehoge"
            test("transform to filepath(directory by package + file by class)") {
                subject(inputPath)

                val capturedArgs = mutableListOf<String>()
                verify(exactly = 2) {
                    mockClassLoaderResourceAccessor.search(capture(capturedArgs), true)
                    mockClassLoaderResourceAccessor.search(capture(capturedArgs), true)
                }
                capturedArgs.toSet() shouldBe setOf(
                    "aaa/bbb/com/example/hoge/Hogehoge",
                    "aaa/bbb/com/example/hoge/Hogehoge.class"
                )
            }
        }
        context("directory + packageName + classFile") {
            val inputPath = "aaa/bbb/com.example.hoge/Hogehoge.class"
            test("transform to filepath(file by class)") {
                subject(inputPath)

                val capturedArgs = mutableListOf<String>()
                verify(exactly = 1) {
                    mockClassLoaderResourceAccessor.search(capture(capturedArgs), true)
                }
                capturedArgs.toSet() shouldBe setOf(
                    "aaa/bbb/com/example/hoge/Hogehoge.class"
                )
            }
        }
        context("directory + packageName") {
            val inputPath = "aaa/bbb/com.example.hoge"
            test("transform to filepath(directory by package + file by class)") {
                subject(inputPath)

                val capturedArgs = mutableListOf<String>()
                verify(exactly = 2) {
                    mockClassLoaderResourceAccessor.search(capture(capturedArgs), true)
                    mockClassLoaderResourceAccessor.search(capture(capturedArgs), true)
                }
                capturedArgs.toSet() shouldBe setOf(
                    "aaa/bbb/com/example/hoge",
                    "aaa/bbb/com/example/hoge.class"
                )
            }
        }
        context("directory + packageName with slash suffix") {
            val inputPath = "aaa/bbb/com.example.hoge/"
            test("transform to filepath(directory by package)") {
                subject(inputPath)

                val capturedArgs = mutableListOf<String>()
                verify(exactly = 1) {
                    mockClassLoaderResourceAccessor.search(capture(capturedArgs), true)
                }
                capturedArgs.toSet() shouldBe setOf(
                    "aaa/bbb/com/example/hoge/",
                )
            }
        }
        context("packageName only") {
            val inputPath = "com.example.hoge"
            test("transform to filepath(directory by package + file by class)") {
                subject(inputPath)

                val capturedArgs = mutableListOf<String>()
                verify(exactly = 2) {
                    mockClassLoaderResourceAccessor.search(capture(capturedArgs), true)
                    mockClassLoaderResourceAccessor.search(capture(capturedArgs), true)
                }
                capturedArgs.toSet() shouldBe setOf(
                    "com/example/hoge",
                    "com/example/hoge.class",
                )
            }
        }
        context("packageName with slash suffix") {
            val inputPath = "com.example.hoge/"
            test("transform to filepath(directory by package)") {
                subject(inputPath)

                val capturedArgs = mutableListOf<String>()
                verify(exactly = 1) {
                    mockClassLoaderResourceAccessor.search(capture(capturedArgs), true)
                }
                capturedArgs.toSet() shouldBe setOf(
                    "com/example/hoge/",
                )
            }
        }
    }

    context("getAll") {
        fun subject(inputPath: String) {
            kotlinCompiledResourceAccessor.getAll(inputPath)
        }
        context("directory + packageName + className") {
            val inputPath = "aaa/bbb/com.example.hoge/Hogehoge"
            test("transform to filepath(directory by package + file by class)") {
                subject(inputPath)

                val capturedArgs = mutableListOf<String>()
                verify(exactly = 2) {
                    mockClassLoaderResourceAccessor.getAll(capture(capturedArgs))
                    mockClassLoaderResourceAccessor.getAll(capture(capturedArgs))
                }
                capturedArgs.toSet() shouldBe setOf(
                    "aaa/bbb/com/example/hoge/Hogehoge",
                    "aaa/bbb/com/example/hoge/Hogehoge.class"
                )
            }
        }
        context("directory + packageName + classFile") {
            val inputPath = "aaa/bbb/com.example.hoge/Hogehoge.class"
            test("transform to filepath(file by class)") {
                subject(inputPath)

                val capturedArgs = mutableListOf<String>()
                verify(exactly = 1) {
                    mockClassLoaderResourceAccessor.getAll(capture(capturedArgs))
                }
                capturedArgs.toSet() shouldBe setOf(
                    "aaa/bbb/com/example/hoge/Hogehoge.class"
                )
            }
        }
        context("directory + packageName") {
            val inputPath = "aaa/bbb/com.example.hoge"
            test("transform to filepath(directory by package + file by class)") {
                subject(inputPath)

                val capturedArgs = mutableListOf<String>()
                verify(exactly = 2) {
                    mockClassLoaderResourceAccessor.getAll(capture(capturedArgs))
                    mockClassLoaderResourceAccessor.getAll(capture(capturedArgs))
                }
                capturedArgs.toSet() shouldBe setOf(
                    "aaa/bbb/com/example/hoge",
                    "aaa/bbb/com/example/hoge.class"
                )
            }
        }
        context("directory + packageName with slash suffix") {
            val inputPath = "aaa/bbb/com.example.hoge/"
            test("transform to filepath(directory by package)") {
                subject(inputPath)

                val capturedArgs = mutableListOf<String>()
                verify(exactly = 1) {
                    mockClassLoaderResourceAccessor.getAll(capture(capturedArgs))
                }
                capturedArgs.toSet() shouldBe setOf(
                    "aaa/bbb/com/example/hoge/",
                )
            }
        }
        context("packageName only") {
            val inputPath = "com.example.hoge"
            test("transform to filepath(directory by package + file by class)") {
                subject(inputPath)

                val capturedArgs = mutableListOf<String>()
                verify(exactly = 2) {
                    mockClassLoaderResourceAccessor.getAll(capture(capturedArgs))
                    mockClassLoaderResourceAccessor.getAll(capture(capturedArgs))
                }
                capturedArgs.toSet() shouldBe setOf(
                    "com/example/hoge",
                    "com/example/hoge.class",
                )
            }
        }
        context("packageName with slash suffix") {
            val inputPath = "com.example.hoge/"
            test("transform to filepath(directory by package)") {
                subject(inputPath)

                val capturedArgs = mutableListOf<String>()
                verify(exactly = 1) {
                    mockClassLoaderResourceAccessor.getAll(capture(capturedArgs))
                }
                capturedArgs.toSet() shouldBe setOf(
                    "com/example/hoge/",
                )
            }
        }
    }
})
