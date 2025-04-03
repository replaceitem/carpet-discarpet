### `dc_create_application_command(type, parsable, server?)`

Creates a slash command or context menu command for the bot.

{% include 'warning-blocking.md' %}

!!! tip "Tips"
    1. For an example of slash commands, see the [Slash commands](/examples/slash-commands.md).
    2. For an example of context menu commands, see [Context menus](/examples/slash-commands.md).


### {input:}

* `type` {->} String
  {:} The [type](#command-types) of command to create.
* `parsable` {->}
  [Slash command builder parsable](/parsables/commands/slash-command-builder.md),
  [User context menu builder parsable](/parsables/commands/user-context-menu-builder.md),
  [Message context menu builder parsable](/parsables/commands/message-context-menu-builder.md)
  {:} The parsable to be used.
* `server` {:?} {->} [Server](/values/server.md)
  {:} The server to create the command in.
  <br>If left empty, the command will be created globally, meaning they work in all servers the bot is in.

!!! note
    Global slash commands can take up to an hour hour to update.
    <br>Unless if you really need to, you should create slash commands for a specific server instead as they can be updated immediately.

#### Command types

Depending on the `type`, this function expects different parsables for `parsable`:

* `slash_command` - Slash command builder parsable
* `user_context_menu` - User context menu builder parsable
* `message_context_menu` - Message context menu builder parsable


### {output:}

#### {output values:}

* The created application command value.

#### {output exceptions:}

* Throws an exception on failure.
