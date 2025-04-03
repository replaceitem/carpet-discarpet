`select_menu_string`,
`select_menu_user`,
`select_menu_role`,
`select_menu_mentionable`,
`select_menu_channel`

Used for creating a select menu component.

| Value              | Type                             | Description                                                                      |
|-------------------:|----------------------------------|----------------------------------------------------------------------------------|
| `id`               | String                           | The ID of the button. Used to identify them when pressed.                        |
| `options` {:?}     | List of [Options][1]             | All options] selectable in this select menu, only for `select_menu_string` type. |
| `min` {:?}         | Number                           | The minimum number of options that need to be selected.                          |
| `max` {:?}         | Number                           | The maximum number of options that can be selected.                              |
| `placeholder` {:?} | String                           | The text displayed when nothing is selected yet.                                 |

### Option

`select_menu_option`

Used for creating a select menu option.

| Value                 | Type                       | Description                             |
|----------------------:|----------------------------|-----------------------------------------|
| `value`               | String                     | The internal value of the option.       |
| `label`               | String                     | The label of the option.                |
| `emoji` {:?}          | String or [Emoji][2] value | The emoji displayed next to the label.  |
| `description` {:?}    | String                     | The description of the option.          |
| `default_option` {:?} | Boolean                    | Whether if this is selected by default. |

[1]: #option
[2]: /values/emoji.md