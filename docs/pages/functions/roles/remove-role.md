### `dc_remove_role(user, role, reason?)`

{% include 'warning-blocking.md' %}

Removes a [`role`](/values/role.md) to a [`user`](/values/user.md).

If provided, `reason` will be shown in the audit log of your server.

Throws an exception on failure