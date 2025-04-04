### `dc_set_status(status)`

Sets the status of the bot.


### {input:}

| Parameter | Type   | Description                                 |
|----------:|:-------|:--------------------------------------------|
|  `status` | String | The [type](#status-types) of status to use. |

#### Status types

|      String | Description    |
|------------:|:---------------|
|    `ONLINE` | Online         |
|      `IDLE` | Idle           |
|       `DND` | Do Not Disturb |
| `INVISIBLE` | Invisible      |
|   `OFFLINE` | Offline        |


### {output:}

#### {output values:}

* Null, if successful.