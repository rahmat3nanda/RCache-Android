package id.nesd.rcache

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.annotations.SerializedName
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val byteArray = RCache.Key("byteArray")
    private val string = RCache.Key("string")
    private val bool = RCache.Key("bool")
    private val int = RCache.Key("int")
    private val array = RCache.Key("array")
    private val map = RCache.Key("map")
    private val double = RCache.Key("double")
    private val float = RCache.Key("float")

    @Before
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        RCache.initialize(appContext)
    }

    @Test
    fun testCommon() {
        val values = Values(
            byteArray = "RCache-common".toByteArray(),
            string = "RCache-common",
            bool = false,
            int = 1,
            array = listOf(1, "b", true),
            map = mapOf("a" to 1, "b" to 2),
            double = 12.123,
            float = 15.554f,
            dataClassModel = MyDataClassModel(
                id = 1,
                name = "RCache-common",
                phoneNumber = "081"
            )
        )

        RCache.common.save(values.byteArray, byteArray)
        RCache.common.save(values.string, string)
        RCache.common.save(values.bool, bool)
        RCache.common.save(values.int, int)
        RCache.common.save(values.array, array)
        RCache.common.save(values.map, map)
        RCache.common.save(values.double, double)
        RCache.common.save(values.float, float)

        assertTrue(
            "Common byteArray match",
            values.byteArray.contentEquals(RCache.common.readByteArray(byteArray))
        )
        assertEquals(values.string, RCache.common.readString(string))
        assertEquals(values.bool, RCache.common.readBool(bool))
        assertEquals(values.int, RCache.common.readInteger(int))
        assertEquals(values.array.size, RCache.common.readArray<Any>(array)?.size)
        assertEquals(values.map.size, RCache.common.readMap<Any>(map)?.size)
        assertEquals(values.double, RCache.common.readDouble(double))
        assertEquals(values.float, RCache.common.readFloat(float))
    }

    @Test
    fun testCredentials() {
        val values = Values(
            byteArray = "RCache-credentials".toByteArray(),
            string = "RCache-credentials",
            bool = false,
            int = 1,
            array = listOf(1, "b", true),
            map = mapOf("a" to 1, "b" to 2),
            double = 12.123,
            float = 15.554f,
            dataClassModel = MyDataClassModel(
                id = 2,
                name = "RCache-credentials",
                phoneNumber = "082"
            )
        )

        RCache.credentials.save(values.byteArray, byteArray)
        RCache.credentials.save(values.string, string)
        RCache.credentials.save(values.bool, bool)
        RCache.credentials.save(values.int, int)
        RCache.credentials.save(values.array, array)
        RCache.credentials.save(values.map, map)
        RCache.credentials.save(values.double, double)
        RCache.credentials.save(values.float, float)

        assertTrue(
            "Credentials byteArray match",
            values.byteArray.contentEquals(RCache.credentials.readByteArray(byteArray))
        )
        assertEquals(values.string, RCache.credentials.readString(string))
        assertEquals(values.bool, RCache.credentials.readBool(bool))
        assertEquals(values.int, RCache.credentials.readInteger(int))
        assertEquals(values.array.size, RCache.credentials.readArray<Any>(array)?.size)
        assertEquals(values.map.size, RCache.credentials.readMap<Any>(map)?.size)
        assertEquals(values.double, RCache.credentials.readDouble(double))
        assertEquals(values.float, RCache.credentials.readFloat(float))
    }
}

data class MyDataClassModel(
    val id: Int,
    val name: String,
    @SerializedName("phone_number") val phoneNumber: String
)

data class Values(
    val byteArray: ByteArray,
    val string: String,
    val bool: Boolean,
    val int: Int,
    val array: List<Any>,
    val map: Map<String, Any>,
    val double: Double,
    val float: Float,
    val dataClassModel: MyDataClassModel
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Values

        if (!byteArray.contentEquals(other.byteArray)) return false
        if (string != other.string) return false
        if (bool != other.bool) return false
        if (int != other.int) return false
        if (array != other.array) return false
        if (map != other.map) return false
        if (double != other.double) return false
        if (float != other.float) return false
        if (dataClassModel != other.dataClassModel) return false

        return true
    }

    override fun hashCode(): Int {
        var result = byteArray.contentHashCode()
        result = 31 * result + string.hashCode()
        result = 31 * result + bool.hashCode()
        result = 31 * result + int
        result = 31 * result + array.hashCode()
        result = 31 * result + map.hashCode()
        result = 31 * result + double.hashCode()
        result = 31 * result + float.hashCode()
        result = 31 * result + dataClassModel.hashCode()
        return result
    }
}