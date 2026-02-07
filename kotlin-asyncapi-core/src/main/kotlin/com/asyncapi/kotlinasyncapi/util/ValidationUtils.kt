package com.asyncapi.kotlinasyncapi.util

internal fun <T> T.checkInitialized(
    vararg checks: Pair<String, T.() -> Unit>
): T = also {
    val missing = checks.mapNotNull { (name, check) ->
        try {
            it.check()
            null
        } catch (_: UninitializedPropertyAccessException) {
            name
        }
    }

    check(missing.isEmpty()) {
        "Missing required properties: ${missing.joinToString()}"
    }
}
