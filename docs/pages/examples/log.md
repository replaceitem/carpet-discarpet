Sends all system messages to a log channel,
and sends a message when a player executes a command.

```sc title="log.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_log = dc_channel_from_id('789877625497190440');

// listens for system messages, and logs it
__on_system_message(text, type) -> (
    task(_(outer(text)) -> (
        dc_send_message(global_log, text);
    ));
);

// listens for triggered commands, and logs it
__on_player_command(player, command) -> (
    task(_(outer(player), outer(command)) -> (
        dc_send_message(global_log, str('%s ran `%s`', player, command));
    ));
);
```