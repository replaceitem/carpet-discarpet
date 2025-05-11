`presence_updater`

Used for updating the bot's activity and status with [`dc_update_presence`](/functions/update-presence.md).


### {map:}

|                  Key | Type   | Description                                                                                                                             |
|---------------------:|:-------|:----------------------------------------------------------------------------------------------------------------------------------------|
|        `status` {:?} | String | If present, the [status type](#status-types) to set for the bot.                                                                        |
| `activity_type` {:?} | String | If present, the [activity type](#activity-types) to set for the bot. Defaults to `playing`.                                             |
| `activity_name` {:?} | String | If present, the name to show in the activity text.                                                                                      |
|  `activity_url` {:?} | String | If present, the url of the activity. Only used for the `streaming` type and opened when clicking a "watch" button on the bot's profile. |

#### Status types

|           String | Description                                  |
|-----------------:|:---------------------------------------------|
|         `online` | Shows up as a green circle on the bot.       |
|           `idle` | Shows up as a yellow moon on the bot.        |
| `do_not_disturb` | Shows up as a red circle on the bot.         |
|      `invisible` | Shows up just like `offline` to other users. |
|        `offline` | Shows up as a gray hollow circle on the bot. |

#### Activity types

|      String | Description                                                                                     |
|------------:|:------------------------------------------------------------------------------------------------|
|   `playing` | Shows up as "Playing \[`activity_name`\]"                                                       |
| `streaming` | Shows up as "Streaming \[`activity_name`\]" and shows a "Watch" button with the `activity_url`. |
| `listening` | Shows up as "Listening \[`activity_name`\]"                                                     |
|  `watching` | Shows up as "Watching \[`activity_name`\]"                                                      |
| `competing` | Shows up as "Competing in \[`activity_name`\]"                                                  |