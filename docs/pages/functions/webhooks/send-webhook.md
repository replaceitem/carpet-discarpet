### `dc_send_webhook(webhook, content, profile)`

{% include 'warning-blocking.md' %}

Sends a message to the webhook. Also lets you change the webhook's profile at the same time.

- `webhook` {->} [Webhook](/values/webhook.md)
  {:} The webhook to send the message to.
- `content` {->}
  [Message content](/parsables/message-content.md),
  String
  {:} The content of the message.
- `profile` {->} [Webhook message profile](/parsables/webhooks/webhook-message-profile.md)
  {:} The profile to use.