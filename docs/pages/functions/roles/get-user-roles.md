### `dc_get_user_roles(user, server)`

Gets all roles a user has in a server.


### {input:}

| Parameter | Type                        | Description                              |
|----------:|:----------------------------|:-----------------------------------------|
|    `user` | [User](/values/user.md)     | The user to get the roles from.          |
|  `server` | [Server](/values/server.md) | The server to get the user's roles from. |


### {output:}

#### {output values:}

* List of all [roles](/values/role.md) from the provided user.