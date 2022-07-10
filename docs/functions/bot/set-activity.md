### `dc_set_activity(type,text)`

Sets the activity of the bot to the specified `text` and `type`.
The `type` can be `playing`, `streaming`, `listening`, `watching`, or ~~`custom`~~
_(`custom` is not supported for bots from the discord api, so it has no use)_.
The `text` will appear after the type. Returns `null` if the `type` was invalid.