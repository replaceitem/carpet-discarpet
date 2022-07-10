### `dc_send_message(target,content)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

This functions sends a message in a specific Discord channel, to a private message channel or a webhook.
`target` can be a Channel, User or Webhook value.
The `content` is a parsable [Message content](./docs/parsable.md#Message-content). But if you just want text, it can be a regular string.

This example shows how you can send a message and add reactions to it as soon as it was sent

```py
task(_()->(
    message = dc_send_message(dc_channel_from_id('YOUR CHANNEL ID'),'Test message');
    dc_react(message,'🟥');
    dc_react(message,'🟩');
));
```