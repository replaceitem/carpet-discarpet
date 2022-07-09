`dc_user_context_menu_interaction`

| Property       | Type    | Description                                                                                                            |
|----------------|---------|------------------------------------------------------------------------------------------------------------------------|
| `id`           | String  | The id of the interaction                                                                                              |
| `channel`      | Channel | The channel this command was executed in.                                                                              |
| `user`         | User    | The user that executed the command.                                                                                    |
| `token`        | String  | The token used to respond to the interaction (normally not needed)                                                     |
| `server`       | Server  | The server this interaction was made in                                                                                |
| `locale`       | String  | The [locale](https://discord.com/developers/docs/reference#locales) of the user executing the interaction (e.g. en-US) |
| `command_id`   | String  | The id of the application command                                                                                      |
| `command_name` | String  | The name of the application command                                                                                    |
| `target`       | User    | The user the context menu interaction was used on                                                                      |