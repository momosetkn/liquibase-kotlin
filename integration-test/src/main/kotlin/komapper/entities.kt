@file:Suppress("ktlint:standard:filename", "MatchingDeclarationName")

package komapper

import org.komapper.annotation.KomapperColumn
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable
import java.util.UUID

@KomapperEntity
@KomapperTable(name = "company")
data class Company(
    @KomapperId @KomapperColumn(name = "id") val id: UUID,
    @KomapperColumn(name = "name") val name: String,
)

@KomapperEntity
@KomapperTable(name = "company2")
data class Company2(
    @KomapperId @KomapperColumn(name = "id") val id: UUID,
    @KomapperColumn(name = "name") val name: String,
)
