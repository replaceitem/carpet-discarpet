`text_input`

!!! example-scripts inline end "Example scripts"
    * [Modal](/examples/modal.md)

Used for creating a text input component.

Can only be used inside [modals](/schemas/modal.md).


### {map:}

|                Key | Type    | Description                                            |
|-------------------:|:--------|:-------------------------------------------------------|
|        `component` | String  | For a text input always `text_input`                   |
|               `id` | String  | The ID of the text input.                              |
|            `style` | String  | The [style](#text-input-styles) of the text input.     |
|            `label` | String  | The label of the text input.                           |
|  `min_length` {:?} | Number  | The minimum length of the text in the text input.      |
|  `max_length` {:?} | Number  | The maximum length of the text in the text input.      |
|    `required` {:?} | Boolean | Whether this text input is required.                   |
|       `value` {:?} | String  | The pre-filled value for the text input.               |
| `placeholder` {:?} | String  | The placeholder text shown if the text input is empty. |

#### Text input styles

|      String | Description       |
|------------:|:------------------|
|     `short` | Single-line input |
| `paragraph` | Multi-line input  |