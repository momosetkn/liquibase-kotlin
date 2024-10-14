package momosetkn.utils

data class Version(
    val major: Int,
    val minor: Int,
    val patch: Int,
) : Comparable<Version> {
    override fun compareTo(other: Version): Int {
        return compareValuesBy(this, other, Version::major, Version::minor, Version::patch)
    }
}

val maxVersion = Version(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)

fun String.toVersion(): Version {
    return runCatching {
        val (major, minor, patch) = this.split(".").map { it.toInt() }
        return Version(major = major, minor = minor, patch = patch)
    }.fold(
        onSuccess = { it },
        onFailure = { maxVersion }
    )
}
