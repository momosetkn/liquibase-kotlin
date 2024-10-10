package momosetkn

import io.kotest.core.config.AbstractProjectConfig
import momosetkn.liquibase.client.configureLiquibase

class KotestProjectConfig : AbstractProjectConfig() {
    init {
        configureLiquibase {
            global {
                general {
                    showBanner = false
                }
            }
        }
    }
}
