### `dc_set_nickname(user,server,name,reason?)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Sets the nickname of the `user` on the `server`.#
Returns `true` if successful, false otherwise.
If provided, `reason` will be shown in the audit log of your server.