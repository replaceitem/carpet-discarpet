### `dc_create_application_command(type, commandBuilder, server?)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Depending on the `type`, this function expects different parsables:

* `slash_command` -> [Slash command builder parsable](../../../parsables/commands/slash-command-builder)
* `user_context_menu` -> [User context menu builder parsable](../../../parsables/commands/user-context-menu-builder)
* `message_context_menu` -> [Message context menu builder parsable](../../../parsables/commands/message-context-menu-builder)

Function for creating slash commands for the bot.
When specifying a `server`, the slash command will only be created for that particular server.
If `server` is not provided, the slash command will be global, meaning they work in all servers the bot is in.

*NOTE:* GLOBAL slash commands can take up to 1 hour to update, so for testing,
you should only use server slash commands, which are created immediately.

Returns an application command value.

For a full examples of commands, see [the slash command example](../../../examples/slash-commands)