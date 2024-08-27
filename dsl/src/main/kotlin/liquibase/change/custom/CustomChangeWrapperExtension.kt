package liquibase.change.custom

fun CustomChangeWrapper.setCustomChange(customChange: CustomChange) {
    this.customChange = customChange
}
