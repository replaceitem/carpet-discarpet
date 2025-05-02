`select_menu`

Used for creating a select menu component.


### {map:}

|                  Key | Type                       | Description                                                                                                        |
|---------------------:|:---------------------------|:-------------------------------------------------------------------------------------------------------------------|
|          `component` | String                     | One of the select menu [component types](/parsabled/components/component.md#component-types) ending with `_select` |
|                 `id` | String                     | The ID of the select menu. Used to identify when interacted with.                                                  |
|       `options` {:?} | List of [Options](#option) | All options selectable in this select menu.[^1]                                                                    |
| `channel_types` {:?} | List of Strings            | The [channel types](/values/channel.md#channel-types) allowed if this is a `channel_select` component.             |
|           `min` {:?} | Number                     | The minimum number of options that need to be selected.                                                            |
|           `max` {:?} | Number                     | The maximum number of options that can be selected.                                                                |
|   `placeholder` {:?} | String                     | The text displayed when nothing is selected yet.                                                                   |



## Option

`select_menu_option`

Used for creating a select menu option.


### {map:}

|                   Key | Type                         | Description                                 |
|----------------------:|:-----------------------------|:--------------------------------------------|
|               `value` | String                       | The internal value of the option.           |
|               `label` | String                       | The label of the option.                    |
|          `emoji` {:?} | [Emoji](/parsables/emoji.md) | The emoji displayed next to the label.      |
|    `description` {:?} | String                       | The description of the option.              |
| `default_option` {:?} | Boolean                      | Whether option this is selected by default. |



[^1]: Only for `string_select` type.