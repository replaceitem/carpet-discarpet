import('env','env');

__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_channel = dc_channel_from_id(env('channelId'));

task(_() -> (
    button_components = [
        {
            'component' -> 'button',
            'id' -> 'red_button',
            'style' -> 'danger',
            'label' -> 'Red button',
            'emoji' -> '✖️'
        },
        {
            'component' -> 'button',
            'id' -> 'blurple_button',
            'style' -> 'primary',
            'label' -> 'Blurple button',
            'emoji' -> '🚪'
        },
        {
            'component' -> 'button',
            'id' -> 'green_button',
            'style' -> 'success',
            'label' -> 'Green button',
            'emoji' -> '👑'
        },
        {
            'component' -> 'button',
            'id' -> 'grey_button',
            'style' -> 'secondary',
            'label' -> 'Grey button',
            'emoji' -> '📧'
        },
        {
            'component' -> 'button',
            // 'id' is not required
            'style' -> 'link',
            'label' -> 'Open replaceitem\'s github',
            'emoji' -> '🌐',
            'url' -> 'https://github.com/replaceitem'
        }
    ];

    select_string_component = [{
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
    }];

    select_user_component = [{
        'component' -> 'user_select',
        'id' -> 'select_user',
        'placeholder' -> 'Pick a user'
    }];

    select_role_component = [{
        'component' -> 'role_select',
        'id' -> 'select_role',
        'placeholder' -> 'Pick a role'
    }];

    select_mentionable_component = [{
        'component' -> 'mentionable_select',
        'id' -> 'select_mentionable',
        'placeholder' -> 'Pick a mentionable'
    }];

    select_channel_component = [{
        'component' -> 'channel_select',
        'id' -> 'select_channel',
        'placeholder' -> 'Pick a channel'
    }];

    button_url_component = [{
        // leaving 'id' out since its a url button
        'component' -> 'button',
        'style' -> 'url',
        'label' -> 'Open replaceitem\'s github',
        'emoji' -> '🌐',
        'url' -> 'https://github.com/replaceitem'
    }];

    // note: 'components' cannot have more than 5 rows
    dc_send_message(global_channel, {
        'content' -> 'Example interactions',
        'components' -> [
            button_components,
            select_string_component,
            select_user_component,
            select_role_component,
            //select_mentionable_component,
            select_channel_component
        ]
    });
));

__on_discord_button(interaction) -> (
    task(_(outer(interaction)) -> (
        dc_respond_interaction(interaction, 'RESPOND_IMMEDIATELY', str(
            'Pressed button **%s**', interaction~'custom_id'
        ));
    ));
);

__on_discord_select_menu(interaction) -> (
    task(_(outer(interaction)) -> (
        dc_respond_interaction(interaction, 'RESPOND_IMMEDIATELY', str(
            'Selected **%s**', str(interaction~'chosen')
        ));
    ));
);