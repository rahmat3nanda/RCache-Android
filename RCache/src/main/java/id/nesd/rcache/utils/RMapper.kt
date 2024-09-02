package id.nesd.rcache.utils

fun List<Any>.toRString(): String {
    return this.joinToString("#RCache#")
}

fun String.toRList(): List<Any> {
    if (this.isEmpty()) {
        return emptyList()
    }
    return this.split("#RCache#")
}

fun Map<String, Any>.toRString(): String {
    return this.entries.joinToString("#RCache#")
}

fun String.toRMap(): Map<String, Any> {
    if (this.isEmpty()) {
        return emptyMap()
    }
    return this.split("#RCache#").associate {
        val (key, value) = it.split("=")
        key to value
    }
}