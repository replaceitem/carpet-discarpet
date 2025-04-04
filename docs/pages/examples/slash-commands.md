---
icon: octicons/command-palette-16
---


Adds multiple [slash commands](/parsables/commands/slash-command-builder.md):

- `/ping` with fake latency
- `/upload` image
- `/complex [create|delete] channel` with various options


![Demo slash commands](/assets/examples/slash-commands.png)


```sc title="slash_commands.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_server = dc_server_from_id('put id here!');

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

    // more complex command
    // takes in various options and spits them back out
    dc_create_application_command('slash_command', {
        'name' -> 'complex',
        'description' -> 'Test command',
        'options' -> [
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
                            },
                            {
                                'type' -> 'BOOLEAN',
                                'name' -> 'private',
                                'description' -> 'Is this channel private?',
                                'required' -> true
                            }
                        ]
                    }
                ]
            },
            {
                'type' -> 'SUB_COMMAND_GROUP',
                'name' -> 'delete',
                'description' -> 'Delete something',
                'options' -> [
                    {
                        'type' -> 'SUB_COMMAND',
                        'name' -> 'channel',
                        'description' -> 'Delete a channel',
                        'options' -> [
                            {
                                'type' -> 'CHANNEL',
                                'name' -> 'channel',
                                'description' -> 'What channel to delete',
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
            }
        ]
    });
);

// reload commands async, so the game doesnt freeze
task('initialize_commands');

__on_discord_slash_command(cmd) -> (
    cmd_name = cmd~'command_name';
    args = cmd~'arguments_by_name';

    if (cmd_name == 'ping',
        task(_(outer(cmd)) -> (
            dc_respond_interaction(cmd, 'RESPOND_LATER');
            sleep(5000);
            dc_respond_interaction(cmd, 'RESPOND_FOLLOWUP', 'Pong! 5 seconds delay (catastrophic!)')
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

    if (cmd_name == 'complex',
        task(_(outer(cmd), outer(args)) -> (
            if (args~'create', (
                dc_respond_interaction(cmd, 'RESPOND_IMMEDIATELY', {
                    'content' -> {
                        'name' -> args:'name'~'value',
                        'type' -> args:'type'~'value',
                        'private' -> args:'private'~'value'
                    }
                });
            ));

            if (args~'delete', (
                dc_respond_interaction(cmd, 'RESPOND_IMMEDIATELY', {
                    'content' -> {
                        'channel' -> args:'channel'~'value',
                        'force' -> args:'force'~'value'
                    }
                });
            ));
        ));
    );
);
```