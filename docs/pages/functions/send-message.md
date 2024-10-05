### `dc_send_message(target, content)`

{% include 'warning-blocking.md' %}

Sends a message to a target.

Returns the [message](/values/message.md) that was sent.

- `target` {->}
  [Channel](/values/channel.md),
  [User](/values/user.md),
  [Webhook](/values/webhook.md)
  {:} The target to use to send the message.
- `content` {->}
  [Message content](/parsables/message-content.md),
  String
  {:} The content of the message.

<!--
This example shows how you can send a message and add reactions to it as soon as it was sent:

```sc
task(_() -> (
    channel = dc_channel_from_id('YOUR CHANNEL ID');
    message = dc_send_message(channel, 'Test message');
    dc_react(message, '🟥');
    dc_react(message, '🟩');
));
```
-->