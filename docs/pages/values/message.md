`dc_message`

Represents a message sent by a user, bot, webhook, or server.

#### Queryable:

| Property             | Type                     | Description                                                                                                                                                                                    |
|---------------------:|--------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `content`            | String                   | The content of the message. All emojis, mentions, and channels will appear with their ID.                                                                                                      |
| `readable_content`   | String                   | The content of the message. All emojis, mentions, and channels will appear with a more readable representation.<br>Note that if a user is not cached, mentions to them may not get parsed.     |
| `id`                 | String                   | The ID of the message.                                                                                                                                                                         |
| `channel`            | [Channel][1]             | The channel this message was sent in.                                                                                                                                                          |
| `user`               | [User][2]                | The user that sent this message.<br>Returns `null` if not sent or triggered by a user, or if the user is not cached (either through [`__on_discord_message`][5] or [`dc_message_from_id`][6]). |
| `webhook_id`         | Number                   | The webhook that sent this message, represented by its ID.<br>To get the [Webhook][7] value, use [`dc_webhook_from_id`][8].<br>Returns `null` if not sent by a webhook.                        |
| `server`             | Server                   | The server this message was written in.                                                                                                                                                        |
| `nonce`              | String                   | The nonce of this message.                                                                                                                                                                     |
| `attachments`        | List of [Attachments][3] | The attachments in this message.                                                                                                                                                               |
| `sticker_ids`        | List of Strings          | The stickers in this message represented with their IDs.<br>To get [Sticker][9] values, use [`dc_sticker_from_id`][10].                                                                        |
| `referenced_message` | [Message][4]             | The message that is being referenced or replied to                                                                                                                                             |
| `type`               | String                   | The [type][11] of the message.                                                                                                                                                                 |
| `link`               | String                   | The URL linking to the message.                                                                                                                                                                |
| `flags`              | List of Strings          | The [flags][12] of the message.                                                                                                                                                                |
| `creation_timestamp` | Number                   | The unix timestamp since message creation.                                                                                                                                                     |
| `edit_timestamp`     | Number                   | The unix timestamp since the message was last edited.                                                                                                                                          |
| `position`           | Number                   | The position of the message in a thread.                                                                                                                                                       |

[1]: /values/channel.md
[2]: /values/user.md
[3]: /values/attachment.md
[4]: /values/message.md

[5]: /events/discord-message.md
[6]: /functions/values/message-from-id.md
[7]: /values/webhook.md
[8]: /functions/values/webhook-from-id.md
[9]: /values/sticker.md
[10]: /functions/values/sticker-from-id.md
[11]: https://discord.com/developers/docs/resources/message#message-object-message-types
[12]: https://discord.com/developers/docs/resources/message#message-object-message-flags