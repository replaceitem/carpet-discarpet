`attachment`

!!! example-scripts inline end "Example scripts"
    * [Attachments](/examples/attachments.md)

Used to create an attachment.

An attachment can be created in different ways,
either by using a file, URL, or from a string that will be the raw data of the file.


### {map:}

|                Key | Type                            | Description                                                         |
|-------------------:|:--------------------------------|:--------------------------------------------------------------------|
|             `file` | [File object](/schemas/file.md) | The file to use for the attachment.                                 |
|        `name` {:?} | String                          | The name of the attachment.                                         |
| `description` {:?} | String                          | The description of the attachment. This is the alt-text for images. |
|     `spoiler` {:?} | Boolean                         | Whether this attachment is marked as a spoiler.                     |