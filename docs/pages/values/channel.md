`dc_channel`

Represents a server channel, thread, DMs, or channel category.

Threads and DMs are considered as channels.

#### Queryable:

| Property      | Type                                   | Description                                                                    |
|---------------|----------------------------------------|--------------------------------------------------------------------------------|
| `name`        | String                                 | The name of the channel.                                                       |
| `topic`       | String                                 | The topic/description of the channel.                                          |
| `id`          | String                                 | The ID of the channel.                                                         |
| `mention_tag` | String                                 | The mention tag for the channel. Used to directly link channels in a message.  |
| `server`      | [Server](/values/server.md)            | The server this channel is in.<br>Returns `null` if this is a private channel. |
| `type`        | String                                 | The [type](#channel-types) of the channel.                                     |
| `webhooks`    | List of [Webhooks](/values/webhook.md) | The webhooks in this channel.                                                  |
| `nsfw`        | Boolean                                | Whether if the channel is age-restricted.                                      |

#### Channel types

* `SERVER_TEXT_CHANNEL` - Text channel
* `PRIVATE_CHANNEL` - Direct messages with another user
* `SERVER_VOICE_CHANNEL` - Voice channel
* `GROUP_CHANNEL` - Direct messages with other users
* `CHANNEL_CATEGORY` - Grouping of channels
* `SERVER_NEWS_CHANNEL` - Announcement channel
* `SERVER_STORE_CHANNEL` - Store channel
* `SERVER_NEWS_THREAD` - Thread in an announcement channel
* `SERVER_PUBLIC_THREAD` - Public thread
* `SERVER_PRIVATE_THREAD` - Private thread
* `SERVER_STAGE_VOICE_CHANNEL` - Stage channel
* `SERVER_DIRECTORY_CHANNEL`
* `SERVER_FORUM_CHANNEL` - Forum channel
* `UNKNOWN`