Demonstration of the various ways of sending attachments in a message.
Remember to adjust the channel id to a channel in your server the bot has access to.

![Demo attachments](/assets/examples/attachments.png)

For more info, [check out the documentation](/parsables/attachment.md).

```sc title="attachments.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

dc_send_message(dc_channel_from_id('759102744761335891'), {
    'content' -> '',
    'attachments' -> [
        {
            'url' -> 'https://raw.githubusercontent.com/replaceitem/carpet-discarpet/master/src/main/resources/assets/discarpet/icon.png'
        },
        {
            'bytes' -> 'Hello world!',
            'name' -> 'Message.txt'
        }
    ]
});
```