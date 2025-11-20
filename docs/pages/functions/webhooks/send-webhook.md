### `dc_send_webhook(webhook, content, profile)`

Sends a message to the webhook.
In contrast to `dc_send_message(webhook, content)`, it lets you change the webhook's profile at the same time.

{% include 'blocking-function.md' %}


### {input:}

| Parameter | Type                                                                           | Description                         |
|----------:|:-------------------------------------------------------------------------------|:------------------------------------|
| `webhook` | [Webhook](/values/webhook.md)                                                  | The webhook to send the message to. |
| `content` | [Message content object](/schemas/message-content.md), String                  | The content of the message.         |
| `profile` | [Webhook message profile object](/schemas/webhooks/webhook-message-profile.md) | The profile to use.                 |


### {output:}

#### {output values:}

* The sent [message](/values/message.md).

#### {output exceptions:}

Throws an exception on failure.