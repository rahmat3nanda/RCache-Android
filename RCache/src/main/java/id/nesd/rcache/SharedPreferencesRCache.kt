package id.nesd.rcache

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.nesd.rcache.RCacheEncoding.fromBase64
import id.nesd.rcache.RCacheEncoding.toBase64

class SharedPreferencesRCache private constructor(context: Context) : RCaching {

    companion object {
        @Volatile
        private var instance: SharedPreferencesRCache? = null
        private val lock = Any()

        fun getInstance(context: Context): SharedPreferencesRCache {
            return instance ?: synchronized(lock) {
                instance ?: SharedPreferencesRCache(context).also { instance = it }
            }
        }
    }

    private val identifier: String = context.identifier("SharedPreferencesRCache")

    private val prefs: SharedPreferences =
        context.getSharedPreferences(identifier, Context.MODE_PRIVATE)

    private val gson: Gson = Gson()

    override fun save(byteArray: ByteArray, key: RCache.Key) {
        prefs.edit { putString(generate(key), byteArray.toBase64()) }
    }

    override fun save(string: String, key: RCache.Key) {
        prefs.edit { putString(generate(key), string) }
    }

    override fun save(bool: Boolean, key: RCache.Key) {
        prefs.edit { putBoolean(generate(key), bool) }
    }

    override fun save(integer: Int, key: RCache.Key) {
        prefs.edit { putInt(generate(key), integer) }
    }

    override fun <T> save(array: List<T>, key: RCache.Key) {
        prefs.edit { putString(generate(key), gson.toJson(array)) }
    }

    override fun <T> save(map: Map<String, T>, key: RCache.Key) {
        prefs.edit { putString(generate(key), gson.toJson(map)) }
    }

    override fun save(double: Double, key: RCache.Key) {
        prefs.edit { putString(generate(key), double.toString()) }
    }

    override fun save(float: Float, key: RCache.Key) {
        prefs.edit { putString(generate(key), float.toString()) }
    }

    override fun <T> save(dataClass: T, key: RCache.Key) {
        prefs.edit().putString(generate(key), gson.toJson(dataClass)).apply()
    }

    override fun readByteArray(key: RCache.Key): ByteArray? {
        val base64 = prefs.getString(generate(key), null) ?: return null
        return base64.fromBase64()
    }

    override fun readString(key: RCache.Key): String? {
        return prefs.getString(generate(key), null)
    }

    override fun readBool(key: RCache.Key): Boolean? {
        return prefs.getBoolean(generate(key), false).takeIf { prefs.contains(generate(key)) }
    }

    override fun readInteger(key: RCache.Key): Int? {
        return prefs.getInt(generate(key), Int.MIN_VALUE).takeIf { prefs.contains(generate(key)) }
    }

    override fun <T> readArray(key: RCache.Key): List<T>? {
        val s = prefs.getString(generate(key), null) ?: return null
        val type = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(s, type)
    }

    override fun <T> readMap(key: RCache.Key): Map<String, T>? {
        val s = prefs.getString(generate(key), null) ?: return null

        // Deserialize JSON to Map<String, Any>
        val typeToken = object : TypeToken<Map<String, Any>>() {}.type
        val map: Map<String, Any>? = gson.fromJson(s, typeToken)

        // Convert LinkedTreeMap and LinkedHashMap to regular Map
        val convertedMap: Map<String, Any>? = map?.let { RCacheEncoding.convertMap(it) }

        // Convert the map to the desired type
        return convertedMap?.mapValues { it.value as T }
    }

    override fun readDouble(key: RCache.Key): Double? {
        return prefs.getString(generate(key), null)?.toDouble()
    }

    override fun readFloat(key: RCache.Key): Float? {
        return prefs.getString(generate(key), null)?.toFloat()
    }

    override fun <T> readDataClass(key: RCache.Key, classOfT: Class<T>): T? {
        val s = prefs.getString(generate(key), null) ?: return null
        return gson.fromJson(s, classOfT)
    }

    override fun remove(key: RCache.Key) {
        prefs.edit { remove(generate(key)) }
    }

    override fun clear() {
        prefs.edit {
            clear()
        }
    }

    private fun generate(key: RCache.Key): String {
        return key.stringId(identifier)
    }
}
