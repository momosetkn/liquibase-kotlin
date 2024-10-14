package momosetkn.liquibase.kotlin.parser

import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import liquibase.resource.ClassLoaderResourceAccessor

class KotlinCompiledResourceAccessorSpec : FunSpec({

    val mockClassLoaderResourceAccessor = mockk<ClassLoaderResourceAccessor>()
    val subject = KotlinCompiledResourceAccessor(mockClassLoaderResourceAccessor)
    beforeEach {
        every {
            mockClassLoaderResourceAccessor.search(any(), any<Boolean>())
        } returns emptyList()
        every {
            mockClassLoaderResourceAccessor.getAll(any())
        } returns emptyList()
    }

    context("search") {
        context("packageName + classFile") {
            val inputPath = "aaa/bbb/com.example.hoge/hogehoge.class"
            test("transform to filepath") {
                subject.search(inputPath, true)

                verify {
                    mockClassLoaderResourceAccessor.search(
                        withArg {
                            "aaa/bbb/com/example/hoge/hogehoge.class"
                        },
                        true,
                    )
                }
            }
        }
        context("packageName") {
            val inputPath = "aaa/bbb/com.example.hoge"
            test("transform to filepath") {
                subject.search(inputPath, true)

                verify {
                    mockClassLoaderResourceAccessor.search(
                        withArg {
                            "aaa/bbb/com/example/hoge/"
                        },
                        true,
                    )
                }
            }
        }
        context("packageName with slash suffix") {
            val inputPath = "aaa/bbb/com.example.hoge/"
            test("transform to filepath") {
                subject.search(inputPath, true)

                verify {
                    mockClassLoaderResourceAccessor.search(
                        withArg {
                            "aaa/bbb/com/example/hoge/"
                        },
                        true,
                    )
                }
            }
        }
    }

    context("getAll") {
        context("packageName + classFile") {
            val inputPath = "aaa/bbb/com.example.hoge/hogehoge.class"
            test("transform to filepath") {
                subject.getAll(inputPath)

                verify {
                    mockClassLoaderResourceAccessor.getAll(
                        withArg {
                            "aaa/bbb/com/example/hoge/hogehoge.class"
                        }
                    )
                }
            }
        }
        context("packageName") {
            val inputPath = "aaa/bbb/com.example.hoge"
            test("transform to filepath") {
                subject.getAll(inputPath)

                verify {
                    mockClassLoaderResourceAccessor.getAll(
                        withArg {
                            "aaa/bbb/com/example/hoge/"
                        }
                    )
                }
            }
        }
        context("packageName with slash suffix") {
            val inputPath = "aaa/bbb/com.example.hoge/"
            test("transform to filepath") {
                subject.getAll(inputPath)

                verify {
                    mockClassLoaderResourceAccessor.getAll(
                        withArg {
                            "aaa/bbb/com/example/hoge/"
                        }
                    )
                }
            }
        }
    }
})
