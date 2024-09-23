package momosetkn

import io.kotest.core.config.AbstractProjectConfig
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class KotestProjectConfig : AbstractProjectConfig() {
    override val projectTimeout: Duration = 5.minutes
    override val timeout = 30.seconds
}
