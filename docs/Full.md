# Setting up Bots


## Logging in

First, you need a Discord bot account.
You can create one at the [Discord developer portal](https://discord.com/developers/applications).
If you aren't sure how to do that, look up a tutorial for that.

When you installed Discarpet and start your server, it should create a `discarpet.json` file in the config folder of the server
(Yes, this mod is made for servers, I never tried using it in singleplayer, it may work, it may not).

The file should look like this by default:

```json
{
  "bots": [
    {
      "bot_id": "Your bot ID",
      "bot_token": "Your bot token",
      "member_intent": false,
      "presence_intent": false
    }
  ],
  "disable_reconnect_logs": false
}
```

To add your bot to the game, copy and paste your Bot token from the Developer portal into the `"Your Bot Token"` field.
The `"bot_id"` is used to identify your bot in scarpet later. You should just give the bot a name, so you can identify it.
This doesn't need to be what you called it in the developer portal,
it's just an arbitrary name.
The `member_intent` and `presence_intent` can be set to `true` if these intents are needed by your bots.
For more info, see the section about [intents](#Intents).

Now your config should look something like this:

```json
{
  "bots": [
    {
      "bot_id": "coolbot",
      "bot_token": "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789",
      "member_intent": false,
      "presence_intent": false
    }
  ],
  "disable_reconnect_logs": false
}
```

Now, you can use the in-game command `/discarpet reload` to reload all the bots, and start new ones if you added one to the config.
This command will tell you whether the bots could be logged in or not.

When the login was successful, you can use `/discarpet getInvite [bot id]` to get a link in chat, which upon click,
will take you directly to the webpage where you can add the bot to any discord server you have admin permission in.

## Making a script

Now it's time to create your bot scarpet script!

Since Discarpet supports multiple bots at the same time, you need to specify which bot you want to use in your script.
This is done by specifying a `'bot'` to the config line, like this:

`__config()->{'bot'->'bot_id'}`

The `'bot_id'` is the id you specified in the config.
Without a valid bot specified, no discarpet functions will work and will all throw an error.

## Multiple bots

To have multiple bots running on your server, just add them to the config file like this:

```json
{
  "bots": [
    {
      "bot_id": "bot1",
      "bot_token": "token1"
    },
    {
      "bot_id": "bot2",
      "bot_token": "token2"
    },
    {
      "bot_id": "third_bot",
      "bot_token": "token for third bot"
    }
  ],
  "disable_reconnect_logs": false
}
```

Keep in mind that each script can only have one bot.
Each script will only receive events from that one specified bot,
and when getting [values from ids](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Functions.md#Values-from-ids),
the value will have the context of the bot of the script. That would mean that if you pass a message value from an event to another script,
and add a reaction there, the user of the reaction will still be from the script where the event happened.
Only if you query [values from ids](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Functions.md#Values-from-ids),
the bot from the config will be applied.

## Intents

In the config file, you can enable two kinds of intents for your bot.
You should leave them as `false`, unless you use functions that require your bot to have this permission.
In that case, you also need to enable the permissions in the Discord developer portal (Bot/Privileged Gateway Intents).
There is a hint at all functions that need an additional Intent, so you know when you need them.

## Disabling log messages

If you run Discarpet for a while, you might notice messages that look like this:

```
[ReadingThread/INFO]: Websocket closed with reason 'Discord commanded a reconnect (Received opcode 7)' and code COMMANDED_RECONNECT (4999) by client!
[ReadingThread/INFO]: Trying to reconnect/resume in 1 seconds!
```

To disable those messages, you can set the `disable_reconnect_logs` config option to `true`# Discarpet Values

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
| `type`        | String           | Channel type                                                                                          |
| `webhooks`    | List of Webhooks | All webhooks in this channel                                                                          |


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
| `url`     | String  | Webhook URL, only works for incoming webhooks                                  |# Parsable values

Many discord entities don't have their own value, as they can be represented with scarpet maps, lists, and other values.

### Allowed mentions

Note that all of the options default to false, meaning that as soon as the allowed mentions are specified, all mentions are disabled by default.

| Value              | Type                                  | Description                                      |
|--------------------|---------------------------------------|--------------------------------------------------|
| `mention_roles`    | boolean (optional, defaults to false) | Whether roles can be mentioned                   |
| `mention_users`    | boolean (optional, defaults to false) | Whether users can be mentioned                   |
| `mention_everyone` | boolean (optional, defaults to false) | Whether `@everyone` and `@here` can be mentioned |
| `roles`            | List of Role ids (String)             | Roles that should be mentioned                   |
| `users`            | List of User ids (String)             | Users that should be mentioned                   |

### Attachment

An attachment can be created in different ways.
From a File, a URL or from a string which will be the raw bytes of the file.

| Value     | Type                                                                                      | Description                                  |
|-----------|-------------------------------------------------------------------------------------------|----------------------------------------------|
| `file`    | String (optional)                                                                         | File path for attached file                  |
| `url`     | String (optional)                                                                         | URL of the file to attach                    |
| `bytes`   | String (optional)                                                                         | String, which will be the file's binary data |
| `image`   | Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) (optional) | Image to be sent as an attachment            |
| `name`    | String (optional)                                                                         | File name if using `bytes`                   |
| `spoiler` | boolean (optional, defaults to false)                                                     | Whether this attachment is a spoiler         |

### Button

| Value       | Type                                                    | Description                                                                    |
|-------------|---------------------------------------------------------|--------------------------------------------------------------------------------|
| `component` | String                                                  | Must be `button`                                                               |
| `id`        | String (optional, not required for `url` style buttons) | Custom id of the button. Used to identify them when pressed                    |
| `label`     | String                                                  | The text shown on the button                                                   |
| `disabled`  | boolean (optional, defaults to false)                   | Whether the button is greyed out or pressable                                  |
| `style`     | String (optional, defaults to grey)                     | Button style. Can be `blurple`, `grey`, `green`, `red` and `url`               |
| `emoji`     | String or Emoji value (optional)                        | The emoji shown on the button                                                  |
| `url`       | String (optional, only required for `url` style)        | The URL opened when clicking the button. This is only used for the `url` style |

### Color

Can also be parsed directly from a list `[r,g,b]`, or a number (`0xRRGGBB`)

| Value | Type   | Description     |
|-------|--------|-----------------|
| `r`   | number | Red component   |
| `g`   | number | Green component |
| `b`   | number | Blue component  |

### Component

Can be either a [Button](#Button) or a [Select menu](#Select menu).
In both cases, the values from those parsables must be included.

| Value       | Type   | Description                       |
|-------------|--------|-----------------------------------|
| `component` | String | Must be `button` or `select_menu` |

### Embed author

Can also be parsed directly from a [User](/docs/Values.md#User) value, or a string (which will only set `name`).

| Value  | Type                                                                                                | Description                                                              |
|--------|-----------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| `name` | String                                                                                              | The displayed name of the author                                         |
| `url`  | String (optional)                                                                                   | The URL link when clicking on the author name                            |
| `icon` | String or Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) (optional) | The URL/File path/Image of the icon/avatar shown next to the author name |

### Embed field

| Value    | Type               | Description                            |
|----------|--------------------|----------------------------------------|
| `name`   | String             | The name or title of this field        |
| `value`  | String             | The value or description of this field |
| `inline` | boolean (optional) | Whether this field is inline or not    |

### Embed footer

| Value  | Type                                                                                                | Description                                          |
|--------|-----------------------------------------------------------------------------------------------------|------------------------------------------------------|
| `text` | String                                                                                              | The footer text                                      |
| `icon` | String or Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) (optional) | The URL/File path/image of the icon next to the text |

### Embed

| Value         | Type                                                                                                | Description                                                             |
|---------------|-----------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------|
| `title`       | String                                                                                              | The title of the embed                                                  |
| `url`         | String (optional)                                                                                   | The URL redirect when clicking on the embed title                       |
| `description` | String (optional)                                                                                   | Description text below the title                                        |
| `author`      | [Embed author](#Embed-author) (optional)                                                            | The author shown on top of the embed                                    |
| `fields`      | List of [Embed fields](#Embed-field) (optional)                                                     | All fields inside the embed                                             |
| `color`       | Color (optional)                                                                                    | The color of the embed                                                  |
| `footer`      | [Embed footer](#Embed-footer) (optional)                                                            | The footer shown at the bottom of the embed                             |
| `image`       | String or Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) (optional) | The URL/File path/image which will be shown in the embed                |
| `thumbnail`   | String or Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) (optional) | The URL/File path/image which will be shown as a thumbnail in the embed |
| `timestamp`   | [Timestamp](#Timestamp) (optional)                                                                  | The timestamp of the embed, which will be shown at the bottom           |

### Timestamp

Can also be parsed directly from a number, or a string `'now'`, which will parse to the current instant.

| Value          | Type   | Description                       |
|----------------|--------|-----------------------------------|
| `epoch_millis` | number | Milliseconds since the epoch time |

### Message content

Can also be parsed directly from a string (In which case only a `content` is present).

| Value              | Type                                                        | Description                                                                                          |
|--------------------|-------------------------------------------------------------|------------------------------------------------------------------------------------------------------|
| `content`          | String                                                      | Message content as a string, same thing as specifying just a string instead of a map                 |
| `attachments`      | List of [Attachments](#Attachment) (optional)               | A list of all the attachments on this message                                                        |
| `embeds`           | List of [Embeds](#Embed) (optional)                         | A list of all the embeds on this message                                                             |
| `components`       | List of List of [Message components](#Component) (optional) | Each item in this list is one row of message components, and each sub-list (row) contains Components |
| `allowed_mentions` | [Allowed mentions](#Allowed-mentions) (optional)            | Allowed mentions of this message                                                                     |
| `reply_to`         | [Message](/docs/Values.md#Message) (optional)               | Message this message is replying to                                                                  |
| `nonce`            | String (optional)                                           | Nonce of the message                                                                                 |
| `tts`              | boolean (optional)                                          | Whether this message is a text-to-speech message                                                     |

### Select menu option

| Value            | Type                             | Description                                |
|------------------|----------------------------------|--------------------------------------------|
| `value`          | String                           | The internal value of this option.         |
| `label`          | String                           | The text shown on the option               |
| `emoji`          | String or Emoji value (optional) | The emoji shown on the option              |
| `description`    | String (optional)                | A description for this option              |
| `default_option` | boolean (optional)               | Whether this is selected by default or not |

### Select menu

| Value         | Type                                               | Description                                                 |
|---------------|----------------------------------------------------|-------------------------------------------------------------|
| `component`   | String                                             | Must be `select_menu`                                       |
| `id`          | String                                             | Custom id of the button. Used to identify them when pressed |
| `options`     | List of [Select menu options](#Select-menu-option) | All options selectable on this select menu                  |
| `min`         | number (optional)                                  | The minimum number of options that need to be selected      |
| `max`         | number (optional)                                  | The maximum number of options that need to be selected      |
| `placeholder` | String (optional)                                  | The text displayed when nothing is selected yet             |

### Slash command builder

| Value         | Type                          | Description                    |
|---------------|-------------------------------|--------------------------------|
| `name`        | String                        | The slash command name         |
| `description` | String                        | The description                |
| `options`     | List of slash command options | The sub-options of the command |

### Slash command option choice

| Value   | Type   | Description                                                                    |
|---------|--------|--------------------------------------------------------------------------------|
| `name`  | String | The visible autocompleted filled in choice for the option                      |
| `value` | String | The value that will be received in the slash command event as the option value |

### Slash command option

There are two things this can do, depending on the `type`.
Either add subcommand literals, or parameters to the command. 
Sub command groups are always on the first "layer",
while subcommands are always one layer deeper than sub command groups.
Note that this is quite limited in comparison to minecraft commands.
All paths of the command tree have to have either just a sub command, or a sub command group with sub commands each.
This means that the length of the command chains (without the other options that aren't subcommands) has to be equal for all subcommands.
See: https://canary.discord.com/developers/docs/interactions/application-commands#subcommands-and-subcommand-groups

| Value         | Type                                                                            | Description                                                                                                                                                       |
|---------------|---------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `type`        | String                                                                          | The type of slash command option. Can be `sub_command`,`sub_command_group`, `string`, `integer`, `boolean`, `user`, `channel`, `role`, `mentionable` and `number` |
| `name`        | String                                                                          | The name of this option                                                                                                                                           |
| `description` | String                                                                          | The description shown for this command option                                                                                                                     |
| `required`    | boolean (optional, defaults to false)                                           | Whether this option is required to be specified                                                                                                                   |
| `options`     | List of [Slash command options](#Slash-command-option) (optional)               | Sub-options to this sub-command/group. This is only for `sub_command` or `sub_command_group`.                                                                     |
| `choices`     | List of [Slash command option choices](#Slash-command-option-choice) (optional) | Autocompletable choices for this command option                                                                                                                   |



### Webhook message profile

Used when updating the webhook profile along with sending a message

| Value    | Type              | Description                              |
|----------|-------------------|------------------------------------------|
| `name`   | String (optional) | The username of the webhook              |
| `avatar` | String (optional) | A URL to the avatar shown on the webhook |

### Webhook profile

Used when creating a webhook

| Value    | Type                                                                                                | Description                                                                     |
|----------|-----------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| `name`   | String                                                                                              | The username of the webhook                                                     |
| `avatar` | String or Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) (optional) | A URL/File path/image to the avatar shown on the webhook                        |
| `reason` | String (optional)                                                                                   | Reason shown in Audit log, only for `dc_create_webhook` and `dc_update_webhook` |

### Webhook profile updater

Used when updating a webhook profile

| Value    | Type                                                                                                | Description                                                                     |
|----------|-----------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| `name`   | String (optional)                                                                                   | The username of the webhook                                                     |
| `avatar` | String or Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) (optional) | A URL/File path/image to the avatar shown on the webhook                        |
| `reason` | String (optional)                                                                                   | Reason shown in Audit log, only for `dc_create_webhook` and `dc_update_webhook` |# Discarpet functions

Discarpet adds a lot of functions to scarpet to control your bot.
Below is a list of all functions and how they work.

## Messages

### `dc_send_message(target,content)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

This functions sends a message in a specific Discord channel, to a private message channel or a webhook.
`target` can be a Channel, User or Webhook value.
The `content` is a parsable [Message content](/docs/Parsable.md#Message-content). But if you just want text, it can be a regular string.

This example shows how you can send a message and add reactions to it as soon as it was sent

```py
task(_()->(
    message = dc_send_message(dc_channel_from_id('YOUR CHANNEL ID'),'Test message');
    dc_react(message,'🟥');
    dc_react(message,'🟩');
));
```

For more examples, see [Examples](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Examples.md)

### `dc_delete_message(message)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

Deletes the message.
Returns true if successful, otherwise false.

### `dc_react(message,emoji)`

React to a [`Message`](/docs/Values.md#message) with an `emoji`.
The `emoji` can be a unicode emoji (as a string) or an
[`emoji`](/docs/Values.md#emoji) value.

## Channels

### `dc_set_channel_topic(channel,text)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

This function sets the description of the [`channel`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#channel)
to the specified `text`. Remember to give the bot permission to do that.

### `dc_create_webhook(channel, webhookProfile)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

Creates a new Webhook in the specified `channel` with the specified options in `webhookProfile` as a [webhook profile parsable](/docs/Parsable.md#Webhook-profile).

### `dc_update_webhook(webhook, webhookProfile)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

Updates the `webhook` with the specified options in `webhookProfile` as a [webhook profile parsable](/docs/Parsable.md#Webhook-profile).

### `dc_send_webhook(webhook, content, webhookProfile)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

Sends a message to the webhook, but in contrast to `dc_send_message(webhook, content)` also allows to change the `webhookProfile` in one request.

### `dc_delete_webhook(webhook)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

Deletes the `webhook`.

## Self bot actions

### `dc_get_bot_user()`

Returns a user value of the bot itself.

### `dc_set_activity(type,text)`

Sets the activity of the bot to the specified `text` and `type`.
The `type` can be `playing`,`streaming`,`listening`,`watching`,or ~~custom~~
_(`custom` is not supported for bots from the discord api, so it has no use)_.
The `text` will appear after the type. Returns `null` if the `type` was invalid.

### `dc_set_status(status)`

Changes the status of the bot. Can be `online`,`idle`,`dnd`(Do not disturb),`invisible` and `offline`.

## Users

### `dc_get_display_name(user,server)`

Gets the nickname, or name if no nickname is present, from the [`user`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#user) in the [`server`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#server).

### `dc_set_nickname(user,server,name)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

Sets the nickname of the `user` on the `server`.#
Returns `true` if successful, false otherwise.

### `dc_add_role(user, role, reason)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

Adds a `role` to a `user`. `reason` will be shown in the audit log of your server.

### `dc_remove_role(user, role, reason)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

Removes a `role` to a `user`. `reason` will be shown in the audit log of your server.

### `dc_get_user_roles(user, server)`

Returns a list of roles the `user` has in the `server`.

### `dc_get_user_color(user, server)`

Returns the hex color of the top role of the `user` in the `server`. If the user has no role with a color, returns null.

## Interactions

### `dc_create_slash_command(commandBuilder, server?)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

Function for creating slash commands for the bot using the [Slash command builder parsable](/docs/Parsable.md#Slash-command-builder).
When specifying a `server`, the slash command will only be for that particular server.
If `server` is `null`, the slash command will be global, meaning they work in all servers the bot is in.
*NOTE:* GLOBAL slash commands can take up to 1 hour to update, so for testing,
you should only use server slash commands, which are created immediately.

Additionally, you can specify additional [Slash command options](/docs/Parsable.md#Slash-command-option) to your command.
Options are supplied in a list, with each option being a map that specifies some parameters.

For full examples of commands, see [Examples](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Examples.md#Slash-commands)

### `dc_delete_slash_command()` `dc_delete_slash_command(server)` `dc_delete_slash_command(server,name)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

Used for deleting slash commands.
Without any arguments, deletes all global and server commands of the bot.
When a server is specified, deletes all slash commands in that server, or if the server is `null`,
deletes all global slash commands. When a name is specified, deletes only the slash commands with that name.

Note that this function halts the current thread in order to ensure that the slash commands got removed,
so creating a slash command immediately after wouldn't conflict with this.

### `dc_respond_interaction(interaction,type)` `dc_respond_interaction(interaction,type,message)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

This function is used for responding to interactions.
The first parameter is any interaction (slash command, button, select menu) from its corresponding event.
Discord interactions expect a response within 3 seconds after executing it.
Either, that response is directly sending an answer,
or telling discord that the answer will come, which gives a 15-minute time to send a followup response.
The `type` can be one of three things:

* `'RESPOND_LATER'` This does not require the third `message` parameter,
and just tells discord that the interaction was received and an answer will come.
You will then need to send a RESPOND_FOLLOWUP response within 15 minutes.

* `'RESPOND_IMMEDIATELY'` This sends an immediate response which has to come within 3 seconds.
The `message` needs to be specified for this.

* `'RESPOND_FOLLOWUP'` This is used for sending a followup response within 15 minutes after the `RESPOND_LATER` response has been sent.
The `message` needs to be specified for this.

The `message` parameter the same as the [Message content](/docs/Parsable.md#Message-content) parameter in `dc_send_message`

This function returns `null` if the response could not be sent,
otherwise `true`.
Only if using `RESPOND_FOLLOWUP`,
this will return a message value with the sent message.

## Values from IDs

### `dc_channel_from_id(id)`

Returns a `channel` value from the
specified channel id, or `null` if the channel was not found.

### `dc_server_from_id(id)`

Returns a `server` value from the
specified server id, or `null` if the server was not found.

### `dc_emoji_from_id(id)`

Returns a `emoji` value from the
specified emoji id in a `server`.
This is only for custom emojis, since standard emojis are specified from the unicode emoji.

### `dc_role_from_id(id)`

Returns a `role` value from the
specified role id, or `null` if the role was not found.

### `dc_user_from_id(id)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

Returns a `user` value from the
specified role id, or `null` if the user was not found.

### `dc_message_from_id(id, channel)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

Returns a `message` value from the
specified message id and channel, or `null` if the message was not found.

### `dc_webhook_from_id(id, token)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

Returns a `webhook` value from the
specified webhook id and token, or `null` if the webhook was not found.

### `dc_webhook_from_url(url)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

Returns a `webhook` value from the
specified webhook url, or `null` if the webhook was not found.

## Utility functions

### `dc_delete(value)`

| ❗ **Note** This function is blocking, use it in a task to avoid freezing your game. |
|-------------------------------------------------------------------------------------|

Deletes whatever value provided.
Returns true or false, depending on whether the deletion was successful.

This works for:

* Emoji
* Message
* Role
* Slash command
* Webhook# Discarpet Events


Discarpet's scarpet events are used to detect events that happen in discord servers the bot is in. Additionally, there are also events for when a chat message gets sent in minecraft or a general system message happens.

# Minecraft events

## `__on_system_message(text,type)`

Event that execute on system messages, for example to be used to redirect system messages to a discord chat.

`text` -> String: Text of the system message

`type` -> String: Translation text key, or null if `text` is a literal text. For example:
   
* `chat.type.text` -> Normal chat message
* `multiplayer.player.left` -> Someone left the game
* `chat.type.admin` -> Admin command executed

# Discord events

## `__on_discord_message(message)`

Executes when a message is sent in a channel the bot has access to.

`message` -> [Message](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#message): The message that was sent

## `__on_discord_reaction(reaction,user,added)`

Executes when a user reacts to a message with some emoji

`reaction` -> [Reaction](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#reaction): The reaction that was made containing the emoji

`user` -> [User](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#user): The user who reacted

`added` -> boolean, `true` if the reaction was added, `false` if the reaction was removed

## `__on_discord_slash_command(interaction)`

Executes when a user runs a slash command

`interaction` -> [Slash command interaction](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#Slash-command-interaction): The slash command interaction containing everything about the command that was executed

## `__on_discord_button(interaction)`

Executes when a user presses a button component on a message

`interaction` -> [Button interaction](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#Button-and-Select-menu-interaction): The button interaction containing everything about the button that was pressed

## `__on_discord_select_menu(interaction)`

Executes when a user uses a select menu component on a message

`interaction` -> [Select menu interaction](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#Button-and-Select-menu-interaction): The select menu interaction containing everything about the select menu that was used# Example scripts

## Replying to messages

```py
__config() -> {'scope'->'global','bot'->'BOT'};

__on_discord_message(message) -> (
    if(message~'user'~'is_self',return()); // stop the event if the message is from the bot itself

    if(message~'content' == 'Ping',
        // use a task to not freeze the game
        task(_(outer(message)) -> (
            dc_send_message(message~'channel','Pong!');
        ));
    );
);

```

## Showing online players in bot activity

```py
__config() -> {'scope'->'global','bot'->'BOT'};

__on_tick() -> (
    if(tick_time()%(20*30) == 0, // Consider discords rate limit (only execute every 30 seconds)
        if(length(player('all')) != 0, // are players online?
            dc_set_activity('playing','with ' + join(', ',player('all'))); //display list of players
            dc_set_status('online'); // status should be online
        ,
            dc_set_activity('playing','with nobody'); // alternative text if nobody is online
            dc_set_status('idle'); // set status to idle
        );
    );
);

```

## Sending Minecraft log messages to a discord channel

```py
__config() -> {'scope'->'global','bot'->'BOT'};

global_executions = 0;
global_log = dc_channel_from_id('789877625497190440');

__on_tick() -> (
    global_executions = 0;
);

__on_system_message(text,type) -> (
    global_executions += 1; //prevent recursion
    if(global_executions < 10,
        if((type~'commands.save.') == null, //dont send 'saving world' messages
            task(_(outer(text)) -> (
                dc_send_message(global_log,text); //send to discord
            ));
        );
    );
);

```

## Simple discord command (not a slash command)

```py
__config() -> {'scope'->'global','bot'->'BOT'};

__on_discord_message(message) -> (
    if(message~'user'~'is_self',return()); // ignore messages by the bot itself

    text = message~'content';

    if(slice(text,0,1)=='!', // check for a ! indicating a command
        // split all arguments to a list
        cmd = split(' ',slice(text,1));

        if(cmd:0 == 'list', // !list command
            task(_(outer(message)) -> (
                dc_send_message(message~'channel','Currently online: ' + join(', ',player('all')));
            ));
            return();
        );

        if(cmd:0 == 'help',  // !help command
            task(_(outer(message)) -> (
                dc_send_message(message~'channel','I\'m sorry but i cant help you');
            ));
            return();
        );
    );
);

```

## If you don't trust your server members as much
```py
__config() -> {'scope'->'global','bot'->'BOT'};

global_log = dc_channel_from_id('789877625497190440');

__on_player_interacts_with_block(player, hand, block, face, hitvec) -> (
    if((['chest','barrel']~block != null) || (block~'shulker_box' != null), //warning when player opens chest/barrel/shulkerbox
        task(_(outer(player),outer(block)) -> (
            dc_send_message(global_log,str('%s opened %s at %s in %s',player,block,pos(block),current_dimension()));
        ));
    );
);
__on_player_places_block(player, item_tuple, hand, block) -> (
    if(block == 'tnt', //warning when player places tnt
        task(_(outer(player),outer(block)) -> (
            dc_send_message(global_log,str(':warning: %s placed %s at %s in %s',player,block,pos(block),current_dimension()));
        ));
    );
);

```
## Reactions as user input

```py
__config() -> {'scope'->'global','bot'->'BOT'};

global_channel = dc_channel_from_id('759102744761335891');

task(_()->(
    message = dc_send_message(global_channel,'React with 🟩 to accept or 🟥 to deny');
    global_msgid = message~'id';
    dc_react(message,'🟥');
    dc_react(message,'🟩');
));

__on_discord_reaction(reaction,user,added) -> (
    if(user~'is_self',return());
    if(reaction~'message'~'id' == global_msgid,
        action = if(added,'voted with','removed the vote for');
        task(_(outer(reaction),outer(user),outer(action))-> (
            dc_send_message(reaction~'message'~'channel',user~'name' + ' ' + action + ' ' + reaction~'emoji'~'unicode');
        ));
    );
);

```

## Embeds

```py
__config() -> {'scope'->'global','bot'->'BOT','stay_loaded'->false};

e = {
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
    'embeds'->[e]
});

```
See [`dc_build_embed()`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Functions.md#dc_build_embed-dc_build_embedpropertyvalue)

## Chat between Minecraft and Discord

```py
__config() -> {'scope'->'global','bot'->'BOT','stay_loaded'->false};

global_chat = dc_channel_from_id('789877643070799902');

global_rec = 0;

__on_tick() -> global_rec = 0;

__on_discord_message(message) -> (
    if(message~'channel'~'id'!=global_chat~'id',return()); //limit to chat channel only
    if(message~'user'~'is_self',return()); //ignore messages by the bot itself
    for(player('all'),
        col = 'd'; // this could be replaced with a custom way of fetching user color
        if(col == null,col = 'w');
        col = dc_get_user_color(message~'user',message~'server');
        if(col==null,col='#FFFFFF');
        print(_,format(str('%s [%s]',col,dc_get_display_name(message~'user',message~'server')))+format(str('w  %s',message~'readable_content')))
    );
);

__on_system_message(text,type,entity) -> (
    if(global_rec > 10,return());
    global_rec += 1;
    if(!(type~'admin'),
        if((type~'commands.save.') == null, //don't send 'saving world' messages
            msg = parse_mentions(text,global_chat~'server'); //allow for pings from inside minecraft
            task(_(outer(msg)) -> dc_send_message(global_chat,msg)); //send to discord
        );
    );
);

parse_mentions(msg,server) -> (
    for(server~'users',
        msg = replace(msg,'@' + dc_get_display_name(_,server),_~'mention_tag');
    );
    msg;
);

```

## Slash commands

```py
__config() -> {'scope'->'global','bot'->'BOT'};


initialize_commands() -> (
    //remove all commands first

    server = dc_server_from_id('689483030754099267');

    //simple ping command
    dc_create_slash_command({
        'name'->'ping',
        'description'->'Ping -> Pong!'
    },server);

    //more complex command with subcommand groups and subcommands, as well as options
    dc_create_slash_command({
        'name'->'example',
        'description'->'Test command',
        'options'->[
            {
                'type'->'SUB_COMMAND_GROUP',
                'name'->'delete',
                'description'->'Delete something',
                'options'->[
                    {
                        'type'->'SUB_COMMAND',
                        'name'->'channel',
                        'description'->'Remove something',
                        'options'->[
                            {
                                'type'->'CHANNEL',
                                'name'->'channel',
                                'description'->'What channel to delete',
                                'required'->true
                            },
                            {
                                'type'->'BOOLEAN',
                                'name'->'force',
                                'description'->'Force delete channel?',
                                'required'->false
                            }
                        ]
                    }
                ]
            },
            {
                'type'->'SUB_COMMAND_GROUP',
                'name'->'create',
                'description'->'Create something',
                'options'->[
                    {
                        'type'->'SUB_COMMAND',
                        'name'->'channel',
                        'description'->'Create a channel',
                        'options'->[
                            {
                                'type'->'STRING',
                                'name'->'name',
                                'description'->'Name of the channel',
                                'required'->true
                            },
                            {
                                'type'->'BOOLEAN',
                                'name'->'private',
                                'description'->'Is this channel private?',
                                'required'->true
                            },
                            {
                                'type'->'STRING',
                                'name'->'type',
                                'description'->'Channel type',
                                'required'->true,
                                'choices'->[
                                    {
                                        'name'->'Text',
                                        'value'->'text'
                                    },
                                    {
                                        'name'->'Voice',
                                        'value'->'voice'
                                    },
                                    {
                                        'name'->'Announcement',
                                        'value'->'announcement'
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ]
    },server);
);

// reload commands async, as that would otherwise freeze the game for multiple seconds
task('initialize_commands');



__on_discord_slash_command(cmd) -> (
    //check which command was executed
    if(cmd~'command':0 == 'ping',
        //respond immediately
        task(_(outer(cmd))->dc_respond_interaction(cmd,'RESPOND_IMMEDIATELY','Pong!'));
    , //else
        //tell discord that its gonna take a bit for the response
        dc_respond_interaction(cmd,'RESPOND_LATER');
        //respond after 5 seconds
        task(_(outer(cmd))->(
            sleep(5000);
            dc_respond_interaction(cmd,'RESPOND_FOLLOWUP',cmd~'user' + ' executed ' + cmd~'command_name' + ' with options ' + cmd~'arguments');
        ));
    );
);

```


## Attachments

```py
__config() -> {'scope'->'global','bot'->'BOT','stay_loaded'->false};

dc_send_message(dc_channel_from_id('759102744761335891'),{
    'content'->'',
    'attachments'->[
        {
            'file'->'C:/some/path/to/file.zip'
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

## Buttons and select menus

```py
__config() -> {'scope'->'global','stay_loaded'->true,'bot'->'BOT'};

global_ch = dc_channel_from_id('759102744761335891');

task(_()->(
    dc_send_message(global_ch,{
        'content'->'Example interactions',
        'components'->
        [
            [
                {
                    'component'->'button',
                    'id'->'btn1',
                    'style'->'red',
                    'label'->'Red button',
                    'emoji'->'❌'
                },
                {
                    'component'->'button',
                    'id'->'btn2',
                    'style'->'blurple',
                    'label'->'Blurple button',
                    'emoji'->'🚪'
                },
                {
                     'component'->'button',
                     'id'->'btn3',
                     'style'->'green',
                     'label'->'Green button',
                     'emoji'->'👑'
                },
                 {
                      'component'->'button',
                      'id'->'btn4',
                      'style'->'grey',
                      'label'->'Grey button',
                      'emoji'->'📧'
                 }
            ],
            [

                {
                    'component'->'select_menu',
                    'id'->'select1',
                    'placeholder'->'Select at least 2 items here',
                    'min'->2,
                    'max'->5,
                    'options'->[
                        {
                            'value'->'pizza',
                            'label'->'Pizza',
                            'description'->'I mean who doesn\'t like pizza?',
                            'emoji'->'🍕'
                        },
                        {
                            'value'->'cake',
                            'label'->'Cake',
                            'description'->'If you prefer sweet food',
                            'emoji'->'🍰'
                        },
                        {
                            'value'->'popcorn',
                            'label'->'Popcorn',
                            'description'->'Just like in the cinema',
                            'emoji'->'🍿'
                        },
                        {
                            'value'->'bread',
                            'label'->'Bread',
                            'description'->'Something simple but delicious',
                            'emoji'->'🍞'
                        },
                        {
                            'value'->'carrot',
                            'label'->'Carrot',
                            'description'->'Something healthy',
                            'emoji'->'🥕'
                        }
                    ]
                }
            ]
        ]
    });

    //doing this in a seperate message, since there is a bug in javacord that breaks events from regular buttons if there is a url button in the same message
    dc_send_message(global_ch,{
        'content'->'My github:',
        'components'-> [[
            {
                //leaving 'id' out since its a url button
                'component'->'button',
                'style'->'url',
                'label'->'Open replaceitem\'s github',
                'emoji'->'🌐',
                'url'->'https://github.com/replaceitem'
            }
        ]],

    });


));

__on_discord_button(int) -> (
    task(_(outer(int)) -> dc_respond_interaction(int,'respond_immediately','Pressed button ' + int~'id'));
);

__on_discord_select_menu(int) -> (
    task(_(outer(int)) -> dc_respond_interaction(int,'respond_immediately','Selected ' + str(int~'chosen')));
);

```