`embed`

Used to create an embed.


### {input:}

|              Value | Type                                                                                   | Description                                                   |
|-------------------:|:---------------------------------------------------------------------------------------|:--------------------------------------------------------------|
|            `title` | String                                                                                 | The title of the embed.                                       |
|         `url` {:?} | String                                                                                 | The URL redirect when clicking on the embed title.            |
| `description` {:?} | String                                                                                 | The description below the title.                              |
|      `author` {:?} | [Embed author](#author)                                                                | The author shown on top of the embed.                         |
|      `fields` {:?} | List of [Embed fields](#field)                                                         | The fields inside the embed.                                  |
|       `color` {:?} | [Color](/parsables/color.md)                                                           | The color of the embed.                                       |
|      `footer` {:?} | [Embed footer](#footer)                                                                | The footer shown at the bottom of the embed.                  |
|       `image` {:?} | String, Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) | The image URL (or image file) used for the embed's image.     |
|   `thumbnail` {:?} | String, Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) | The image URL (or image file) used for the embed's thumbnail. |
|   `timestamp` {:?} | [Timestamp](/parsables/timestamp.md)                                                   | The timestamp of the embed.                                   |



## Author

`embed_author`

Used to create the author in an embed.

Can also be parsed directly from a [User](/values/user.md) value, or a string (which will only set `name`).


### {input:}

|       Value | Type                                                                                   | Description                                               |
|------------:|:---------------------------------------------------------------------------------------|:----------------------------------------------------------|
|      `name` | String                                                                                 | The display name of the author.                           |
|  `url` {:?} | String                                                                                 | The URL link when clicking on the author name.            |
| `icon` {:?} | String, Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) | The image URL (or image file) used for the author's icon. |



## Field

`embed_field`

Used to create a field in an embed.


### {input:}

|         Value | Type    | Description                             |
|--------------:|:--------|:----------------------------------------|
|        `name` | String  | The name/title of this field.           |
|       `value` | String  | The value/description of this field.    |
| `inline` {:?} | Boolean | Whether if this field should be inline. |



## Footer

Used to create the footer in an embed.


### {input:}

`embed_footer`

|       Value | Type                                                                                   | Description                                               |
|------------:|:---------------------------------------------------------------------------------------|:----------------------------------------------------------|
|      `text` | String                                                                                 | The footer text.                                          |
| `icon` {:?} | String, Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) | The image URL (or image file) used for the footer's icon. |