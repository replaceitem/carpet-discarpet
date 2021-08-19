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

__on_tick() -> (
    global_executions = 0;
);

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

__on_discord_reaction(reaction,user,added) -> (
    if(user~'is_self',return());
    if(reaction~'message'~'id' == global_msgid,
        if(added,
            dc_send_message(reaction~'message'~'channel',user~'name' + ' voted with ' + reaction~'emoji'~'unicode');
        ,
            dc_send_message(reaction~'message'~'channel',user~'name' + ' removed the vote for ' + reaction~'emoji'~'unicode');
        );
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

## Chat between Minecraft and Discord

```py
__config() -> {'scope'->'global','bot'->'YOUR BOT'};

global_executions = 0;
global_chat = dc_channel_from_id('CHANNEL ID');

__on_tick() -> (
    global_executions = 0;
);

__parse_mentions(msg,server) -> (
    for(server~'users',
        msg = replace(msg,'@' + dc_get_display_name(_,server),_~'mention_tag');
    );
    msg;
);

__on_discord_message(message) -> (
    if(message~'channel'~'id'!=global_chat~'id',return()); //limit to chat channel only
    if(message~'user'~'is_self',return()); //ignore messages by the bot itself
    for(player('all'), //note that printing to individual players doesn't trigger __on_system_message(), which is what we want
        print(_,format(str('%s [%s]',col,dc_get_display_name(message~'user',message~'server')))+format(str('#7289DA  %s',message~'readable_content')))
    );
);

__on_system_message(text,type,entity) -> (
    global_executions += 1; //prevent recursion
    if(global_executions < 10,
        if(!(type~'admin') && !(type~'commands.save.'), //dont send 'saving world' messages and admin messages
            dc_send_message(global_chat,__parse_mentions(text,global_chat~'server')); //send to discord
        );
    );
);
```

## Slash commands


//MISSING!!