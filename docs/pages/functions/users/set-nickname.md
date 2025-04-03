### `dc_set_nickname(user, server, name, reason?)`

Sets the nickname of a user in a server.

{% include 'warning-blocking.md' %}


### {input:}

* `user` {->} [User](/values/user.md)
  {:} The user to set the nickname.
* `server` {->} [Server](/values/user.md)
  {:} The server to set the user's nickname.
* `name` {->} String
  {:} The name to set the user to.
* `reason` {:?} {->} String
  {:} The audit log reason.


### {output:}

#### {output values:}

* Boolean, whether if the operation was successful.

#### {output exceptions:}

* `missing_permission`
    - `50013` - You do not have "Manage Nicknames" permission, or you are setting a user that is of higher hierarchy