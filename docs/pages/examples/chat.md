This script bridges chat messages in Minecraft to the configured
`global_channel` channel, and bridges messages in that
channel to the server chat.

!!! warning "Requires privileged intents"
    To use this example script, your bot will require the [MESSAGE_CONTENT intent](/setup.md#intents).

![Demo chat](/assets/examples/chat.png)

```sc title="chat.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_channel = dc_channel_from_id('789877643070799902');

__on_discord_message(message) -> (
    // limit to chat channel only
    if (message~'channel'~'id' != global_channel~'id', return());

    user = message~'user';

    // ignore messages without a user (e.g. interactions)
    if (user == null, return());

    // ignore messages by the bot itself
    if (user~'is_self', return()); 

    // get the user's name
    name = dc_get_display_name(user, message~'server');

    // get the user's color. if `null`, use white
    color = dc_get_user_color(user, message~'server') || '#FFFFFF';

    // format the message, with the color of the user
    mc_message = format(str('%s [%s]', color, name));
    mc_message += format(str('w  %s', message~'readable_content'));

    print(player('all'), mc_message);
);


__on_system_message(text,type) -> (
    // don't send admin command messages
    if (type != 'chat.type.admin', 
        // allow for pings from inside Minecraft
        msg = parse_mentions(text, global_channel~'server');
        // send to Discord
        task(_(outer(msg)) -> dc_send_message(global_channel, msg)); 
    );
);

parse_mentions(msg,server) -> (
    for (server~'users',
        msg = replace(msg, '@' + dc_get_display_name(_, server), _~'mention_tag');
    );
    msg;
);
```