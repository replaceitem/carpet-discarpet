### `dc_timeout(user, server, timestamp?, reason?)`

{% include 'warning-blocking.md' %}

With only [`user`](/values/user.md) and [`server`](/values/server.md) specified, returns the timestamp when the users' timeout will expire, or null if no timeout is active.
When a `timestamp` is specified, timeouts the [`user`](/values/user.md) until the given `timestamp`.
If provided, `reason` will be shown in the audit log of your server.