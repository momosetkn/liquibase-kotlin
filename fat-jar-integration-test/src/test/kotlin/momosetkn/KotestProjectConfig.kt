package momosetkn

import io.kotest.core.config.AbstractProjectConfig
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class KotestProjectConfig : AbstractProjectConfig() {
    override val projectTimeout = 30.minutes
    override val timeout = 30.seconds
    override val invocationTimeout = 1_000L
}