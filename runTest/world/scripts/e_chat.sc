import('env','env');

__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_channel = dc_channel_from_id(env('channelId'));

// bridge dc -> mc
__on_discord_message(message) -> (
    // make sure to only bridge messages from our channel
    if (message~'channel'~'id' != global_channel~'id', return());

    // get server member that sent the message
    author = message~'member';

    // ignore messages without a member (e.g. interactions)
    if (author == null, return());

    // ignore messages by the bot itself
    if (author~'is_self', return()); 

    // get member details
    name = author~'effective_name';
    color = author~'color' || '#FFFFFF';

    // format the message
    mc_message = format(str('%s [%s]', color, name));
    mc_message += format(str('w  %s', message~'readable_content'));

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
    for (server~'members',
        message = replace(
            message,
            '@' + _~'effective_name',
            _~'user'~'mention_tag'
        );
    );
    return (message);
);