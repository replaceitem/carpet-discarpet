### `dc_webhook_from_url(url)`

Gets the webhook from the specified URL.

{% include 'blocking-function.md' %}


### {input:}

| Parameter | Type   | Description             |
|----------:|:-------|:------------------------|
|     `url` | String | The URL of the webhook. |


### {output:}

#### {output values:}

* [Webhook](/values/webhook.md)

#### {output exceptions:}

Throws an exception on failure.

* `api_exception`
* `missing_permission`