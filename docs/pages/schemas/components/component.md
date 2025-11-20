`component`

Used for creating a component, which contains additional values from the used component type.
The value specified in `type` determines the component. Each component has additional properties you can specify.
See the list of [types](#component-types) below for links to the pages for each component.

### {map:}

|         Key | Type   | Description                                    |
|------------:|:-------|:-----------------------------------------------|
| `component` | String | The [type](#component-types) of the component. |

Values from the used component type must be included, like so:

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

|               String | Description                                                                                                      |
|---------------------:|:-----------------------------------------------------------------------------------------------------------------|
|         `action_row` | [Action row](action-row.md) for multiple buttons in one row or one select menu.                                  |
|             `button` | [Button](button.md)                                                                                              |
|      `string_select` | [String select menu](select-menu.md) for selecting an item.                                                      |
|        `user_select` | [User select menu](select-menu.md) for selecting a user.                                                         |
|        `role_select` | [Role select menu](select-menu.md) for selecting a role.                                                         |
| `mentionable_select` | [Mentionable select menu](select-menu.md) for selecting a user/role.                                             |
|     `channel_select` | [Channel select menu](select-menu.md) for selecting a channel.                                                   |
|         `text_input` | [Text input](text-input.md)                                                                                      |
|              `label` | [Label](label.md) for wrapping a component with a label and description.                                         |
|          `separator` | [Separator](separator.md) that creates a divisor line between components.                                        |
|          `container` | [Container](container.md) that wrap other components in a box while also showing an accent bar on the left side. |
|            `section` | [Section](section.md) that shows an accessory component to the right of the other child components.              |
|       `text_display` | [Text display](text-display.md) for showing markdown text, just like the `content` in messages.                  |
