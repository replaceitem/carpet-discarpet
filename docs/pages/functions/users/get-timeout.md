### `dc_get_timeout(user, server)`

Get the timeout of a user in a server.


### {input:}

* `user` {->} [User](/values/user.md)
  {:} The user to get the timeout from.
* `server` {->} [Server](/values/server.md)
  {:} The server to get the user's timeout from.


### {output:}

#### {output values:}

* The timeout of the user, as a Number in unix time milliseconds.