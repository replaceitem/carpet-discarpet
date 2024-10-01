Adds some slash commands.

```sc title="slash_commands.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'dcmc'
};

global_server = dc_server_from_id('1234567891011121314');

initialize_commands() -> (
    // remove all commands first
    for (global_server~'slash_commands', dc_delete(_));

    // simple ping command
    dc_create_application_command('slash_command', {
        'name' -> 'ping',
        'description' -> 'Ping -> Pong!'
    }, global_server);

    // slighly more complex command
    // shows the file you uploaded
    dc_create_application_command('slash_command', {
        'name' -> 'upload',
        'description' -> 'Upload and display file',
        'options' -> [{
            'name' -> 'attachment',
            'description' -> 'Upload a file',
            'type' -> 'ATTACHMENT',
            'required' -> true
        }]
    });

    // ill add a more complex command here someday
);

// reload commands async, so the game doesnt freeze
task('initialize_commands');

__on_discord_slash_command(cmd) -> (
    cmd_name = cmd~'command_name';
    args = cmd~'arguments_by_name';

    // check which command was executed
    // if 'ping', respond immediately
    // if 'example', tell Discord that its gonna take a bit for the response

    if (cmd_name == 'ping',
        task(_(outer(cmd)) -> (
            dc_respond_interaction(cmd, 'RESPOND_IMMEDIATELY', 'Pong!');
        ));
    );

    if (cmd_name == 'upload',
        task(_(outer(cmd), outer(args)) -> (
            dc_respond_interaction(cmd, 'RESPOND_IMMEDIATELY', {
                'content' -> str('Thank you for %s', args:'attachment'~'value'~'url'),
                'ephemeral' -> true
            });
        ));
    );
);
```