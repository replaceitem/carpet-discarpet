`media_gallery`

!!! example-scripts inline end "Example scripts"
    * [Miscellaneous components](/examples/misc-components.md)

A media gallery shows several images in a gallery.

### {map:}

|                Key | Type                                               | Description                                 |
|-------------------:|:---------------------------------------------------|:--------------------------------------------|
|        `component` | String                                             | For media galleries always `media_gallery`. |
|            `items` | List of [Media gallery items](#media-gallery-item) | The images in the gallery.                  |

## Media gallery item

### {map:}

|                Key | Type                            | Description                                 |
|-------------------:|:--------------------------------|:--------------------------------------------|
|            `media` | [File object](/schemas/file.md) | The image file to show.                     |
| `description` {:?} | String                          | The alt text for the image.                 |
|     `spoiler` {:?} | Boolean                         | Whether to show the image as a spoiler.     |
