package momosetkn.liquibase.kotlin.dsl.util

internal object DateUtils {
    fun java.time.temporal.TemporalAccessor.toDate(): java.util.Date {
        val zonedDateTime = java.time.ZonedDateTime.from(this)
        return java.util.Date.from(zonedDateTime.toInstant())
    }
}
