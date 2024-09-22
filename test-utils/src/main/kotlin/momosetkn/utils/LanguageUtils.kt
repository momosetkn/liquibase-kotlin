package momosetkn.utils

import org.intellij.lang.annotations.Language

object LanguageUtils {
    fun sql(
        @Language("sql") s: String
    ) = s
}
