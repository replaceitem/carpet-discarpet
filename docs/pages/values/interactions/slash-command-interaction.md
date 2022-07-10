`dc_slash_command_interaction`

Value from `__on_discord_slash_command(interaction)` event, used for getting the command that was executed, and then replying to it with `dc_respond_interaction()`

#### Queryable:

| Property            | Type                                                                                                               | Description                                                                                                            |
|---------------------|--------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| `id`                | String                                                                                                             | The id of the interaction                                                                                              |
| `channel`           | [Channel](/values/channel.md)                                                                                      | The channel this command was executed in.                                                                              |
| `user`              | [User](/values/user.md)                                                                                            | The user that executed the command.                                                                                    |
| `token`             | String                                                                                                             | The token used to respond to the interaction (normally not needed)                                                     |
| `server`            | [Server](/values/server.md)                                                                                        | The server this interaction was made in                                                                                |
| `locale`            | String                                                                                                             | The [locale](https://discord.com/developers/docs/reference#locales) of the user executing the interaction (e.g. en-US) |
| `command_id`        | String                                                                                                             | The id of the application command                                                                                      |
| `command_name`      | String                                                                                                             | The name of the application command                                                                                    |
| `arguments`         | List of [Slash command interaction options](/values/interactions/slash-command-interaction-option.md)              | The selected options of the command                                                                                    |
| `arguments_by_name` | Map of [Slash command interaction options](/values/interactions/slash-command-interaction-option.md) by their name | Returns a map of all options (and sub-options), with the key being their name                                          |