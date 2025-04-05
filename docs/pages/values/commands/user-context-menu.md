`dc_user_context_menu`

Represents a menu command under a user's context menu.


### {query:}

|      Property | Type                              | Description                                                                                        |
|--------------:|:----------------------------------|:---------------------------------------------------------------------------------------------------|
|          `id` | String                            | The ID of the menu.                                                                                |
|        `name` | String                            | The name of the menu.                                                                              |
| `description` | String                            | The description of the menu.                                                                       |
|      `server` | [Server](/values/server.md), Null | The server this application command is in.<br>Returns `null` if it's a global application command. |