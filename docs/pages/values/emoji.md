`dc_emoji`

Represents a Unicode emoji, or a custom emoji from a server.


### {query:}

|      Property | Type         | Description                                                                 |
|--------------:|:-------------|:----------------------------------------------------------------------------|
|          `id` | Number, Null | The ID of this emoji.<br>Returns `null` if it's a custom emoji.             |
| `mention_tag` | String       | The mention tag for the emoji. Used to insert the emoji into messages.      |
|     `unicode` | String, Null | The emoji as a unicode character.<br>Returns `null` if it's a custom emoji. |
| `is_animated` | Boolean      | Whether this emoji is animated.                                             |
|  `is_unicode` | Boolean      | Whether this emoji is a unicode emoji.                                      |
|   `is_custom` | Boolean      | Whether this emoji is from a server.                                        |
|        `type` | String       | `unicode` or `custom`                                                       |