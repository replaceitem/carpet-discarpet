`component`

Can be either a [Button](button.md), a [Select menu](select-menu.md) or a [Text input](text-input.md).

| Value       | Type   | Description                     |
|------------:|--------|---------------------------------|
| `component` | String | The [type][1] of the component. |

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

* `button` - Button
* `text_input` - Text input
* `select_menu_string` - Select an item
* `select_menu_user` - Select a user
* `select_menu_role` - Select a role
* `select_menu_mentionable` - Select a user/role
* `select_menu_channel` - Select a channel

[1]: #component-types