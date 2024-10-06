package momosetkn.liquibase.kotlin.change.custom.core

import liquibase.change.custom.CustomChange
import liquibase.change.custom.CustomChangeWrapper
import liquibase.change.custom.setCustomChange
import momosetkn.liquibase.kotlin.dsl.ChangeSetDsl

fun ChangeSetDsl.addCustomChange(change: CustomChange) {
    val customChangeWrapper = this.changeSetSupport.createChange("customChange") as CustomChangeWrapper
    customChangeWrapper.setCustomChange(change)
    changeSetSupport.addChange(customChangeWrapper)
}
