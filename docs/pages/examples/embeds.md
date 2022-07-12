Sends a message with a demo embed that looks like this:

![Demo embed](/assets/demo_embed.png)

```sc title="embeds.sc"
__config() -> {'scope'->'global','bot'->'BOT','stay_loaded'->false};

embed = {
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
    'embeds'->[embed]
});
```