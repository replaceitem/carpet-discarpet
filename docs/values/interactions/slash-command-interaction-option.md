`dc_slash_command_interaction_option`

Represents the user-chosen options of a slash command execution.

#### Queryable:

| Property                 | Type                                                                             | Description                                             |
|--------------------------|----------------------------------------------------------------------------------|---------------------------------------------------------|
| `name`                   | String                                                                           | Name of the command option                              |
| `is_subcommand_or_group` | boolean                                                                          | Whether this option is a subcommand or subcommand group |
| `value`                  | ? (depends on the slash command option type)                                     | The value chosen by the user                            |
| `options`                | List of [slash command interaction options](../slash-command-interaction-option) | The sub-options of this option                          |