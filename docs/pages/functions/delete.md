### `dc_delete(value)`

Deletes whatever value provided.

{% include 'warning-blocking.md' %}

### {input:}

* `value` {->}
  [Message](/values/message.md),
  [Webhook](/values/webhook.md),
  [Role](/values/role.md),
  [Emoji](/values/emoji.md),
  [Slash command](/values/commands/slash-command.md),
  [User context menu](/values/commands/user-context-menu.md),
  [Message context menu](/values/commands/message-context-menu.md)
  {:} The value to delete.

### {output:}

#### {output values:}

* Boolean, whether if the operation was successful.

#### {output exceptions:}

* Throws an exception on failure.