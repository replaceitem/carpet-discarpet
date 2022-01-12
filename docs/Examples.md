# Example scripts

## Replying to messages

```py
__config() -> {'scope'->'global','bot'->'BOT'};

__on_discord_message(message) -> (
    if(message~'user'~'is_self',return()); // stop the event if the message is from the bot itself

    if(message~'content' == 'Ping',
        // use a task to not freeze the game
        task(_(outer(message)) -> (
            dc_send_message(message~'channel','Pong!');
        ));
    );
);

```

## Showing online players in bot activity

```py
__config() -> {'scope'->'global','bot'->'BOT'};

__on_tick() -> (
    if(tick_time()%(20*30) == 0, // Consider discords rate limit (only execute every 30 seconds)
        if(length(player('all')) != 0, // are players online?
            dc_set_activity('playing','with ' + join(', ',player('all'))); //display list of players
            dc_set_status('online'); // status should be online
        ,
            dc_set_activity('playing','with nobody'); // alternative text if nobody is online
            dc_set_status('idle'); // set status to idle
        );
    );
);

```

## Sending all Minecraft log messages to a discord channel

```py
__config() -> {'scope'->'global','bot'->'BOT'};

global_executions = 0;
global_log = dc_channel_from_id('789877625497190440');

__on_tick() -> (
    global_executions = 0;
);

__on_system_message(text,type,entity) -> (
    global_executions += 1; //prevent recursion
    if(global_executions < 10,
        if((type~'commands.save.') == null, //dont send 'saving world' messages
            task(_(outer(text)) -> (
                dc_send_message(global_log,text); //send to discord
            ));
        );
    );
);

```

## Simple discord command (not a slash command)

```py
__config() -> {'scope'->'global','bot'->'BOT'};

__on_discord_message(message) -> (
    if(message~'user'~'is_self',return()); // ignore messages by the bot itself

    text = message~'content';

    if(slice(text,0,1)=='!', // check for a ! indicating a command
        // split all arguments to a list
        cmd = split(' ',slice(text,1));

        if(cmd:0 == 'list', // !list command
            task(_(outer(message)) -> (
                dc_send_message(message~'channel','Currently online: ' + join(', ',player('all')));
            ));
            return();
        );

        if(cmd:0 == 'help',  // !help command
            task(_(outer(message)) -> (
                dc_send_message(message~'channel','I\'m sorry but i cant help you');
            ));
            return();
        );
    );
);

```

## If you dont trust your server members as much
```py
__config() -> {'scope'->'global','bot'->'BOT'};

global_log = dc_channel_from_id('789877625497190440');

__on_player_interacts_with_block(player, hand, block, face, hitvec) -> (
    if((['chest','barrel']~block != null) || (block~'shulker_box' != null), //warning when player opens chest/barrel/shulkerbox
        task(_(outer(player),outer(block)) -> (
            dc_send_message(global_log,str('%s opened %s at %s in %s',player,block,pos(block),current_dimension()));
        ));
    );
);
__on_player_places_block(player, item_tuple, hand, block) -> (
    if(block == 'tnt', //warning when player places tnt
        task(_(outer(player),outer(block)) -> (
            dc_send_message(global_log,str(':warning: %s placed %s at %s in %s',player,block,pos(block),current_dimension()));
        ));
    );
);

```
## Reactions as user input

```py
__config() -> {'scope'->'global','bot'->'BOT'};

global_channel = dc_channel_from_id('759102744761335891');

task(_()->(
    message = dc_send_message(global_channel,'React with 🟩 to accept or 🟥 to deny');
    global_msgid = message~'id';
    dc_react(message,'🟥');
    dc_react(message,'🟩');
));

__on_discord_reaction(reaction,user,added) -> (
    if(user~'is_self',return());
    if(reaction~'message'~'id' == global_msgid,
        action = if(added,'voted with','removed the vote for');
        task(_(outer(reaction),outer(user),outer(action))-> (
            dc_send_message(reaction~'message'~'channel',user~'name' + ' ' + action + ' ' + reaction~'emoji'~'unicode');
        ));
    );
);

```

## Embeds

```py
__config() -> {'scope'->'global','bot'->'BOT','stay_loaded'->false};

e = {
    'title'->'SuperCoolEmbed',
    'url'->'https://github.com/gnembon/fabric-carpet',
    'description'->'SuperCoolEmbed',
    'author'->{
        'name'->'replaceitem',
        'url'->'https://github.com/replaceitem',
        'icon'->'https://avatars3.githubusercontent.com/u/40722305?s=460&u=ae87da388b3b0aeab05edf67cef1c6f7208727d3&v=4'
    },
    'fields'->[
        {
            'name'->'Field 1',
            'value'->'This field is the first'
        },
        {
            'name'->'Field 2',
            'value'->'This field is the second'
        },
        {
            'name'->'Inline field 1',
            'value'->'This is an inline field',
            'inline'->true
        },
        {
            'name'->'Inline field 2',
            'value'->'This is another inline field',
            'inline'->true
        }
    ],
    'color'->[255,128,0],
    'footer'->{
        'text'->'gnembon',
        'icon'->'https://avatars1.githubusercontent.com/u/41132274?s=460&v=4'
    },
    'image'->'https://raw.githubusercontent.com/replaceitem/carpet-discarpet/master/src/main/resources/assets/discarpet/icon.png',
    'thumbnail'->'https://repository-images.githubusercontent.com/185908133/04119080-f738-11e9-9e23-03d4e371d438',
    'timestamp'->convert_date(2022,12,1,20,51,20)
};

dc_send_message(dc_channel_from_id('759102744761335891'),{
    'content'->'',
    'embeds'->[e]
});

```
See [`dc_build_embed()`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Functions.md#dc_build_embed-dc_build_embedpropertyvalue)

## Chat between Minecraft and Discord

```py
__config() -> {'scope'->'global','bot'->'BOT','stay_loaded'->false};

global_executions = 0;
global_chat = dc_channel_from_id('789877643070799902');

__on_tick() -> (
    global_executions = 0;
);

__on_discord_message(message) -> (
    if(message~'channel'~'id'!=global_chat~'id',return()); //limit to chat channel only
    if(message~'user'~'is_self',return()); //ignore messages by the bot itself
    for(player('all'),
        col = 'd'; // this could be replaced with a custom way of fetching user color
        if(col == null,col = 'w');
        print(_,format(str('%s [%s]',col,dc_get_display_name(message~'user',message~'server')))+format(str('w  %s',message~'readable_content')))
    );
);

__on_system_message(text,type,entity) -> (
    global_executions += 1; //prevent recursion
    if(global_executions < 10,
        if(!(type~'admin'),
            if((type~'commands.save.') == null, //dont send 'saving world' messages
                msg = __parse_mentions(text,global_chat~'server');
                task(_(outer(msg)) -> dc_send_message(global_chat,msg)); //send to discord
            );
        );
    );
);

__on_chat_message(msg,player,command) -> (
    task(_(outer(msg)) -> dc_send_message(global_chat,'chat: ' + msg)); //send to discord
);

__parse_mentions(msg,server) -> (
    for(server~'users',
        msg = replace(msg,'@' + dc_get_display_name(_,server),_~'mention_tag');
    );
    msg;
);

```

## Slash commands

```py
__config() -> {'scope'->'global','bot'->'BOT'};


initialize_commands() -> (
    //remove all commands first
    dc_delete_slash_command();

    server = dc_server_from_id('689483030754099267');

    //simple ping command
    dc_create_slash_command('ping','Ping -> Pong!',server);

    //more complex command with subcommand groups and subcommands, as well as options
    dc_create_slash_command('example','Test command',server,[
        {
            'type'->'SUB_COMMAND_GROUP',
            'name'->'delete',
            'description'->'Delete something',
            'options'->[
                {
                    'type'->'SUB_COMMAND',
                    'name'->'channel',
                    'description'->'Remove something',
                    'options'->[
                        {
                            'type'->'CHANNEL',
                            'name'->'channel',
                            'description'->'What channel to delete',
                            'required'->true
                        },
                        {
                            'type'->'BOOLEAN',
                            'name'->'force',
                            'description'->'Force delete channel?',
                            'required'->false
                        }
                    ]
                }
            ]
        },
        {
            'type'->'SUB_COMMAND_GROUP',
            'name'->'create',
            'description'->'Create something',
            'options'->[
                {
                    'type'->'SUB_COMMAND',
                    'name'->'channel',
                    'description'->'Create a channel',
                    'options'->[
                        {
                            'type'->'STRING',
                            'name'->'name',
                            'description'->'Name of the channel',
                            'required'->true
                        },
                        {
                            'type'->'BOOLEAN',
                            'name'->'private',
                            'description'->'Is this channel private?',
                            'required'->true
                        },
                        {
                            'type'->'STRING',
                            'name'->'type',
                            'description'->'Channel type',
                            'required'->true,
                            'choices'->[
                                {
                                    'name'->'Text',
                                    'value'->'text'
                                },
                                {
                                    'name'->'Voice',
                                    'value'->'voice'
                                },
                                {
                                    'name'->'Announcement',
                                    'value'->'announcement'
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ]);
);

// reload commands async, as that would otherwise freeze the game for multiple seconds
task('initialize_commands');



__on_discord_slash_command(cmd) -> (
    //check which command was executed
    if(cmd~'command':0 == 'ping',
        //respond immediately
        task(_(outer(cmd))->dc_respond_interaction(cmd,'RESPOND_IMMEDIATELY','Pong!'));
    , //else
        //tell discord that its gonna take a bit for the response
        dc_respond_interaction(cmd,'RESPOND_LATER');
        //respond after 5 seconds
        task(_(outer(cmd))->(
            sleep(5000);
            dc_respond_interaction(cmd,'RESPOND_FOLLOWUP','Executed ' + cmd~'command' + ' with options ' + cmd~'options');
        ));
    );
);

```


## Attachments

```py
__config() -> {'scope'->'global','bot'->'BOT','stay_loaded'->false};

dc_send_message(dc_channel_from_id('759102744761335891'),{
    'content'->'',
    'attachments'->[
        {
            'file'->'C:/some/path/to/file.zip'
        },
        {
            'url'->'https://raw.githubusercontent.com/replaceitem/carpet-discarpet/master/src/main/resources/assets/discarpet/icon.png'
        },
        {
            'bytes'->'Hello world!',
            'name'->'Message.txt'
        }
    ]
});

```

## Buttons and select menus

```py
__config() -> {'scope'->'global','stay_loaded'->true,'bot'->'BOT'};

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