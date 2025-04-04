### `dc_get_user_color(user, server)`

Gets the color of a user in a server.


### {input:}

| Parameter | Type                        | Description                              |
|----------:|:----------------------------|:-----------------------------------------|
|    `user` | [User](/values/user.md)     | The user to get the color from.          |
|  `server` | [Server](/values/server.md) | The server to get the user's color from. |


### {output:}

#### {output values:}

* The color of the user, as a String.
<br>(e.g. #F1C40F)
* Null, if the user has no color from a role.