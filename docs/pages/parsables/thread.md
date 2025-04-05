`thread`

Used for creating a thread in a channel.


### {map:}

|                          Key | Type                          | Description                                                                                                     |
|-----------------------------:|:------------------------------|:----------------------------------------------------------------------------------------------------------------|
|                       `name` | String                        | The name of the thread<br>(max 100 characters).                                                                 |
|               `message` {:?} | [Message](/values/message.md) | The message to start this thread with.<br>(Only required if `channel` is not provided)                          |
|               `channel` {:?} | [Channel](/values/channel.md) | The channel in which to create the thread.<br>(Only required if `message` is not provided)                      |
|               `channel_type` | String                        | The [type](/parsables/thread.md#channel-types) of thread channel.                                               |
|             `invitable` {:?} | Boolean                       | Whether if non-moderators can add other non-moderators to the thread.<br>(Exclusive to `server_private_thread`) |
| `auto_archive_duration` {:?} | Number                        | The duration in minutes until the thread gets archived without activity.                                        |
|       `slow_mode_delay` {:?} | Number                        | The slow mode time in seconds.<br>(0 - 21600)                                                                   |
|                `reason` {:?} | String                        | The audit log reason.                                                                                           |

#### Channel types

|                  String | Description                     |
|------------------------:|:--------------------------------|
|  `SERVER_PUBLIC_THREAD` | Public thread                   |
| `SERVER_PRIVATE_THREAD` | Private thread                  |
|    `SERVER_NEWS_THREAD` | Thread in announcements channel |