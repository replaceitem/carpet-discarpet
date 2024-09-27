Sends a message with a detailed embed

![Demo embed](/assets/demo_embed.png)

For more info, [check the documentation](/parsables/embeds/embed.md).

```sc title="embeds.sc"
__config() -> {
    'scope' -> 'global',
    'bot' -> 'mybot'
};

embed = {
    'title' -> 'SuperCoolEmbed',
    'url' -> 'https://github.com/gnembon/fabric-carpet',
    'description' -> 'SuperCoolEmbed',
    'author'-> {
        'name' -> 'replaceitem',
        'url' -> 'https://github.com/replaceitem',
        'icon' -> 'https://github.com/replaceitem.png'
    },
    'fields' -> [
        {
            'name' -> 'Field 1',
            'value' -> 'This field is the first'
        },
        {
            'name' -> 'Field 2',
            'value' -> 'This field is the second'
        },
        {
            'name' -> 'Inline field 1',
            'value' -> 'This is an inline field',
            'inline' -> true
        },
        {
            'name' -> 'Inline field 2',
            'value' -> 'This is another inline field',
            'inline' -> true
        }
    ],
    'color' -> [255, 128, 0],
    'footer' -> {
        'text' -> 'gnembon',
        'icon' -> 'https://github.com/gnembon.png'
    },
    'image' -> 'https://raw.githubusercontent.com/replaceitem/carpet-discarpet/master/src/main/resources/assets/discarpet/icon.png',
    'thumbnail' -> 'https://repository-images.githubusercontent.com/185908133/04119080-f738-11e9-9e23-03d4e371d438',
    'timestamp' -> convert_date(2022, 12, 1, 20, 51, 20)
};

dc_send_message(dc_channel_from_id('759102744761335891'), {
    'content' -> '',
    'embeds' -> [embed]
});
```