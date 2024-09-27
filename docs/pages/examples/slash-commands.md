Adds some demo slash commands.

Remember to set your own server id!

```sc title="slash_commands.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

initialize_commands() -> (
    // remove all commands first
    for (server~'slash_commands', dc_delete(_));

    server = dc_server_from_id('689483030754099267');

    // simple ping command
    dc_create_application_command('slash_command', {
        'name' -> 'ping',
        'description' -> 'Ping -> Pong!'
    }, server);

    // more complex command with subcommand groups and subcommands, as well as options
    dc_create_application_command('slash_command', {
        'name' -> 'example',
        'description' -> 'Test command',
        'options'-> [
            {
                'type' -> 'SUB_COMMAND_GROUP',
                'name' -> 'delete',
                'description' -> 'Delete something',
                'options'-> [
                    {
                        'type' -> 'SUB_COMMAND',
                        'name' -> 'channel',
                        'description' -> 'Remove something',
                        'options' -> [
                            {
                                'type' -> 'CHANNEL',
                                'name' -> 'channel',
                                'description'->'What channel to delete',
                                'required' -> true
                            },
                            {
                                'type' -> 'BOOLEAN',
                                'name' -> 'force',
                                'description' -> 'Force delete channel?',
                                'required' -> false
                            }
                        ]
                    }
                ]
            },
            {
                'type' -> 'SUB_COMMAND_GROUP',
                'name' -> 'create',
                'description' -> 'Create something',
                'options' -> [
                    {
                        'type' -> 'SUB_COMMAND',
                        'name' -> 'channel',
                        'description' -> 'Create a channel',
                        'options' -> [
                            {
                                'type' -> 'STRING',
                                'name' -> 'name',
                                'description' -> 'Name of the channel',
                                'required' -> true
                            },
                            {
                                'type' -> 'BOOLEAN',
                                'name' -> 'private',
                                'description' -> 'Is this channel private?',
                                'required' -> true
                            },
                            {
                                'type' -> 'STRING',
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
                            }
                        ]
                    }
                ]
            },
            {
                'type' -> 'SUB_COMMAND_GROUP',
                'name' -> 'upload',
                'description'->'Upload something',
                'options'->[
                    {
                        'type' -> 'SUB_COMMAND',
                        'name' -> 'file',
                        'description'->'Upload file as attachment',
                        'options'->[
                            {
                                'type' -> 'ATTACHMENT',
                                'name' -> 'attachment',
                                'description' -> 'Upload a file',
                                'required' -> true
                            }
                        ]
                    }
                ]
            }
        ]
    }, server);
);

// reload commands async, as that would otherwise freeze the game for multiple seconds
task('initialize_commands');



__on_discord_slash_command(cmd) -> (
    args = cmd~'arguments_by_name';
    global_testargs = args;
    // check which command was executed
    if (cmd~'command_name' == 'ping', (
        // if so, respond immediately
        task(_(outer(cmd)) -> (
            dc_respond_interaction(cmd,'RESPOND_IMMEDIATELY','Pong!');
        ));
    ), (
        // if not, tell Discord that its gonna take a bit for the response
        if (args:'upload', (
            task(_(outer(cmd),outer(args)) -> (
                dc_respond_interaction(cmd, 'RESPOND_IMMEDIATELY', {
                    'content' -> str('Thank you for %s', args:'attachment'~'value'~'url'),
                    'ephemeral' -> true
                });
            ));
        ), (
            dc_respond_interaction(cmd,'RESPOND_LATER');
            
            // respond after 5 seconds
            task(_(outer(cmd)) -> (
                sleep(5000);
                dc_respond_interaction(cmd, 'RESPOND_FOLLOWUP', 
                    str('%s executed %s with options %s and locale %s', cmd~'user', cmd~'command_name', cmd~'arguments_by_name', cmd~'locale'));
            ));
        ));
    ));
);
```