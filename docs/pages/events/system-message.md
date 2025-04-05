### `__on_system_message(text, type)`

Executes when a system message is sent, such as server chat and operator messages.


### {event inputs:}

|  Value | Type           | Description                                                                          |
|-------:|:---------------|:-------------------------------------------------------------------------------------|
| `text` | Formatted text | The text of the system message.                                                      |
| `type` | String, Null   | The type of the translation text key.<br>Returns `null` if `text` is a literal text. |

To extract components from `text` for further processing, you can get them by using `decode_json(encode_json(text))`

You can look up the types you can get from `type` with any lang file (e.g. `en_us.json`) from Minecraft's assets,
or by just printing it out and testing various chat message types.

Here are some example types:

|                      Type | Description            |
|--------------------------:|:-----------------------|
|          `chat.type.text` | Normal chat message    |
| `multiplayer.player.left` | Player left the game   |
|         `chat.type.admin` | Admin command executed |