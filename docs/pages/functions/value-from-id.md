Functions for getting values from IDs. All functions return `null` if they cannot get the value.

### `dc_channel_from_id(id)`

Returns a [Channel](/values/channel.md) value from the specified channel ID.

### `dc_server_from_id(id)`

Returns a [Server](/values/server.md) value from the specified server ID.

### `dc_emoji_from_id(id)`

Returns an [Emoji](/values/emoji.md) value from the specified emoji ID.

This is only for custom emojis, since standard emojis are specified from the unicode emoji.

### `dc_sticker_from_id(id)`

Returns a [Sticker](/values/sticker.md) value from the specified sticker ID.

### `dc_role_from_id(id)`

Returns a [Role](/values/role.md) value from the specified role ID.

### `dc_user_from_id(id)`

{% include 'warning-blocking.md' %}

Returns a [User](/values/user.md) value from the specified role ID.

### `dc_message_from_id(id, channel)`

{% include 'warning-blocking.md' %}

Returns a [Message](/values/message.md) value from the specified message ID and channel.

### `dc_webhook_from_id(id, token)`

{% include 'warning-blocking.md' %}

Returns a [Webhook](/values/webhook.md) value from the specified webhook ID and token.

### `dc_webhook_from_url(url)`

{% include 'warning-blocking.md' %}

Returns a [Webhook](/values/webhook.md) value from the specified webhook URL.