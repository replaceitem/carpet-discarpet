This script sends chat messages in minecraft to the configured
`global_chat` channel, and messages in that
channel to the minecraft chat.

!!! warning "Requires privileged intents"
    To use this example script, your bot will require the `MESSAGE_CONTENT` intent. You can add these inside `discarpet.json` as described in [Getting started](/setup.md#intents).

```sc title="chat.sc"
__config() -> {'scope'->'global','bot'->'BOT'};

global_chat = dc_channel_from_id('789877643070799902');

__on_discord_message(message) -> (
    if(message~'channel'~'id'!=global_chat~'id',return()); //limit to chat channel only
    user = message~'user';
    if(user==null, return()); // ignore messages without a user (interactions)
    if(user~'is_self',return()); //ignore messages by the bot itself
    col = dc_get_user_color(user,message~'server');
    if(col==null,col='#FFFFFF');
    mc_message = format(str('%s [%s]',col,dc_get_display_name(user,message~'server')))+format(str('w  %s',message~'readable_content'));
    print(player('all'), mc_message);
);


__on_system_message(text,type) -> (
    if(type~'chat.type.admin' == null, //don't send admin command messages
        msg = parse_mentions(text,global_chat~'server'); //allow for pings from inside minecraft
        task(_(outer(msg)) -> dc_send_message(global_chat,msg)); //send to discord
    );
);

parse_mentions(msg,server) -> (
    for(server~'users',
        msg = replace(msg,'@' + dc_get_display_name(_,server),_~'mention_tag');
    );
    msg;
);
```