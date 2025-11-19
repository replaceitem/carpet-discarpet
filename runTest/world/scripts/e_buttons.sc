import('env','env');

__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_channel = dc_channel_from_id(env('channelId'));

task(_() -> (
    buttons = [
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

    dc_send_message(global_channel, {
        'content' -> 'Button example',
        'components' -> [
            buttons
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
