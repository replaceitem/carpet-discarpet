import('env','env');

__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_channel = dc_channel_from_id(env('channelId'));

task(_() -> (
    string_select_component = {
        'component' -> 'string_select',
        'id' -> 'select_string',
        'placeholder' -> 'Select at least 2 items here',
        'min' -> 2,
        'max' -> 3,
        'options' -> [
            {
                'value' -> 'pizza',
                'label' -> 'Pizza',
                'description' -> 'I mean who doesn\'t like pizza?',
                'emoji' -> '🍕'
            },
            {
                'value' -> 'cake',
                'label' -> 'Cake',
                'description' -> 'If you prefer sweet food',
                'emoji' -> '🍰'
            },
            {
                'value' -> 'popcorn',
                'label' -> 'Popcorn',
                'description' -> 'Just like in the cinema',
                'emoji' -> '🍿'
            },
            {
                'value' -> 'bread',
                'label' -> 'Bread',
                'description' -> 'Something simple but delicious',
                'emoji' -> '🍞'
            },
            {
                'value' -> 'carrot',
                'label' -> 'Carrot',
                'description' -> 'Something healthy',
                'emoji' -> '🥕'
            }
        ]
    };

    user_select_component = {
        'component' -> 'user_select',
        'id' -> 'select_user',
        'placeholder' -> 'Pick a user'
    };

    role_select_component = {
        'component' -> 'role_select',
        'id' -> 'select_role',
        'placeholder' -> 'Pick a role'
    };

    mentionable_select_component = {
        'component' -> 'mentionable_select',
        'id' -> 'select_mentionable',
        'placeholder' -> 'Pick a mentionable'
    };

    channel_select_component = {
        'component' -> 'channel_select',
        'id' -> 'select_channel',
        'placeholder' -> 'Pick a channel'
    };

    dc_send_message(global_channel, {
        'content' -> 'Select menu example',
        'components' -> [
            [string_select_component],
            [user_select_component],
            [role_select_component],
            [mentionable_select_component],
            [channel_select_component]
        ]
    });
));

__on_discord_select_menu(interaction) -> (
    task(_(outer(interaction)) -> (
        dc_respond_interaction(interaction, 'RESPOND_IMMEDIATELY', str(
            'Selected **%s**', str(interaction~'chosen')
        ));
    ));
);