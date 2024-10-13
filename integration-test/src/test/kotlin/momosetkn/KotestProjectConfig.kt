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

    // TODO: off parallelism. because liquibase generateChangeLog is not threadSafe.
//    override val parallelism = 4
}
