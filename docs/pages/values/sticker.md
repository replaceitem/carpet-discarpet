`dc_sticker`

This value stores a Sticker, either a default discord sticker or a custom sticker from a server.

#### Queryable:

| Property      | Type                        | Description                                                 |
|---------------|-----------------------------|-------------------------------------------------------------|
| `id`          | String                      | The id of the sticker                                       |
| `name`        | String                      | The name of the sticker                                     |
| `description` | String                      | The description of the sticker                              |
| `tags`        | String                      | Tags for autocompletion/suggestion of the sticker           |
| `sort_value`  | Number                      | The sort order in the sticker pack (or `null`)              |
| `format_type` | String                      | The format type of the sticker (PNG, APNG, LOTTIE, UNKNOWN) |
| `pack_id`     | Number                      | The id of the sticker pack this sticker is in (or `null`)   |
| `server`      | [Server](/values/server.md) | The server this sticker is in (or `null`)                   |
| `type`        | String                      | The type of sticker (STANDARD, SERVER, UNKNOWN)             |
| `user`        | [User](/values/user.md)     | The user who created this sticker (or `null`)               |
