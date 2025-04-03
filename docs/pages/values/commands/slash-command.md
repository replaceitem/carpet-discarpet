`dc_slash_command`

Represents a slash command in a server.

#### Queryable:

| Property             | Type            | Description                                                                                                                                                                         |
|---------------------:|-----------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `id`                 | String          | The ID of the command.                                                                                                                                                              |
| `name`               | String          | The name of the command.                                                                                                                                                            |
| `description`        | String          | The description of the command.                                                                                                                                                     |
| `server`             | [Server][1]     | The server this application command is in.<br>Returns `null` if it's a global application command.                                                                                  |
| `options`            | List of Strings | The names of the slash command options.<br>(didn't bother adding a slash command option value, since nobody gonna use it anyway, but if you need it, make an issue and i'll add it) |

[1]: /values/server.md