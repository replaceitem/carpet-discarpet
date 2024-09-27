`dc_attachment`

An attachment from a message or slash command

#### Queryable:

| Property     | Type                          | Description                                                                                                           |
|--------------|-------------------------------|-----------------------------------------------------------------------------------------------------------------------|
| `message`    | [Message](/values/message.md) | The message of this attachment.<br>Returns `null` if this is not a message attachment                                 |
| `file_name`  | String                        | The name of the attachment                                                                                            |
| `size`       | Number                        | The size as the number of bytes of the attached file                                                                  |
| `url`        | String                        | The URL of the attachment                                                                                             |
| `proxy_url`  | String                        | The proxy URL of this file                                                                                            |
| `is_image`   | Boolean                       | Whether this file is an image or not                                                                                  |
| `width`      | Number                        | The width of the attached image.<br>Returns `null` if the attachment is not an image                                  |
| `height`     | Number                        | The height of the attached image.<br>Returns `null` if the attachment is not an image                                 |
| `is_spoiler` | Boolean                       | Whether this file is marked as a spoiler                                                                              |
| `download`   | String                        | Downloads the file's bytes as a string.<br>Be careful with this, as big files can block the game for quite some time! |