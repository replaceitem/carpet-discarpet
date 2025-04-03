`dc_server`

Represents a server.

#### Queryable:

| Property         | Type                        | Description                                                             |
|-----------------:|-----------------------------|-------------------------------------------------------------------------|
| `name`           | String                      | The name of the server.                                                 |
| `id`             | String                      | The ID of the server.                                                   |
| `users`          | List of [Users][1]          | All users in this server.<br>(Requires the [`GUILD_MEMBERS`][8] intent) |
| `channels`       | List of [Channels][2]       | All channels in this server.                                            |
| `roles`          | List of [Roles][3]          | All roles in this server.                                               |
| `webhooks`       | List of [Webhooks][4]       | All webhooks in this server.<br>Throws an exception on failure.         |
| `slash_commands` | List of [Slash commands][5] | All slash commands in this server.<br>Throws an exception on failure.   |
| `emojis`         | List of [Emojis][6]         | All emojis in this server.                                              |
| `sticker`        | List of [Stickers][7]       | All stickers in this server.                                            |

[1]: /values/user.md
[2]: /values/channel.md
[3]: /values/role.md
[4]: /values/webhook.md
[5]: /values/commands/slash-command.md
[6]: /values/emoji.md
[7]: /values/sticker.md

[8]: /setup.md#using-intents