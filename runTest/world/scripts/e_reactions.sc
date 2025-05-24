import('env','env');

__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_channel = dc_channel_from_id(env('channelId'));

task(_() -> (
    // create a message, then add reactions to that message
    message = dc_send_message(global_channel, 'React with 🟩 to accept or 🟥 to deny');
    global_msg_id = message~'id';

    dc_add_reaction(message, '🟩');
    dc_add_reaction(message, '🟥');
));

__on_discord_reaction(reaction, member, added) -> (
    // ignore reactions from itself
    if (member~'is_self', return());

    // if from the same message, send the action
    if (reaction~'message_id' == global_msg_id,
        action = if (added, 'voted with', 'removed their vote for');
        task(_(outer(reaction), outer(member), outer(action)) -> (
            dc_send_message(reaction~'channel',
                str('%s %s %s', member~'effective_name', action, reaction~'emoji'~'unicode'));
        ));
    );
);