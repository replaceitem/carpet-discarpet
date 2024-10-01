`slash_command_option`

There are two things this can do, depending on the `type`:

Either add subcommand literals, or parameters to the command.
Sub command groups are always on the first "layer",
while subcommands are always one layer deeper than sub command groups.

Note that this is quite limited in comparison to Minecraft commands. All paths of the command tree have to have either just a subcommand, or a subcommand group with subcommands each.
This means that the length of the command chains (without the other options that aren't subcommands) has to be equal for all subcommands.

For more info, [check this documentation](https://canary.discord.com/developers/docs/interactions/application-commands#subcommands-and-subcommand-groups).

| Value         | Type                                                                                                     | Description                                                                                                                                                           |
|---------------|----------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `type`        | String                                                                                                   | The [type](https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-option-type) of slash command option. |
| `name`        | String                                                                                                   | The name of the option.                                                                                                                                               |
| `description` | String                                                                                                   | The description of the option.                                                                                                                                        |
| `required`    | Boolean<br>(optional, defaults to false)                                                                 | Whether this option is required to be specified.                                                                                                                      |
| `options`     | List of [Slash command options](/parsables/commands/slash-command-option.md)<br>(optional)               | Sub-options to this sub-command/group.<br>(Only for `SUB_COMMAND` or `SUB_COMMAND_GROUP`)                                                                             |
| `choices`     | List of [Slash command option choices](/parsables/commands/slash-command-option-choice.md)<br>(optional) | Autocompletable choices for this command option.                                                                                                                      |