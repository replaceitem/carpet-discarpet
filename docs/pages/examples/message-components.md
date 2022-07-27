Sends a message with various buttons and a select menu

```sc title="message_components.sc"
__config() -> {'scope'->'global','bot'->'BOT'};

global_ch = dc_channel_from_id('759102744761335891');

task(_()->(
    dc_send_message(global_ch,{
        'content'->'Example interactions',
        'components'->
        [
            [
                {
                    'component'->'button',
                    'id'->'btn1',
                    'style'->'red',
                    'label'->'Red button',
                    'emoji'->'❌'
                },
                {
                    'component'->'button',
                    'id'->'btn2',
                    'style'->'blurple',
                    'label'->'Blurple button',
                    'emoji'->'🚪'
                },
                {
                     'component'->'button',
                     'id'->'btn3',
                     'style'->'green',
                     'label'->'Green button',
                     'emoji'->'👑'
                },
                 {
                      'component'->'button',
                      'id'->'btn4',
                      'style'->'grey',
                      'label'->'Grey button',
                      'emoji'->'📧'
                 }
            ],
            [

                {
                    'component'->'select_menu',
                    'id'->'select1',
                    'placeholder'->'Select at least 2 items here',
                    'min'->2,
                    'max'->5,
                    'options'->[
                        {
                            'value'->'pizza',
                            'label'->'Pizza',
                            'description'->'I mean who doesn\'t like pizza?',
                            'emoji'->'🍕'
                        },
                        {
                            'value'->'cake',
                            'label'->'Cake',
                            'description'->'If you prefer sweet food',
                            'emoji'->'🍰'
                        },
                        {
                            'value'->'popcorn',
                            'label'->'Popcorn',
                            'description'->'Just like in the cinema',
                            'emoji'->'🍿'
                        },
                        {
                            'value'->'bread',
                            'label'->'Bread',
                            'description'->'Something simple but delicious',
                            'emoji'->'🍞'
                        },
                        {
                            'value'->'carrot',
                            'label'->'Carrot',
                            'description'->'Something healthy',
                            'emoji'->'🥕'
                        }
                    ]
                }
            ]
        ]
    });

    //doing this in a seperate message, since there is a bug in javacord that breaks events from regular buttons if there is a url button in the same message
    dc_send_message(global_ch,{
        'content'->'My github:',
        'components'-> [[
            {
                //leaving 'id' out since its a url button
                'component'->'button',
                'style'->'url',
                'label'->'Open replaceitem\'s github',
                'emoji'->'🌐',
                'url'->'https://github.com/replaceitem'
            }
        ]],

    });


));

__on_discord_button(int) -> (
    task(_(outer(int)) -> dc_respond_interaction(int,'respond_immediately','Pressed button ' + int~'id'));
);

__on_discord_select_menu(int) -> (
    task(_(outer(int)) -> dc_respond_interaction(int,'respond_immediately','Selected ' + str(int~'chosen')));
);
```