`embed`

| Value              | Type                                       | Description                                                    |
|-------------------:|--------------------------------------------|----------------------------------------------------------------|
| `title`            | String                                     | The title of the embed.                                        |
| `url` {:?}         | String                                     | The URL redirect when clicking on the embed title.             |
| `description` {:?} | String                                     | The description below the title.                               |
| `author` {:?}      | [Embed author][4]                          | The author shown on top of the embed.                          |
| `fields` {:?}      | List of [Embed fields][5]                  | The fields inside the embed.                                   |
| `color` {:?}       | [Color][1]                                 | The color of the embed.                                        |
| `footer` {:?}      | [Embed footer][6]                          | The footer shown at the bottom of the embed.                   |
| `image` {:?}       | String or Image from [Scarpet Graphics][2] | The image URL (or image file) used for the embed's image.      |
| `thumbnail` {:?}   | String or Image from [Scarpet Graphics][2] | The image URL (or image file) used for the embed's thumbnail.  |
| `timestamp` {:?}   | [Timestamp][3]                             | The timestamp of the embed, which will be shown at the bottom. |

### Author

`embed_author`

Can also be parsed directly from a [User](/values/user.md) value, or a string (which will only set `name`).

| Value       | Type                                        | Description                                               |
|------------:|---------------------------------------------|-----------------------------------------------------------|
| `name`      | String                                      | The display name of the author.                           |
| `url` {:?}  | String                                      | The URL link when clicking on the author name.            |
| `icon` {:?} | String, or Image from [Scarpet Graphics][2] | The image URL (or image file) used for the author's icon. |

### Field

`embed_field`

| Value         | Type    | Description                             |
|--------------:|---------|-----------------------------------------|
| `name`        | String  | The name or title of this field.        |
| `value`       | String  | The value or description of this field. |
| `inline` {:?} | Boolean | Whether if this field should be inline. |

### Footer

`embed_footer`

| Value       | Type                                        | Description                                               |
|------------:|---------------------------------------------|-----------------------------------------------------------|
| `text`      | String                                      | The footer text.                                          |
| `icon` {:?} | String, or Image from [Scarpet Graphics][2] | The image URL (or image file) used for the footer's icon. |

[1]: /parsables/color.md
[2]: https://github.com/replaceitem/scarpet-graphics
[3]: /parsables/timestamp.md

[4]: #author
[5]: #field
[6]: #footer

