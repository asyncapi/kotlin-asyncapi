package org.openfolder.kotlinasyncapi.example.spring

import org.openfolder.kotlinasyncapi.annotation.Schema
import org.openfolder.kotlinasyncapi.annotation.channel.Channel
import org.openfolder.kotlinasyncapi.annotation.channel.Message
import org.openfolder.kotlinasyncapi.annotation.channel.Parameter
import org.openfolder.kotlinasyncapi.annotation.channel.Subscribe
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
