### `dc_send_webhook(webhook, content, webhook_message_profile)`

{% include 'warning-blocking.md' %}

Sends a message to the [`webhook`](../../values/webhook), but in contrast to `dc_send_message(webhook, content)` also allows to change the [`webhook_message_profile`](../../parsables/webhooks/webhook-message-profile) in one request.
