package momosetkn.liquibase.kotlin.dsl

import liquibase.change.ConstraintsConfig
import liquibase.changelog.DatabaseChangeLog
import momosetkn.liquibase.kotlin.dsl.Expressions.evalExpressions

@ChangeLogDslMarker
class ConstraintDsl(
    private val changeLog: DatabaseChangeLog,
) {
    private val constraint: ConstraintsConfig = ConstraintsConfig()

    internal operator fun invoke(
        block: ConstraintDsl.() -> Unit,
    ): ConstraintsConfig {
        block(this)
        return constraint
    }

    fun constraints(
        nullable: Boolean? = null,
        notNullConstraintName: String? = null,
        primaryKey: Boolean? = null,
        primaryKeyName: String? = null,
        primaryKeyTablespace: String? = null,
        references: String? = null,
        referencedTableCatalogName: String? = null,
        referencedTableSchemaName: String? = null,
        referencedTableName: String? = null,
        referencedColumnNames: String? = null,
        unique: Boolean? = null,
        uniqueConstraintName: String? = null,
        checkConstraint: String? = null,
        deleteCascade: Boolean? = null,
        foreignKeyName: String? = null,
        initiallyDeferred: Boolean? = null,
        deferrable: Boolean? = null,
        validateNullable: Boolean? = null,
        validateUnique: Boolean? = null,
        validatePrimaryKey: Boolean? = null,
        validateForeignKey: Boolean? = null,
    ) {
        constraint.apply {
            this.isNullable = nullable
            this.notNullConstraintName = notNullConstraintName?.evalExpressions(changeLog)
            this.isPrimaryKey = primaryKey
            this.primaryKeyName = primaryKeyName?.evalExpressions(changeLog)
            this.primaryKeyTablespace = primaryKeyTablespace?.evalExpressions(changeLog)
            this.references = references?.evalExpressions(changeLog)
            this.referencedTableCatalogName =
                referencedTableCatalogName?.evalExpressions(changeLog)
            this.referencedTableSchemaName = referencedTableSchemaName?.evalExpressions(changeLog)
            this.referencedTableName = referencedTableName?.evalExpressions(changeLog)
            this.referencedColumnNames = referencedColumnNames?.evalExpressions(changeLog)
            this.isUnique = unique
            this.uniqueConstraintName = uniqueConstraintName?.evalExpressions(changeLog)
            this.checkConstraint = checkConstraint?.evalExpressions(changeLog)
            this.isDeleteCascade = deleteCascade
            this.foreignKeyName = foreignKeyName?.evalExpressions(changeLog)
            this.isInitiallyDeferred = initiallyDeferred
            this.isDeferrable = deferrable
            this.validateNullable = validateNullable
            this.validateUnique = validateUnique
            this.validatePrimaryKey = validatePrimaryKey
            this.validateForeignKey = validateForeignKey
        }
    }
}
