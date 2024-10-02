### `dc_set_nickname(user, server, name, reason?)`

{% include 'warning-blocking.md' %}

Sets the nickname of a user in a server.

Returns a Boolean, whether if the operation was successful.

- `user` {->} [User](/values/user.md)
  {:} The user to set the nickname.
- `server` {->} [Server](/values/user.md)
  {:} The server to set the user's nickname.
- `name` {->} String
  {:} The name to set the user to.
- `reason` {:?} {->} String
  {:} The audit log reason.