`dc_message_context_menu_interaction`

#### Queryable:

| Property             | Type                          | Description                                                                                                            |
|----------------------|-------------------------------|------------------------------------------------------------------------------------------------------------------------|
| `id`                 | String                        | Id of the interaction                                                                                                  |
| `channel`            | [Channel](/values/channel.md) | The channel this interaction was made in.                                                                              |
| `user`               | [User](/values/user.md)       | The user that used the interaction.                                                                                    |
| `token`              | String                        | The continuation token internally used for responding to this interaction                                              |
| `server`             | [Server](/values/server.md)   | The server this reaction was made in                                                                                   |
| `locale`             | String                        | The [locale](https://discord.com/developers/docs/reference#locales) of the user executing the interaction (e.g. en-US) |
| `creation_timestamp` | Number                        | The timestamp as epoc milliseconds when this interaction was made                                                      |
| `command_id`         | String                        | The id of the application command                                                                                      |
| `command_name`       | String                        | The name of the application command                                                                                    |
| `target`             | [Message](/values/message.md) | The message the context menu interaction was used on                                                                   |