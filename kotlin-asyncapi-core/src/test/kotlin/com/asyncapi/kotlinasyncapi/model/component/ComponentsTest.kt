package com.asyncapi.kotlinasyncapi.model.component

import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.model.TestUtils.assertJsonEquals
import com.asyncapi.kotlinasyncapi.model.TestUtils.json

internal class ComponentsTest {

    @Test
    fun `should build a components component`() {
        val components = Components().apply {
            schemas { }
            servers { }
            serverVariables { }
            channels { }
            messages { }
            securitySchemes { }
            parameters { }
            correlationIds { }
            operationTraits { }
            messageTraits { }
            serverBindings { }
            channelBindings { }
            operationBindings { }
            messageBindings { }
        }
        val expected = json("component/components.json")
        val actual = json(components)

        assertJsonEquals(expected, actual)
    }
}
