`dc_slash_command`

Represents a slash command on a server

#### Queryable:

| Property             | Type                   | Description                                                                                                                                                                     |
|----------------------|------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `id`                 | String                 | Id of the command                                                                                                                                                               |
| `name`               | String                 | Name of the command                                                                                                                                                             |
| `description`        | String                 | Description of the command                                                                                                                                                      |
| `server`             | [Server](../../server) | The server this application command is in, or null if it's a global application command                                                                                         |
| `options`            | List of Strings        | The names of the slash command options (didn't bother adding a slash command option value, since nobody gonna use it anyway, but if you need it, make an issue and i'll add it) |