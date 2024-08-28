package com.asyncapi.kotlinasyncapi.model.channel

import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.model.TestUtils.assertJsonEquals
import com.asyncapi.kotlinasyncapi.model.TestUtils.json

internal class OperationBindingTest {

    @Test
    fun `should build a operation bindings component`() {
        val operationBindings = OperationBindings().apply {
            mqtt {
                qos(1)
                retain(true)
                bindingVersion("bindingVersionValue")
            }
        }
        val expected = json("channel/operation_bindings.json")
        val actual = json(operationBindings)

        assertJsonEquals(expected, actual)
    }
}
