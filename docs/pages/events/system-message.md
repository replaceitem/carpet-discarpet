### `__on_system_message(text, type)`

Executes when a system message is sent, such as server chat & operator messages.

* `text` -> Formatted text: The text of the system message.
To extract components of this text for further processing,
you can use `decode_json(encode_json(text))` to turn it into a scarpet map.
* `type` -> String: The type of the translation text key.
Returns `null` if `text` is a literal text.

You can look up the types you can get from `type` with any lang file (e.g. `en_us.json`) from Minecraft's assets,
or by just printing it out and testing various chat message types.

Here are some example types:

* `chat.type.text` - Normal chat message
* `multiplayer.player.left` - Someone left the game
* `chat.type.admin` - Admin command executed