`dc_channel`

A channel value represents a Discord server channel, DM channel, server thread or channel category.

#### Queryable:

| Property      | Type                                   | Description                                                                                           |
|---------------|----------------------------------------|-------------------------------------------------------------------------------------------------------|
| `name`        | String                                 | The name of the Discord channel                                                                       |
| `topic`       | String                                 | The topic of the channel (See `dc_set_channel_topic`)                                                 |
| `id`          | String                                 | ID of the channel                                                                                     |
| `mention_tag` | String                                 | Mention tag for the channel. This can be put inside a message for the channel to be a clickable link. |
| `server`      | [Server](/values/server.md)            | Server this channel is in, or null if this is a private channel                                       |
| `type`        | String                                 | [Channel type](#channel-types)                                                                        |
| `webhooks`    | List of [Webhooks](/values/webhook.md) | All webhooks in this channel. Throws an exception on failure                                          |
| `nsfw`        | boolean                                | Whether the channel is marked as "Not safe for work"                                                  |

#### Channel types

* `server_text_channel`
* `private_channel`
* `server_voice_channel`
* `group_channel`
* `channel_category`
* `server_news_channel`
* `server_store_channel`
* `server_news_thread`
* `server_public_thread`
* `server_private_thread`
* `server_stage_voice_channel`
* `server_directory_channel`
* `server_forum_channel`
* `unknown`