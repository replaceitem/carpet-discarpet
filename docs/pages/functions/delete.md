### `dc_delete(value)`

Deletes whatever value provided.

{% include 'blocking-function.md' %}

### {input:}

| Parameter | Type                                                                                                                                                                                                                                                                                                                              | Description          |
|----------:|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------------------|
|   `value` | [Message](/values/message.md), [Role](/values/role.md), [Emoji](/values/emoji.md), [Sticker](/values/sticker.md), [Webhook](/values/webhook.md), [Slash command](/values/commands/slash-command.md), [User context menu](/values/commands/user-context-menu.md), [Message context menu](/values/commands/message-context-menu.md) | The value to delete. |


### {output:}

#### {output values:}

* Null, if successful.

#### {output exceptions:}

* Throws an exception on failure.
* `missing_permissions`
    * Message - You do not have "Manage Messages" permission.
    * Role - You do not have "Manage Roles" permission, or the role is of higher hierarchy.
    * Emoji, Sticker - You do not have "Manage Expressions" permission.
    * Webhook - You do not have "Manage Webhooks" permission.