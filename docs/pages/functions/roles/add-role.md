### `dc_add_role(user, role, reason?)`

{% include 'warning-blocking.md' %}

Adds a role to a user.

- `user` {->} [User](/values/user.md)
  {:} The user to add the role to.
- `role` {->} [Role](/values/role.md)
  {:} The role to add.
- `reason` {:?} {->} String
  {:} The audit log reason.