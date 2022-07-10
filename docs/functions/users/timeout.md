### `dc_timeout(user, server, timestamp?, reason?)`

> **Warning**
> This function is blocking, use it in a task to avoid freezing your game.

With only `user` and `server` specified, returns the timestamp when the users' timeout will expire, or null if no timeout is active.
When a `timestamp` is specified, timeouts the `user` until the given `timestamp`.
If provided, `reason` will be shown in the audit log of your server.