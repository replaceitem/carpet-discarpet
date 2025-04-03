`attachment`

An attachment can be created in different ways,
either by using a file, URL, or from a string that will be the raw data of the file.

| Value          | Type                             | Description                            |
|---------------:|----------------------------------|----------------------------------------|
| `file` {:?}    | String                           | The file path for attached file.       |
| `url` {:?}     | String                           | The URL of the file to attach.         |
| `bytes` {:?}   | String                           | The file's binary data.                |
| `image` {:?}   | Image from [Scarpet Graphics][1] | The image to be sent as an attachment. |
| `name` {:?}    | String                           | The file name if using `bytes`.        |
| `spoiler` {:?} | Boolean<br>(`false` by default)  | Whether this attachment is a spoiler.  |

[1]: https://github.com/replaceitem/scarpet-graphics