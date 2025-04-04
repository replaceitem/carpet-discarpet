`dc_webhook`

Represents a webhook in a channel.


### {query:}

|  Property | Type                          | Description                                                     |
|----------:|:------------------------------|:----------------------------------------------------------------|
|      `id` | String                        | The ID of the webhook.                                          |
| `channel` | [Channel](/values/channel.md) | The channel the webhook is in.                                  |
|    `type` | String                        | The [type](#webhook-types) of the webhook.                      |
|   `token` | String                        | The token of the webhook.<br>(Only works for incoming webhooks) |
|     `url` | String                        | The URL of the webhook.<br>(Only works for incoming webhooks)   |

#### Webhook types

* `INCOMING`
* `CHANNEL_FOLLOWER`
* `UNKNOWN`