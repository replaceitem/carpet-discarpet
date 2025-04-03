`dc_slash_command_interaction`

Represents an interaction of an executed slash command from [`__on_discord_slash_command`](/events/discord-slash-command.md).

#### Queryable:

| Property             | Type                              | Description                                                            |
|----------------------|-----------------------------------|------------------------------------------------------------------------|
| `command_id`         | String                            | The ID of the application command.                                     |
| `command_name`       | String                            | The name of the application command.                                   |
| `arguments`          | List of [Options][1]              | The selected options of the command.                                   |
| `arguments_by_name`  | Map of [Options][1] by their name | A map of all options (and sub-options), with the key being their name. |

### Option

`dc_slash_command_interaction_option`

Represents a user-chosen option of an executed slash command.

#### Queryable:

| Property                 | Type                                                                         | Description                                              |
|--------------------------|------------------------------------------------------------------------------|----------------------------------------------------------|
| `name`                   | String                                                                       | The name of the command option.                          |
| `is_subcommand_or_group` | Boolean                                                                      | Whether this option is a subcommand or subcommand group. |
| `value`                  | String, Number, Boolean, [User][2], [Channel][3], [Role][4], [Attachment][5] | The value chosen by the user.                            |
| `options`                | List of [options][1]                                                         | The sub-options of this option.                          |

[1]: #option
[2]: /values/user.md
[3]: /values/channel.md
[4]: /values/role.md
[5]: /values/attachment.md