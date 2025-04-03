### `dc_webhook_from_id(id, token)`

Gets a webhook from the specified ID and token.

{% include 'warning-blocking.md' %}


### {input:}

* `id` {->} String
  {:} The ID of the webhook.
* `token` {->} String
  {:} The token of the webhook.


### {output:}

#### {output values:}

* [Webhook](/values/webhook.md)

#### {output exceptions:}

* Throws an exception on failure.