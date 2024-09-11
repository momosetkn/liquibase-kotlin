package liquibase.change.custom

internal fun CustomChangeWrapper.setCustomChange(customChange: CustomChange) {
    // this.customChange is package private.
    // it is necessary to perform this configuration process within the same package.
    this.customChange = customChange
}
