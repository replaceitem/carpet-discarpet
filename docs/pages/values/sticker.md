`dc_sticker`

Represents an official Discord sticker or a custom sticker from a server.

#### Queryable:

| Property      | Type        | Description                                                                                |
|--------------:|-------------|--------------------------------------------------------------------------------------------|
| `id`          | String      | The ID of the sticker.                                                                     |
| `name`        | String      | The name of the sticker.                                                                   |
| `description` | String      | The description of the sticker.                                                            |
| `tags`        | String      | The autocompletion/suggestion tags of the sticker.                                         |
| `sort_value`  | Number      | The sort order in the sticker pack.<br>Returns `null` if the sticker is custom.            |
| `format_type` | String      | The [format type][3] of the sticker.                                                       |
| `pack_id`     | Number      | The ID of the sticker pack this sticker is in.<br>Returns `null` if the sticker is custom. |
| `server`      | [Server][1] | The server this sticker is in.<br>Returns `null` if the sticker is official.               |
| `type`        | String      | The [type][4] of sticker.                                                                  |
| `user`        | [User][2]   | The user who created this sticker.<br>Returns `null` if the sticker is official.           |

#### Sticker types

* `STANDARD` - An official Discord sticker made by Discord
* `SERVER` - A custom sticker made in a server
* `UNKNOWN`

#### Format types

* `PNG`
* `APNG`
* `LOTTIE`
* `UNKNOWN`

[1]: /values/server.md
[2]: /values/user.md

[3]: /values/sticker.md#format-types
[4]: /values/sticker.md#sticker-types