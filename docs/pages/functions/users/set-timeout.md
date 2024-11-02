### `dc_set_timeout(user, server, timestamp, reason?)`

{% include 'warning-blocking.md' %}

Timeouts the [`user`](/values/user.md) in a [`server`](/values/server.md) until the given `timestamp` given in unix time milliseconds.
If provided, `reason` will be shown in the audit log of your server.