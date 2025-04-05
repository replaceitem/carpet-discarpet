`component`

Used for creating a component, which contains additional values from the used component.

Can be either a [Button](button.md), [Select menu](select-menu.md), or a [Text input](text-input.md).


### {map:}

|         Key | Type   | Description                                    |
|------------:|:-------|:-----------------------------------------------|
| `component` | String | The [type](#component-types) of the component. |

Values from the used parsable must be included, like so:

```sc
component = {
    'component' -> 'BUTTON',
    'id' -> 'blurple_button',
    'style' -> 'BLURPLE',
    'label' -> 'Blurple button',
    'emoji' -> '🚪'
},
```

#### Component types

|                    String | Description        |
|--------------------------:|:-------------------|
|                  `BUTTON` | Button             |
|      `SELECT_MENU_STRING` | Select an item     |
|        `SELECT_MENU_USER` | Select a user      |
|        `SELECT_MENU_ROLE` | Select a role      |
| `SELECT_MENU_MENTIONABLE` | Select a user/role |
|     `SELECT_MENU_CHANNEL` | Select a channel   |
|              `TEXT_INPUT` | Text input         |