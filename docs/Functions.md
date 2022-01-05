# Discarpet functions

Discarpet adds a lot of functions to scarpet to control your bot.
Below is a list of all functions and how they work.

## Messages

### `dc_send_message(channel,content)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|---|

This functions sends a message in a specific Discord `channel`. 
The `content` is a parsable Message content. But if you just want text, it can be a regular string.

This example shows how you can send a message and add reactions to it as soon as it was sent

```py
task(_()->(
    message = dc_send_message(dc_channel_from_id('YOUR CHANNEL ID'),'Test message');
    dc_react(message,'🟥');
    dc_react(message,'🟩');
));
```

For more examples, see [Examples](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Examples.md)

### `dc_delete_message(message)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|---|

Deletes the message.
Returns true if successful, otherwise false.

### `dc_react(message,emoji)`

React to a [`Message`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#message) with an `emoji`.
The `emoji` can be a unicode emoji (as a string) or an
[`emoji`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#emoji) value.

## Channels

### `dc_set_channel_topic(channel,text)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|---|

This function sets the description of the [`channel`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#channel)
to the specified `text`. Remember to give the bot permission to do that.

## Self bot actions

### `dc_get_bot_user()`

Returns a user value of the bot itself.

### `dc_set_activity(type,text)`

Sets the activity of the bot to the specified `text` and `type`.
The `type` can be `playing`,`streaming`,`listening`,`watching`,or ~~custom~~
_(`custom` is not supported for bots from the discord api, so it has no use)_.
The `text` will appear after the type. Returns `null` if the `type` was invalid.

### `dc_set_status(status)`

Changes the status of the bot. Can be `online`,`idle`,`dnd`(Do not disturb),`invisible` and `offline`.

## Users

### `dc_get_display_name(user,server)`

Gets the nickname, or name if no nickname is present, from the [`user`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#user) in the [`server`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#server).

### `dc_set_nickname(user,server,name)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|---|

Sets the nickname of the `user` on the `server`.#
Returns `true` if successful, false otherwise.

### `dc_add_role(user, role, reason)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|---|

Adds a `role` to a `user`. `reason` will be shown in the audit log of your server.

### `dc_remove_role(user, role, reason)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|---|

Removes a `role` to a `user`. `reason` will be shown in the audit log of your server.

### `dc_get_user_roles(user, server)`

Returns a list of roles the `user` has in the `server`.

### `dc_get_user_color(user, server)`

Returns the hex color of the top role of the `user` in the `server`.

## Embeds

### `dc_build_embed()` `dc_build_embed(property,value...)`

| ❗ **Note** This function is **deprecated**, use the parsable embed value instead. |
|---|

This function is used to create custom Embeds which can be sent using `dc_send_message`.

When ran without arguments, returns an empty [embed](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#embedbuilder) value.

The value is used to specify different properties of the Embed. This is done by specifying a `property` and a `value` in the function.

The following properties are allowed:

`dc_build_embed(embed,'title',title)`
`dc_build_embed(embed,'description',description)`
`dc_build_embed(embed,'author',userValue)` Automatically uses the name of the user, as well as his avatar and clickable link
`dc_build_embed(embed,'author',name)` Only use a custom name
`dc_build_embed(embed,'author',name,clickURL,iconURL)` Use custom name, url and icon
`dc_build_embed(embed,'add_field',title,description)`
`dc_build_embed(embed,'add_inline_field',title,description)`
`dc_build_embed(embed,'color',r,g,b)`
`dc_build_embed(embed,'color',[r,g,b])`
`dc_build_embed(embed,'footer',name,iconURL)`
`dc_build_embed(embed,'image',imageURL)`
`dc_build_embed(embed,'thumbnail','thumbnailURL')`

Here is an example on how to build an embed:

```py
e = dc_build_embed();
dc_build_embed(e,'title','SuperCoolEmbed');
dc_build_embed(e,'description','This embed is super cool!');
dc_build_embed(e,'author','replaceitem','https://github.com/replaceitem','https://avatars3.githubusercontent.com/u/40722305?s=460&u=ae87da388b3b0aeab05edf67cef1c6f7208727d3&v=4');
dc_build_embed(e,'add_field','Field 1','This field is the first');
dc_build_embed(e,'add_field','Field 2','This field is the second');
dc_build_embed(e,'add_inline_field','Inline field 1','This is an inline field');
dc_build_embed(e,'add_inline_field','Inline field 2','This is another inline field');
dc_build_embed(e,'color',255,128,0);
dc_build_embed(e,'footer','gnembon','https://avatars1.githubusercontent.com/u/41132274?s=460&v=4');
dc_build_embed(e,'image','https://raw.githubusercontent.com/replaceitem/carpet-discarpet/master/logo.png');
dc_build_embed(e,'thumbnail','https://avatars3.githubusercontent.com/u/40722305?s=460&u=ae87da388b3b0aeab05edf67cef1c6f7208727d3&v=4');
dc_send_message(dc_channel_from_id('CHANNEL ID HERE'),e);
```

Which gives this result:

![Example embed](/docs/embed.png)

## Interactions

### `dc_create_slash_command(name, description, server)` `dc_create_slash_command(name, description, server, options)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|---|

Function for creating slash commands for the bot. When called with 3 parameters,
only a simple command with no additional options or subcommands is created (e.g. `/ping`).
`name` and `description` are shown by discord inside the slash command menu.
When specifying a `server`, the slash command will only be for that particular server.
If `server` is `null`, the slash command will be global, meaning they work in all servers the bot is in.
*NOTE:* GLOBAL slash commands can take up to 1 hour to update, so for testing,
you should only use server slash commands, which are created immediately.

Additionally, you can specify additional options to your command.
Options are supplied in a list, with each option being a map that specifies some parameters.

e.g.:

```py
dc_create_slash_command(name, description, server, [
    {
        option 1
    },
    {
        option 2
    }
])
```

Each option has multiple things you can specify:

* `'type'` (String): the type of option. There are two things this can do, either:
    * Add a subcommand group or subcommand using `'SUB_COMMAND_GROUP'` and `'SUB_COMMAND'`.
    Sub command groups are always on the first "layer",
    while subcommands are always one layer deeper than sub command groups. 
    Note that this is quite limited in comparison to minecraft commands.
    All paths of the command tree have to have either just a sub command, or a sub command group with sub commands each.
    This means that the length of the commands (without the other options that aren't subcommands) has to be equal. 
    See: https://canary.discord.com/developers/docs/interactions/slash-commands#nested-subcommands-and-groups
    
    * Add options to the back of the command, with the types:
        * `'STRING'`
        * `'INTEGER'`
        * `'BOOLEAN'`
        * `'USER'`
        * `'CHANNEL'`
        * `'ROLE'`
        * `'MENTIONABLE'`

* `'name'` (String): For subcommands, this is the name of the subcommands, and for other options,
this is the name displayed by discord

* `'description'` (String): A description which will be shown in discord about the command option

* `'required'` (boolean, optional): If this option is required or not. If left out, defaults to false.

* `'options'` (list, optional): Sub-options to this sub command/group. This is only for subcommands or subcommand groups.

* `'choices'` (list, optional): Specify value that can be autocompleted in the slash command.
Entries in this list are maps containing a name, and a value.
The name is what's actually shown, and the value what will be received when executing the command.
The value can be a string or a number.

e.g.:

```py
'choices'->[
    {
        'name'->'Red',
        'value'->'red'
    },
    {
        'name'->'Green',
        'value'->'green'
    },
    {
        'name'->'Blue',
        'value'->'blue'
    }
]
```

For full examples of commands, see [Examples](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Examples.md#Slash-commands)

### `dc_delete_slash_command()` `dc_delete_slash_command(server)` `dc_delete_slash_command(server,name)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|---|

Used for deleting slash commands.
Without any arguments, deletes all global and server commands of the bot.
When a server is specified, deletes all slash commands in that server, or if the server is `null`,
deletes all global slash commands. When a name is specified, deletes only the slash commands with that name.

Note that this function halts the current thread in order to ensure that the slash commands got removed,
so creating a slash command immediately after wouldn't conflict with this.

### `dc_respond_interaction(interaction,type)` `dc_respond_interaction(interaction,type,message)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|---|

This function is used for responding to interactions.
The first parameter is any interaction (slash command, button, select menu) from its corresponding event.
Discord interactions expect a response within 3 seconds after executing it.
Either, that response is directly sending an answer,
or telling discord that the answer will come, which gives a 15-minute time to send a followup response.
The `type` can be one of three things:

* `'RESPOND_LATER'` This does not require the third `message` parameter,
and just tells discord that the interaction was received and an answer will come.
You will then need to send a RESPOND_FOLLOWUP response within 15 minutes.

* `'RESPOND_IMMEDIATELY'` This sends an immediate response which has to come within 3 seconds.
The `message` needs to be specified for this.

* `'RESPOND_FOLLOWUP'` This is used for sending a followup response within 15 minutes after the `RESPOND_LATER` response has been sent.
The `message` needs to be specified for this.

The `message` parameter the same as the message parameter in `dc_send_message`

This function returns `null` if the response could not be sent,
otherwise `true`.
Only if using `RESPOND_FOLLOWUP`,
this will return a message value with the sent message.

## Values from IDs

### `dc_channel_from_id(id)`

Returns a `channel` value from the
specified channel id, or `null` if the channel was not found.

### `dc_server_from_id(id)`

Returns a `server` value from the
specified server id, or `null` if the server was not found.

### `dc_emoji_from_id(id)`

Returns a `emoji` value from the
specified emoji id in a `server`.
This is only for custom emojis, since standard emojis are specified from the unicode emoji.

### `dc_role_from_id(id)`

Returns a `role` value from the
specified role id, or `null` if the server was not found.