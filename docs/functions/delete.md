### `dc_delete(value)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Deletes whatever value provided.
Returns true or false, depending on whether the deletion was successful.

This works for:

* [Emoji](../../values/emoji)
* [Message](../../values/message)
* [Role](../../values/role)
* [Webhook](../../values/webhook)
* [Slash command](../../values/commands/slash-command)
* [User context menu](../../values/commands/user-context-menu)
* [Message context menu](../../values/commands/message-context-menu)
