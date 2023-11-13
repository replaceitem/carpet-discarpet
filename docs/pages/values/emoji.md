`dc_emoji`

This value stores an Emoji, which could be a normal unicode emoji, or a custom emoji from a Discord server.

#### Queryable:

| Property      | Type    | Description                                                                            |
|---------------|---------|----------------------------------------------------------------------------------------|
| `id`          | Number  | The id of this emoji (if it is a custom emoji)                                         |
| `mention_tag` | String  | Mention tag for the emoji. This can be used to put into messages to contain the emoji. |
| `unicode`     | String  | Returns the emoji as a unicode character. If it's a custom emoji, returns `null`       |
| `is_animated` | boolean | True if the emojis is animated, false if not                                           |
| `is_unicode`  | boolean | True if the emoji is a unicode emoji, false otherwise                                  |
| `is_custom`   | boolean | True if the emoji is a custom one, otherwise false                                     |