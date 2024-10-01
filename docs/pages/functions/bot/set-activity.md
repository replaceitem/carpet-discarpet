### `dc_set_activity(type, text)`

Sets the activity of the bot to the specified `text` and `type`.

The `type` can be one of:

* `playing` - Playing [text]
* `streaming` - Streaming [text]
* `listening` - Listening to [text]
* `watching` - Watching [text]

Returns `null` if the `type` was invalid.