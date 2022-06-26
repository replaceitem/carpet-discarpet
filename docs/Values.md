# Discarpet Values

Discarpet adds various new value types to scarpet, that wrap a Discord value.
This page lists all of them and which values can be queried from it.
Querying happens just like with scarpets entity values: `channel~'value'`.
All Discarpet values have a type name (which you get from `type()`) with the prefix `dc_`.

## Channel

`dc_channel`

A channel value represents a Discord channel.

Queryable:

| Property      | Type             | Description                                                                                           |
|---------------|------------------|-------------------------------------------------------------------------------------------------------|
| `name`        | String           | The name of the Discord channel                                                                       |
| `topic`       | String           | The topic of the channel (See `dc_set_channel_topic`)                                                 |
| `id`          | String           | ID of the channel                                                                                     |
| `mention_tag` | String           | Mention tag for the channel. This can be put inside a message for the channel to be a clickable link. |
| `server`      | Server           | Server this channel is in, or null if this is a private channel                                       |
| `type`        | String           | Channel type (See below)                                                                              |
| `webhooks`    | List of Webhooks | All webhooks in this channel                                                                          |

Possible `type`s are:

* `server_text_channel`
* `private_channel`
* `server_voice_channel`
* `group_channel`
* `channel_category`
* `server_news_channel`
* `server_store_channel`
* `server_news_thread`
* 

## Emoji

`dc_emoji`

This value stores an Emoji, which could be a normal unicode emoji, or a custom emoji from a Discord server.

Queryable:

| Property      | Type    | Description                                                                            |
|---------------|---------|----------------------------------------------------------------------------------------|
| `mention_tag` | String  | Mention tag for the emoji. This can be used to put into messages to contain the emoji. |
| `unicode`     | String  | Returns the emoji as a unicode character. If it's a custom emoji, returns `null`       |
| `is_animated` | boolean | True if the emojis is animated, false if not                                           |
| `is_unicode`  | boolean | True if the emoji is a unicode emoji, false otherwise                                  |
| `is_custom`   | boolean | True if the emoji is a custom one, otherwise false                                     |

## Message

`dc_message`

A Discord message which has been sent. Main use is in `__on_discord_message` event

Queryable:

| Property           | Type                | Description                                                                                                                                                                         |
|--------------------|---------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `content`          | String              | Get the content (text) of the message, not that things like emojis, mentions or channels will appear as their id. For the readable content, see below.                              |
| `readable_content` | String              | Get the content and replace all emojis, mentions, channels with their readable representation. Note that if a user is not cached, mentions to him may not get parsed.               |
| `id`               | String              | Get the id of the message                                                                                                                                                           |
| `channel`          | Channel             | Get the channel this message is inside                                                                                                                                              |
| `user`             | User                | Get the user that wrote this message. Note that this may fail (and return null) if the user is not cached, but if queried after the `__on_discord_message` event, it should be fine |
| `server`           | Server              | Get the server this message was written in                                                                                                                                          |
| `delete`           | boolean             | This is not actually a query, but it removes the message. Returns false if the bot does not have permission to delete the message, otherwise false                                  |
| `nonce`            | String              | The nonce of this message                                                                                                                                                           |
| `attachments`      | List of Attachments | A list of attachments on this message                                                                                                                                               |

## Attachment

`dc_attachment`

An attachment from a message

Queryable:

| Property     | Type           | Description                                                                                                        |
|--------------|----------------|--------------------------------------------------------------------------------------------------------------------|
| `message`    | Message        | The message of this attachment                                                                                     |
| `file_name`  | String         | File name of the attachment                                                                                        |
| `size`       | number         | The size as the number of bytes of the attached file                                                               |
| `url`        | String         | The URL of this file                                                                                               |
| `proxy_url`  | String         | The proxy URL of this file                                                                                         |
| `is_image`   | boolean        | Whether this file is an image or not                                                                               |
| `width`      | number or null | The width of the attached image, or null if not an image                                                           |
| `height`     | number or null | The height of the attached image, or null if not an image                                                          |
| `is_spoiler` | boolean        | Whether this file is marked as a spoiler                                                                           |
| `download`   | String         | Downloads the file's bytes as a string. Be careful with this one, big files can block the game for quite some time |

## Reaction

`dc_reaction`

A reaction on a message. Main use is in `__on_discord_reaction` event

Queryable:

| Property  | Type    | Description                              |
|-----------|---------|------------------------------------------|
| `emoji`   | Emoji   | The emoji of this reaction               |
| `count`   | Number  | Amount of reactions with this emoji      |
| `message` | Message | The message this reaction is attached to |

## Server

`dc_server`

A Discord server

Queryable:

| Property         | Type                   | Description                                                |
|------------------|------------------------|------------------------------------------------------------|
| `name`           | String                 | The name of the server                                     |
| `id`             | String                 | The ID of the server                                       |
| `users`          | List of Users          | All users in this server (this requires the member Intent) |
| `channels`       | List of Channels       | All channels in this server                                |
| `roles`          | List of Roles          | All roles in this server                                   |
| `webhooks`       | List of Webhooks       | All webhooks in this server                                |
| `slash_commands` | List of Slash commands | All slash commands in this server                          |

## User

`dc_user`

A discord user. Can be from a real discord account or a bot

Queryable:

| Property             | Type    | Description                                                                                                      |
|----------------------|---------|------------------------------------------------------------------------------------------------------------------|
| `name`               | String  | The name of the user (Does not include nicknames, use `dc_get_display_name` for that)                            |
| `mention_tag`        | String  | The mention tag to mention a user in a message                                                                   |
| `discriminated_name` | String  | The name of the user with its discriminator. e.g. `replaceitem#9118`                                             |
| `id`                 | String  | ID of the user                                                                                                   |
| `avatar`             | String  | URL of the users profile picture                                                                                 |
| `is_bot`             | boolean | True if the user is a bot, false if it is a regular user                                                         |
| `is_self`            | boolean | True if the user is the currently logged in bot account itself. Useful to prevent bots replying to itself        |
| `private_channel`    | Channel | The private messages channel with the user. Note that this may block, if the private channel was not yet opened. |

## Role

`dc_role`

A discord role.

Queryable:

| Property               | Type          | Description                                                      |
|------------------------|---------------|------------------------------------------------------------------|
| `name`                 | String        | The name of the role                                             |
| `id`                   | String        | The id of the role                                               |
| `mention_tag`          | String        | The tag used to mention this role                                |
| `color`                | String        | Hex color string of the role color                               |
| `position`             | number        | The position of this role as it is sorted in the server settings |
| `server`               | Server        | The server of this role                                          |
| `users`                | List of Users | All users with the role                                          |
| `displayed_separately` | boolean       | Are the users with this role shown separately in the member list |
| `is_everyone_role`     | boolean       | Whether this role is the @everyone role                          |
| `managed`              | boolean       | Whether this role is managed by an integration or not            |
| `mentionable`          | boolean       | Whether this role is mentionable or not                          |


## Slash command interaction

`dc_slash_command_interaction`

Value from `__on_discord_slash_command(interaction)` event, used for getting the command that was executed, and then replying to it with `dc_respond_slash_command()`

Queryable:

| Property            | Type                                       | Description                                                                   |
|---------------------|--------------------------------------------|-------------------------------------------------------------------------------|
| `id`                | String                                     | The id of the command                                                         |
| `command_name`      | String                                     | The name of the slash command                                                 |
| `channel`           | Channel                                    | The channel this command was executed in.                                     |
| `user`              | User                                       | The user that executed the command.                                           |
| `token`             | String                                     | The token used to respond to the interaction (normally not needed)            |
| `arguments`         | List of slash command options              | The selected options of the command                                           |
| `arguments_by_name` | Map of slash command options by their name | Returns a map of all options (and sub-options), with the key being their name |

## Slash command interaction option

`dc_slash_command_interaction_option`

Represents the user-chosen options of a slash command execution.

| Property                 | Type                                         | Description                                             |
|--------------------------|----------------------------------------------|---------------------------------------------------------|
| `name`                   | String                                       | Name of the command option                              |
| `is_subcommand_or_group` | boolean                                      | Whether this option is a subcommand or subcommand group |
| `value`                  | ? (depends on the slash command option type) | The value chosen by the user                            |
| `options`                | List of slash command interaction options    | The sub-options of this option                          |

## Slash command

`dc_slash_command`

Represents a slash command on a server

| Property             | Type            | Description                                                                                                                                                                     |
|----------------------|-----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `id`                 | String          | Id of the command                                                                                                                                                               |
| `name`               | String          | Name of the command                                                                                                                                                             |
| `description`        | String          | Description of the command                                                                                                                                                      |
| `server`             | Server          | The server this slash command is in, or null if it's a global slash command                                                                                                     |
| `options`            | List of Strings | The names of the slash command options (didn't bother adding a slash command option value, since nobody gonna use it anyway, but if you need it, make an issue and i'll add it) |
| `creation_timestamp` | number          | The timestamp of the command                                                                                                                                                    |

## Button and Select menu interaction

`dc_button_interaction`, `dc_select_menu_interaction`

Value from `__on_discord_button(interaction)` and `__on_discord_select_menu(interaction)` event, used for getting the message interaction details, and then responding to it with `dc_respond_slash_command()`

These values have mostly the same things to query.

Queryable:

| Property  | Type    | Description                                                                                                 |
|-----------|---------|-------------------------------------------------------------------------------------------------------------|
| `id`      | String  | Id of the button or select menu, which was specified by the user in the `dc_send_message` message parameter |
| `channel` | Channel | The channel this interaction was made in.                                                                   |
| `user`    | User    | The user that used the interaction.                                                                         |
| `message` | Message | The message this interaction is attached to.                                                                |

Queryable things exclusive to select menus:

| Property      | Type   | Description                                             |
|---------------|--------|---------------------------------------------------------|
| `chosen`      | List   | List the values of the chosen options                   |
| `options`     | List   | All values of options in the select menu                |
| `min`         | number | Minimum amount of selected entries for this select menu |
| `max`         | number | Maximum amount of selected entries for this select menu |
| `placeholder` | String | Placeholder text of this select menu                    |

## Webhook

`dc_webhook`

A webhook in a channel.

Queryable:

| Property  | Type    | Description                                                                    |
|-----------|---------|--------------------------------------------------------------------------------|
| `id`      | String  | The id of the webhook                                                          |
| `channel` | Channel | The channel this webhook is in                                                 |
| `type`    | String  | Webhook type, can be either `'INCOMING'`, `'CHANNEL_FOLLOWER'` and `'UNKNOWN'` |
| `token`   | String  | The token of the webhooks, only works for incoming webhooks                    |
| `url`     | String  | Webhook URL, only works for incoming webhooks                                  |