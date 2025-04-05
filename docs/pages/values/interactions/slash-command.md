`dc_slash_command_interaction`

Represents an interaction of an executed slash command from
[`__on_discord_slash_command`](/events/discord-slash-command.md).


### {query:}

|            Property | Type                       | Description                                                            |
|--------------------:|:---------------------------|:-----------------------------------------------------------------------|
|        `command_id` | String                     | The ID of the application command.                                     |
|      `command_name` | String                     | The name of the application command.                                   |
|         `arguments` | List of [Options](#option) | The selected options of the command.                                   |
| `arguments_by_name` | Map of [Options](#option)  | A map of all options (and sub-options), with the key being their name. |



## Option

`dc_slash_command_interaction_option`

Represents a user-chosen option of an executed slash command.


### {query:}

|                 Property | Type                                                                                                                                          | Description                                                   |
|-------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------|
|                   `name` | String                                                                                                                                        | The name of the command option.                               |
| `is_subcommand_or_group` | Boolean                                                                                                                                       | Whether if this option is a subcommand or a subcommand group. |
|                  `value` | String, Number, Boolean, [User](/values/user.md), [Channel](/values/channel.md), [Role](/values/role.md), [Attachment](/values/attachment.md) | The value chosen by the user.                                 |
|                `options` | List of [Options](#option)                                                                                                                    | The sub-options of this option.                               |