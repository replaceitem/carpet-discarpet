### `dc_create_application_command(type, commandBuilder, server?)`

{% include 'warning-blocking.md' %}

Depending on the `type`, this function expects different parsables:

* `slash_command` -> [Slash command builder parsable](/parsables/commands/slash-command-builder.md)
* `user_context_menu` -> [User context menu builder parsable](/parsables/commands/user-context-menu-builder.md)
* `message_context_menu` -> [Message context menu builder parsable](/parsables/commands/message-context-menu-builder.md)

Function for creating slash commands for the bot.
When specifying a `server`, the slash command will only be created for that particular server.
If `server` is not provided, the slash command will be global, meaning they work in all servers the bot is in.

!!! info
    GLOBAL slash commands can take up to 1 hour to update, so for testing,
    you should only use server slash commands, which are created immediately.

Returns an application command value.

For a full examples of commands, see [the slash command example](/examples/slash-commands.md)