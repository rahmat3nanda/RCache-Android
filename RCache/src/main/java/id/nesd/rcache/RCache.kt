package id.nesd.rcache

import android.content.Context

/**
 * This is a class for managing cache
 */
object RCache {

    private lateinit var appContext: Context

    /**
     * Initialize the RCache with the application context.
     *
     * @param context The application context.
     */
    fun initialize(context: Context) {
        // Use the application context to prevent memory leaks
        this.appContext = context.applicationContext
    }

    /**
     * Get a RCaching instance with common level. Used to store RCache for regular data.
     *
     * @return RCaching
     *
     * @note This instance is an instance of SharedPreferencesRCache.
     */
    val common: RCaching
        get() = SharedPreferencesRCache.getInstance(appContext)

    /**
     * Get a RCaching instance with credentials level. Used to store RCache for credentials data.
     *
     * @return RCaching
     *
     * @note This instance is an instance of KeychainRCache.
     */
    val credentials: RCaching
        get() = KeychainRCache.getInstance(appContext)

    /**
     * Method to delete all data for common and credential levels stored via RCache/RCaching.
     */
    fun clear() {
        common.clear()
        credentials.clear()
    }

    // MARK: - Key
    class Key(val rawValue: String)
}