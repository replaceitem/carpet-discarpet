# Discarpet functions

Discarpet adds a lot of functions to scarpet to control your bot.
Below is a list of all functions and how they work.

## Sending

### `dc_send_message(channel,content,function?)`

This functions sends a message in a specific Discord `channel`. 
The `content` can be a String, or a [`EmbedBuilder`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#embedbuilder)
to send an Embed. Optionally, you can specify a function (or lambda expression, see example below)
that will be executed when the message was send to modify it, add reactions etc.

This example shows how you can send a message and add reactions to it as soon as it was sent

```py
dc_send_message(dc_channel_from_id('YOUR CHANNEL ID'),'Test message',_(message)->(
    dc_react(message,'🟥');
    dc_react(message,'🟩');
));
```

### `dc_react(message,emoji)`

React to a [`Message`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#message) with an `emoji`.
The `emoji` can be a unicode emoji (as a string) or an
[`emoji`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#emoji) value.

## Values from IDs

### `dc_channel_from_id(id)`

Returns a [`channel`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#channel) value from the
specified channel id, or `null` if the channel was not found.

### `dc_server_from_id(id)`

Returns a [`server`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#server) value from the
specified server id, or `null` if the server was not found.

### `dc_emoji_from_id(server,id)`

Returns a [`emoji`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#emoji) value from the
specified emoji id in a `server`. If there is no emoji with that id, it will instead search for custom emojis which have the name `id`. If there are none, returns an empty list.
This is only for custom emojis, since standard emojis are specified from the unicode emoji.

## Set

### `dc_set_channel_topic(channel,text)`

This function sets the description of the [`channel`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#channel)
to the specified `text`. Remember to give the bot permission to do that.

### `dc_set_activity(type,text)`

Sets the activity of the bot to the specified `text` and `type`.
The `type` can be `playing`,`streaming`,`listening`,`watching`,or ~~custom~~
_(`custom` is not supported for bots from the discord api, so it has no use)_.
The `text` will appear after the type. Returns `null` if the `type` was invalid.

### `dc_set_status(status)`

Changes the status of the bot. Can be `online`,`idle`,`dnd`(Do not disturb),`invisible` and `offline`.

## Get

### `dc_get_display_name(user,server)`

Gets the nickname, or name if no nickname is present, from the [`user`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#user) in the [`server`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#server).

## Embeds

### `dc_build_embed()` `dc_build_embed(property,value...)`

This function is used to create custom Embeds which can be sent using [`dc_send_message`](#dc_send_messagechannelcontentfunction).

When ran without arguments, returns an empty [embed](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#embedbuilder) value.

The value is used to specify different properties of the Embed. This is done by specifying a `property` and a `value` in the function.

The following properties are allowed:

`dc_build_embed(embed,'title',title)`
`dc_build_embed(embed,'description',description)`
`dc_build_embed(embed,'author',userValue)` Automtically uses the name us the user, as well as his avatar and clickable link
`dc_build_embed(embed,'author',name)` Only use a custom name
`dc_build_embed(embed,'author',name,clickURL,iconURL)` Use custom name, url and icon
`dc_build_embed(embed,'add_field',title,description)`
`dc_build_embed(embed,'add_inline_field',title,description)`
`dc_build_embed(embed,'color',r,g,b)`
`dc_build_embed(embed,'color',[r,g,b])`
`dc_build_embed(embed,'footer',name,iconURL)`
`dc_build_embed(embed,'image',imageURL)`
`dc_build_embed(embed,'thumbnail','thumbnailURL')`

Here is a example on how to build an embed:

```py
e = dc_build_embed();
dc_build_embed(e,'title','SuperCoolEmbed');
dc_build_embed(e,'description','This embed is super cool!');
dc_build_embed(e,'author','replaceitem','https://github.com/replaceitem','https://avatars3.githubusercontent.com/u/40722305?s=460&u=ae87da388b3b0aeab05edf67cef1c6f7208727d3&v=4');
dc_build_embed(e,'add_field','Field 1','This field is the first');
dc_build_embed(e,'add_field','Field 2','This field is the second');
dc_build_embed(e,'add_inline_field','Inline field 1','This is an inline field');
dc_build_embed(e,'add_inline_field','Inline field 2','This is another inline field');
dc_build_embed(e,'color',255,128,0);
dc_build_embed(e,'footer','gnembon','https://avatars1.githubusercontent.com/u/41132274?s=460&v=4');
dc_build_embed(e,'image','https://raw.githubusercontent.com/replaceitem/carpet-discarpet/master/logo.png');
dc_build_embed(e,'thumbnail','https://avatars3.githubusercontent.com/u/40722305?s=460&u=ae87da388b3b0aeab05edf67cef1c6f7208727d3&v=4');
dc_send_message(dc_channel_from_id('CHANNEL ID HERE'),e);
```

Which gives this result:

![Example embed](/docs/embed.png)
