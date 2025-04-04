### `dc_message_from_id(id, channel)`

Gets a message from the specified ID and channel.

{% include 'blocking-function.md' %}


### {input:}

| Parameter | Type                          | Description                          |
|----------:|:------------------------------|:-------------------------------------|
|      `id` | String, Number                | The ID of the message.               |
| `channel` | [Channel](/values/channel.md) | The channel to get the message from. |


### {output:}

#### {output values:}

* [Message](/values/message.md)

#### {output exceptions:}

* Throws an exception on failure.