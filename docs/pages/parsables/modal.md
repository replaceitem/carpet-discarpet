`modal`

Used for creating a modal.


### {map:}

|          Key | Type                                                              | Description                                            |
|-------------:|:------------------------------------------------------------------|:-------------------------------------------------------|
|         `id` | String                                                            | The ID for the modal. Used to identify when submitted. |
|      `title` | String                                                            | The title of the modal popup.                          |
| `components` | List of Lists of [Components](/parsables/components/component.md) | The rows of containers with components.[^1]            |


[^1]: Only supports [text input](/parsables/components/text-input.md) components.