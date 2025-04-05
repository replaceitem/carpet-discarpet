`dc_channel`

Represents a server channel, thread, DMs, or channel category.

Threads and DMs are considered as channels.


### {query:}

|      Property | Type                                   | Description                                                                    |
|--------------:|:---------------------------------------|:-------------------------------------------------------------------------------|
|        `name` | String                                 | The name of the channel.                                                       |
|       `topic` | String                                 | The topic/description of the channel.                                          |
|          `id` | String                                 | The ID of the channel.                                                         |
| `mention_tag` | String                                 | The mention tag for the channel. Used to directly link channels in a message.  |
|      `server` | [Server](/values/server.md), Null      | The server this channel is in.<br>Returns `null` if this is a private channel. |
|        `type` | String                                 | The [type](#channel-types) of the channel.                                     |
|    `webhooks` | List of [Webhooks](/values/webhook.md) | The webhooks in this channel.<br>Throws an exception on failure.               |
|        `nsfw` | Boolean                                | Whether if this channel is age-restricted.                                     |

#### Channel types

|                       String | Description                       |
|-----------------------------:|:----------------------------------|
|        `SERVER_TEXT_CHANNEL` | Text channel                      |
|       `SERVER_VOICE_CHANNEL` | Voice channel                     |
|       `SERVER_FORUM_CHANNEL` | Forum channel                     |
| `SERVER_STAGE_VOICE_CHANNEL` | Stage channel                     |
|        `SERVER_NEWS_CHANNEL` | Announcement channel              |
|       `SERVER_STORE_CHANNEL` | Store channel                     |
|       `SERVER_PUBLIC_THREAD` | Public thread                     |
|      `SERVER_PRIVATE_THREAD` | Private thread                    |
|         `SERVER_NEWS_THREAD` | Thread in an announcement channel |
|            `PRIVATE_CHANNEL` | Direct messages with another user |
|              `GROUP_CHANNEL` | Group chat                        |
|           `CHANNEL_CATEGORY` | Channel categories                |
|   `SERVER_DIRECTORY_CHANNEL` |                                   |
|                    `UNKNOWN` |                                   |