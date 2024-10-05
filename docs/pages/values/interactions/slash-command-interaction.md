`dc_slash_command_interaction`

Represents an interaction of an executed slash command from [`__on_discord_slash_command()`](/events/discord-slash-command.md).

#### Queryable:

| Property             | Type                                                                                                | Description                                                            |
|----------------------|-----------------------------------------------------------------------------------------------------|------------------------------------------------------------------------|
| `command_id`         | String                                                                                              | The ID of the application command.                                     |
| `command_name`       | String                                                                                              | The name of the application command.                                   |
| `arguments`          | List of [options](/values/interactions/slash-command-interaction.md#option-definition)              | The selected options of the command.                                   |
| `arguments_by_name`  | Map of [options](/values/interactions/slash-command-interaction.md#option-definition) by their name | A map of all options (and sub-options), with the key being their name. |

### Option definition

`dc_slash_command_interaction_option`

Represents a user-chosen option of an executed slash command.

#### Queryable:

| Property                 | Type                                                                                                                                          | Description                                              |
|--------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------|
| `name`                   | String                                                                                                                                        | The name of the command option.                          |
| `is_subcommand_or_group` | Boolean                                                                                                                                       | Whether this option is a subcommand or subcommand group. |
| `value`                  | String, Number, Boolean, [User](/values/user.md), [Channel](/values/channel.md), [Role](/values/role.md), [Attachment](/values/attachment.md) | The value chosen by the user.                            |
| `options`                | List of [options](/values/interactions/slash-command-interaction.md#option-definition)                                                        | The sub-options of this option.                          |