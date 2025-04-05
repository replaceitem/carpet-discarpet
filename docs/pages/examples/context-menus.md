---
icon: material/menu-open
---


Adds multiple context menu commands:

* One [message command](/parsables/commands/message-context-menu-builder.md), which waits 10 seconds and then deletes the message.
* Two [user commands](/parsables/commands/user-context-menu-builder.md), one for adding a [prefix] to a user's nickname, and one for removing it.

!!! note "Notes"
    1. Your bot will need "Manage Messages" and "Manage Nicknames" permissions.
    2. The bot cannot set the nickname of someone that is higher in role hierarchy than itself.


![Demo context menu](/assets/examples/context-menu.png)


```sc title="context_menu.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

initialize_commands() -> (
    // remove all context menu interactions
    for (dc_get_global_application_commands(),
        command_value_type = type(_);
        if (command_value_type ~ '_context_menu', dc_delete(_));
    );

    global_set_prefix_cmd = dc_create_application_command('USER_CONTEXT_MENU', {
        'name' -> 'Set prefix'
    });

    global_reset_prefix_cmd = dc_create_application_command('USER_CONTEXT_MENU', {
        'name' -> 'Reset prefix'
    });

    global_delete_cmd = dc_create_application_command('MESSAGE_CONTEXT_MENU',{
        'name' -> 'Delete in 10 seconds'
    });
);

// reload commands async, as that would otherwise freeze the game for multiple seconds
task('initialize_commands');

// the targeted user for changing prefix, stored for retrieving after modal interaction
global_targets = {};


__on_discord_user_context_menu(interaction) -> (
    command_id = interaction~'command_id';

    print(str(
        '%s used %s on %s (%s)',
        interaction~'user',
        interaction~'command_name',
        interaction~'target',
        command_id
    ));

    if (command_id == global_set_prefix_cmd~'id',
        task(_(outer(interaction)) -> (
            global_targets:(interaction~'user'~'id') = interaction~'target';
            dc_respond_interaction(interaction,'respond_modal', {
                'id' -> 'set_prefix',
                'title' -> 'Custom prefix',
                'components' -> [
                    [{
                        'component' -> 'text_input',
                        'id' -> 'prefix_input',
                        'style' -> 'short',
                        'label' -> 'Prefix',
                        'required' -> true,
                        'placeholder' -> 'Enter the prefix'
                    }]
                ]
            });
        ));
    );

    if (command_id == global_reset_prefix_cmd~'id',
        task(_(outer(interaction)) -> (
            target = interaction~'target';
            dc_set_nickname(target, interaction~'server', target~'name');
            dc_respond_interaction(interaction, 'respond_immediately', {
                'content' -> str('Reset prefix of %s', target~'name'),
                'ephemeral' -> true
            });
        ));
    );
);


__on_discord_message_context_menu(interaction) -> (
    print(str('%s used %s on %s', interaction~'user', interaction~'command_id', interaction~'target'));

    if (interaction~'command_id' == global_delete_cmd~'id',
        task(_(outer(interaction)) -> (
            dc_respond_interaction(interaction, 'respond_later');
            sleep(10000);
            dc_delete(interaction~'target');
            dc_respond_interaction(interaction, 'respond_followup', {
                'content' -> 'Deleted message'
            });
        ));
    );
);


__on_discord_modal(interaction) -> (
    task(_(outer(interaction)) -> (
        user = interaction~'user';
        target = global_targets:(user~'id');

        delete(global_targets, user);

        prefix = interaction~'input_values_by_id':'prefix_input';
        nickname = str('[%s] %s', prefix, target~'name');

        dc_set_nickname(target, interaction~'server', nickname);
        dc_respond_interaction(interaction, 'respond_immediately', {
            'content' -> str('Changed prefix of %s', target~'name', prefix),
            'ephemeral' -> true
        });
    ));
);
```