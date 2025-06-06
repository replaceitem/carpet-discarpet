`embed`

Used to create an embed.


### {map:}

|                Key | Type                                      | Description                                          |
|-------------------:|:------------------------------------------|:-----------------------------------------------------|
|            `title` | String                                    | The title of the embed.                              |
|         `url` {:?} | String                                    | The URL redirect when clicking on the embed title.   |
| `description` {:?} | String                                    | The description below the title.                     |
|      `author` {:?} | [Embed author object](#author)            | The author shown on top of the embed.                |
|      `fields` {:?} | List of [Embed field objects](#field)     | The fields inside the embed.                         |
|       `color` {:?} | [Color object](/schemas/color.md)         | The color of the embed.                              |
|      `footer` {:?} | [Embed footer object](#footer)            | The footer shown at the bottom of the embed.         |
|       `image` {:?} | [File object](/schemas/file.md)           | The image file to be used for the embed's image.     |
|   `thumbnail` {:?} | [File object](/schemas/file.md)           | The image file to be used for the embed's thumbnail. |
|   `timestamp` {:?} | [Timestamp object](/schemas/timestamp.md) | The timestamp of the embed.                          |



## Author

`embed_author`

Used to create the author in an embed.

Can also be parsed directly from a [User](/values/user.md) value, or a string (which will only set `name`).


### {map:}

|         Key | Type                            | Description                                    |
|------------:|:--------------------------------|:-----------------------------------------------|
|      `name` | String                          | The display name of the author.                |
|  `url` {:?} | String                          | The URL link when clicking on the author name. |
| `icon` {:?} | [File object](/schemas/file.md) | The image file to use for the author icon.     |



## Field

`embed_field`

Used to create a field in an embed.


### {map:}

|           Key | Type    | Description                            |
|--------------:|:--------|:---------------------------------------|
|        `name` | String  | The name/title of this field.          |
|       `value` | String  | The value/description of this field.   |
| `inline` {:?} | Boolean | Whether this field should be inline.   |



## Footer

Used to create the footer in an embed.


### {map:}

`embed_footer`

|         Key | Type                            | Description                                |
|------------:|:--------------------------------|:-------------------------------------------|
|      `text` | String                          | The footer text.                           |
| `icon` {:?} | [File object](/schemas/file.md) | The image file to use for the footer icon. |