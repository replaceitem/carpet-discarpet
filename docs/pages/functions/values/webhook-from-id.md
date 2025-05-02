### `dc_webhook_from_id(id, token)`

Gets the webhook from the specified ID and token.

{% include 'blocking-function.md' %}


### {input:}

| Parameter | Type           | Description               |
|----------:|:---------------|:--------------------------|
|      `id` | String, Number | The ID of the webhook.    |
|   `token` | String         | The token of the webhook. |


### {output:}

#### {output values:}

* [Webhook](/values/webhook.md)

#### {output exceptions:}

Throws an exception on failure.
* `api_exception`
* `missing_permission`