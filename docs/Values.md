# Discarpet Values

Discarpet adds various new value types to scarpet, that wrap a Discord value.
This page lists all of them and which values can be queried from it.
Querying happens just like with scarpets entity values: `channel~'value'`.
All Discarpet values have a type name (which you get from `type()`) with the prefix `dc_`.

## Channel

`dc_channel`

A channel value represents a Discord channel in a Discord server.

Queryable:

`name` (String) The name of the Discord channel

`topic` (String) The topic of the channel (See `dc_set_channel_topic`)

`id` (String) ID of the channel

`mention_tag` (String) Mention tag for the channel. This can be put inside a message for the channel to be a clickable link.

`server` ([Server](#Server)) Server this channel is in

## EmbedBuilder

`dc_embed_builder`

A EmbedBuilder is used to create Embeds. This is mainly used with the `dc_build_embed` function.

Queryable:

This value has nothing to query.

## Emoji

`dc_emoji`

This value stores an Emoji, which could be a normal unicode emoji, or a custom emoji from a Discord server.

Queryable:

`mention_tag` (String) Mention tag for the emoji. This can be used to put into messages to contain the emoji.

`unicode` (String) Returns the emoji as a unicode character. If it's a custom emoji, returns `null`

`is_animated` (boolean) True if the emojis is animated, false if not

`is_unicode` (boolean) True if the emoji is a unicode emoji, false otherwise

`is_custom` (boolean) True if the emoji is a custom one, otherwise false

## Message

`dc_message`

A Discord message which has been sent. Main use is in `__on_discord_message` event

Queryable:

`content` (String) Get the content (text) of the message, not that things like emojis, mentions or channels will appear as their id. For the readable content, see below.

`readable_content` (String) Get the content and replace all emojis, mentions, channels with their readable representation. Note that if a user is not cached, mentions to him may not get parsed.

`id` (String) Get the id of the message

`channel` ([Channel](#Channel)) Get the channel this message is inside

`user` ([User](#User)) Get the user that wrote this message. Note that this may fail (and return null) if the user is not cached, but if queried after the `__on_discord_message` event, it should be fine

`server` ([Server](#Server)) Get the server this message was written in

`delete` (boolean) This is not actually a query, but it removes the message. Returns false if the bot does not have permission to delete the message, otherwise false

## Reaction

`dc_reaction`

A reaction on a message. Main use is in `__on_discord_reaction` event

Queryable:

`emoji` ([Emoji](#Emoji)) The emoji of this reaction

`count` (Number) Amount of reactions with this emoji

`message` ([Message](#Message)) The message this reacion is attached to

## Server

`dc_server`

A Discord server

Queryable:

`name` (String) The name of the server

`id` (String) The ID of the server

`users` (List of Users) All users in this server (this requires the member [intent](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Setup.md#Intents))

`channels` (List of Channels) All channels in this server

## User

`dc_user`

A discord user. Can be from a real discord account or a bot

Queryable:

`name` (String) The name of the user (Does not include nicknames, use `dc_get_display_name` for that)

`mention_tag` (String) The mention tag to mention a user in a message

`discriminated_name` (String) The name of the user with its discriminator. e.g. `replaceitem#9118`

`id` (String) ID of the user

`avatar` (String) URL of the users profile picture

`is_bot` (boolean) True if the user is a bot, false if it is a regular user

`is_self` (boolean) True if the user is the currently logged in bot account itself. Useful to prevent bots replying to itself

## Slash command interaction

`dc_slash_command_interaction`

Value from `__on_discord_slash_command(interaction)` event, used for getting the command that was executed, and then replying to it with `dc_respond_slash_command()`

Queryable:

`command` (List) List of command option that were executed. If a user executed the slash command `/channel remove #bot-spam`, this would return `['channel', 'remove']`.

`options` (Map) A map containing all options that were specified in the command, with the key being the name of the option, with a corresponding value that has been chosen for this option.

`user` ([User](#User)) The user that executed the command.

`channel` ([Channel](#Channel)) The channel this command was executed in.