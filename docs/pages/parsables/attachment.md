`attachment`

Used to create an attachment.

An attachment can be created in different ways,
either by using a file, URL, or from a string that will be the raw data of the file.


### {input:}

|          Value | Type                                                                           | Description                              |
|---------------:|:-------------------------------------------------------------------------------|:-----------------------------------------|
|    `file` {:?} | String                                                                         | The file path for attached file.         |
|     `url` {:?} | String                                                                         | The URL of the file to attach.           |
|   `bytes` {:?} | String                                                                         | The file's binary data.                  |
|   `image` {:?} | Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) | The image to be sent as an attachment.   |
|    `name` {:?} | String                                                                         | The file name if using `bytes`.          |
| `spoiler` {:?} | Boolean                                                                        | Whether if this attachment is a spoiler. |