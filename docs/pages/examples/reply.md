Directly replies to all messages with a text of `Ping`

!!! warning "Requires privileged intents"
    To use this example script, your bot will require the `MESSAGE_CONTENT` intent. You can add these inside `discarpet.json` as described in [Getting started](/setup.md#intents).

```sc title="reply.sc"
__config() -> {'scope'->'global','bot'->'BOT'};

__on_discord_message(message) -> (
    if(message~'user'~'is_self',return()); // stop the event if the message is from the bot itself

    if(message~'content' == 'Ping',
        // use a task to not freeze the game
        task(_(outer(message)) -> (
            dc_send_message(message~'channel',
                {
                    'content'->'Pong! ' + message~'user'~'mention_tag',
                    'reply_to'->message
                }
            );
        ));
    );
);
```