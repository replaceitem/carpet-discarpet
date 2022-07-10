`slash_command_option`

There are two things this can do, depending on the `type`.
Either add subcommand literals, or parameters to the command.
Sub command groups are always on the first "layer",
while subcommands are always one layer deeper than sub command groups.
Note that this is quite limited in comparison to minecraft commands.
All paths of the command tree have to have either just a sub command, or a sub command group with sub commands each.
This means that the length of the command chains (without the other options that aren't subcommands) has to be equal for all subcommands.
See: https://canary.discord.com/developers/docs/interactions/application-commands#subcommands-and-subcommand-groups

| Value         | Type                                                                            | Description                                                                                                                                                           |
|---------------|---------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `type`        | String                                                                          | The type of [slash command option](https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-option-type). |
| `name`        | String                                                                          | The name of this option                                                                                                                                               |
| `description` | String                                                                          | The description shown for this command option                                                                                                                         |
| `required`    | boolean (optional, defaults to false)                                           | Whether this option is required to be specified                                                                                                                       |
| `options`     | List of [Slash command options](#Slash-command-option) (optional)               | Sub-options to this sub-command/group. This is only for `sub_command` or `sub_command_group`.                                                                         |
| `choices`     | List of [Slash command option choices](#Slash-command-option-choice) (optional) | Autocompletable choices for this command option                                                                                                                       |