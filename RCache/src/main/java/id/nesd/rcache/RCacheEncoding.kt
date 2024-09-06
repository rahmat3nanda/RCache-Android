package id.nesd.rcache

import android.content.Context
import com.google.gson.internal.LinkedTreeMap

internal object RCacheEncoding {

    internal fun ByteArray.toBase64(): String {
        return android.util.Base64.encodeToString(this, android.util.Base64.DEFAULT)
    }

    internal fun String.fromBase64(): ByteArray {
        return android.util.Base64.decode(this, android.util.Base64.DEFAULT)
    }

    fun convertMap(map: Map<String, Any>): Map<String, Any> {
        return map.mapValues { entry ->
            when (val value = entry.value) {
                is LinkedTreeMap<*, *> -> convertMap(value as Map<String, Any>)
                is LinkedHashMap<*, *> -> convertMap(value as Map<String, Any>)
                else -> value
            }
        }
    }
}

fun Context.identifier(from: String): String {
    return "${this.packageName}-$from"
}

fun RCache.Key.stringId(from: String): String {
    return "${from}-${rawValue}".replace(" ", "_")
}