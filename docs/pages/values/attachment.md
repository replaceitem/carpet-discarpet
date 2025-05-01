`dc_attachment`

Represents an attachment from a message or slash command.


### {query:}

|     Property | Type                                | Description                                                                            |
|-------------:|:------------------------------------|:---------------------------------------------------------------------------------------|
|  `file_name` | String                              | The name of the attachment.                                                            |
|       `size` | Number                              | The size as the number of bytes of the attached file.                                  |
|        `url` | String                              | The URL of the attachment.                                                             |
|  `proxy_url` | String                              | The proxy URL of the file.                                                             |
|   `is_image` | Boolean                             | Whether if this file is an image.                                                      |
|      `width` | Number, Null                        | The width of the attached image.<br>Returns `null` if the attachment is not an image.  |
|     `height` | Number, Null                        | The height of the attached image.<br>Returns `null` if the attachment is not an image. |
| `is_spoiler` | Boolean                             | Whether if this file is marked as a spoiler.                                           |
|   `download` | String                              | Downloads the file's bytes as a string.[^1]<br>Throws an exception on failure.         |


[^1]: {% include 'blocking-property.md' %}