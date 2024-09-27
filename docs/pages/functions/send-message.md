### `dc_send_message(target, content)`

{% include 'warning-blocking.md' %}

Sends a message to the target.

The `target` can be one of:

* [Channel](/values/channel.md)
* [User](/values/user.md)
* [Webhook](/values/webhook.md)

The `content` is a parsable [Message content](/parsables/message-content.md), but if you just want text, it can be a regular string.

This example shows how you can send a message and add reactions to it as soon as it was sent

```py
task(_() -> (
    channel = dc_channel_from_id('YOUR CHANNEL ID');
    message = dc_send_message(channel, 'Test message');
    dc_react(message, '🟥');
    dc_react(message, '🟩');
));
```