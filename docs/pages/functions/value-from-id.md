### `dc_channel_from_id(id)`

Returns a [Channel](/values/channel.md) value from the
specified channel id, or `null` if the channel was not found.

### `dc_server_from_id(id)`

Returns a [Server](/values/server.md) value from the
specified server id, or `null` if the server was not found.

### `dc_emoji_from_id(id)`

Returns an [Emoji](/values/emoji.md) value from the
specified emoji id.
This is only for custom emojis, since standard emojis are specified from the unicode emoji.

### `dc_sticker_from_id(id)`

Returns a [Sticker](/values/sticker.md) value from the
specified sticker id.

### `dc_role_from_id(id)`

Returns a [Role](/values/role.md) value from the
specified role id, or `null` if the role was not found.

### `dc_user_from_id(id)`

{% include 'warning-blocking.md' %}

Returns a [User](/values/user.md) value from the
specified role id, or `null` if the user was not found.

### `dc_message_from_id(id, channel)`

{% include 'warning-blocking.md' %}

Returns a [Message](/values/message.md) value from the
specified message id and channel, or `null` if the message was not found.

### `dc_webhook_from_id(id, token)`

{% include 'warning-blocking.md' %}

Returns a [Webhook](/values/webhook.md) value from the
specified webhook id and token, or `null` if the webhook was not found.

### `dc_webhook_from_url(url)`

{% include 'warning-blocking.md' %}

Returns a [Webhook](/values/webhook.md) value from the
specified webhook url, or `null` if the webhook was not found.