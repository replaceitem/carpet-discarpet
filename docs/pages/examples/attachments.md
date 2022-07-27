Demonstration of the various ways of sending attachments in a message.
Remember to adjust the channel id to a channel in your server the bot has access to.

```sc title="attachments.sc"
__config() -> {'scope'->'global','bot'->'BOT'};

dc_send_message(dc_channel_from_id('759102744761335891'),{
    'content'->'',
    'attachments'->[
        {
            'file'->'C:/path/to/some/file.txt'
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