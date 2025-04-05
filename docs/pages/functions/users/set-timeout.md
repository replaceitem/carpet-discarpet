### `dc_set_timeout(user, server, timestamp, reason?)`

Timeouts the user in a server until the given timestamp.

{% include 'blocking-function.md' %}


### {input:}

|     Parameter | Type                        | Description                              |
|--------------:|:----------------------------|:-----------------------------------------|
|        `user` | [User](/values/user.md)     | The user to set the timeout to.          |
|      `server` | [Server](/values/server.md) | The server to set the user's timeout to. |
|   `timestamp` | Number                      | The timestamp in unix time milliseconds. |
| `reason` {:?} | String                      | The audit log reason.                    |


### {output:}

#### {output values:}

* Null, if successful.

#### {output exceptions:}

* Throws an exception on failure.
* `bad_request`, if the given timestamp is invalid.