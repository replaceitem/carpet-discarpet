`dc_emoji`

Represents a Unicode emoji, or a custom emoji from a server.

#### Queryable:

| Property      | Type    | Description                                                                                |
|---------------|---------|--------------------------------------------------------------------------------------------|
| `id`          | Number  | The ID of this emoji.<br>Returns `null` if it's a custom emoji.                            |
| `mention_tag` | String  | The mention tag for the emoji. This can be used to put into messages to contain the emoji. |
| `unicode`     | String  | The emoji as a unicode character.<br>Returns `null` if it's a custom emoji.                |
| `is_animated` | Boolean | Whether if the emoji is animated.                                                          |
| `is_unicode`  | Boolean | Whether if the emoji is a unicode emoji.                                                   |
| `is_custom`   | Boolean | Whether if the emoji is from a server.                                                     |