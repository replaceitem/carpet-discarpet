`attachment`

An attachment can be created in different ways, either by using a file, URL, or from a string that will be the raw data of the file.

| Value     | Type                                                                                         | Description                            |
|-----------|----------------------------------------------------------------------------------------------|----------------------------------------|
| `file`    | String<br>(optional)                                                                         | The file path for attached file.       |
| `url`     | String<br>(optional)                                                                         | The URL of the file to attach.         |
| `bytes`   | String<br>(optional)                                                                         | The file's binary data.                |
| `image`   | Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics)<br>(optional) | The image to be sent as an attachment. |
| `name`    | String<br>(optional)                                                                         | The file name if using `bytes`.        |
| `spoiler` | Boolean<br>(optional, defaults to false)                                                     | Whether this attachment is a spoiler.  |