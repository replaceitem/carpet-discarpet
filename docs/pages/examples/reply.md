Directly replies to all messages with a text of `Ping`

!!! warning "Requires privileged intents"
    To use this example script, your bot will require the `MESSAGE_CONTENT` intent. You can add these inside `discarpet.json` as described in [Getting started](/setup.md#intents).

```sc title="reply.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

__on_discord_message(message) -> (
    // ignore messages by the bot itself
    if (message~'user'~'is_self', return());

    // if content is Ping, proceed to Pong!
    if (message~'content' == 'Ping',
        task(_(outer(message)) -> (
            dc_send_message(message~'channel', {
                'content' -> str('Pong! %s', message~'user'~'mention_tag'),
                'reply_to' -> message
            });
        ));
    );
);
```