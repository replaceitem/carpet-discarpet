`thread`

Used for creating a thread in a channel.


### {map:}

|                          Key | Type    | Description                                                                                                     |
|-----------------------------:|:--------|:----------------------------------------------------------------------------------------------------------------|
|                       `name` | String  | The name of the thread<br>(max 100 characters).                                                                 |
|            `is_private` {:?} | Boolean | Whether the thread should be private (Defaults to false)                                                        |
|             `invitable` {:?} | Boolean | Whether if non-moderators can add other non-moderators to the thread.<br>(Exclusive to `server_private_thread`) |
| `auto_archive_duration` {:?} | Number  | The duration in minutes until the thread gets archived without activity.                                        |
|              `slowmode` {:?} | Number  | The slow mode time in seconds.<br>(0 - 21600)                                                                   |
|                `reason` {:?} | String  | The audit log reason.                                                                                           |
