`dc_server`

Represents a server.


### {query:}

|         Property | Type                                         | Description                                                                                   |
|-----------------:|:---------------------------------------------|:----------------------------------------------------------------------------------------------|
|           `name` | String                                       | The name of the server.                                                                       |
|             `id` | String                                       | The ID of the server.                                                                         |
|        `members` | List of [Members](/values/member.md)         | All users in this server.<br>(Requires the [`GUILD_MEMBERS`](/setup.md#using-intents) intent) |
|          `users` | List of [Users](/values/user.md)             | All users in this server.<br>(Requires the [`GUILD_MEMBERS`](/setup.md#using-intents) intent) |
|       `channels` | List of [Channels](/values/channel.md)       | All channels in this server.                                                                  |
|          `roles` | List of [Roles](/values/role.md)             | All roles in this server.                                                                     |
|       `webhooks` | List of [Webhooks](/values/webhook.md)       | All webhooks in this server.<br>Throws an exception on failure.                               |
| `slash_commands` | List of [Slash commands](/values/command.md) | All slash commands in this server.<br>Throws an exception on failure.                         |
|         `emojis` | List of [Emojis](/values/emoji.md)           | All emojis in this server.                                                                    |
|        `sticker` | List of [Stickers](/values/sticker.md)       | All stickers in this server.                                                                  |