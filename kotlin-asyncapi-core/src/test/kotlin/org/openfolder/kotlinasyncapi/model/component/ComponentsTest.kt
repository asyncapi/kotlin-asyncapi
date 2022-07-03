package org.openfolder.kotlinasyncapi.model.component

import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json
import org.junit.jupiter.api.Test

internal class ComponentsTest {

    @Test
    fun `should build a components component`() {
        val components = Components().apply {
            schemas { }
            servers { }
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
