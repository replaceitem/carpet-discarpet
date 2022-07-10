### `dc_create_application_command(type,commandBuilder, server?)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Depending on the `type`, this function expects different parsables:
* `slash_command` -> Slash command parsable
* `user_context_menu` -> User context menu parsable
* `message_context_menu` -> User context menu parsable

Function for creating slash commands for the bot using the [Slash command builder parsable](/docs/parsable.md#Slash-command-builder).
When specifying a `server`, the slash command will only be for that particular server.
If `server` is `null`, the slash command will be global, meaning they work in all servers the bot is in.
*NOTE:* GLOBAL slash commands can take up to 1 hour to update, so for testing,
you should only use server slash commands, which are created immediately.

Additionally, you can specify additional [Slash command options](/docs/parsable.md#Slash-command-option) to your command.
Options are supplied in a list, with each option being a map that specifies some parameters.

Returns an application command value.

For full examples of commands, see [Examples](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Examples.md#Slash-commands)