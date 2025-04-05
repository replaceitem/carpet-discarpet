### `dc_create_application_command(type, parsable, server?)`

Creates a slash command or a context menu command for the bot.

{% include 'blocking-function.md' %}


### {input:}

|     Parameter | Type                                  | Description                                                                                                        |
|--------------:|:--------------------------------------|:-------------------------------------------------------------------------------------------------------------------|
|        `type` | String                                | The [type](#command-types) of command to create.                                                                   |
|    `parsable` | [(See command types)](#command-types) | The parsable to be used.                                                                                           |
| `server` {:?} | [Server](/values/server.md)           | The server to create the command in.<br>If left empty, the command will be available in all servers the bot is in. |


#### Command types

Depending on the `type`, this function expects different parsables for `parsable`:

|                 String | Parsable                                                                                     |
|-----------------------:|:---------------------------------------------------------------------------------------------|
|        `SLASH_COMMAND` | [Slash command builder](/parsables/commands/slash-command-builder.md)               |
|    `USER_CONTEXT_MENU` | [User context menu builder](/parsables/commands/user-context-menu-builder.md)       |
| `MESSAGE_CONTEXT_MENU` | [Message context menu builder](/parsables/commands/message-context-menu-builder.md) |


### {output:}

#### {output values:}

* The created application command value.

#### {output exceptions:}

* Throws an exception on failure.