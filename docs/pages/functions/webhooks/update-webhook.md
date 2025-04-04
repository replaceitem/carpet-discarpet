### `dc_update_webhook(webhook, profile)`

Updates a webhook's profile.

{% include 'blocking-function.md' %}


### {input:}

| Parameter | Type                                                                      | Description            |
|----------:|:--------------------------------------------------------------------------|:-----------------------|
| `webhook` | [Webhook](/values/webhook.md)                                             | The webhook to update. |
| `profile` | [Webhook profile updater](/parsables/webhooks/webhook-profile-updater.md) | The profile to use.    |


### {output:}

#### {output values:}

* The updated [webhook](/values/webhook.md).

#### {output exceptions:}

* Throws an exception on failure.