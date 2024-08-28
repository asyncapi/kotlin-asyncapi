package com.asyncapi.kotlinasyncapi.model.info

import org.junit.jupiter.api.Test
import com.asyncapi.kotlinasyncapi.model.TestUtils.assertJsonEquals
import com.asyncapi.kotlinasyncapi.model.TestUtils.json

internal class ContactTest {

    @Test
    fun `should build a contact component`() {
        val contact = Contact().apply {
            name("nameValue")
            url("urlValue")
            email("emailValue")
        }
        val expected = json("info/contact.json")
        val actual = json(contact)

        assertJsonEquals(expected, actual)
    }
}
