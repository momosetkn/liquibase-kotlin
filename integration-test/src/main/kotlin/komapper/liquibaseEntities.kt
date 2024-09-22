@file:Suppress("ktlint:standard:filename")

package komapper

import org.komapper.annotation.KomapperColumn
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable
import java.time.LocalDateTime

@KomapperEntity
@KomapperTable(name = "databasechangelog")
data class Databasechangelog(
    @KomapperId @KomapperColumn(name = "id") val id: String,
    @KomapperColumn(name = "author") val author: String,
    @KomapperColumn(name = "filename") val filename: String,
    @KomapperColumn(name = "dateexecuted") val dateexecuted: LocalDateTime,
    @KomapperColumn(name = "orderexecuted") val orderexecuted: Int,
    @KomapperColumn(name = "exectype") val exectype: String,
    @KomapperColumn(name = "md5sum") val md5sum: String?,
    @KomapperColumn(name = "description") val description: String?,
    @KomapperColumn(name = "comments") val comments: String?,
    @KomapperColumn(name = "tag") val tag: String?,
    @KomapperColumn(name = "liquibase") val liquibase: String?,
    @KomapperColumn(name = "contexts") val contexts: String?,
    @KomapperColumn(name = "labels") val labels: String?,
    @KomapperColumn(name = "deployment_id") val deploymentId: String?
)

@KomapperEntity
@KomapperTable(name = "databasechangeloglock")
data class Databasechangeloglock(
    @KomapperId @KomapperColumn(name = "id") val id: Int,
    @KomapperColumn(name = "locked") val locked: Boolean,
    @KomapperColumn(name = "lockgranted") val lockgranted: LocalDateTime?,
    @KomapperColumn(name = "lockedby") val lockedby: String?
)
