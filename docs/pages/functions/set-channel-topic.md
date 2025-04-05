### `dc_set_channel_topic(channel, text)`

Sets the description of a channel to the specified text.

{% include 'blocking-function.md' %}


### {input:}

| Parameter | Type                          | Description                     |
|----------:|:------------------------------|:--------------------------------|
| `channel` | [Channel](/values/channel.md) | The channel to set the topic.   |
|    `text` | String                        | The text to set the channel to. |


### {output:}

#### {output values:}

* Null, if successful.

#### {output exceptions:}

* Throws an exception on failure.