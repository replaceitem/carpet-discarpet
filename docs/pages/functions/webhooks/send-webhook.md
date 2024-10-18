### `dc_send_webhook(webhook, content, webhook_message_profile)`

{% include 'warning-blocking.md' %}

Sends a message to the [`webhook`](/values/webhook.md),
but in contrast to `dc_send_message(webhook, content)`
also allows to change the
[`webhook_message_profile`](/parsables/webhooks/webhook-message-profile.md)
in one request.

Returns the sent [`message`](/values/message.md)

Throws an exception on failure