`dc_message_context_menu_interaction`

Represents an interaction of an executed message context menu command from [`__on_discord_message_context_menu`](/events/discord-message-context-menu.md).

#### Queryable:

| Property             | Type         | Description                                           |
|----------------------|--------------|-------------------------------------------------------|
| `command_id`         | String       | The ID of the application command.                    |
| `command_name`       | String       | The name of the application command.                  |
| `target`             | [Message][1] | The message the context menu interaction was used on. |

[1]: /values/message.md