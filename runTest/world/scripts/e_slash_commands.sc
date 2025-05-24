import('env','env');

__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_server = dc_server_from_id(env('serverId'));

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
            'type' -> 'attachment',
            'name' -> 'attachment',
            'description' -> 'Upload a file',
            'required' -> true
        }]
    });

    // more complex command
    // takes in various options and spits them back out
    dc_create_application_command('slash_command', {
        'name' -> 'complex',
        'description' -> 'Test command',
        'options' -> [
            {
                'type' -> 'sub_command_group',
                'name' -> 'create',
                'description' -> 'Create something',
                'options' -> [
                    {
                        'type' -> 'sub_command',
                        'name' -> 'channel',
                        'description' -> 'Create a channel',
                        'options' -> [
                            {
                                'type' -> 'string',
                                'name' -> 'name',
                                'description' -> 'Name of the channel',
                                'required' -> true
                            },
                            {
                                'type' -> 'string',
                                'name' -> 'type',
                                'description' -> 'Channel type',
                                'required' -> true,
                                'choices' -> [
                                    {
                                        'name' -> 'Text',
                                        'value' -> 'text'
                                    },
                                    {
                                        'name' -> 'Voice',
                                        'value' -> 'voice'
                                    },
                                    {
                                        'name' -> 'Announcement',
                                        'value' -> 'announcement'
                                    }
                                ]
                            },
                            {
                                'type' -> 'boolean',
                                'name' -> 'private',
                                'description' -> 'Is this channel private?',
                                'required' -> true
                            }
                        ]
                    }
                ]
            },
            {
                'type' -> 'sub_command_group',
                'name' -> 'delete',
                'description' -> 'Delete something',
                'options' -> [
                    {
                        'type' -> 'sub_command',
                        'name' -> 'channel',
                        'description' -> 'Delete a channel',
                        'options' -> [
                            {
                                'type' -> 'channel',
                                'name' -> 'channel',
                                'description' -> 'What channel to delete',
                                'required' -> true
                            },
                            {
                                'type' -> 'boolean',
                                'name' -> 'force',
                                'description' -> 'Force delete channel?',
                                'required' -> false
                            }
                        ]
                    }
                ]
            }
        ]
    });
);

// reload commands async, so the game doesnt freeze
task('initialize_commands');

__on_discord_slash_command(cmd) -> (
    cmd_name = cmd~'command_name';
    group_name = cmd~'sub_command_group';
    subcommand_name = cmd~'sub_command';
    args = cmd~'arguments_by_name';
    logger(cmd_name);
    logger(group_name);
    logger(subcommand_name);

    if (cmd_name == 'ping',
        task(_(outer(cmd)) -> (
            dc_respond_interaction(cmd, 'respond_later');
            sleep(5000);
            dc_respond_interaction(cmd, 'respond_followup', 'Pong! 5 seconds delay (catastrophic!)')
        ));
    );

    if (cmd_name == 'upload',
        task(_(outer(cmd), outer(args)) -> (
            dc_respond_interaction(cmd, 'respond_immediately', {
                'content' -> str('Thank you for %s', args:'attachment'~'value'~'url'),
                'ephemeral' -> true
            });
        ));
    );

    if (cmd_name == 'complex',
        if (group_name == 'create' && subcommand_name == 'channel', (
            task(_(outer(cmd), outer(args)) -> (
                dc_respond_interaction(cmd, 'respond_immediately', {
                    'content' -> {
                        'name' -> args:'name'~'value',
                        'type' -> args:'type'~'value',
                        'private' -> args:'private'~'value'
                    }
                });
            ));
        ));

        if (group_name == 'delete' && subcommand_name == 'channel', (
            task(_(outer(cmd), outer(args)) -> (
                dc_respond_interaction(cmd, 'respond_immediately', {
                    'content' -> {
                        'channel' -> args:'channel'~'value',
                        'force' -> args:'force'~'value'
                    }
                });
            ));
        ));
    );
);