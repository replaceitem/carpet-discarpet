`file_type`

!!! example-scripts inline end "Example scripts"
    * [Embed](/examples/modal.md)

Used for filtering file types in [File upload](/schemas/components/file-upload.md) components or attachment [Slash command options](/schemas/commands/slash-command-builder.md#option)
For more information, check the [Discord Developer Docs](https://docs.discord.com/developers/reference#file-type-filtering).

Can be either a direct file extension, or a group.

Can also be parsed directly from a String.
If prefixed with a dot (`.`), is interpreted as an `extension`, otherwise a `group`.

For example: `'image'`, `'.pdf'`.


### {map:}

|         Key | Type   | Description                                                        |
|------------:|:-------|:-------------------------------------------------------------------|
| `extension` | String | File extension, like `png`, `pdf`, `mp4`                           |
|     `group` | String | Group of file extension. Can be either `image`, `video` or `audio` |