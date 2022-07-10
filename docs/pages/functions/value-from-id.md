### `dc_channel_from_id(id)`

Returns a [Channel](../values/channel) value from the
specified channel id, or `null` if the channel was not found.

### `dc_server_from_id(id)`

Returns a [Server](../values/server) value from the
specified server id, or `null` if the server was not found.

### `dc_emoji_from_id(id)`

Returns an [Emoji](../values/emoji) value from the
specified emoji id in a `server`.
This is only for custom emojis, since standard emojis are specified from the unicode emoji.

### `dc_role_from_id(id)`

Returns a [Role](../values/role) value from the
specified role id, or `null` if the role was not found.

### `dc_user_from_id(id)`

{% include 'warning-blocking.md' %}

Returns a [User](../values/user) value from the
specified role id, or `null` if the user was not found.

### `dc_message_from_id(id, channel)`

{% include 'warning-blocking.md' %}

Returns a [Message](../values/message) value from the
specified message id and channel, or `null` if the message was not found.

### `dc_webhook_from_id(id, token)`

{% include 'warning-blocking.md' %}

Returns a [Webhook](../values/webhook) value from the
specified webhook id and token, or `null` if the webhook was not found.

### `dc_webhook_from_url(url)`

{% include 'warning-blocking.md' %}

Returns a [Webhook](../values/webhook) value from the
specified webhook url, or `null` if the webhook was not found.