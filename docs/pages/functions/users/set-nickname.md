### `dc_set_nickname(user, server, name, reason?)`

{% include 'warning-blocking.md' %}

Sets the nickname of the [`user`](/values/user.md) on the [`server`](/values/server.md).
Returns `true` if successful, false otherwise.
If provided, `reason` will be shown in the audit log of your server.