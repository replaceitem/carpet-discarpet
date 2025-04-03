### `dc_get_global_application_commands()`

Gets all global application commands of the bot.

{% include 'warning-blocking.md' %}


### {output:}

#### {output values:}

* List of all global application commands. This includes:
    * [Slash commands](/values/commands/slash-command.md)
    * [User context menus](/values/commands/user-context-menu.md)
    * [Message context menus](/values/commands/message-context-menu.md)

#### {output exceptions:}

* Throws an exception on failure.