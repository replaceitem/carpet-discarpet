`component`

Can be either a [Button](button.md), a [Select menu](select-menu.md) or a [Text input](text-input.md).

|       Value | Type   | Description                                    |
|------------:|:-------|:-----------------------------------------------|
| `component` | String | The [type](#component-types) of the component. |

Values from the used parsable must be included, like so:

```sc
component = {
    'component' -> 'button',
    'id' -> 'blurple_button',
    'style' -> 'blurple',
    'label' -> 'Blurple button',
    'emoji' -> '🚪'
},
```

#### Component types

|                    String | Description        |
|--------------------------:|:-------------------|
|                  `button` | Button             |
|      `select_menu_string` | Select an item     |
|        `select_menu_user` | Select a user      |
|        `select_menu_role` | Select a role      |
| `select_menu_mentionable` | Select a user/role |
|     `select_menu_channel` | Select a channel   |
|              `text_input` | Text input         |