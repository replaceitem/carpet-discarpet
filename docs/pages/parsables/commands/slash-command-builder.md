`slash_command_builder`

Used to create a slash command.


### {map:}

|           Key | Type                       | Description                           |
|--------------:|:---------------------------|:--------------------------------------|
|        `name` | String                     | The name of the slash command.        |
| `description` | String                     | The description of the slash command. |
|     `options` | List of [Options](#option) | The sub-options of the slash command. |



## Option

`slash_command_option`

There are two things this can do, depending on the `type`:

Either add subcommand literals, or parameters to the command.
Sub command groups are always on the first "layer",
while subcommands are always one layer deeper than sub command groups.

Note that this is quite limited in comparison to Minecraft commands. All paths of the command tree have to have either just a subcommand, or a subcommand group with subcommands each.
This means that the length of the command chains (without the other options that aren't subcommands) has to be equal for all subcommands.

For more info, [check this documentation](https://canary.discord.com/developers/docs/interactions/application-commands#subcommands-and-subcommand-groups).


### {map:}

|             Key | Type                                    | Description                                         |
|----------------:|:----------------------------------------|:----------------------------------------------------|
|          `type` | String                                  | The [type](#option-types) of slash command option.  |
|          `name` | String                                  | The name of the option.                             |
|   `description` | String                                  | The description of the option.                      |
| `required` {:?} | Boolean<br>(`false` by default)         | Whether if this option is required to be specified. |
|  `options` {:?} | List of [Options](#option)              | Sub-options to this sub-command/group.[^1]          |
|  `choices` {:?} | List of [Option choices](#option-types) | Autocompletable choices for this command option.    |

#### [Option types][1] { id="option-types" }

|              String | Description                        |
|--------------------:|:-----------------------------------|
|       `SUB_COMMAND` |                                    |
| `SUB_COMMAND_GROUP` |                                    |
|            `STRING` |                                    |
|           `INTEGER` | Any integer between -2^53 and 2^53 |
|           `BOOLEAN` |                                    |
|              `USER` | Select a user                      |
|           `CHANNEL` | All channel types + categories     |
|              `ROLE` | Select a role                      |
|       `MENTIONABLE` | Select a user/role                 |
|            `NUMBER` |                                    |
|        `ATTACHMENT` | Upload an attachment               |



## Option choice

`slash_command_option_choice`

|     Key | Type   | Description                                                                     |
|--------:|:-------|:--------------------------------------------------------------------------------|
|  `name` | String | The visible autocompleted filled in choice for the option.                      |
| `value` | String | The value that will be received in the slash command event as the option value. |



[1]: https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-option-type

[^1]: Only for `SUB_COMMAND` and `SUB_COMMAND_GROUP` types.