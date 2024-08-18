package org.liquibase.kotlin.dsl.util

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.liquibase.kotlin.dsl.util.StringsUtil.splitAndTrim

class StringsUtilSpec : FunSpec({
    test("splitAndTrim with normal input") {
        val testString = " a, b,c ,d , e ,"
        val result = testString.splitAndTrim()
        result shouldBe listOf("a", "b", "c", "d", "e")
    }

    test("splitAndTrim with extra spaces") {
        val testString = "   a,    b, c   ,d ,     e ,    "
        val result = testString.splitAndTrim()
        result shouldBe listOf("a", "b", "c", "d", "e")
    }

    test("splitAndTrim with no spaces") {
        val testString = "a,b,c,d,e,"
        val result = testString.splitAndTrim()
        result shouldBe listOf("a", "b", "c", "d", "e")
    }

    test("splitAndTrim with empty string") {
        val testString = ""
        val result = testString.splitAndTrim()
        result shouldBe emptyList()
    }
})
