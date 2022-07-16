package org.openfolder.kotlinasyncapi.model

import io.mockk.clearConstructorMockk
import io.mockk.every
import io.mockk.mockkConstructor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json

internal class SchemaTest {

    @AfterEach
    fun clearMocks() {
        clearConstructorMockk(ExternalDocumentation::class)
    }

    @Test
    fun `should build a schema component`() {
        mockkConstructor(ExternalDocumentation::class)
        every {
            anyConstructed<ExternalDocumentation>().url
        } returns "mocked"

        val schemas = Schema().apply {
            title("titleValue")
            description("descriptionValue")
            default(mapOf("defaultKey" to "defaultValue"))
            readOnly(true)
            writeOnly(true)
            examples(mapOf("exampleKey" to "exampleValue"))
            contentEncoding("contentEncodingValue")
            contentMediaType("contentMediaTypeValue")
            type("typeValue")
            enum("enumValue")
            const(mapOf("constKey" to "constValue"))
            multipleOf(1)
            maximum(1)
            exclusiveMaximum(1)
            minimum(1)
            exclusiveMinimum(1)
            maxLength(1)
            minLength(1)
            pattern("patternValue")
            item { }
            additionalItems { }
            maxItems(1)
            minItems(1)
            uniqueItems(true)
            contains { }
            maxProperties(1)
            minProperties(1)
            required("requiredValue")
            properties { }
            patternProperties { }
            additionalProperties { }
            dependencies(mapOf("dependenciesKey" to "dependenciesValue"))
            propertyNames { }
            `if` { }
            then { }
            `else` { }
            allOf { }
            anyOf { }
            oneOf { }
            not { }
            format("formatValue")
            discriminator("discriminatorValue")
            externalDocs { }
            deprecated(true)
        }
        val expected = json("schemas.json")
        val actual = json(schemas)

        assertJsonEquals(expected, actual)
    }
}
