`component`

Used for creating a component, which contains additional values from the used component.

Can be either a [Button](button.md), [Select menu](select-menu.md), or a [Text input](text-input.md).


### {map:}

|         Key | Type   | Description                                    |
|------------:|:-------|:-----------------------------------------------|
| `component` | String | The [type](#component-types) of the component. |

Values from the used schema must be included, like so:

```sc
component = {
    'component' -> 'button',
    'id' -> 'blurple_button',
    'style' -> 'primary',
    'label' -> 'Blurple button',
    'emoji' -> '🚪'
},
```

#### Component types

|               String | Description        |
|---------------------:|:-------------------|
|             `button` | Button             |
|      `string_select` | Select an item     |
|        `user_select` | Select a user      |
|        `role_select` | Select a role      |
| `mentionable_select` | Select a user/role |
|     `channel_select` | Select a channel   |
|         `text_input` | Text input         |