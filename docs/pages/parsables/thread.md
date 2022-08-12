`thread`

Used for creating a thread in a channel, using [`dc_create_thread`](/functions/create-thread.md)

| Value                   | Type                                     | Description                                                                                                                          |
|-------------------------|------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| `name`                  | String                                   | Name of the thread (max 100 characters)                                                                                              |
| `message`               | [Message](/values/message.md) (optional) | The message to start this thread with                                                                                                |
| `channel`               | [Channel](/values/channel.md) (optional) | The channel in which to create the thread (only required if `message` is not provided                                                |
| `channel_type`          | String (optional)                        | The type of thread channel (see below)                                                                                               |
| `invitable`             | boolean (optional)                       | Whether non-moderators can add other non-moderators to the thread (only works when `channel_type` is set to `server_private_thread`) |
| `auto_archive_duration` | number (optional)                        | Duration in minutes until the thread gets archived without activity                                                                  |
| `slow_mode_delay`       | number (optional)                        | The slow mode time in seconds (0-21600)                                                                                              |
| `reason`                | String (optional)                        | The audit log reason                                                                                                                 |

### `channel_type`s

* `server_public_thread`
* `server_news_thread` (Only for news channels)
* `server_private_thread` (Only usable when the server is boosted)