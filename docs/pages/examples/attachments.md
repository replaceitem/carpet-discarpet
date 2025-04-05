---
icon: material/file-upload
---


Sends a message with multiple [attachments](/parsables/attachment.md):

* Image attachment from an external URL
* File attachment with specified `bytes`


![Demo attachments](/assets/examples/attachments.png)


```sc title="attachments.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

channel = dc_channel_from_id('put id here!');

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