`modal`

!!! example-scripts inline end "Example scripts"
    * [Modal](/examples/modal.md)

Used for creating a modal.


### {map:}

|          Key | Type                                                                   | Description                                            |
|-------------:|:-----------------------------------------------------------------------|:-------------------------------------------------------|
|         `id` | String                                                                 | The ID for the modal. Used to identify when submitted. |
|      `title` | String                                                                 | The title of the modal popup.                          |
| `components` | List of Lists of [Component objects](/schemas/components/component.md) | The rows of containers with components.[^1]            |


[^1]: Only supports [text input](/schemas/components/text-input.md) components.