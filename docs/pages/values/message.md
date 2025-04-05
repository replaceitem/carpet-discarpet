`dc_message`

Represents a message sent by a user, bot, webhook, or server.


### {query:}

|             Property | Type                                         | Description                                                                                                            |
|---------------------:|:---------------------------------------------|:-----------------------------------------------------------------------------------------------------------------------|
|            `content` | String                                       | The content of the message.<br>All emojis, mentions, and channels will appear with their ID.                           |
|   `readable_content` | String                                       | The content of the message.<br>All emojis, mentions, and channels will appear with a more readable representation.[^1] |
|                 `id` | String                                       | The ID of the message.                                                                                                 |
|            `channel` | [Channel](/values/channel.md)                | The channel this message was sent in.                                                                                  |
|               `user` | [User](/values/user.md), Null                | The user that sent this message.<br>Returns `null` if not sent or triggered by a user, or if the user is not cached.   |
|         `webhook_id` | Number, Null                                 | The webhook that sent this message, represented by its ID.[^2]<br>Returns `null` if not sent by a webhook.             |
|             `server` | Server                                       | The server this message was written in.                                                                                |
|              `nonce` | String                                       | The nonce of this message.                                                                                             |
|        `attachments` | List of [Attachments](/values/attachment.md) | The attachments in this message.                                                                                       |
|        `sticker_ids` | List of Strings                              | The stickers in this message represented with their IDs.[^3]                                                           |
| `referenced_message` | [Message](/values/message.md)                | The message that is being referenced or replied to.                                                                    |
|               `type` | String                                       | The [type](https://discord.com/developers/docs/resources/message#message-object-message-types) of the message.         |
|               `link` | String                                       | The URL linking to the message.                                                                                        |
|              `flags` | List of Strings                              | The [flags](https://discord.com/developers/docs/resources/message#message-object-message-flags) of the message.        |
| `creation_timestamp` | Number                                       | The timestamp in unix time milliseconds since message creation.                                                        |
|     `edit_timestamp` | Number                                       | The timestamp in unix time milliseconds since the message was last edited.                                             |
|           `position` | Number                                       | The position of the message in a thread.                                                                               |

[^1]: If a user is not cached, mentions to them may not get parsed.
[^2]: To get the [Webhook](/values/webhook.md) value, use [`dc_webhook_from_id`](/functions/values/webhook-from-id.md).
[^3]: To get [Sticker](/values/sticker.md) values, use [`dc_sticker_from_id`](/functions/values/sticker-from-id.md).