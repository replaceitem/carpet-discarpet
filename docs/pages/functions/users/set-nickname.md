### `dc_set_nickname(user, server, name, reason?)`

Sets the nickname of a user in a server.

{% include 'blocking-function.md' %}


### {input:}

|     Parameter | Type                        | Description                            |
|--------------:|:----------------------------|:---------------------------------------|
|        `user` | [User](/values/user.md)     | The user to set the nickname.          |
|      `server` | [Server](/values/server.md) | The server to set the user's nickname. |
|        `name` | String                      | The name to set the user to.           |
| `reason` {:?} | String                      | The audit log reason.                  |


### {output:}

#### {output values:}

* Null, if successful.

#### {output exceptions:}

Throws an exception on failure.
* `api_exception`
* `missing_permission`