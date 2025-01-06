package com.asyncapi.kotlinasyncapi.example.ktor

import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import com.asyncapi.kotlinasyncapi.ktor.AsyncApiPlugin

fun main() {
    embeddedServer(Netty, port = 8000) {
        install(AsyncApiPlugin) {
            extension = asyncApiExtension
        }
    }.start(wait = true)
}
