package org.openfolder.kotlinasyncapi.context

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

object TestUtils {
    val objectMapper = ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .enable(SerializationFeature.INDENT_OUTPUT)

    fun json(path: String): String = TestUtils::class.java.classLoader.getResource(path)?.readText()!!

    fun json(value: Any): String = objectMapper.writeValueAsString(value)

    fun assertJsonEquals(expected: String, actual: String) =
        JSONAssert.assertEquals(expected, actual, JSONCompareMode.NON_EXTENSIBLE)
}
