import('env','env');

__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_channel = dc_channel_from_id(env('channelId'));

task(_() -> (
    dc_send_message(global_channel, {
        'use_components_v2' -> true,
        'components' -> [
            [{
                'component' -> 'button',
                'style' -> 'link',
                'url' -> 'https://example.org',
                'label' -> 'Example button',
            }],
            {
                'component' -> 'separator',
            },
            {
                'component' -> 'container',
                'accent_color' -> 0x5522DD,
                'components'->[
                    [{
                        'component' -> 'button',
                        'style' -> 'link',
                        'url' -> 'https://example.org',
                        'label' -> 'Example button',
                    }]
                ],
            ],
            {
                'component' -> 'separator',
                'is_divider' -> false,
            },
            {
                'component' -> 'text_display',
                'content' -> '# This is some text\nJust as an example.\nWith some **markdown**.',
            },
            {
                'component' -> 'separator',
                'spacing' -> 'large',
            },
            {
                'component' -> 'section',
                'components' -> [
                    {
                        'component' -> 'text_display',
                        'content' -> 'This is some content.\nThere is a button to the right of this content.\nClick if if you wanna learn more.'
                    }
                ],
                'accessory' -> {
                    'component' -> 'button',
                    'style' -> 'link',
                    'url' -> 'https://example.org',
                    'label' -> 'Visit',
                    'emoji' -> '🌐',
                },
            },
        ]
    });
));