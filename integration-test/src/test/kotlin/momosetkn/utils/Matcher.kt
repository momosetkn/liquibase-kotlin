package momosetkn.utils

import io.kotest.matchers.shouldBe

infix fun String.shouldMatchWithoutLineBreaks(expected: String?): String {
    val actualWithoutLineBreaks = this.lines().joinToString("\n")
    val expectedWithoutLineBreaks = this.lines().joinToString("\n")
    return actualWithoutLineBreaks shouldBe expectedWithoutLineBreaks
}
