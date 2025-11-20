### `dc_member_from_user(user, server)`

Gets the member value from a user and server. If the member is cached, it is returned immediately.
Otherwise, it will be requested and this call will block. 

{% include 'blocking-function.md' %}


### {input:}

| Parameter | Type                        | Description                      |
|----------:|:----------------------------|:---------------------------------|
|    `user` | [User](/values/user.md)     | The user.                        |
|  `server` | [Server](/values/server.md) | The server in which the user is. |

### {output:}

#### {output values:}

* [Member](/values/member.md)

#### {output exceptions:}

Throws an exception on failure.

* `api_exception`
* `missing_permission`