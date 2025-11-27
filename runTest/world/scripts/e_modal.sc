import('env','env');

__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_channel = dc_channel_from_id(env('channelId'));

send_modal(int) -> (
    dc_respond_interaction(int, 'respond_modal', {
        'id' -> 'my_modal',
        'title' -> 'A Custom Modal',
        'components' -> [
            {
                'component'->'label',
                'label' -> 'Your name',
                'description' -> 'What\'s your name?',
                'child'-> {
                    'component' -> 'text_input',
                    'id' -> 'name_input',
                    'style' -> 'short',
                    'min_length' -> 2,
                    'max_length' -> 32,
                    'required' -> true,
                    'placeholder' -> 'Put your name here'
                },
            },
            {
                'component'->'label',
                'label' -> 'Introduce yourself',
                'description' -> 'Write a short introduction about your hobbies, things you like or things you don\t like.',
                'child'-> {
                    'component' -> 'text_input',
                    'id' -> 'introduction_input',
                    'style' -> 'paragraph',
                    'required' -> false,
                    'value' -> 'Hello, I am'
                }
            },
            {
                'component'->'label',
                'label' -> 'Favorite animal',
                'description' -> 'What\'s your favorite animal',
                'child'-> {
                    'component' -> 'string_select',
                    'id' -> 'favorite_animal',
                    'placeholder' -> 'Select your favorite animal',
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
                'label' -> 'Favorite meme',
                'description' -> 'Upload your favorite meme here',
                'child'-> {
                    'component' -> 'file_upload',
                    'id' -> 'meme_upload',
                }
            },
        ]
    });
);

task(_() -> (
    dc_send_message(global_channel, {
        'content' -> 'Click below to open modal',
        'components' -> [
            [{
                'id' -> 'modal_btn',
                'component' -> 'button',
                'label' -> 'Open modal'
            }]
        ]
    });
));

__on_discord_button(int) -> (
    if (int~'custom_id' == 'modal_btn',
        task(_(outer(int)) -> (
            send_modal(int);
        ));
    );
);

__on_discord_modal(interaction) -> (
    options = interaction~'values_by_id';
    meme = options:'meme_upload'~'value':0;
    if(!meme~'is_image',
        dc_respond_interaction(interaction, 'respond_immediately', {
            'content' -> 'Please upload an image',
        });
        return();
    );

    dc_respond_interaction(interaction, 'respond_immediately', {
        'use_components_v2' -> true,
        'components' -> [
            {
                'component' -> 'text_display',
                'content' -> str(
                    '# Hello %s.\nI also like %ss.\n\nThis is your image:',
                    options:'name_input'~'value',
                    options:'favorite_animal'~'value':0
                ),
            },
            {
                'component' -> 'media_gallery',
                'items' -> [
                    {
                        'media' -> {
                            'url' -> meme~'url',
                        },
                    },
                ],
            },
        ],
    });
);