`dc_message_context_menu_interaction`

Represents an interaction of an executed message context menu command from
[`__on_discord_message_context_menu`](/events/discord-message-context-menu.md).


### {query:}

Also has all properties from the [base interaction](/values/interactions/interaction.md)

|       Property | Type                          | Description                                           |
|---------------:|:------------------------------|:------------------------------------------------------|
|   `command_id` | String                        | The ID of the application command.                    |
| `command_name` | String                        | The name of the application command.                  |
|       `target` | [Message](/values/message.md) | The message that the application command was used on. |