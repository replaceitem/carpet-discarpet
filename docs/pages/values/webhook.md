`dc_webhook`

A webhook in a channel.

#### Queryable:

| Property  | Type                          | Description                                                                    |
|-----------|-------------------------------|--------------------------------------------------------------------------------|
| `id`      | String                        | The id of the webhook                                                          |
| `channel` | [Channel](/values/channel.md) | The channel this webhook is in                                                 |
| `type`    | String                        | Webhook type, can be either `'INCOMING'`, `'CHANNEL_FOLLOWER'` and `'UNKNOWN'` |
| `token`   | String                        | The token of the webhooks, only works for incoming webhooks                    |
| `url`     | String                        | Webhook URL, only works for incoming webhooks                                  |