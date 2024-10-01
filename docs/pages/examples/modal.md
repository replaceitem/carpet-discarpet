Demonstrates how to make and respond to a modal.

![Demo modal](/assets/examples/modal.png)

```sc title="modal.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

global_channel = dc_channel_from_id('759102744761335891');

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
            dc_respond_interaction(int, 'respond_modal', {
                'id' -> 'my_modal',
                'title' -> 'A Custom Modal',
                'components' -> [
                    [{
                        'component' -> 'text_input',
                        'id' -> 'name_input',
                        'style' -> 'short',
                        'label' -> 'What\'s your name?',
                        'min_length' -> 3,
                        'max_length' -> 32,
                        'required' -> true,
                        'placeholder' -> 'Put your name here'
                    }],
                    [{
                        'component' -> 'text_input',
                        'id' -> 'age_input',
                        'style' -> 'short',
                        'label' -> 'How old are you?',
                        'min_length' -> 1,
                        'max_length' -> 3,
                        'required' -> true,
                        'placeholder' -> 'Enter a number'
                    }],
                    [{
                        'component' -> 'text_input',
                        'id' -> 'introduction_input',
                        'style' -> 'paragraph',
                        'label' -> 'Introduce yourself',
                        'required' -> false,
                        'value' -> 'Hello, I am'
                    }]
                ]
            });
        ));
    );
);

__on_discord_modal(interaction) -> (
    dc_respond_interaction(interaction, 'respond_immediately', 'Welcome ' + interaction~'input_values_by_id':'name_input' + '!');
);
```