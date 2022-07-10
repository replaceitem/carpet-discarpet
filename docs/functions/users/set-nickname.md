### `dc_set_nickname(user,server,name,reason?)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Sets the nickname of the [`user`](../../../values/user) on the [`server`](../../../values/server).
Returns `true` if successful, false otherwise.
If provided, `reason` will be shown in the audit log of your server.