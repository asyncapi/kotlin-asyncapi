package com.asyncapi.kotlinasyncapi.example.spring

import com.asyncapi.kotlinasyncapi.annotation.Schema
import com.asyncapi.kotlinasyncapi.annotation.channel.Channel
import com.asyncapi.kotlinasyncapi.annotation.channel.Message
import com.asyncapi.kotlinasyncapi.annotation.channel.Parameter
import com.asyncapi.kotlinasyncapi.annotation.channel.Subscribe
import org.springframework.stereotype.Component

@Component
@Channel(
    value = "/rooms/{roomId}",
    parameters = [
        Parameter(
            value = "roomId",
            schema = Schema(
                type = "string",
                examples = [""""53307860c3599d1de448e19d""""]
            )
        )
    ]
)
class RoomsChannel {

    @Subscribe(
        messages = [
            Message(ChatMessage::class),
            Message(
                name = "heartbeat",
                summary = "Its purpose is to keep the connection alive.",
                payload = Schema(
                    type = "string",
                    enum = [""""\r\n""""],
                ),
            ),
        ]
    )
    fun publish(message: String): Nothing = TODO()
}
