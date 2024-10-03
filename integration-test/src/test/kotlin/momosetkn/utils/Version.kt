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

fun String.toVersion(): Version {
    val (major, minor, patch) = this.split(".").map { it.toInt() }
    return Version(major = major, minor = minor, patch = patch)
}
