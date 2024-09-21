@file:Suppress("ktlint:standard:filename", "MatchingDeclarationName")

package komapper

import org.komapper.annotation.KomapperColumn
import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable
import java.util.UUID

@KomapperEntity
@KomapperTable(name = "created_by_komapper")
data class CreatedByKomapper(
    @KomapperId @KomapperColumn(name = "id") val id: UUID,
    @KomapperColumn(name = "name") val name: String,
)
