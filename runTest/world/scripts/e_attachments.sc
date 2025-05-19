import('env','env');

__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

channel = dc_channel_from_id(env('channelId'));

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