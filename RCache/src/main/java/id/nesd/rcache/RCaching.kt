package id.nesd.rcache

/**
 * Interface for RCache.
 */
interface RCaching {

    /**
     * Gets an instance of RCaching
     * @return RCaching
     * # Example #
     * ```
     * // RCaching.instance
     * ```
     */
    companion object {
        lateinit var instance: RCaching
    }

    /**
     * Method for storing data with a defined key.
     *
     * @param byteArray ByteArray.
     * @param key RCache.Key.
     * # Example #
     * ```
     * // RCaching.instance.save(byteArray, RCache.Key("byteArray"))
     * ```
     */
    fun save(byteArray: ByteArray, key: RCache.Key)

    /**
     * Method for storing String with a defined key.
     *
     * @param string String.
     * @param key RCache.Key.
     * # Example #
     * ```
     * // RCaching.instance.save("data string", RCache.Key("string"))
     * ```
     */
    fun save(string: String, key: RCache.Key)

    /**
     * Method for storing Boolean with a defined key.
     *
     * @param bool Boolean.
     * @param key RCache.Key.
     * # Example #
     * ```
     * // RCaching.instance.save(true, RCache.Key("bool"))
     * ```
     */
    fun save(bool: Boolean, key: RCache.Key)

    /**
     * Method for storing Integer with a defined key.
     *
     * @param integer Int.
     * @param key RCache.Key.
     * # Example #
     * ```
     * // RCaching.instance.save(101, RCache.Key("integer"))
     * ```
     */
    fun save(integer: Int, key: RCache.Key)

    /**
     * Method for storing Array with a defined key.
     *
     * @param array List<T>.
     * @param key RCache.Key.
     * # Example #
     * ```
     * // RCaching.instance.save<Any>(listOf(101, "string", true), RCache.Key("array"))
     * ```
     */
    fun <T> save(array: List<T>, key: RCache.Key)

    /**
     * Method for storing Map<String, T> with a defined key.
     *
     * @param map Map<String, T>.
     * @param key RCache.Key.
     * # Example #
     * ```
     * // RCaching.instance.save<Any>(mapOf("bool" to true, "integer" to 101), RCache.Key("dictionary"))
     * ```
     */
    fun <T> save(map: Map<String, T>, key: RCache.Key)

    /**
     * Method for storing Double with a defined key.
     *
     * @param double Double.
     * @param key RCache.Key.
     * # Example #
     * ```
     * // RCaching.instance.save(2.0, RCache.Key("double"))
     * ```
     */
    fun save(double: Double, key: RCache.Key)

    /**
     * Method for storing Float with a defined key.
     *
     * @param float Float.
     * @param key RCache.Key.
     * # Example #
     * ```
     * // RCaching.instance.save(3.01f, RCache.Key("float"))
     * ```
     */
    fun save(float: Float, key: RCache.Key)

    /**
     * Method for storing data class with a defined key.
     *
     * @param dataClass data class.
     * @param key RCache.Key.
     * # Example #
     * ```
     * // RCaching.instance.save(MyDataClass(), RCache.Key("dataClass"))
     * ```
     */
    fun <T> save(dataClass: T, key: RCache.Key)

    /**
     * Method for getting data with a defined key.
     *
     * @param key RCache.Key.
     * @return ByteArray?
     * # Example #
     * ```
     * // RCaching.instance.readByteArray(RCache.Key("byteArray"))
     * ```
     */
    fun readByteArray(key: RCache.Key): ByteArray?

    /**
     * Method for getting String with a defined key.
     *
     * @param key RCache.Key.
     * @return String?
     * # Example #
     * ```
     * // RCaching.instance.readString(RCache.Key("string"))
     * ```
     */
    fun readString(key: RCache.Key): String?

    /**
     * Method for getting Boolean with a defined key.
     *
     * @param key RCache.Key.
     * @return Boolean?
     * # Example #
     * ```
     * // RCaching.instance.readBool(RCache.Key("bool"))
     * ```
     */
    fun readBool(key: RCache.Key): Boolean?

    /**
     * Method for getting Integer with a defined key.
     *
     * @param key RCache.Key.
     * @return Int?
     * # Example #
     * ```
     * // RCaching.instance.readInteger(RCache.Key("integer"))
     * ```
     */
    fun readInteger(key: RCache.Key): Int?

    /**
     * Method for getting Array with a defined key.
     *
     * @param key RCache.Key.
     * @return List<T>?
     * # Example #
     * ```
     * // RCaching.instance.readArray<Any>(RCache.Key("array"))
     * ```
     */
    fun <T> readArray(key: RCache.Key): List<T>?

    /**
     * Method for getting Map<String, T> with a defined key.
     *
     * @param key RCache.Key.
     * @return Map<String, T>?
     * # Example #
     * ```
     * // RCaching.instance.readMap<Any>(RCache.Key("map"))
     * ```
     */
    fun <T> readMap(key: RCache.Key): Map<String, T>?

    /**
     * Method for getting Double with a defined key.
     *
     * @param key RCache.Key.
     * @return Double?
     * # Example #
     * ```
     * // RCaching.instance.readDouble(RCache.Key("double"))
     * ```
     */
    fun readDouble(key: RCache.Key): Double?

    /**
     * Method for getting Float with a defined key.
     *
     * @param key RCache.Key.
     * @return Float?
     * # Example #
     * ```
     * // RCaching.instance.readFloat(RCache.Key("float"))
     * ```
     */
    fun readFloat(key: RCache.Key): Float?

    /**
     * Method for getting data class with a defined key.
     *
     * @param key RCache.Key.
     * @return data class?
     * # Example #
     * ```
     * // RCaching.instance.readDataClass<MyDataClass>(RCache.Key("float"), MyDataClass::java.class)
     * ```
     */
    fun <T> readDataClass(key: RCache.Key, classOfT: Class<T>): T?

    /**
     * Method for deleting data stored with a defined key.
     *
     * @param key RCache.Key.
     * # Example #
     * ```
     * // RCaching.instance.remove(RCache.Key("dictionary"))
     * ```
     */
    fun remove(key: RCache.Key)

    /**
     * Method for deleting all data stored via Caching.
     *
     * # Example #
     * ```
     * // RCaching.instance.clear()
     * ```
     */
    fun clear()
}