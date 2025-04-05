`text_input`

Used for creating a text input component.

Can only be used inside [modals](/parsables/modal.md).


### {map:}

|                Key | Type    | Description                                            |
|-------------------:|:--------|:-------------------------------------------------------|
|               `id` | String  | The ID of the text input.                              |
|            `style` | String  | The [style](#text-input-styles) of the text input.     |
|            `label` | String  | The label of the text input.                           |
|  `min_length` {:?} | Number  | The minimum length of the text in the text input.      |
|  `max_length` {:?} | Number  | The maximum length of the text in the text input.      |
|    `required` {:?} | Boolean | Whether if this text input is required.                |
|       `value` {:?} | String  | The pre-filled value for the text input.               |
| `placeholder` {:?} | String  | The placeholder text shown if the text input is empty. |

#### Text input styles

|      String | Description       |
|------------:|:------------------|
|     `SHORT` | Single-line input |
| `PARAGRAPH` | Multi-line input  |