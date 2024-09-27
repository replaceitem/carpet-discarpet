`dc_user_context_menu`

#### Queryable:

| Property      | Type                        | Description                                                                                        |
|---------------|-----------------------------|----------------------------------------------------------------------------------------------------|
| `id`          | String                      | The ID of the command                                                                              |
| `name`        | String                      | The name of the command                                                                            |
| `description` | String                      | The description of the command                                                                     |
| `server`      | [Server](/values/server.md) | The server this application command is in.<br> Returns `null` if it's a global application command |