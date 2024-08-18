package momosetkn.liquibase.kotlin.dsl

import liquibase.change.ConstraintsConfig
import liquibase.changelog.DatabaseChangeLog

@ChangeLogDslMarker
class ConstraintDsl(
    private val changeLog: DatabaseChangeLog,
) {
    internal val constraint: ConstraintsConfig = ConstraintsConfig()

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
            this.notNullConstraintName = notNullConstraintName.expandExpressions(changeLog)
            this.isPrimaryKey = primaryKey
            this.primaryKeyName = primaryKeyName.expandExpressions(changeLog)
            this.primaryKeyTablespace = primaryKeyTablespace.expandExpressions(changeLog)
            this.references = references.expandExpressions(changeLog)
            this.referencedTableCatalogName =
                referencedTableCatalogName.expandExpressions(changeLog)
            this.referencedTableSchemaName = referencedTableSchemaName.expandExpressions(changeLog)
            this.referencedTableName = referencedTableName.expandExpressions(changeLog)
            this.referencedColumnNames = referencedColumnNames.expandExpressions(changeLog)
            this.isUnique = unique
            this.uniqueConstraintName = uniqueConstraintName.expandExpressions(changeLog)
            this.checkConstraint = checkConstraint.expandExpressions(changeLog)
            this.isDeleteCascade = deleteCascade
            this.foreignKeyName = foreignKeyName.expandExpressions(changeLog)
            this.isInitiallyDeferred = initiallyDeferred
            this.isDeferrable = deferrable
            this.validateNullable = validateNullable
            this.validateUnique = validateUnique
            this.validatePrimaryKey = validatePrimaryKey
            this.validateForeignKey = validateForeignKey
        }
    }
}
