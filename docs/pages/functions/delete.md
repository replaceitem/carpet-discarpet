### `dc_delete(value)`

!!! example-scripts inline end "Example scripts"
    * [Context menus](/examples/context-menus.md)

Deletes whatever value provided.

{% include 'blocking-function.md' %}

### {input:}

| Parameter | Type                                                                                                                                                                                         | Description          |
|----------:|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------------------|
|   `value` | [Message](/values/message.md), [Role](/values/role.md), [Emoji](/values/emoji.md), [Sticker](/values/sticker.md), [Webhook](/values/webhook.md), [Slash command](/values/command.md)         | The value to delete. |


### {output:}

#### {output values:}

* Null, if successful.

#### {output exceptions:}

Throws an exception on failure.

* `api_exception`
* `missing_permission`
    * Message - You do not have "Manage Messages" permission.
    * Role - You do not have "Manage Roles" permission, or the role is of higher hierarchy.
    * Emoji, Sticker - You do not have "Manage Expressions" permission.
    * Webhook - You do not have "Manage Webhooks" permission.