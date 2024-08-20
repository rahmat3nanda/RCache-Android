package id.nesd.rcache

import java.util.Dictionary

interface RCaching {
    companion object {
        internal lateinit var instance: RCaching
    }

    fun save(data: ByteArray, key: RCache.Key)

    fun save(string: String, key: RCache.Key)

    fun save(boolean: Boolean, key: RCache.Key)

    fun save(int: Int, key: RCache.Key)

    fun save(array: Array<Any>, key: RCache.Key)

    fun save(dictionary: Dictionary<String, Any>, key: RCache.Key)

    fun save(double: Double, key: RCache.Key)

    fun save(float: Float, key: RCache.Key)

    fun readData(key: RCache.Key)

    fun readString(key: RCache.Key)

    fun readBoolean(key: RCache.Key)

    fun readInt(key: RCache.Key)

    fun readArray(key: RCache.Key)

    fun readDictionary(key: RCache.Key)

    fun readDouble(key: RCache.Key)

    fun readFloat(key: RCache.Key)
}