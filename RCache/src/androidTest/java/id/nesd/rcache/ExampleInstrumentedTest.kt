package id.nesd.rcache

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val data = RCache.Key("data")
    private val string = RCache.Key("string")
    private val bool = RCache.Key("bool")
    private val int = RCache.Key("int")
    private val array = RCache.Key("array")
    private val dictionary = RCache.Key("dictionary")
    private val double = RCache.Key("double")
    private val float = RCache.Key("float")

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        RCache.initialize(appContext)
        testCommon()
        testCredentials()
    }

    @Test
    fun testCommon() {
        val values = Values(
            data = "RCache-common".toByteArray(),
            string = "RCache-common",
            bool = false,
            int = 1,
            array = listOf(1, "b", true),
            dictionary = mapOf("a" to 1, "b" to 2),
            double = 12.123,
            float = 15.554f
        )

        RCache.common.save(values.data, data)
        RCache.common.save(values.string, string)
        RCache.common.save(values.bool, bool)
        RCache.common.save(values.int, int)
        RCache.common.save(values.array, array)
        RCache.common.save(values.dictionary, dictionary)
        RCache.common.save(values.double, double)
        RCache.common.save(values.float, float)

        assertTrue(
            "Common data match",
            values.data.contentEquals(RCache.common.readData(data))
        )
        assertEquals(values.string, RCache.common.readString(string))
        assertEquals(values.bool, RCache.common.readBool(bool))
        assertEquals(values.int, RCache.common.readInteger(int))
        assertEquals(values.array.size, RCache.common.readArray(array)?.size)
        assertEquals(values.dictionary.size, RCache.common.readDictionary(dictionary)?.size)
        assertEquals(values.double, RCache.common.readDouble(double))
        assertEquals(values.float, RCache.common.readFloat(float))
    }

    @Test
    fun testCredentials() {
        val values = Values(
            data = "RCache-credentials".toByteArray(),
            string = "RCache-credentials",
            bool = false,
            int = 1,
            array = listOf(1, "b", true),
            dictionary = mapOf("a" to 1, "b" to 2),
            double = 12.123,
            float = 15.554f
        )

        RCache.credentials.save(values.data, data)
        RCache.credentials.save(values.string, string)
        RCache.credentials.save(values.bool, bool)
        RCache.credentials.save(values.int, int)
        RCache.credentials.save(values.array, array)
        RCache.credentials.save(values.dictionary, dictionary)
        RCache.credentials.save(values.double, double)
        RCache.credentials.save(values.float, float)

        assertTrue(
            "Credentials data match",
            values.data.contentEquals(RCache.credentials.readData(data))
        )
        assertEquals(values.string, RCache.credentials.readString(string))
        assertEquals(values.bool, RCache.credentials.readBool(bool))
        assertEquals(values.int, RCache.credentials.readInteger(int))
        assertEquals(values.array.size, RCache.credentials.readArray(array)?.size)
        assertEquals(values.dictionary.size, RCache.credentials.readDictionary(dictionary)?.size)
        assertEquals(values.double, RCache.credentials.readDouble(double))
        assertEquals(values.float, RCache.credentials.readFloat(float))
    }
}

data class Values(
    val data: ByteArray,
    val string: String,
    val bool: Boolean,
    val int: Int,
    val array: List<Any>,
    val dictionary: Map<String, Any>,
    val double: Double,
    val float: Float
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Values

        if (!data.contentEquals(other.data)) return false
        if (string != other.string) return false
        if (bool != other.bool) return false
        if (int != other.int) return false
        if (array != other.array) return false
        if (dictionary != other.dictionary) return false
        if (double != other.double) return false
        if (float != other.float) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.contentHashCode()
        result = 31 * result + string.hashCode()
        result = 31 * result + bool.hashCode()
        result = 31 * result + int
        result = 31 * result + array.hashCode()
        result = 31 * result + dictionary.hashCode()
        result = 31 * result + double.hashCode()
        result = 31 * result + float.hashCode()
        return result
    }
}