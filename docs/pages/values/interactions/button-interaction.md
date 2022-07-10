`dc_button_interaction`

Value from `__on_discord_button(interaction)` event, used for getting the message interaction details, and then responding to it with `dc_respond_interaction()`

#### Queryable:

| Property  | Type                     | Description                                                                                                            |
|-----------|--------------------------|------------------------------------------------------------------------------------------------------------------------|
| `id`      | String                   | Id of the button or select menu, which was specified by the user in the `dc_send_message` message parameter            |
| `channel` | [Channel](../../channel) | The channel this interaction was made in.                                                                              |
| `user`    | [User](../../user)       | The user that used the interaction.                                                                                    |
| `message` | [Message](../../message) | The message this interaction is attached to.                                                                           |
| `locale`  | String                   | The [locale](https://discord.com/developers/docs/reference#locales) of the user executing the interaction (e.g. en-US) |