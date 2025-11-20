### `dc_update_channel(channel, channel_updater)`

Updates a channel. Only works for server channels.

{% include 'blocking-function.md' %}


### {input:}

|         Parameter | Type                                                  | Description            |
|------------------:|:------------------------------------------------------|:-----------------------|
|         `channel` | [Channel](/values/channel.md)                         | The channel to update. |
| `channel_updater` | [Channel updater object](/schemas/channel-updater.md) | The data to update.    |


### {output:}

#### {output values:}

* Returns `null`.

#### {output exceptions:}

Throws an exception on failure.

* `api_exception`
* `missing_permission`