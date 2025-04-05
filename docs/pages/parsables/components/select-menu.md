`select_menu_string`,
`select_menu_user`,
`select_menu_role`,
`select_menu_mentionable`,
`select_menu_channel`

Used for creating a select menu component.


### {map:}

|                Key | Type                       | Description                                                       |
|-------------------:|:---------------------------|:------------------------------------------------------------------|
|               `id` | String                     | The ID of the select menu. Used to identify when interacted with. |
|     `options` {:?} | List of [Options](#option) | All options selectable in this select menu.[^1]                   |
|         `min` {:?} | Number                     | The minimum number of options that need to be selected.           |
|         `max` {:?} | Number                     | The maximum number of options that can be selected.               |
| `placeholder` {:?} | String                     | The text displayed when nothing is selected yet.                  |



## Option

`select_menu_option`

Used for creating a select menu option.


### {map:}

|                   Key | Type                                      | Description                             |
|----------------------:|:------------------------------------------|:----------------------------------------|
|               `value` | String                                    | The internal value of the option.       |
|               `label` | String                                    | The label of the option.                |
|          `emoji` {:?} | String or [Emoji](/values/emoji.md)   Key | The emoji displayed next to the label.  |
|    `description` {:?} | String                                    | The description of the option.          |
| `default_option` {:?} | Boolean                                   | Whether if this is selected by default. |



[^1]: Only for `select_menu_string` type.