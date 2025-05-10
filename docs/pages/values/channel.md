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
|    `position` | Number                                 | The position in the server channel list.                                       |

#### Channel types

|                 String | Description                       |
|-----------------------:|:----------------------------------|
|                 `text` | Text channel                      |
|              `private` | Direct messages with another user |
|                `voice` | Voice channel in a server         |
|                `group` | Group DM channel                  |
|             `category` | Server channel category           |
|                 `news` | Server news channel               |
|                `stage` | Server stage voice channel        |
|    `guild_news_thread` | Server news thread                |
|  `guild_public_thread` | Server public thread              |
| `guild_private_thread` | Server private thread             |
|                `forum` | Server forum channel              |
|                `media` | Media channel                     |