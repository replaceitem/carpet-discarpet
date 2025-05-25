### `dc_create_application_command(type, data, server?)`

Creates a slash command or a context menu command for the bot.

{% include 'blocking-function.md' %}


### {input:}

|     Parameter | Type                                  | Description                                                                                                        |
|--------------:|:--------------------------------------|:-------------------------------------------------------------------------------------------------------------------|
|        `type` | String                                | The [type](#command-types) of command to create.                                                                   |
|        `data` | [(See command types)](#command-types) | The schema to be used depends on the `type`.                                                                       |
| `server` {:?} | [Server](/values/server.md)           | The server to create the command in.<br>If left empty, the command will be available in all servers the bot is in. |


#### Command types

Depending on the `type`, this function expects different schemas for `data`:

|                 String | Schema                                                                                   |
|-----------------------:|:-----------------------------------------------------------------------------------------|
|        `slash_command` | [Slash command builder object](/schemas/commands/slash-command-builder.md)               |
|    `user_context_menu` | [User context menu builder object](/schemas/commands/user-context-menu-builder.md)       |
| `message_context_menu` | [Message context menu builder object](/schemas/commands/message-context-menu-builder.md) |


### {output:}

#### {output values:}

* The created application command value.

#### {output exceptions:}

* Throws an exception on failure.