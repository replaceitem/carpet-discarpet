`dc_user`

Represents a regular user, or a bot.


### {query:}

|          Property | Type                          | Description                                                                                                        |
|------------------:|:------------------------------|:-------------------------------------------------------------------------------------------------------------------|
|            `name` | String                        | The name of the user.                                                                                              |
|     `mention_tag` | String                        | The mention tag to mention a user in a message.                                                                    |
|              `id` | String                        | The ID of the user.                                                                                                |
|          `avatar` | String                        | The image URL used in the user's avatar picture.                                                                   |
|          `is_bot` | Boolean                       | Whether the user is a bot.                                                                                         |
|         `is_self` | Boolean                       | Whether the user is the currently logged in bot account itself.<br>Useful to prevent bots from replying to itself. |
| `private_channel` | [Channel](/values/channel.md) | The private messages (DMs) channel with the user.[^1]<br>Throws an exception on failure.                           |


[^1]: {% include 'blocking-property.md' %}
      <br>It does not freeze the game if the channel has already been created.