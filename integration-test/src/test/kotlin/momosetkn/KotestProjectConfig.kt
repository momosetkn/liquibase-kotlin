package momosetkn

import io.kotest.core.config.AbstractProjectConfig
import momosetkn.liquibase.client.LiquibaseClient
import momosetkn.liquibase.client.configureLiquibase
import momosetkn.liquibase.command.client.LiquibaseCommandClient

class KotestProjectConfig : AbstractProjectConfig() {
    init {
        configureLiquibase {
            global {
                general {
                    showBanner = false
                }
            }
        }
        LiquibaseCommandClient.everyUseNewClassloader = true
        LiquibaseClient.everyUseNewClassloader = true
    }

    override val parallelism = 4
}
