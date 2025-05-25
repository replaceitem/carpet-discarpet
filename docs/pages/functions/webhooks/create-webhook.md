### `dc_create_webhook(channel, profile)`

Creates a new webhook in a channel.

{% include 'blocking-function.md' %}


### {input:}

| Parameter | Type                                                           | Description                           |
|----------:|:---------------------------------------------------------------|:--------------------------------------|
| `channel` | [Channel](/values/channel.md)                                  | The channel to create the webhook in. |
| `profile` | [Webhook profile object](/schemas/webhooks/webhook-profile.md) | The webhook's profile.                |


### {output:}

#### {output values:}

* The created [webhook](/values/webhook.md).

#### {output exceptions:}

* Throws an exception on failure.
