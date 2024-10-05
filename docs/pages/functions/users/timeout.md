### `dc_timeout(user, server, timestamp?, reason?)`

{% include 'warning-blocking.md' %}

Gets or sets a user's timeout.

With only `user` and `server` specified, returns the timestamp when the user's timeout will expire.

Returns `null` if no timeout is active.

Returns a Boolean for setting timeout, whether if the operation was successful.

- `user` {->} [User](/values/user.md)
  {:} The user to get or set the timeout.
- `server` {->} [Server](/values/server.md)
  {:} The server to get or set the user's timeout.
- `timestamp` {:?} {->} Number
  {:} The timestamp in milliseconds to set the timeout until expiration.
- `reason` {:?} {->} String
  {:} The audit log reason.