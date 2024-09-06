package id.nesd.rcache

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.nesd.rcache.RCacheEncoding.fromBase64
import id.nesd.rcache.RCacheEncoding.toBase64

class KeychainRCache private constructor(context: Context) : RCaching {
    private val mKey: MasterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val identifier: String = context.identifier("KeychainRCache")

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        identifier,
        mKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val gson: Gson = Gson()

    companion object {
        @Volatile
        private var INSTANCE: KeychainRCache? = null

        @Synchronized
        fun getInstance(context: Context): KeychainRCache {
            return INSTANCE ?: KeychainRCache(context).also { INSTANCE = it }
        }
    }

    override fun save(byteArray: ByteArray, key: RCache.Key) {
        sharedPreferences.edit().putString(generate(key), byteArray.toBase64()).apply()
    }

    override fun save(string: String, key: RCache.Key) {
        sharedPreferences.edit().putString(generate(key), string).apply()
    }

    override fun save(bool: Boolean, key: RCache.Key) {
        sharedPreferences.edit().putBoolean(generate(key), bool).apply()
    }

    override fun save(integer: Int, key: RCache.Key) {
        sharedPreferences.edit().putInt(generate(key), integer).apply()
    }

    override fun <T> save(array: List<T>, key: RCache.Key) {
        sharedPreferences.edit().putString(generate(key), gson.toJson(array)).apply()
    }

    override fun <T> save(map: Map<String, T>, key: RCache.Key) {
        sharedPreferences.edit().putString(generate(key), gson.toJson(map)).apply()
    }

    override fun save(double: Double, key: RCache.Key) {
        sharedPreferences.edit().putString(generate(key), double.toString()).apply()
    }

    override fun save(float: Float, key: RCache.Key) {
        sharedPreferences.edit().putString(generate(key), float.toString()).apply()
    }

    override fun <T> save(dataClass: T, key: RCache.Key) {
        sharedPreferences.edit().putString(generate(key), gson.toJson(dataClass)).apply()
    }

    override fun readByteArray(key: RCache.Key): ByteArray? {
        val base64 = sharedPreferences.getString(generate(key), null) ?: return null
        return base64.fromBase64()
    }

    override fun readString(key: RCache.Key): String? {
        return sharedPreferences.getString(generate(key), null)
    }

    override fun readBool(key: RCache.Key): Boolean {
        return sharedPreferences.getBoolean(generate(key), false)
    }

    override fun readInteger(key: RCache.Key): Int? {
        return sharedPreferences.getInt(generate(key), -1).takeIf { it != -1 }
    }

    override fun <T> readArray(key: RCache.Key): List<T>? {
        val s = sharedPreferences.getString(generate(key), null) ?: return null
        val type = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(s, type)
    }

    override fun <T> readMap(key: RCache.Key): Map<String, T>? {
        val s = sharedPreferences.getString(generate(key), null) ?: return null

        // Deserialize JSON to Map<String, Any>
        val typeToken = object : TypeToken<Map<String, Any>>() {}.type
        val map: Map<String, Any>? = gson.fromJson(s, typeToken)

        // Convert LinkedTreeMap and LinkedHashMap to regular Map
        val convertedMap: Map<String, Any>? = map?.let { RCacheEncoding.convertMap(it) }

        // Convert the map to the desired type
        return convertedMap?.mapValues { it.value as T }
    }

    override fun readDouble(key: RCache.Key): Double? {
        return sharedPreferences.getString(generate(key), null)?.toDouble()
    }

    override fun readFloat(key: RCache.Key): Float? {
        return sharedPreferences.getString(generate(key), null)?.toFloat()
    }

    override fun <T> readDataClass(key: RCache.Key, classOfT: Class<T>): T? {
        val s = sharedPreferences.getString(generate(key), null) ?: return null
        return gson.fromJson(s, classOfT)
    }

    override fun remove(key: RCache.Key) {
        sharedPreferences.edit().remove(generate(key)).apply()
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    private fun generate(key: RCache.Key): String {
        return key.stringId(identifier)
    }
}