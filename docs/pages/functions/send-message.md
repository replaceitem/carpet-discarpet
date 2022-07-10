### `dc_send_message(target,content)`

{% include 'warning-blocking.md' %}

This functions sends a message in a specific Discord channel, to a private message channel or a webhook.

The `target` can be one of

* [Channel](../values/channel)
* [User](../values/user)
* [Webhook](../values/webhook)

The `content` is a parsable [Message content](../parsables/message-content), but if you just want text, it can be a regular string.

This example shows how you can send a message and add reactions to it as soon as it was sent

```py
task(_()->(
    message = dc_send_message(dc_channel_from_id('YOUR CHANNEL ID'),'Test message');
    dc_react(message,'🟥');
    dc_react(message,'🟩');
));
```