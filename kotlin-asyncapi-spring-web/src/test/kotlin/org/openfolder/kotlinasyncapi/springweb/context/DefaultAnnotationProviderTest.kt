package org.openfolder.kotlinasyncapi.springweb.context

import org.junit.jupiter.api.Test

import org.openfolder.kotlinasyncapi.annotation.Schema
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.springweb.EnableAsyncApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class DefaultAnnotationProviderTest {

    @Test
    fun `should provide annotation components`() {

    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @EnableAsyncApi
    open class TestConfig

    @Message(
        messageId = "testMessageId",
        name = "testName",
        description = "testDescription",
        headers = Schema(implementation = TestHeaders::class)
    )
    data class TestMessage(
        val id: Int = 0,
        val name: String,
        val isTest: Boolean
    )

    @Schema
    data class TestHeaders(
        val type: String
    )
}
