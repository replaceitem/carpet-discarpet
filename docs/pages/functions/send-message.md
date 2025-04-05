### `dc_send_message(target, content)`

Sends a message to a target.

{% include 'blocking-function.md' %}


### {input:}

| Parameter | Type                                                                                  | Description                            |
|----------:|:--------------------------------------------------------------------------------------|:---------------------------------------|
|  `target` | [Channel](/values/channel.md), [User](/values/user.md), [Webhook](/values/webhook.md) | The target to use to send the message. |
| `content` | [Message content](/parsables/message-content.md)                                      | The content of the message.            |


### {output:}

#### {output values:}

* The sent [message](/values/message.md).

#### {output exceptions:}

* Throws an exception on failure.


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