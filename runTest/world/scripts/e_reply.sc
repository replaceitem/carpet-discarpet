import('env','env');

__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

__on_discord_message(message) -> (
    // ignore messages from itself
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