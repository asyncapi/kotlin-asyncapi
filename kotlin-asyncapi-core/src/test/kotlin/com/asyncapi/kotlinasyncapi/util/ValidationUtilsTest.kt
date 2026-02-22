package com.asyncapi.kotlinasyncapi.util

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.lang.IllegalStateException

internal class ValidationUtilsTest {

    class TestClass {
        lateinit var requiredProperty: String
        var optionalProperty: String? = null
    }

    @Test
    fun `checkInitialized should pass when required property is initialized`() {
        val instance = TestClass().apply {
            requiredProperty = "value"
        }

        assertDoesNotThrow {
            instance.checkInitialized(
                "requiredProperty" to { requiredProperty }
            )
        }
    }

    @Test
    fun `checkInitialized should fail when required property is not initialized`() {
        val instance = TestClass()

        val exception = assertThrows(IllegalStateException::class.java) {
            instance.checkInitialized(
                "requiredProperty" to { requiredProperty }
            )
        }

        assert(exception.message!!.contains("Missing required properties: requiredProperty"))
    }

    @Test
    fun `checkInitialized should handle multiple checks`() {
        val instance = TestClass()

        val exception = assertThrows(IllegalStateException::class.java) {
            instance.checkInitialized(
                "requiredProperty" to { requiredProperty },
                "anotherMissing" to { throw UninitializedPropertyAccessException() }
            )
        }

        assert(exception.message!!.contains("requiredProperty"))
        assert(exception.message!!.contains("anotherMissing"))
    }
}
