`dc_sticker`

Represents an official Discord sticker or a custom sticker from a server.

#### Queryable:

| Property      | Type                        | Description                                                                                |
|---------------|-----------------------------|--------------------------------------------------------------------------------------------|
| `id`          | String                      | The ID of the sticker.                                                                     |
| `name`        | String                      | The name of the sticker.                                                                   |
| `description` | String                      | The description of the sticker.                                                            |
| `tags`        | String                      | The autocompletion/suggestion tags of the sticker.                                         |
| `sort_value`  | Number                      | The sort order in the sticker pack.<br>Returns `null` if the sticker is custom.            |
| `format_type` | String                      | The [format type](/values/sticker.md#format-types) of the sticker.                         |
| `pack_id`     | Number                      | The ID of the sticker pack this sticker is in.<br>Returns `null` if the sticker is custom. |
| `server`      | [Server](/values/server.md) | The server this sticker is in.<br>Returns `null` if the sticker is official.               |
| `type`        | String                      | The [type](/values/sticker.md#format-types) of sticker.                                    |
| `user`        | [User](/values/user.md)     | The user who created this sticker.<br>Returns `null` if the sticker is official.           |

#### Sticker types

* `STANDARD` - An official Discord sticker made by Discord
* `SERVER` - A custom sticker made in a server
* `UNKNOWN`

#### Format types

* `PNG`
* `APNG`
* `LOTTIE`
* `UNKNOWN`