### `dc_set_name(value, name)`

Renames the value provided.

{% include 'blocking-function.md' %}


### {input:}

| Parameter | Type                                                                                                                                                                         | Description                    |
|----------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:-------------------------------|
|   `value` | [Channel](/values/channel.md), [Role](/values/role.md), [Emoji](/values/emoji.md), [Sticker](/values/sticker.md), [Webhook](/values/webhook.md), [Server](/values/server.md) | The value to rename.           |
|    `name` | String                                                                                                                                                                       | The name to use for the value. |


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
    * Server - You do not have "Manage Server" permission.