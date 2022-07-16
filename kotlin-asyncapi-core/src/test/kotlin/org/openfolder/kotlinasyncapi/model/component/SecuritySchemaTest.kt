package org.openfolder.kotlinasyncapi.model.component

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json

internal class SecuritySchemaTest {

    @Test
    fun `should build a security schema component`() {
        val securitySchema = SecuritySchema().apply {
            type("typeValue")
            description("descriptionValue")
            name("nameValue")
            `in`("inValue")
            scheme("schemeValue")
            bearerFormat("bearerFormatValue")
            flows { }
            openIdConnectUrl("openIdConnectUrlValue")
        }
        val expected = json("component/security_schema.json")
        val actual = json(securitySchema)

        assertJsonEquals(expected, actual)
    }
}
