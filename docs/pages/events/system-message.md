### `__on_system_message(text,type)`

Event that execute on system messages, for example to be used to redirect system messages to a discord chat.

* `text` -> String: Text of the system message
* `type` -> String: Translation text key, or null if `text` is a literal text. For example:
    * `chat.type.text` -> Normal chat message
    * `multiplayer.player.left` -> Someone left the game
    * `chat.type.admin` -> Admin command executed