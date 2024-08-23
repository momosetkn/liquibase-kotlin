package momosetkn.liquibase.kotlin.dsl

import liquibase.Scope
import liquibase.change.Change
import liquibase.change.ChangeFactory
import liquibase.changelog.ChangeSet
import liquibase.exception.ChangeLogParseException

class ChangeSetSupport(
    private val changeSet: ChangeSet,
    private val inRollback: Boolean = false,
) {
    private val changeFactory = Scope.getCurrentScope().getSingleton(ChangeFactory::class.java)

    fun createChange(name: String): Change {
        val change = changeFactory.create(name)
        change.changeSet = changeSet
        return change
            ?: throw ChangeLogParseException(
                "ChangeSet '${changeSet.id}': '$name' is not a valid element of a ChangeSet",
            )
    }

    fun addChange(change: Change) {
        if (inRollback) {
            changeSet.addRollbackChange(change)
        } else {
            changeSet.addChange(change)
        }
    }
}
