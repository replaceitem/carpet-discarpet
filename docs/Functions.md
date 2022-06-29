# Discarpet functions

Discarpet adds a lot of functions to scarpet to control your bot.
Below is a list of all functions and how they work.

## Messages

### `dc_send_message(target,content)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

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

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Deletes the message.
Returns true if successful, otherwise false.

### `dc_react(message,emoji)`

React to a [`Message`](/docs/Values.md#message) with an `emoji`.
The `emoji` can be a unicode emoji (as a string) or an
[`emoji`](/docs/Values.md#emoji) value.

## Channels

### `dc_set_channel_topic(channel,text)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

This function sets the description of the [`channel`](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Values.md#channel)
to the specified `text`. Remember to give the bot permission to do that.

### `dc_create_webhook(channel, webhookProfile)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Creates a new Webhook in the specified `channel` with the specified options in `webhookProfile` as a [webhook profile parsable](/docs/Parsable.md#Webhook-profile).

### `dc_update_webhook(webhook, webhookProfile)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Updates the `webhook` with the specified options in `webhookProfile` as a [webhook profile parsable](/docs/Parsable.md#Webhook-profile).

### `dc_send_webhook(webhook, content, webhookProfile)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Sends a message to the webhook, but in contrast to `dc_send_message(webhook, content)` also allows to change the `webhookProfile` in one request.

### `dc_delete_webhook(webhook)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

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

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Sets the nickname of the `user` on the `server`.#
Returns `true` if successful, false otherwise.

### `dc_add_role(user, role, reason)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Adds a `role` to a `user`. `reason` will be shown in the audit log of your server.

### `dc_remove_role(user, role, reason)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Removes a `role` to a `user`. `reason` will be shown in the audit log of your server.

### `dc_get_user_roles(user, server)`

Returns a list of roles the `user` has in the `server`.

### `dc_get_user_color(user, server)`

Returns the hex color of the top role of the `user` in the `server`. If the user has no role with a color, returns null.

## Interactions

### `dc_create_slash_command(commandBuilder, server?)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Function for creating slash commands for the bot using the [Slash command builder parsable](/docs/Parsable.md#Slash-command-builder).
When specifying a `server`, the slash command will only be for that particular server.
If `server` is `null`, the slash command will be global, meaning they work in all servers the bot is in.
*NOTE:* GLOBAL slash commands can take up to 1 hour to update, so for testing,
you should only use server slash commands, which are created immediately.

Additionally, you can specify additional [Slash command options](/docs/Parsable.md#Slash-command-option) to your command.
Options are supplied in a list, with each option being a map that specifies some parameters.

For full examples of commands, see [Examples](https://github.com/replaceitem/carpet-discarpet/blob/master/docs/Examples.md#Slash-commands)

### `dc_delete_slash_command()` `dc_delete_slash_command(server)` `dc_delete_slash_command(server,name)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Used for deleting slash commands.
Without any arguments, deletes all global and server commands of the bot.
When a server is specified, deletes all slash commands in that server, or if the server is `null`,
deletes all global slash commands. When a name is specified, deletes only the slash commands with that name.

Note that this function halts the current thread in order to ensure that the slash commands got removed,
so creating a slash command immediately after wouldn't conflict with this.

### `dc_respond_interaction(interaction,type)` `dc_respond_interaction(interaction,type,message)` `dc_respond_interaction(interaction,type,modal)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

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
The `message` needs to be specified as the third parameter for this.

* `'RESPOND_FOLLOWUP'` This is used for sending a followup response within 15 minutes after the `RESPOND_LATER` response has been sent.
The `message` needs to be specified as the third parameter for this.

* `'RESPOND_MODAL'` Opens a modal for the user. Requires a [Modal](/docs/Parsable.md#Modal) as the third parameter.

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

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Returns a `user` value from the
specified role id, or `null` if the user was not found.

### `dc_message_from_id(id, channel)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Returns a `message` value from the
specified message id and channel, or `null` if the message was not found.

### `dc_webhook_from_id(id, token)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Returns a `webhook` value from the
specified webhook id and token, or `null` if the webhook was not found.

### `dc_webhook_from_url(url)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Returns a `webhook` value from the
specified webhook url, or `null` if the webhook was not found.

## Utility functions

### `dc_delete(value)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Deletes whatever value provided.
Returns true or false, depending on whether the deletion was successful.

This works for:

* Emoji
* Message
* Role
* Slash command
* Webhook

### `dc_set_name(value, name)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Renames whatever value provided.
Returns true or false, depending on whether the operation was successful.

This works for:

* Channel
* Emoji
* Role
* Server
* Webhook