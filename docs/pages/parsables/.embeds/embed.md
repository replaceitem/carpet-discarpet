`embed`

| Value              | Type                                       | Description                                                    |
|-------------------:|--------------------------------------------|----------------------------------------------------------------|
| `title`            | String                                     | The title of the embed.                                        |
| `url` {:?}         | String                                     | The URL redirect when clicking on the embed title.             |
| `description` {:?} | String                                     | The description below the title.                               |
| `author` {:?}      | [Embed author][1]                          | The author shown on top of the embed.                          |
| `fields` {:?}      | List of [Embed fields][2]                  | The fields inside the embed.                                   |
| `color` {:?}       | [Color][3]                                 | The color of the embed.                                        |
| `footer` {:?}      | [Embed footer][4]                          | The footer shown at the bottom of the embed.                   |
| `image` {:?}       | String or Image from [Scarpet Graphics][5] | The image URL (or image file) used for the embed's image.      |
| `thumbnail` {:?}   | String or Image from [Scarpet Graphics][5] | The image URL (or image file) used for the embed's thumbnail.  |
| `timestamp` {:?}   | [Timestamp][6]                             | The timestamp of the embed, which will be shown at the bottom. |

[1]: /parsables/embeds/embed-author.md
[2]: /parsables/embeds/embed-field.md
[3]: /parsables/color.md
[4]: /parsables/embeds/embed-footer.md
[5]: https://github.com/replaceitem/scarpet-graphics
[6]: /parsables/timestamp.md