`dc_user_context_menu_interaction`

Represents an interaction of an executed user-context app from [`__on_discord_user_context_menu()`](/events/discord-user-context-menu.md).

#### Queryable:

| Property             | Type                          | Description                                                                                                             |
|----------------------|-------------------------------|-------------------------------------------------------------------------------------------------------------------------|
| `command_id`         | String                        | The ID of the application command.                                                                                      |
| `command_name`       | String                        | The name of the application command.                                                                                    |
| `target`             | [User](/values/user.md)       | The user the context menu interaction was used on.                                                                      |