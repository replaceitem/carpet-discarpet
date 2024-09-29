### `__on_system_message(text, type)`

Event that execute on system messages, for example to be used to redirect system messages to a discord chat.

* `text` -> Formatted text: Text of the system message
To extract components of this text for further processing,
you can use `encode_json(text)` to turn it into json text
and then `decode_json` to turn it into a scarpet value to parse it.
* `type` -> String: Translation text key, or null if `text` is a literal text.
You can look these up in the `en_us.json` lang file in the minecraft assets,
or by just printing it out and testing various chat message types.
For example:
    * `chat.type.text` -> Normal chat message
    * `multiplayer.player.left` -> Someone left the game
    * `chat.type.admin` -> Admin command executed