`dc_select_menu_interaction`

Represents an interaction from a user inputting into a select menu from
[`__on_discord_select_menu`](/events/discord-select-menu.md).


### {query:}

Also has all properties from the [base interaction](/values/interactions/interaction.md)

|         Property | Type                          | Description                                                                               |
|-----------------:|:------------------------------|:------------------------------------------------------------------------------------------|
|      `custom_id` | String                        | The custom ID of the select menu.                                                         |
|         `chosen` | List                          | All selected options.                                                                     |
|        `options` | List of Strings               | All values of options in the select menu.[^1]                                             |
|            `min` | Number                        | Minimum amount of selected entries for this select menu.                                  |
|            `max` | Number                        | Maximum amount of selected entries for this select menu.                                  |
|    `placeholder` | String                        | The placeholder text of this select menu.                                                 |
|        `message` | [Message](/values/message.md) | The message this interaction is attached to.                                              |
| `component_type` | String                        | The type of the component for distinguishing between the different types of select menus. |


[^1]: Only applicable for `select_menu_string`.