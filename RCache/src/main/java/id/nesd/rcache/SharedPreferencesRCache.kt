package id.nesd.rcache

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import id.nesd.rcache.utils.toRList
import id.nesd.rcache.utils.toRMap
import id.nesd.rcache.utils.toRString

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

    private val prefs: SharedPreferences =
        context.getSharedPreferences("SharedPreferencesRCache", Context.MODE_PRIVATE)

    override fun save(data: ByteArray, key: RCache.Key) {
        prefs.edit { putString(generate(key), data.joinToString(",") { it.toInt().toString() }) }
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

    override fun save(array: List<Any>, key: RCache.Key) {
        prefs.edit { putString(generate(key), array.toRString()) }
    }

    override fun save(dictionary: Map<String, Any>, key: RCache.Key) {
        prefs.edit { putString(generate(key), dictionary.toRString()) }
    }

    override fun save(double: Double, key: RCache.Key) {
        prefs.edit { putString(generate(key), double.toString()) }
    }

    override fun save(float: Float, key: RCache.Key) {
        prefs.edit { putString(generate(key), float.toString()) }
    }

    override fun readData(key: RCache.Key): ByteArray? {
        return prefs.getString(generate(key), null)?.split(",")?.map { it.toByte() }?.toByteArray()
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

    override fun readArray(key: RCache.Key): List<Any>? {
        val s = prefs.getString(generate(key), null) ?: return null
        return s.toRList()
    }

    override fun readDictionary(key: RCache.Key): Map<String, Any>? {
        val s = prefs.getString(generate(key), null) ?: return null
        return s.toRMap()
    }

    override fun readDouble(key: RCache.Key): Double? {
        return prefs.getString(generate(key), null)?.toDouble()
    }

    override fun readFloat(key: RCache.Key): Float? {
        return prefs.getString(generate(key), null)?.toFloat()
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
        return "SharedPreferencesRCache-${key.rawValue}"
    }
}
