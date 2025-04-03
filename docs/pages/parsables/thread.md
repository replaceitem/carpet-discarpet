`thread`

Used for creating a thread in a channel, using [`dc_create_thread`](/functions/create-thread.md)

| Value                        | Type         | Description                                                                                                                              |
|-----------------------------:|--------------|------------------------------------------------------------------------------------------------------------------------------------------|
| `name`                       | String       | The name of the thread<br>(max 100 characters).                                                                                          |
| `message` {:?}               | [Message][1] | The message to start this thread with.<br>(Only required if `channel` is not provided).                                                  |
| `channel` {:?}               | [Channel][2] | The channel in which to create the thread<br>(Only required if `message` is not provided).                                               |
| `channel_type`               | String       | The [type][3] of thread channel.                                                                                                         |
| `invitable` {:?}             | Boolean      | Whether non-moderators can add other non-moderators to the thread<br>(Only works when `channel_type` is set to `server_private_thread`). |
| `auto_archive_duration` {:?} | Number       | The duration in minutes until the thread gets archived without activity.                                                                 |
| `slow_mode_delay` {:?}       | Number       | The slow mode time in seconds (0 - 21600).                                                                                               |
| `reason` {:?}                | String       | The audit log reason.                                                                                                                    |

#### Channel types

- `server_public_thread` - Public thread
- `server_private_thread` - Private thread
- `server_news_thread` - Thread in announcements channel

[1]: /values/message.md
[2]: /values/channel.md

[3]: /parsables/thread.md#channel-types