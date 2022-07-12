Sends all system messages to a log channel,
and sends a message when a player executes a command.

```sc title="log.sc"
__config() -> {'scope'->'global','bot'->'BOT'};

global_log = dc_channel_from_id('789877625497190440');

__on_system_message(text,type) -> (
    task(_(outer(text)) -> (
        dc_send_message(global_log,text);
    ));
);

__on_command_executed(player,command)->(
    task(_(outer(player),outer(command)) -> (
        dc_send_message(global_log,player + ' ran `' + command + '`');
    ));
);
```