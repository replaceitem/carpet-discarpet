### `dc_create_application_command(type, parsable, server?)`

{% include 'warning-blocking.md' %}

Creates a slash command or context menu for the bot.

Returns the application command created.

- `type` {->} String
  {:} The type of command or menu to create.
- `parsable` {->}
  [Slash command builder parsable](/parsables/commands/slash-command-builder.md),
  [User context menu builder parsable](/parsables/commands/user-context-menu-builder.md),
  [Message context menu builder parsable](/parsables/commands/message-context-menu-builder.md)
  {:} The parsable to be used.
- `server` {:?} {->} [Server](/values/server.md)
  {:} The server to create the command or menu in.
  If left empty, makes the command global, meaning they work in all servers the bot is in.

For a full examples of commands, see the [Slash command example](/examples/slash-commands.md).

!!! tip
    Global slash commands can take up to 1 hour to update.
    <br>Unless if you really need it, you should only use server slash commands, which are updated immediately.

#### Command types

Depending on the `type`, this function expects different parsables for `parsable`:

- `slash_command` - Slash command builder parsable
- `user_context_menu` - User context menu builder parsable
- `message_context_menu` - Message context menu builder parsable