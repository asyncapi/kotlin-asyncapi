package org.openfolder.kotlinasyncapi.springweb.context.annotation.processor

import org.junit.jupiter.api.Test
import org.openfolder.kotlinasyncapi.annotation.Schema
import org.openfolder.kotlinasyncapi.springweb.TestUtils
import kotlin.reflect.full.findAnnotation

internal class SchemaProcessorTest {

    private val processor = SchemaProcessor()

    @Test
    fun `should process schema annotation`() {
        val payload = TestSchema::class
        val annotation = payload.findAnnotation<Schema>()!!

        val expected = TestUtils.json("annotation/schema_component.json")
        val actual = TestUtils.json(processor.process(annotation, payload))

        TestUtils.assertJsonEquals(expected, actual)
    }

    @Schema
    data class TestSchema(
        val id: Int = 0,
        val name: String,
        val isTest: Boolean,
        val sub: TestSubSchema?
    )

    data class TestSubSchema(
        val exists: Boolean
    )
}
