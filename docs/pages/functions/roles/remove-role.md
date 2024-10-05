### `dc_remove_role(user, role, reason?)`

{% include 'warning-blocking.md' %}

Removes a role from a user.

- `user` {->} [User](/values/user.md)
  {:} The user to remove a role.
- `role` {->} [Role](/values/role.md)
  {:} The role to remove.
- `reason` {:?} {->} String
  {:} The audit log reason.