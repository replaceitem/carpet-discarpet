Represents an interaction. All specialized interactions have these properties, along with their additional properties.

Used for getting its details, and responding with
[`dc_respond_interaction`](/functions/interactions/respond-interaction.md).

| Property             | Type         | Description                                                                |
|----------------------|--------------|----------------------------------------------------------------------------|
| `id`                 | String       | The ID of the interaction.                                                 |
| `channel`            | [Channel][1] | The channel this interaction was made in.                                  |
| `user`               | [User][2]    | The user who made the interaction.                                         |
| `token`              | String       | The continuation token internally used for responding to this interaction. |
| `server`             | [Server][3]  | The server this interaction was made in.                                   |
| `locale`             | String       | The [locale][4] of the user executing the interaction (e.g. en-US).        |
| `creation_timestamp` | Number       | The timestamp as epoch milliseconds when this interaction was made.        |

[1]: /values/channel.md
[2]: /values/user.md
[3]: /values/server.md

[4]: https://discord.com/developers/docs/reference#locales