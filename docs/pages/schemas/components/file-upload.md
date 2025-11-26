`file_upload`

!!! example-scripts inline end "Example scripts"
    * [Modal](/examples/modal.md)

Used for creating an upload area inside a modal that lets the user upload one or more files.

Can only be used inside [modals](/schemas/modal.md).


### {map:}

|               Key | Type    | Description                            |
|------------------:|:--------|:---------------------------------------|
|       `component` | String  | For a file upload always `file_upload` |
|              `id` | String  | The ID of the file upload component.   |
| `min_values` {:?} | Number  | The minimum number of files to upload. |
| `max_values` {:?} | Number  | The maximum number of files to upload. |
|   `required` {:?} | Boolean | Whether the file upload is required.   |
