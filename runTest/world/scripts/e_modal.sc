import('env','env');

__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_channel = dc_channel_from_id(env('channelId'));

send_modal(int) -> (
    dc_respond_interaction(int, 'RESPOND_MODAL', {
        'id' -> 'my_modal',
        'title' -> 'A Custom Modal',
        'components' -> [
            [{
                'component' -> 'TEXT_INPUT',
                'id' -> 'name_input',
                'style' -> 'SHORT',
                'label' -> 'What\'s your name?',
                'min_length' -> 3,
                'max_length' -> 32,
                'required' -> true,
                'placeholder' -> 'Put your name here'
            }],
            [{
                'component' -> 'TEXT_INPUT',
                'id' -> 'age_input',
                'style' -> 'SHORT',
                'label' -> 'How old are you?',
                'min_length' -> 1,
                'max_length' -> 3,
                'required' -> true,
                'placeholder' -> 'Enter a number'
            }],
            [{
                'component' -> 'TEXT_INPUT',
                'id' -> 'introduction_input',
                'style' -> 'PARAGRAPH',
                'label' -> 'Introduce yourself',
                'required' -> false,
                'value' -> 'Hello, I am'
            }]
        ]
    });
);

task(_() -> (
    dc_send_message(global_channel, {
        'content' -> 'Click below to open modal',
        'components' -> [
            [{
                'id' -> 'modal_btn',
                'component' -> 'BUTTON',
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
    input = interaction~'input_values_by_id';

    dc_respond_interaction(interaction, 'RESPOND_IMMEDIATELY', {
        'content' -> {
            'name' -> input:'name_input',
            'age' -> input:'age_input',
            'introduction' -> input:'introduction_input'
        }
    });
);