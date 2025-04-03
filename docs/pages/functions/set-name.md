### `dc_set_name(value, name)`

Renames the value provided.

{% include 'warning-blocking.md' %}


### {input:}

* `value` {->}
  [Channel](/values/channel.md), 
  [Emoji](/values/emoji.md),
  [Role](/values/role.md),
  [Server](/values/server.md),
  [Webhook](/values/webhook.md)
  {:} The value to rename.
* `name` {->} String
  {:} The name to use for the value.


### {output:}

#### {output values:}

* Boolean, whether if the operation was successful.

#### {output exceptions:}

* Throws an exception on failure.