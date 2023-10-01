package org.openfolder.kotlinasyncapi.example.spring

import org.openfolder.kotlinasyncapi.annotation.channel.Message

@Message
data class ChatMessage(
    val id: String,
    val text: String
)
