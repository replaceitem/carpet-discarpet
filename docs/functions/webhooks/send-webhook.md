### `dc_send_webhook(webhook, content, webhook_message_profile)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Sends a message to the [`webhook`](../../../values/webhook), but in contrast to `dc_send_message(webhook, content)` also allows to change the [`webhook_message_profile`](../../../parsables/webhooks/webhook-message-profile) in one request.
