package momosetkn.liquibase.client

internal object DateUtils {
    private val zoneOffset = java.time.ZoneOffset.ofHours(0)

    fun java.time.LocalDateTime.toJavaUtilDate(): java.util.Date {
        return java.util.Date.from(this.toInstant(zoneOffset))
    }
}
