### `dc_get_display_name(user, server)`

Gets a user's nickname in a server, or display name if no nickname is present.


### {input:}

| Parameter | Type                        | Description                             |
|----------:|:----------------------------|:----------------------------------------|
|    `user` | [User](/values/user.md)     | The user to get the name from.          |
|  `server` | [Server](/values/server.md) | The server to get the user's name from. |


### {output:}

#### {output values:}

* The display name of the user, as a String.