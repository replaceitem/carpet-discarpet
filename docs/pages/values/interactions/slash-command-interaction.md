`dc_slash_command_interaction`

Represents an interaction of an executed slash command from [`__on_discord_slash_command()`](/events/discord-slash-command.md).

{% include 'getting-interaction-details.md' %}

#### Queryable:

| Property             | Type                                                                                                               | Description                                                                                                             |
|----------------------|--------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------|
| `id`                 | String                                                                                                             | The ID of the interaction.                                                                                              |
| `channel`            | [Channel](/values/channel.md)                                                                                      | The channel this interaction was made in.                                                                               |
| `user`               | [User](/values/user.md)                                                                                            | The user that who the interaction.                                                                                      |
| `token`              | String                                                                                                             | The continuation token internally used for responding to this interaction.                                              |
| `server`             | [Server](/values/server.md)                                                                                        | The server this interaction was made in.                                                                                |
| `locale`             | String                                                                                                             | The [locale](https://discord.com/developers/docs/reference#locales) of the user executing the interaction (e.g. en-US). |
| `creation_timestamp` | Number                                                                                                             | The timestamp in epoch milliseconds when this interaction was made.                                                     |
| `command_id`         | String                                                                                                             | The ID of the application command.                                                                                      |
| `command_name`       | String                                                                                                             | The name of the application command.                                                                                    |
| `arguments`          | List of [Slash command interaction options](/values/interactions/slash-command-interaction-option.md)              | The selected options of the command.                                                                                    |
| `arguments_by_name`  | Map of [Slash command interaction options](/values/interactions/slash-command-interaction-option.md) by their name | A map of all options (and sub-options), with the key being their name.                                                  |