Demonstrates adding reactions and using the reaction event.

![Demo reactions](/assets/examples/reactions.png)

```sc title="reactions.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_channel = dc_channel_from_id('1234567891011121314');

task(_() -> (
    // create a message, then add reactions to that message
    message = dc_send_message(global_channel, 'React with 🟩 to accept or 🟥 to deny');
    global_msg_id = message~'id';

    dc_react(message, '🟩');
    dc_react(message, '🟥');
));

__on_discord_reaction(reaction, user, added) -> (
    // ignore reactions from itself
    if (user~'is_self', return());

    // if from the same message, send the action
    if (reaction~'message'~'id' == global_msg_id,
        action = if (added, 'voted with', 'removed their vote for');
        task(_(outer(reaction), outer(user), outer(action)) -> (
            dc_send_message(reaction~'message'~'channel',
                str('%s %s %s', user~'name', action, reaction~'emoji'~'unicode'));
        ));
    );
);
```