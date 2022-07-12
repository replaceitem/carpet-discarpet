`attachment`

An attachment can be created in different ways.
From a File, a URL or from a string which will be the raw bytes of the file.

| Value     | Type                                                                                      | Description                                  |
|-----------|-------------------------------------------------------------------------------------------|----------------------------------------------|
| `file`    | String (optional)                                                                         | File path for attached file                  |
| `url`     | String (optional)                                                                         | URL of the file to attach                    |
| `bytes`   | String (optional)                                                                         | String, which will be the file's binary data |
| `image`   | Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) (optional) | Image to be sent as an attachment            |
| `name`    | String (optional)                                                                         | File name if using `bytes`                   |
| `spoiler` | boolean (optional, defaults to false)                                                     | Whether this attachment is a spoiler         |