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
      "bot_token": "Your Bot Token"
    }
  ]
}
```

To add your bot to the game, copy and paste your Bot token from the Developer portal into the `"Your Bot Token"` field.
The `"bot_id"` is used to identify your bot in scarpet later. You should just give the bot a name so you can identify it.
This doesnt need to be what you called it in the developer portal, it's just an arbitrary name.

Now your config should look something like this:

```json
{
  "bots": [
    {
      "bot_id": "coolbot",
      "bot_token": "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789"
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
# Discarpet Values

Discarpet adds various new value types to scarpet, that wrap a Discord value.
This page lists all of them and which values can be queried from it.
Querying happens just like with scarpets entity values: `channel~'value'`.
All Discarpet values have a type name (which you get from `type()`) with the prefix `dc_`.

## Channel

`dc_channel`

A channel value represents a Discord channel in a Discord server.

Queryable:

`name` (String) The name of the Discord channel

`topic` (String) The topic of the channel (See `dc_set_channel_topic`)

`id` (String) ID of the channel

`mention_tag` (String) Mention tag for the channel. This can be put inside a message for the channel to be a clickable link.

`server` ([Server](#Server)) Server this channel is in

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

`channel` ([Channel](#Channel)) Get the channel this message is inside

`user` ([User](#User)) Get the user that wrote this message. Note that this may fail (and return null) if the user is not cached, but if queried after the `__on_discord_message` event, it should be fine

`server` ([Server](#Server)) Get the server this message was written in

`delete` (boolean) This is not actually a query, but it removes the message. Returns false if the bot does not have permission to delete the message, otherwise false

## Reaction

`dc_reaction`

A reaction on a message. Main use is in `__on_discord_reaction` event

Queryable:

`emoji` ([Emoji](#Emoji)) The emoji of this reaction

`count` (Number) Amount of reactions with this emoji

`message` ([Message](#Message)) The message this reacion is attached to

## Server

`dc_server`

A Discord server

Queryable:

`name` (String) The name of the server

` id` (String) The ID of the server

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

`is_self` (boolean) True if the user is the currently logged in bot account itself. Useful to prevent bots replying to itself# Discarpet functions

Discarpet adds a lot of functions to scarpet to control your bot.
Below is a list of all functions and how they work.

## Sending

### `dc_send_message(channel,content,function?)`

This functions sends a message in a specific Discord `channel`. 
The `content` can be a String, or a [`EmbedBuilder`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#embedbuilder)
to send an Embed. Optionally, you can specify a function (or lambda expression, see example below)
that will be executed when the message was send to modify it, add reactions etc.

This example shows how you can send a message and add reactions to it as soon as it was sent

```py
dc_send_message(dc_channel_from_id('YOUR CHANNEL ID'),'Test message',_(message)->(
    dc_react(message,'🟥');
    dc_react(message,'🟩');
));
```

### `dc_react(message,emoji)`

React to a [`Message`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#message) with an `emoji`.
The `emoji` can be a unicode emoji (as a string) or an
[`emoji`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#emoji) value.

## Values from IDs

### `dc_channel_from_id(id)`

Returns a [`channel`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#channel) value from the
specified channel id, or `null` if the channel was not found.

### `dc_server_from_id(id)`

Returns a [`server`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#server) value from the
specified server id, or `null` if the server was not found.

### `dc_emoji_from_id(id)`

Returns a [`emoji`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#emoji) value from the
specified emoji id, or `null` if the emoji was not found.
This is only for custom emojis, since standard emojis are specified from the unicode emoji.

## Set

### `dc_set_channel_topic(channel,text)`

This function sets the description of the [`channel`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#channel)
to the specified `text`. Remember to give the bot permission to do that.

### `dc_set_activity(type,text)`

Sets the activity of the bot to the specified `text` and `type`.
The `type` can be `playing`,`streaming`,`listening`,`watching`,or ~~custom~~
_(`custom` is not supported for bots from the discord api, so it has no use)_.
The `text` will appear after the type. Returns `null` if the `type` was invalid.

### `dc_set_status(status)`

Changes the status of the bot. Can be `online`,`idle`,`dnd`(Do not disturb),`invisible` and `offline`.

## Get

### `dc_get_display_name(user,server)`

Gets the nickname, or name if no nickname is present, from the [`user`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#user) in the [`server`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#server).

## Embeds

### `dc_build_embed()` `dc_build_embed(property,value...)`

This function is used to create custom Embeds which can be sent using [`dc_send_message`](#dc_send_messagechannelcontentfunction).

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

![Example embed](/docs/embed.png)# Discarpet Events

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

## `on_chat_message(message,player,command)`

Event that execute on chat messages, can be used to redirect chat messages to a discord chat.

`message` -> String: Text of the chat message

`player` -> Entity: Player that sent the message

`command` -> Boolean: Message is a command

# Discord events

## `__on_discord_message(message)`

Executes when a message is sent in a channel the bot has acess to.

`message` -> [Message](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#message): The message that was sent

## `__on_discord_reaction(reaction,user)`

Executes when a user reacts to a message with some emoji

`reaction` -> [Reaction](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#reaction): The reaction that was made containing the emoji

`user` -> [User](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#user): The user who reacted
# Example scripts

## Replying to messages

```py
__config() -> {'scope'->'global','bot'->'YOUR BOT'};

__on_discord_message(message) -> (
    if(message~'user'~'is_self',return()); //# stop the event if the message is from the bot itself

    if(message~'content' == 'Ping',
        dc_send_message(message~'channel','Pong!');
    );
);
```

## Showing online players in bot activity

```py
__config() -> {'scope'->'global','bot'->'YOUR BOT'};

__on_tick() -> (
    if(tick_time()%(20*5) == 0, // Consider discords rate limit (only execute every 5 seconds)
        if(length(player('all')) != 0,
            dc_set_activity('playing','with ' + join(', ',player('all'))); //display list of players
            dc_set_status('online'); //status should be online
        ,
            dc_set_activity('playing','with nobody'); //alternative text if nobody is online
            dc_set_status('idle'); //set status to idle
        );
    );
);
```

## Sending all Minecraft log messages to a discord channel

```py
__config() -> {'scope'->'global','bot'->'YOUR BOT'};

global_executions = 0;
global_log = dc_channel_from_id('CHANNEL ID');

__on_system_message(text,type,entity) -> (
    global_executions += 1; //prevent recursion
    if(global_executions < 10,
        if((type~'commands.save.') == null, //dont send 'saving world' messages
            dc_send_message(global_log,text); //send to discord
        );
    );
);
```

## Simple discord command

```py
__config() -> {'scope'->'global','bot'->'YOUR BOT'};

__on_discord_message(message) -> (
    if(message~'user'~'is_self',return()); //ignore messages by the bot itself

    text = message~'content';

    if(slice(text,0,1)=='!', //check for a ! indicating a command
        cmd = split(' ',slice(text,1));

        if(cmd:0 == 'list', //!list command
            dc_send_message(message~'channel','Currently online: ' + join(', ',player('all')));
            return();
        );

        if(cmd:0 == 'help',  //!help command
            dc_send_message(message~'channel','I\'m sorry but i cant help you');
            return();
        );
    );
);
```

## If you dont trust your server members as much
```py
__config() -> {'scope'->'global','bot'->'YOUR BOT'};

global_log = dc_channel_from_id('CHANNEL ID');

__on_player_interacts_with_block(player, hand, block, face, hitvec) -> (
    if((['chest','barrel']~block != null) || (block~'shulker_box' != null), //warning when player opens chest/barrel/shulkerbox
        dc_send_message(global_log,str('%s opened %s at %s in %s',player,block,pos(block),current_dimension()));
    );
);
__on_player_places_block(player, item_tuple, hand, block) -> (
    if(block == 'tnt', //warning when player places tnt
        dc_send_message(global_log,str(':warning: %s placed %s at %s in %s',player,block,pos(block),current_dimension()));
    );
);
```
## Reactions as user input

```py
__config() -> {'scope'->'global','bot'->'YOUR BOT'};

channel = dc_channel_from_id('CHANNEL ID');

dc_send_message(channel,'React with 🟩 to accept of 🟥 to deny',_(message)->(
    global_msgid = message~'id';
    dc_react(message,'🟥');
    dc_react(message,'🟩');
));

__on_discord_reaction(reaction,user) -> (
    if(user~'is_self',return());
    logger(reaction~'message'~'id' + ' compared ' + global_msgid);
    if(reaction~'message'~'id' == global_msgid,
        dc_send_message(reaction~'message'~'channel',user~'name' + ' voted with ' + reaction~'emoji'~'unicode');
    );
);

```

## Embeds

```py
__config() -> {'scope'->'global','bot'->'YOUR BOT'};

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
dc_send_message(dc_channel_from_id('YOUR CHANNEL'),e);
```
See [`dc_build_embed()`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Functions.md#dc_build_embed-dc_build_embedpropertyvalue)