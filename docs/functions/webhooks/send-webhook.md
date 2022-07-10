### `dc_send_webhook(webhook, content, webhookProfile)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Sends a message to the webhook, but in contrast to `dc_send_message(webhook, content)` also allows to change the `webhookProfile` in one request.
