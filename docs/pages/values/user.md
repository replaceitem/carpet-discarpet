`dc_user`

Represents a regular user, or a bot.

#### Queryable:

| Property             | Type                          | Description                                                                                                               |
|----------------------|-------------------------------|---------------------------------------------------------------------------------------------------------------------------|
| `name`               | String                        | The name of the user.<br>To get the nickname, use [`dc_get_display_name()`](/functions/users/get-display-name.md).        |
| `mention_tag`        | String                        | The mention tag to mention a user in a message.                                                                           |
| `discriminated_name` | String                        | The name of the user with its discriminator (e.g. `replaceitem#9118`).                                                    |
| `id`                 | String                        | The ID of the user.                                                                                                       |
| `avatar`             | String                        | The image URL used in the user's avatar picture.                                                                          |
| `is_bot`             | Boolean                       | Whether if the user is a bot.                                                                                             |
| `is_self`            | Boolean                       | Whether if the user is the currently logged in bot account itself.<br>Useful to prevent bots replying to itself.          |
| `private_channel`    | [Channel](/values/channel.md) | The private messages (DMs) channel with the user.<br>Note that this may block, if the private channel was not yet opened. |