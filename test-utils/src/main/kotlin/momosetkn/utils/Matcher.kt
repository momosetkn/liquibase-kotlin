package momosetkn.utils

import io.kotest.matchers.shouldBe
import momosetkn.utils.StringUtils.toLf

infix fun String.shouldMatchWithoutLineBreaks(expected: String): String {
    return this.toLf() shouldBe expected.toLf()
}
