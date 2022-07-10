### `dc_add_role(user, role, reason?)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

Adds a [`role`](../../../values/role) to a [`user`](../../../values/user).

If provided, `reason` will be shown in the audit log of your server.