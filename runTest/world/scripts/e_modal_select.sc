import('env','env');

__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_channel = dc_channel_from_id(env('channelId'));

send_modal(int) -> (
    dc_respond_interaction(int, 'respond_modal', {
        'id' -> 'example_modal_select',
        'title' -> 'A custom modal for selections',
        'components' -> [
            {
                'component'->'label',
                'label' -> 'Favorite animal',
                'description' -> 'What\'s your favorite animal',
                'child'-> {
                    'component' -> 'string_select',
                    'id' -> 'favorite_animal',
                    'placeholder' -> 'Select your favorite animal?',
                    'options' -> [
                        {
                            'value' -> 'dog',
                            'label' -> 'Dog',
                            'emoji' -> '🐶'
                        },
                        {
                            'value' -> 'cat',
                            'label' -> 'Cat',
                            'emoji' -> '😺'
                        },
                        {
                            'value' -> 'horse',
                            'label' -> 'Horse',
                            'emoji' -> '🐴'
                        },
                        {
                            'value' -> 'parrot',
                            'label' -> 'Parrot',
                            'emoji' -> '🦜'
                        },
                        {
                            'value' -> 'dolphin',
                            'label' -> 'Dolphin',
                            'emoji' -> '🐬'
                        }
                    ]
                },
            },
            {
                'component'->'label',
                'label' -> 'Favorite color',
                'description' -> 'What\'s your favorite color?',
                'child'-> {
                    'component' -> 'radio_group',
                    'id' -> 'favorite_color',
                    'options' -> [
                        {
                            'label' -> 'Red',
                            'value' -> 'red',
                            'description' -> 'Red, like strawberries',
                        },
                        {
                            'label' -> 'Green',
                            'value' -> 'green',
                            'description' -> 'Green, like kiwi',
                        },
                        {
                            'label' -> 'Blue',
                            'value' -> 'blue',
                            'description' -> 'Blue, like blueberries',
                        },
                    ],
                    'required' -> true,
                },
            },
            {
                'component' -> 'text_display',
                'content' -> 'Pineapple on pizza is a heated debate. Please choose wisely:'
            }
            {
                'component'->'label',
                'label' -> 'Pineapple on pizza',
                'description' -> 'Yes or no?',
                'child'-> {
                    'component' -> 'checkbox',
                    'id' -> 'pineapple_pizza',
                },
            },
            {
                'component'->'label',
                'label' -> 'Favorite season',
                'description' -> 'What\'s your favorite season(s)?',
                'child'-> {
                    'component' -> 'checkbox_group',
                    'id' -> 'favorite_season',
                    'min_values' -> 1,
                    'max_values' -> 2,
                    'options' -> [
                        {
                            'label' -> 'Spring',
                            'value' -> 'spring',
                            'description' -> 'When winter is over',
                        },
                        {
                            'label' -> 'Summer',
                            'value' -> 'summer',
                            'description' -> 'When spring is over',
                        },
                        {
                            'label' -> 'Autumn',
                            'value' -> 'autumn',
                            'description' -> 'When summer is over',
                        },
                        {
                            'label' -> 'Winter',
                            'value' -> 'winter',
                            'description' -> 'When autumn is over',
                        },
                    ],
                    'required' -> true,
                },
            },
        ]
    });
);

task(_() -> (
    dc_send_message(global_channel, {
        'content' -> 'Click below to open modal with selections',
        'components' -> [
            [{
                'id' -> 'modal_selections_btn',
                'component' -> 'button',
                'label' -> 'Open modal'
            }]
        ]
    });
));

__on_discord_button(int) -> (
    if (int~'custom_id' == 'modal_selections_btn',
        task(_(outer(int)) -> (
            send_modal(int);
        ));
    );
);

__on_discord_modal(interaction) -> (
    if(interaction~'custom_id' == 'example_modal_select',
        options = interaction~'values_by_id';

        dc_respond_interaction(interaction, 'respond_immediately', {
            'use_components_v2' -> true,
            'components' -> [
                {
                    'component' -> 'text_display',
                    'content' -> str(
                        'I also like %ss.\n\n%s is a nice color.\n\nI really %s pineapple on pizza.\n\nI am also waiting for %s.',
                        options:'favorite_animal'~'value':0,
                        options:'favorite_color'~'value',
                        if(options:'pineapple_pizza'~'value', 'love', 'hate'),
                        join(' and ', options:'favorite_season'~'value'),
                    ),
                }
            ],
        });
    );
);