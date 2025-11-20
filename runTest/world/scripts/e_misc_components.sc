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
                'component' -> 'text_display',
                'content' -> '# This is some text\nJust as an example.\nWith some **markdown**.\n-# Also, below this text display is a separator without divider',
            },
            {
                'component' -> 'separator',
                'is_divider' -> false,
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
            {
                'component' -> 'separator',
                'spacing' -> 'large',
            },
            {
                'component' -> 'section',
                'components' -> [
                    {
                        'component' -> 'text_display',
                        'content' -> 'This is yet another sections, featuring a thumbnail as the section accessory'
                    }
                ],
                'accessory' -> {
                    'component' -> 'thumbnail',
                    'media' -> {
                        'url'->'https://raw.githubusercontent.com/replaceitem/carpet-discarpet/master/src/main/resources/assets/discarpet/icon.png',
                    },
                    'description' -> 'The Discarpet logo',
                },
            },
            {
                'component' -> 'media_gallery',
                'items' -> [
                    {
                        'media'->{
                            'url'->'https://cdn.modrinth.com/data/byUTGrfV/images/5ac7bbbf8da02e37436b52156f0021368dab4f49.png',
                        },
                        'description'->'A big bedrock maze in minecraft',
                    },
                    {
                        'media'->{
                            'url'->'https://cdn.modrinth.com/data/byUTGrfV/images/6cbe1619c045c4100f3c692e80245974715caf29.png',
                        },
                        'description'->'A top down view of a simplex noise bedrock maze in minecraft',
                    },
                    {
                        'media'->{
                            'url'->'https://cdn.modrinth.com/data/byUTGrfV/images/dceb8714596070fadee5885065e0aad1641cf9b2.png',
                        },
                        'description'->'A 3D simplex noise maze in minecraft',
                        'spoiler'->true,
                    }
                ],
            },
        ]
    });
));