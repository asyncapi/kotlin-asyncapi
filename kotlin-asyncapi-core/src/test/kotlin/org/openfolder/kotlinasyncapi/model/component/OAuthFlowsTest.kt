package org.openfolder.kotlinasyncapi.model.component

import org.openfolder.kotlinasyncapi.model.TestUtils.assertJsonEquals
import org.openfolder.kotlinasyncapi.model.TestUtils.json
import org.junit.jupiter.api.Test

internal class OAuthFlowsTest {

    @Test
    fun `should build a OAuthFlows component`() {
        val oAuthFlows = OAuthFlows().apply {
            implicit {
                authorizationUrl("authorizationUrlValue")
                tokenUrl("tokenUrlValue")
                refreshUrl("refreshUrlValue")
                scopes("scopesKey" to "scopesValue")
            }
            password {
                authorizationUrl("authorizationUrlValue")
                tokenUrl("tokenUrlValue")
                refreshUrl("refreshUrlValue")
                scopes("scopesKey" to "scopesValue")
            }
            clientCredentials {
                authorizationUrl("authorizationUrlValue")
                tokenUrl("tokenUrlValue")
                refreshUrl("refreshUrlValue")
                scopes("scopesKey" to "scopesValue")
            }
            authorizationCode {
                authorizationUrl("authorizationUrlValue")
                tokenUrl("tokenUrlValue")
                refreshUrl("refreshUrlValue")
                scopes("scopesKey" to "scopesValue")
            }
        }
        val expected = json("component/oauth_flows.json")
        val actual = json(oAuthFlows)

        assertJsonEquals(expected, actual)
    }
}
