### `dc_set_activity(type, text)`

Sets the activity of the bot.


### {input:}

* `type` {->} String
  {:} The [type](#activity-types) of activity.
* `text` {->} String
  {:} The text of the activity.

#### Activity types

* `PLAYING` - Playing [text]
* `STREAMING` - Streaming [text]
* `LISTENING` - Listening to [text]
* `WATCHING` - Watching [text]


### {output:}

#### {output values:}

* Boolean, whether if the operation was successful.