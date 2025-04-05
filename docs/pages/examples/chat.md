---
icon: material/chat
---


Bridges chat messages to Discord, and vice versa with the ability to ping users by their @name.

!!! warning "Requires privileged intents"
    This script requires the
    [`MESSAGE_CONTENT` and `GUILD_MEMBERS`](/setup.md#using-intents)
    intent to be used.


![Demo chat](/assets/examples/chat.png)


```sc title="chat.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_channel = dc_channel_from_id('put id here!');

// bridge dc -> mc
__on_discord_message(message) -> (
    // make sure to only bridge messages from our channel
    if (message~'channel'~'id' != global_channel~'id', return());

    // ignore messages without a user (e.g. interactions)
    if (message~'user' == null, return());

    // ignore messages by the bot itself
    if (message~'user'~'is_self', return()); 

    // get user details
    name = dc_get_display_name(message~'user', message~'server');
    color = dc_get_user_color(message~'user', message~'server') || '#FFFFFF';

    // format the message
    mc_message = format(str('%s [%s]', color, name));
    mc_message += format(str('w \ %s', message~'readable_content'));

    print(player('all'), mc_message);
);

// bridge mc -> dc
__on_system_message(text, type) -> (
    if (type != 'chat.type.admin',
        message = parse_mentions(text, global_channel~'server');
        task(_(outer(message)) -> dc_send_message(global_channel, message)); 
    );
);

// parse pings from mc -> dc
parse_mentions(message, server) -> (
    for (server~'users',
        message = replace(
            message,
            '@' + dc_get_display_name(_, server),
            _~'mention_tag'
        );
    );
    return (message);
);
```