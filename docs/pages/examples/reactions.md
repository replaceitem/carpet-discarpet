Demonstrates adding reactions and using the reaction event.

```sc title="reactions.sc"
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