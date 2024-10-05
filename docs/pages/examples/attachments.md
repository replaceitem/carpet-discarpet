Demonstration of the various ways of sending attachments in a message.
Remember to adjust the channel id to a channel in your server the bot has access to.

For more info, check out the [Attachment parsable](/parsables/attachment.md).

![Demo attachments](/assets/examples/attachments.png)

```sc title="attachments.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

channel = dc_channel_from_id('1234567891011121314');

dc_send_message(channel, {
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