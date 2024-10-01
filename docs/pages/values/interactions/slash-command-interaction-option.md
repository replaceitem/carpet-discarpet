`dc_slash_command_interaction_option`

Represents the user-chosen options of an executed slash command.

#### Queryable:

| Property                 | Type                                                                                                  | Description                                              |
|--------------------------|-------------------------------------------------------------------------------------------------------|----------------------------------------------------------|
| `name`                   | String                                                                                                | The name of the command option.                          |
| `is_subcommand_or_group` | Boolean                                                                                               | Whether this option is a subcommand or subcommand group. |
| `value`                  | Any type                                                                                              | The value chosen by the user.                            |
| `options`                | List of [Slash command interaction options](/values/interactions/slash-command-interaction-option.md) | The sub-options of this option.                          |