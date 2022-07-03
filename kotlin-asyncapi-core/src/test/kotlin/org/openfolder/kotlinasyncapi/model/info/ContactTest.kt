package org.openfolder.kotlinasyncapi.model.info

import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json
import org.junit.jupiter.api.Test

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
