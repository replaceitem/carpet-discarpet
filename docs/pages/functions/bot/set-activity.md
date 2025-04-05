### `dc_set_activity(type, text)`

Sets the activity of the bot.


### {input:}

| Parameter | Type   | Description                              |
|----------:|:-------|:-----------------------------------------|
|    `type` | String | The [type](#activity-types) of activity. |
|    `text` | String | The text of the activity.                |

#### Activity types

|      String | Description         |
|------------:|:--------------------|
|   `PLAYING` | Playing [text]      |
| `STREAMING` | Streaming [text]    |
| `LISTENING` | Listening to [text] |
|  `WATCHING` | Watching [text]     |


### {output:}

#### {output values:}

* Null, if successful.