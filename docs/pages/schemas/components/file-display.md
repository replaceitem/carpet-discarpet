`file_display`

!!! example-scripts inline end "Example scripts"
    * [Miscellaneous components](/examples/misc-components.md)

A file display is a component that shows a file for downloading.

### {map:}

|            Key | Type                            | Description                              |
|---------------:|:--------------------------------|:-----------------------------------------|
|    `component` | String                          | For file displays always `file_display`. |
|         `file` | [File object](/schemas/file.md) | The file to show.                        |
|    `name` {:?} | String                          | The alternative name for the file.       |
| `spoiler` {:?} | Boolean                         | Whether to show the file as a spoiler.   |
