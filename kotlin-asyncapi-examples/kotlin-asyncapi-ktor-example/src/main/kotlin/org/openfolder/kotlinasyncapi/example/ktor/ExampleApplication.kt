package org.openfolder.kotlinasyncapi.example.ktor

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.openfolder.kotlinasyncapi.ktor.AsyncApiPlugin

fun main() {
    embeddedServer(Netty, port = 8000) {
        install(AsyncApiPlugin) {
            extension = asyncApiExtension
        }
    }.start(wait = true)
}
