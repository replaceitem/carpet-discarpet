`dc_select_menu_interaction`

Value from [`__on_discord_select_menu`](/events/discord-select-menu.md) event, used for getting the message interaction details, and then responding to it with `dc_respond_interaction()`

#### Queryable:

| Property             | Type                                            | Description                                                                                                            |
|----------------------|-------------------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| `id`                 | String                                          | Id of the interaction                                                                                                  |
| `channel`            | [Channel](/values/channel.md)                   | The channel this interaction was made in.                                                                              |
| `user`               | [User](/values/user.md)                         | The user that used the interaction.                                                                                    |
| `token`              | String                                          | The continuation token internally used for responding to this interaction                                              |
| `server`             | [Server](/values/server.md)                     | The server this reaction was made in                                                                                   |
| `locale`             | String                                          | The [locale](https://discord.com/developers/docs/reference#locales) of the user executing the interaction (e.g. en-US) |
| `creation_timestamp` | Number                                          | The timestamp as epoc milliseconds when this interaction was made                                                      |
| `custom_id`          | String                                          | The custom id of the select menu, as specified when creating the select menu component                                 |
| `chosen`             | List of Values (depends on type of select menu) | List of the chosen options                                                                                             |
| `options`            | List of Strings                                 | All values of options in the select menu. Only applicable for `select_menu_string`                                     |
| `min`                | number                                          | Minimum amount of selected entries for this select menu                                                                |
| `max`                | number                                          | Maximum amount of selected entries for this select menu                                                                |
| `placeholder`        | String                                          | Placeholder text of this select menu                                                                                   |
| `message`            | [Message](/values/message.md)                   | The message this interaction is attached to.                                                                           |