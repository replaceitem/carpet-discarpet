### `dc_set_channel_topic(channel, text)`

Sets the description of a channel to the specified text.

{% include 'warning-blocking.md' %}


### {input:}

* `channel` {->} [Channel](/values/channel.md)
  {:} The channel to set the topic.
* `text` {->} String
  {:} The text to set the channel to.


### {output:}

#### {output values:}

* Boolean, whether if the operation was successful.

#### {output exceptions:}

* Throws an exception on failure.