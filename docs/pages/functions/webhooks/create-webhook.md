### `dc_create_webhook(channel, webhookProfile)`

{% include 'warning-blocking.md' %}

Creates a new Webhook in the specified [`channel`](/values/channel.md)
with the specified options in `webhookProfile`
as a [webhook profile parsable](/parsables/webhooks/webhook-profile.md).

Returns a [`webhook`](/values/webhook.md)

Throws an exception on failure