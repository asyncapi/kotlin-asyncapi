package org.openfolder.kotlinasyncapi.mavenplugin

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.mavenplugin.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.mavenplugin.TestUtils.json
import org.openfolder.kotlinasyncapi.model.AsyncApi
import java.io.File
import kotlin.test.AfterTest

internal class AsyncApiJsonWriterTest {

    @AfterTest
    fun cleanUp() {
        val file = File("src/test/resources/write.json")
        file.delete()
    }

    @Test
    fun `should write resource to file`() {
        val fileWriter = AsyncApiJsonWriter()
        val file = File("src/test/resources/write.json")
        val asyncApi = AsyncApi.asyncApi {
            info {
                title("titleValue")
                version("versionValue")
            }
            channels { }
        }

        fileWriter.write(asyncApi, file)

        val expected = json("asyncapi.json")
        val actual = file.bufferedReader().lineSequence().joinToString()

        assertJsonEquals(expected, actual)
    }
}
