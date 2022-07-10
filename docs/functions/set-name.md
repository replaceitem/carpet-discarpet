### `dc_set_name(value, name)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Renames whatever value provided.
Returns true or false, depending on whether the operation was successful.

This works for:

* Changing the [Channel](../../values/channel) name
* Renaming an [Emoji](../../values/emoji)
* Changing a [Role](../../values/role) name
* Renaming a [Server](../../values/server)
* Changing the name of a [Webhook](../../values/webhook)