`dc_attachment`

An attachment from a message or slash command

#### Queryable:

| Property     | Type                  | Description                                                                                                        |
|--------------|-----------------------|--------------------------------------------------------------------------------------------------------------------|
| `message`    | [Message](../message) | The message of this attachment, or null if this is not a message attachment                                        |
| `file_name`  | String                | File name of the attachment                                                                                        |
| `size`       | number                | The size as the number of bytes of the attached file                                                               |
| `url`        | String                | The URL of this file                                                                                               |
| `proxy_url`  | String                | The proxy URL of this file                                                                                         |
| `is_image`   | boolean               | Whether this file is an image or not                                                                               |
| `width`      | number or null        | The width of the attached image, or null if not an image                                                           |
| `height`     | number or null        | The height of the attached image, or null if not an image                                                          |
| `is_spoiler` | boolean               | Whether this file is marked as a spoiler                                                                           |
| `download`   | String                | Downloads the file's bytes as a string. Be careful with this one, big files can block the game for quite some time |