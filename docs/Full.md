# Setting up Bots


## Logging in

First, you need a Discord bot account.
You can create one at the [Discord developer portal](https://discord.com/developers/applications).
If youre not sure how to do that, look up a tutorial for that.

When you installed Discarpet and start your server, it should create a `discarpet.json` file in the config folder of the server
(Yes, this mod is made for servers, i never tried using it in singleplayer, it may work, it may not).

The file should look like this by default:

```json
{
  "bots": [
    {
      "bot_id": "Your Bot ID",
      "bot_token": "Your Bot Token",
      "member_intent": false,
      "presence_intent": false
    }
  ]
}
```

To add your bot to the game, copy and paste your Bot token from the Developer portal into the `"Your Bot Token"` field.
The `"bot_id"` is used to identify your bot in scarpet later. You should just give the bot a name so you can identify it.
This doesnt need to be what you called it in the developer portal,
it's just an arbitrary name.
The `member_intent` and `presence_intent` can be set to `true` if these intents are needed by your bots.
For more info, see the section about [intents](#Intents).

Now your config should look something like this:

```json
{
  "bots": [
    {
      "bot_id": "coolbot",
      "bot_token": "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789",
      "member_intent": false,
      "presence_intent": false
    }
  ]
}
```

Now, you can use the in-game command `/discarpet reload` to reload all the bots, and start new ones if you added one to the config.
This command will tell you whether the bots could be logged in or not.

When the login was successful, you can use `/discarpet getInvite [bot id]` to get a link in chat, which upon click,
will take you directly to the webpage where you can add the bot to any discord server you have admin permission in.

## Making a script

Now its time time to create your bot scarpet script!

Since Discarpet supports multiple bots at the same time, you need to specify which bot you want to use in your script.
This is done by specifying a `'bot'` to the config line, like this:

`__config()->{'bot'->'bot_id'}`

The `'bot_id'` is the id you specified in the config.
Without a valid bot specified, no discarpet functions will work and will all throw an error.

## Multiple bots

To have multiple bots running on your server, just add them to the config file like this:

```json
{
  "bots": [
    {
      "bot_id": "bot1",
      "bot_token": "token1"
    },
    {
      "bot_id": "bot2",
      "bot_token": "token2"
    },
    {
      "bot_id": "third_bot",
      "bot_token": "token for third bot"
    }
  ]
}
```

Keep in mind that each script can only have one bot.
Each script will only receive events from that one specified bot,
and when getting [values from ids](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Functions.md#Values-from-ids),
the value will have the context of the bot of the script. That would mean that if you pass a message value from an event to another script,
and add a reaction there, ther user of the reaction will still be from the script where the event happened.
Only if you query [values from ids](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Functions.md#Values-from-ids),
the bot from the config will be applied.

## Intents

In the config file, you can enable two kinds of intents for your bot.
You should leave them as `false`, unless you use functions that require your bot to have this permission.
In that case, you also need to enable the permissions in the Discord developer portal (Bot/Privileged Gateway Intents).
There is a hint at all functions that need an additional Intent, so you know when you need them.

# Discarpet Values

Discarpet adds various new value types to scarpet, that wrap a Discord value.
This page lists all of them and which values can be queried from it.
Querying happens just like with scarpets entity values: `channel~'value'`.
All Discarpet values have a type name (which you get from `type()`) with the prefix `dc_`.

## Channel

`dc_channel`

A channel value represents a Discord channel.

Queryable:

`name` (String) The name of the Discord channel

`topic` (String) The topic of the channel (See `dc_set_channel_topic`)

`id` (String) ID of the channel

`mention_tag` (String) Mention tag for the channel. This can be put inside a message for the channel to be a clickable link.

`server` (Server) Server this channel is in, or null if this is a private channel

`type` (String) Channel type

## EmbedBuilder

`dc_embed_builder`

A EmbedBuilder is used to create Embeds. This is mainly used with the `dc_build_embed` function.

Queryable:

This value has nothing to query.

## Emoji

`dc_emoji`

This value stores an Emoji, which could be a normal unicode emoji, or a custom emoji from a Discord server.

Queryable:

`mention_tag` (String) Mention tag for the emoji. This can be used to put into messages to contain the emoji.

`unicode` (String) Returns the emoji as a unicode character. If it's a custom emoji, returns `null`

`is_animated` (boolean) True if the emojis is animated, false if not

`is_unicode` (boolean) True if the emoji is a unicode emoji, false otherwise

`is_custom` (boolean) True if the emoji is a custom one, otherwise false

## Message

`dc_message`

A Discord message which has been sent. Main use is in `__on_discord_message` event

Queryable:

`content` (String) Get the content (text) of the message, not that things like emojis, mentions or channels will appear as their id. For the readable content, see below.

`readable_content` (String) Get the content and replace all emojis, mentions, channels with their readable representation. Note that if a user is not cached, mentions to him may not get parsed.

`id` (String) Get the id of the message

`channel` (Channel) Get the channel this message is inside

`user` (User) Get the user that wrote this message. Note that this may fail (and return null) if the user is not cached, but if queried after the `__on_discord_message` event, it should be fine

`server` (Server) Get the server this message was written in

`delete` (boolean) This is not actually a query, but it removes the message. Returns false if the bot does not have permission to delete the message, otherwise false

## Reaction

`dc_reaction`

A reaction on a message. Main use is in `__on_discord_reaction` event

Queryable:

`emoji` (Emoji) The emoji of this reaction

`count` (Number) Amount of reactions with this emoji

`message` (Message) The message this reacion is attached to

## Server

`dc_server`

A Discord server

Queryable:

`name` (String) The name of the server

`id` (String) The ID of the server

`users` (List of Users) All users in this server (this requires the member Intent)

`channels` (List of Channels) All channels in this server

## User

`dc_user`

A discord user. Can be from a real discord account or a bot

Queryable:

`name` (String) The name of the user (Does not include nicknames, use `dc_get_display_name` for that)

`mention_tag` (String) The mention tag to mention a user in a message

`discriminated_name` (String) The name of the user with its discriminator. e.g. `replaceitem#9118`

`id` (String) ID of the user

`avatar` (String) URL of the users profile picture

`is_bot` (boolean) True if the user is a bot, false if it is a regular user

`is_self` (boolean) True if the user is the currently logged in bot account itself. Useful to prevent bots replying to itself

`private_channel` (Channel) The private messages channel with the user. Note that this may block, if the private channel was not yet opened.

## Slash command interaction

`dc_slash_command_interaction`

Value from `__on_discord_slash_command(interaction)` event, used for getting the command that was executed, and then replying to it with `dc_respond_slash_command()`

Queryable:

`command` (List) List of command option that were executed. If a user executed the slash command `/channel remove #bot-spam`, this would return `['channel', 'remove']`.

`options` (Map) A map containing all options that were specified in the command, with the key being the name of the option, with a corresponding value that has been chosen for this option.

`user` (User) The user that executed the command.

`channel` (Channel) The channel this command was executed in.

## Button and Select menu interaction

`dc_button_interaction`, `dc_select_menu_interaction`

Value from `__on_discord_button(interaction)` and `__on_discord_select_menu(interaction)` event, used for getting the message interaction details, and then responding to it with `dc_respond_slash_command()`

These values have mostly the same things to query.

Queryable:

`id` (String) Id of the button or select menu, which was specified by the user in the `dc_send_message` message parameter

`channel` (Channel) The channel this interaction was made in.

`user` (User) The user that used the interaction.

`message` (Message) The message this interaction is attached to.

Queryable things exclusive to select menus:

`chosen` (List) List the values of the chosen options

`options` (List) All values of options in the select menu

`min` (number) Minimum amount of selected entries for this select menu

`max` (number) Maximum amount of selected entries for this select menu

`placeholder` (String) Placeholder text of this select menu# Discarpet functions

Discarpet adds a lot of functions to scarpet to control your bot.
Below is a list of all functions and how they work.

## Messages

### `dc_send_message(channel,content)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|---|

This functions sends a message in a specific Discord `channel`. 
The `content` can be a String, an [`EmbedBuilder`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#embedbuilder)
or a more complex message consisting of multiple embeds, attachments or interactions (see below).

This example shows how you can send a message and add reactions to it as soon as it was sent

```py
task(_()->(
    message = dc_send_message(dc_channel_from_id('YOUR CHANNEL ID'),'Test message');
    dc_react(message,'🟥');
    dc_react(message,'🟩');
));
```

To construct more complex messages you can use a map as the `content`.

This map can contain various things you can include in your message.

* `'content'` (String) This is just the regular text of the message

* `'attachments'` (List) A list of attachments that will be sent along the message.
Each attachment is represented by a map containing one of these map keys:
    * `'file'` (String) A path to a file that will be attached to the message
    
    * `'url'` (String) A url to a file that will be attached to the message
    
    * `'bytes'` (String) A string, which will be converted to bytes and directly saved to a file.
    This can be used to save to txt files, or even non text file formats,
    in which case the string's characters will be saved as the file's bytes.
    In this case, the map must additionally contain a `'name'` value containing the filename for the file.
    
    Additionally, a `'spoiler'` value can be set to true, to mark the file as a spoiler.

    Example:
    
    ```py
    dc_send_message(channel,{
        'content'->'I am sending you a secret file:',
        'attachments'->[
            {
                'bytes'->'Text in the file',
                'name'->'secret_message.txt',
                'spoiler'->true
            }
        ],
    });
    ```
  
* `'embeds'` (List) A list of EmbedBuilder value to attach to the message

* `'components'` (List) A list of lists with message components like buttons or select menus in them.

Each sub list represents one row of message components.
Each message component is represented by a map.

The type of component is set by a `'component'` value in that map.

This can be `'button'` or `'select_menu'`.

**Button:**

Buttons can have the following values:

* `'id'` (String) the id of this button, which is used to recognize the button in the `__on_discord_button` event. Note that this is only for non `url` type buttons

* `'style'` (String) The type of button, can be either `blurple`, `grey`, `green`, `red` or `url`.

* `'label'` (String) The text shown on the button.

* `'emoji'` (String or Emoji) The emoji shown next to the text on the button.

* `'url'` (String) The url for `url` style buttons only

* `'disabled'` (boolean) If the button is disabled or not

**Select menus:**

Buttons can have the following values:

* `'id'` (String) the id of this button, which is used to recognize the select menu in the `__on_discord_select_menu` event.

* `'options'` (List) All options in the selection menu.
Each entry is a map with the following keys:

    * `'value'` (String) The value behind this option that will be received in the `__on_discord_select_menu` event
    
    * `'label'` (String) The text shown in the menu
    
    * `'description'` (String) The description shown in the menu
    
    * `'emoji'` (Emoji or String) An emoji shown next to the entry in the select menu
    
    * `'default'` (boolean) If this entry is selected by default

* `'min'` (number) The minimum amount of entries that have so be selected

* `'max'` (number) The maximum amount of entries that can be selected

* `'placeholder'` (String) The text displayed if nothing is selected

* `'disabled'` (boolean) If the select menu is disabled or not

For examples, see [Examples](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Examples.md)

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

## Embeds

### `dc_build_embed()` `dc_build_embed(property,value...)`

This function is used to create custom Embeds which can be sent using `dc_send_message`.

When ran without arguments, returns an empty [embed](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#embedbuilder) value.

The value is used to specify different properties of the Embed. This is done by specifying a `property` and a `value` in the function.

The following properties are allowed:

`dc_build_embed(embed,'title',title)`
`dc_build_embed(embed,'description',description)`
`dc_build_embed(embed,'author',userValue)` Automtically uses the name us the user, as well as his avatar and clickable link
`dc_build_embed(embed,'author',name)` Only use a custom name
`dc_build_embed(embed,'author',name,clickURL,iconURL)` Use custom name, url and icon
`dc_build_embed(embed,'add_field',title,description)`
`dc_build_embed(embed,'add_inline_field',title,description)`
`dc_build_embed(embed,'color',r,g,b)`
`dc_build_embed(embed,'color',[r,g,b])`
`dc_build_embed(embed,'footer',name,iconURL)`
`dc_build_embed(embed,'image',imageURL)`
`dc_build_embed(embed,'thumbnail','thumbnailURL')`

Here is a example on how to build an embed:

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

* `'choices'` (list, optional): Specify value that can be autocompleted in in the slash command.
Entries in this list are maps containing a name, and a value.
The name is whats actually shown, and the value what will be received when executing the command.
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
or telling discord that the answer will come, which gives a 15 minute time to send a followup response.
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

Returns a [`channel`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#channel) value from the
specified channel id, or `null` if the channel was not found.

### `dc_server_from_id(id)`

Returns a [`server`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#server) value from the
specified server id, or `null` if the server was not found.

### `dc_emoji_from_id(server,id)`

Returns a [`emoji`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#emoji) value from the
specified emoji id in a `server`. If there is no emoji with that id, it will instead search for custom emojis which have the name `id`. If there are none, returns an empty list.
This is only for custom emojis, since standard emojis are specified from the unicode emoji.# Discarpet Events


Discarpet's scarpet events are used to detect events that happen in discord servers the bot is in. Additionally, there are also events for when a chat message gets sent in minecraft or a general system message happens.

# Minecraft events

## `__on_system_message(text,type,entity)`

Event that execute on system messages, for example to be used to redirect system messages to a discord chat.

`text` -> String: Text of the system message

`type` -> String: Type of the message, could be for example:
   
  chat.type.text -> Normal chat message

  multiplayer.player.left -> Someone left the game
  
  chat.type.admin -> Admin command executed

`entity` -> Entity: Entity that message came from, or null if not sent from an entity

WARNING: DO NOT print out ANYTHING inside this event that would execute this event again! The server would crash because of never ending recursion!

The same thing applies for errors, since those are printed in chat as well, any error inside this event will also result in recursion!

Here is an example how you could prevent server crashes:

```python
__config() -> {'scope' -> 'global'};

global_executions = 0;

__on_system_message(text,type,entity) -> (
    global_executions += 1;
    if(global_executions < 10,
        //put all the code you want to execute here
        scoreboard('blah'); //this would cause an error if the objective doesnt exist, which would instantly call the event again
    );
);

__on_tick() -> (
    global_executions = 0;
);
```

## `__on_chat_message(message,player,command)`

Event that execute on chat messages, can be used to redirect chat messages to a discord chat.

`message` -> String: Text of the chat message

`player` -> Entity: Player that sent the message

`command` -> Boolean: Message is a command

# Discord events

## `__on_discord_message(message)`

Executes when a message is sent in a channel the bot has acess to.

`message` -> [Message](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#message): The message that was sent

## `__on_discord_reaction(reaction,user,added)`

Executes when a user reacts to a message with some emoji

`reaction` -> [Reaction](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#reaction): The reaction that was made containing the emoji

`user` -> [User](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#user): The user who reacted

`added` -> boolean, `true` if the reaction was added, `false` if the reaction was removed

## `__on_discord_slash_command(interaction)`

Executes when a user runs a slash command

`interaction` -> [Slash command interaction](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#Slash-command-interaction): The slash command interaction containing everything about the command that was executed

## `__on_discord_button(interaction)`

Executes when a user presses a button component on a message

`interaction` -> [Button interaction](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#Button-and-Select-menu-interaction): The button interaction containing everything about the button that was pressed

## `__on_discord_select_menu(interaction)`

Executes when a user uses a select menu component on a message

`interaction` -> [Select menu interaction](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#Button-and-Select-menu-interaction): The select menu interaction containing everything about the select menu that was used# Example scripts

## Replying to messages

```py
__config() -> {'scope'->'global','bot'->'BOT'};

__on_discord_message(message) -> (
    if(message~'user'~'is_self',return()); // stop the event if the message is from the bot itself

    if(message~'content' == 'Ping',
        // use a task to not freeze the game
        task(_(outer(message)) -> (
            dc_send_message(message~'channel','Pong!');
        ));
    );
);

```

## Showing online players in bot activity

```py
__config() -> {'scope'->'global','bot'->'BOT'};

__on_tick() -> (
    if(tick_time()%(20*30) == 0, // Consider discords rate limit (only execute every 30 seconds)
        if(length(player('all')) != 0, // are players online?
            dc_set_activity('playing','with ' + join(', ',player('all'))); //display list of players
            dc_set_status('online'); // status should be online
        ,
            dc_set_activity('playing','with nobody'); // alternative text if nobody is online
            dc_set_status('idle'); // set status to idle
        );
    );
);

```

## Sending all Minecraft log messages to a discord channel

```py
__config() -> {'scope'->'global','bot'->'BOT'};

global_executions = 0;
global_log = dc_channel_from_id('789877625497190440');

__on_tick() -> (
    global_executions = 0;
);

__on_system_message(text,type,entity) -> (
    global_executions += 1; //prevent recursion
    if(global_executions < 10,
        if((type~'commands.save.') == null, //dont send 'saving world' messages
            task(_(outer(text)) -> (
                dc_send_message(global_log,text); //send to discord
            ));
        );
    );
);

```

## Simple discord command (not a slash command)

```py
__config() -> {'scope'->'global','bot'->'BOT'};

__on_discord_message(message) -> (
    if(message~'user'~'is_self',return()); // ignore messages by the bot itself

    text = message~'content';

    if(slice(text,0,1)=='!', // check for a ! indicating a command
        // split all arguments to a list
        cmd = split(' ',slice(text,1));

        if(cmd:0 == 'list', // !list command
            task(_(outer(message)) -> (
                dc_send_message(message~'channel','Currently online: ' + join(', ',player('all')));
            ));
            return();
        );

        if(cmd:0 == 'help',  // !help command
            task(_(outer(message)) -> (
                dc_send_message(message~'channel','I\'m sorry but i cant help you');
            ));
            return();
        );
    );
);

```

## If you dont trust your server members as much
```py
__config() -> {'scope'->'global','bot'->'BOT'};

global_log = dc_channel_from_id('789877625497190440');

__on_player_interacts_with_block(player, hand, block, face, hitvec) -> (
    if((['chest','barrel']~block != null) || (block~'shulker_box' != null), //warning when player opens chest/barrel/shulkerbox
        task(_(outer(player),outer(block)) -> (
            dc_send_message(global_log,str('%s opened %s at %s in %s',player,block,pos(block),current_dimension()));
        ));
    );
);
__on_player_places_block(player, item_tuple, hand, block) -> (
    if(block == 'tnt', //warning when player places tnt
        task(_(outer(player),outer(block)) -> (
            dc_send_message(global_log,str(':warning: %s placed %s at %s in %s',player,block,pos(block),current_dimension()));
        ));
    );
);

```
## Reactions as user input

```py
__config() -> {'scope'->'global','bot'->'BOT'};

global_channel = dc_channel_from_id('759102744761335891');

task(_()->(
    message = dc_send_message(global_channel,'React with 🟩 to accept or 🟥 to deny');
    global_msgid = message~'id';
    dc_react(message,'🟥');
    dc_react(message,'🟩');
));

__on_discord_reaction(reaction,user,added) -> (
    if(user~'is_self',return());
    if(reaction~'message'~'id' == global_msgid,
        action = if(added,'voted with','removed the vote for');
        task(_(outer(reaction),outer(user),outer(action))-> (
            dc_send_message(reaction~'message'~'channel',user~'name' + ' ' + action + ' ' + reaction~'emoji'~'unicode');
        ));
    );
);

```

## Embeds

```py
__config() -> {'scope'->'global','bot'->'BOT','stay_loaded'->false};

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
dc_send_message(dc_channel_from_id('759102744761335891'),e);

```
See [`dc_build_embed()`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Functions.md#dc_build_embed-dc_build_embedpropertyvalue)

## Chat between Minecraft and Discord

```py
__config() -> {'scope'->'global','bot'->'BOT','stay_loaded'->false};

global_executions = 0;
global_chat = dc_channel_from_id('789877643070799902');

__on_tick() -> (
    global_executions = 0;
);

__on_discord_message(message) -> (
    if(message~'channel'~'id'!=global_chat~'id',return()); //limit to chat channel only
    if(message~'user'~'is_self',return()); //ignore messages by the bot itself
    for(player('all'),
        col = 'd'; // this could be replaced with a custom way of fetching user color
        if(col == null,col = 'w');
        print(_,format(str('%s [%s]',col,dc_get_display_name(message~'user',message~'server')))+format(str('w  %s',message~'readable_content')))
    );
);

__on_system_message(text,type,entity) -> (
    global_executions += 1; //prevent recursion
    if(global_executions < 10,
        if(!(type~'admin'),
            if((type~'commands.save.') == null, //dont send 'saving world' messages
                msg = __parse_mentions(text,global_chat~'server');
                task(_(outer(msg)) -> dc_send_message(global_chat,msg)); //send to discord
            );
        );
    );
);

__on_chat_message(msg,player,command) -> (
    task(_(outer(msg)) -> dc_send_message(global_chat,'chat: ' + msg)); //send to discord
);

__parse_mentions(msg,server) -> (
    for(server~'users',
        msg = replace(msg,'@' + dc_get_display_name(_,server),_~'mention_tag');
    );
    msg;
);

```

## Slash commands

```py
__config() -> {'scope'->'global','bot'->'BOT'};


initialize_commands() -> (
    //remove all commands first
    dc_delete_slash_command();

    server = dc_server_from_id('689483030754099267');

    //simple ping command
    dc_create_slash_command('ping','Ping -> Pong!',server);

    //more complex command with subcommand groups and subcommands, as well as options
    dc_create_slash_command('example','Test command',server,[
        {
            'type'->'SUB_COMMAND_GROUP',
            'name'->'delete',
            'description'->'Delete something',
            'options'->[
                {
                    'type'->'SUB_COMMAND',
                    'name'->'channel',
                    'description'->'Remove something',
                    'options'->[
                        {
                            'type'->'CHANNEL',
                            'name'->'channel',
                            'description'->'What channel to delete',
                            'required'->true
                        },
                        {
                            'type'->'BOOLEAN',
                            'name'->'force',
                            'description'->'Force delete channel?',
                            'required'->false
                        }
                    ]
                }
            ]
        },
        {
            'type'->'SUB_COMMAND_GROUP',
            'name'->'create',
            'description'->'Create something',
            'options'->[
                {
                    'type'->'SUB_COMMAND',
                    'name'->'channel',
                    'description'->'Create a channel',
                    'options'->[
                        {
                            'type'->'STRING',
                            'name'->'name',
                            'description'->'Name of the channel',
                            'required'->true
                        },
                        {
                            'type'->'BOOLEAN',
                            'name'->'private',
                            'description'->'Is this channel private?',
                            'required'->true
                        },
                        {
                            'type'->'STRING',
                            'name'->'type',
                            'description'->'Channel type',
                            'required'->true,
                            'choices'->[
                                {
                                    'name'->'Text',
                                    'value'->'text'
                                },
                                {
                                    'name'->'Voice',
                                    'value'->'voice'
                                },
                                {
                                    'name'->'Announcement',
                                    'value'->'announcement'
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ]);
);

// reload commands async, as that would otherwise freeze the game for multiple seconds
task('initialize_commands');



__on_discord_slash_command(cmd) -> (
    //check which command was executed
    if(cmd~'command':0 == 'ping',
        //respond immediately
        task(_(outer(cmd))->dc_respond_interaction(cmd,'RESPOND_IMMEDIATELY','Pong!'));
    , //else
        //tell discord that its gonna take a bit for the response
        dc_respond_interaction(cmd,'RESPOND_LATER');
        //respond after 5 seconds
        task(_(outer(cmd))->(
            sleep(5000);
            dc_respond_interaction(cmd,'RESPOND_FOLLOWUP','Executed ' + cmd~'command' + ' with options ' + cmd~'options');
        ));
    );
);

```

## Buttons and select menus

```py
__config() -> {'scope'->'global','stay_loaded'->true,'bot'->'BOT'};

global_ch = dc_channel_from_id('759102744761335891');

task(_()->(
    dc_send_message(global_ch,{
        'content'->'Example interactions',
        'components'->
        [
            [
                {
                    'component'->'button',
                    'id'->'btn1',
                    'style'->'red',
                    'label'->'Red button',
                    'emoji'->'❌'
                },
                {
                    'component'->'button',
                    'id'->'btn2',
                    'style'->'blurple',
                    'label'->'Blurple button',
                    'emoji'->'🚪'
                },
                {
                     'component'->'button',
                     'id'->'btn3',
                     'style'->'green',
                     'label'->'Green button',
                     'emoji'->'👑'
                },
                 {
                      'component'->'button',
                      'id'->'btn4',
                      'style'->'grey',
                      'label'->'Grey button',
                      'emoji'->'📧'
                 }
            ],
            [

                {
                    'component'->'select_menu',
                    'id'->'select1',
                    'placeholder'->'Select at least 2 items here',
                    'min'->2,
                    'max'->5,
                    'options'->[
                        {
                            'value'->'pizza',
                            'label'->'Pizza',
                            'description'->'I mean who doesn\'t like pizza?',
                            'emoji'->'🍕'
                        },
                        {
                            'value'->'cake',
                            'label'->'Cake',
                            'description'->'If you prefer sweet food',
                            'emoji'->'🍰'
                        },
                        {
                            'value'->'popcorn',
                            'label'->'Popcorn',
                            'description'->'Just like in the cinema',
                            'emoji'->'🍿'
                        },
                        {
                            'value'->'bread',
                            'label'->'Bread',
                            'description'->'Something simple but delicious',
                            'emoji'->'🍞'
                        },
                        {
                            'value'->'carrot',
                            'label'->'Carrot',
                            'description'->'Something healthy',
                            'emoji'->'🥕'
                        }
                    ]
                }
            ]
        ]
    });

    //doing this in a seperate message, since there is a bug in javacord that breaks events from regular buttons if there is a url button in the same message
    dc_send_message(global_ch,{
        'content'->'My github:',
        'components'-> [[
            {
                //leaving 'id' out since its a url button
                'component'->'button',
                'style'->'url',
                'label'->'Open replaceitem\'s github',
                'emoji'->'🌐',
                'url'->'https://github.com/replaceitem'
            }
        ]],

    });


));

__on_discord_button(int) -> (
    task(_(outer(int)) -> dc_respond_interaction(int,'respond_immediately','Pressed button ' + int~'id'));
);

__on_discord_select_menu(int) -> (
    task(_(outer(int)) -> dc_respond_interaction(int,'respond_immediately','Selected ' + str(int~'chosen')));
);

```