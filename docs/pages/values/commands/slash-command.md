`dc_slash_command`

Represents a slash command in a server.


### {query:}

|      Property | Type                              | Description                                                                                        |
|--------------:|:----------------------------------|:---------------------------------------------------------------------------------------------------|
|          `id` | String                            | The ID of the command.                                                                             |
|        `name` | String                            | The name of the command.                                                                           |
| `description` | String                            | The description of the command.                                                                    |
|      `server` | [Server](/values/server.md), Null | The server this application command is in.<br>Returns `null` if it's a global application command. |
|     `options` | List of Strings                   | The names of the slash command options.[^1]                                                        |


[^1]: Didn't bother adding a slash command option value since nobody is gonna use it anyway.
      <br>If you need it, make an issue or let me know on Discord, and I'll add it.