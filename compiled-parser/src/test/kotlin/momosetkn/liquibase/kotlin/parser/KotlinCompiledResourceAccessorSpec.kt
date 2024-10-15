package momosetkn.liquibase.kotlin.parser

import io.kotest.assertions.NoopErrorCollector.subject
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import liquibase.resource.ClassLoaderResourceAccessor

class KotlinCompiledResourceAccessorSpec : FunSpec({

    val mockClassLoaderResourceAccessor = mockk<ClassLoaderResourceAccessor>()
    val kotlinCompiledResourceAccessor = KotlinCompiledResourceAccessor(mockClassLoaderResourceAccessor)
    beforeEach {
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
        context("directory + packageName + classFile") {
            val inputPath = "aaa/bbb/com.example.hoge/hogehoge.class"
            test("transform to filepath") {
                subject(inputPath)

                verify {
                    mockClassLoaderResourceAccessor.search(
                        withArg {
                            it shouldBe "aaa/bbb/com/example/hoge/hogehoge.class"
                        },
                        true,
                    )
                }
            }
        }
        context("directory + packageName + txtFile") {
            val inputPath = "aaa/bbb/com.example.hoge/hogehoge.txt"
            test("transform to filepath") {
                subject(inputPath)

                verify {
                    mockClassLoaderResourceAccessor.search(
                        withArg {
                            it shouldBe "aaa/bbb/com/example/hoge/hogehoge/txt"
                        },
                        true,
                    )
                }
            }
        }
        context("directory + packageName") {
            val inputPath = "aaa/bbb/com.example.hoge"
            test("transform to filepath") {
                subject(inputPath)

                verify {
                    mockClassLoaderResourceAccessor.search(
                        withArg {
                            it shouldBe "aaa/bbb/com/example/hoge"
                        },
                        true,
                    )
                }
            }
        }
        context("directory + packageName with slash suffix") {
            val inputPath = "aaa/bbb/com.example.hoge/"
            test("transform to filepath") {
                subject(inputPath)

                verify {
                    mockClassLoaderResourceAccessor.search(
                        withArg {
                            it shouldBe "aaa/bbb/com/example/hoge/"
                        },
                        true,
                    )
                }
            }
        }
        context("packageName only") {
            val inputPath = "com.example.hoge"
            test("transform to filepath") {
                subject(inputPath)

                verify {
                    mockClassLoaderResourceAccessor.search(
                        withArg {
                            it shouldBe "com/example/hoge"
                        },
                        true,
                    )
                }
            }
        }
        context("packageName with slash suffix") {
            val inputPath = "com.example.hoge/"
            test("transform to filepath") {
                subject(inputPath)

                verify {
                    mockClassLoaderResourceAccessor.search(
                        withArg {
                            it shouldBe "com/example/hoge/"
                        },
                        true,
                    )
                }
            }
        }
    }
    context("getAll") {
        fun subject(inputPath: String) {
            kotlinCompiledResourceAccessor.getAll(inputPath)
        }
        context("directory + packageName + classFile") {
            val inputPath = "aaa/bbb/com.example.hoge/hogehoge.class"
            test("transform to filepath") {
                subject(inputPath)

                verify {
                    mockClassLoaderResourceAccessor.getAll(
                        withArg {
                            it shouldBe "aaa/bbb/com/example/hoge/hogehoge.class"
                        },
                    )
                }
            }
        }
        context("directory + packageName") {
            val inputPath = "aaa/bbb/com.example.hoge"
            test("transform to filepath") {
                subject(inputPath)

                verify {
                    mockClassLoaderResourceAccessor.getAll(
                        withArg {
                            it shouldBe "aaa/bbb/com/example/hoge"
                        },
                    )
                }
            }
        }
        context("directory + packageName with slash suffix") {
            val inputPath = "aaa/bbb/com.example.hoge/"
            test("transform to filepath") {
                subject(inputPath)

                verify {
                    mockClassLoaderResourceAccessor.getAll(
                        withArg {
                            it shouldBe "aaa/bbb/com/example/hoge/"
                        },
                    )
                }
            }
        }
        context("packageName only") {
            val inputPath = "com.example.hoge"
            test("transform to filepath") {
                subject(inputPath)

                verify {
                    mockClassLoaderResourceAccessor.getAll(
                        withArg {
                            it shouldBe "com/example/hoge"
                        },
                    )
                }
            }
        }
        context("packageName with slash suffix") {
            val inputPath = "com.example.hoge/"
            test("transform to filepath") {
                subject(inputPath)

                verify {
                    mockClassLoaderResourceAccessor.getAll(
                        withArg {
                            it shouldBe "com/example/hoge/"
                        },
                    )
                }
            }
        }
    }
})
