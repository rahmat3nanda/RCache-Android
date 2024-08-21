package id.nesd.rcache

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import id.nesd.rcache.utils.toRList
import id.nesd.rcache.utils.toRMap
import id.nesd.rcache.utils.toRString

class KeychainRCache private constructor(context: Context) : RCaching {
    private val mKey: MasterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "KeychainRCache",
        mKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        @Volatile
        private var INSTANCE: KeychainRCache? = null

        @Synchronized
        fun getInstance(context: Context): KeychainRCache {
            return INSTANCE ?: KeychainRCache(context).also { INSTANCE = it }
        }
    }

    override fun save(data: ByteArray, key: RCache.Key) {
        sharedPreferences.edit().putString(generate(key), data.toBase64()).apply()
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

    override fun save(array: List<Any>, key: RCache.Key) {
        sharedPreferences.edit().putString(generate(key), array.toRString()).apply()
    }

    override fun save(dictionary: Map<String, Any>, key: RCache.Key) {
        sharedPreferences.edit().putString(generate(key), dictionary.toRString()).apply()
    }

    override fun save(double: Double, key: RCache.Key) {
        sharedPreferences.edit().putString(generate(key), double.toString()).apply()
    }

    override fun save(float: Float, key: RCache.Key) {
        sharedPreferences.edit().putString(generate(key), float.toString()).apply()
    }

    override fun readData(key: RCache.Key): ByteArray? {
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

    override fun readArray(key: RCache.Key): List<Any>? {
        val s = sharedPreferences.getString(generate(key), null) ?: return null
        return s.toRList()
    }

    override fun readDictionary(key: RCache.Key): Map<String, Any>? {
        val s = sharedPreferences.getString(generate(key), null) ?: return null
        return s.toRMap()
    }

    override fun readDouble(key: RCache.Key): Double? {
        return sharedPreferences.getString(generate(key), null)?.toDouble()
    }

    override fun readFloat(key: RCache.Key): Float? {
        return sharedPreferences.getString(generate(key), null)?.toFloat()
    }

    override fun remove(key: RCache.Key) {
        sharedPreferences.edit().remove(generate(key)).apply()
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    private fun generate(key: RCache.Key): String {
        return "KeychainCache-${key.rawValue}"
    }

    // Extension functions for encoding and decoding Base64
    private fun ByteArray.toBase64(): String {
        return android.util.Base64.encodeToString(this, android.util.Base64.DEFAULT)
    }

    private fun String.fromBase64(): ByteArray {
        return android.util.Base64.decode(this, android.util.Base64.DEFAULT)
    }
}
