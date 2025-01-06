package com.asyncapi.kotlinasyncapi.example.ktor

import com.asyncapi.kotlinasyncapi.annotation.channel.Message

@Message
data class ChatMessage(
    val id: String,
    val text: String
)
