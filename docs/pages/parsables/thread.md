`thread`

Used for creating a thread in a channel, using [`dc_create_thread`](/functions/create-thread.md)

| Value                   | Type                                        | Description                                                                                                                             |
|-------------------------|---------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| `name`                  | String                                      | The name of the thread<br>(max 100 characters)                                                                                          |
| `message`               | [Message](/values/message.md)<br>(optional) | The message to start this thread with                                                                                                   |
| `channel`               | [Channel](/values/channel.md)<br>(optional) | The channel in which to create the thread<br>(Only required if `message` is not provided)                                               |
| `channel_type`          | String<br>(optional)                        | The [type](/parsables/thread.md#channel-types) of thread channel                                                                        |
| `invitable`             | Boolean<br>(optional)                       | Whether non-moderators can add other non-moderators to the thread<br>(Only works when `channel_type` is set to `server_private_thread`) |
| `auto_archive_duration` | Number<br>(optional)                        | The duration in minutes until the thread gets archived without activity                                                                 |
| `slow_mode_delay`       | Number<br>(optional)                        | The slow mode time in seconds (0 - 21600)                                                                                               |
| `reason`                | String<br>(optional)                        | The audit log reason                                                                                                                    |

#### Channel types

* `server_public_thread`
* `server_news_thread` - Only for news channels
* `server_private_thread` - Only usable when the server is boosted