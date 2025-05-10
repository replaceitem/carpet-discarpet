`channel_updater`

Used to update a server channel with [`dc_update_channel`](/functions/update-channel.md).


### {map:}

|                             Key | Type                          | Description                                                                                                                 |
|--------------------------------:|:------------------------------|:----------------------------------------------------------------------------------------------------------------------------|
|                     `name` {:?} | String                        | The name of the channel.                                                                                                    |
|                    `topic` {:?} | String                        | The channel description. Only works for text or forum channels.                                                             |
|                     `nsfw` {:?} | Boolean                       | Whether the channel is marked as NSFW.                                                                                      |
|                 `position` {:?} | Number                        | The position for ordering in the server list.                                                                               |
|                   `parent` {:?} | [Channel](/values/channel.md) | The parent channel. This must be a channel category.                                                                        |
|                 `slowmode` {:?} | Number                        | The slowmode of the channel.                                                                                                |
|    `auto_archive_duration` {:?} | Number                        | The auto archive duration of the thread channel in minutes. Must be one of 60, 1440, 4320 or 10080. Only works for threads. |
|                 `archived` {:?} | Boolean                       | Whether the thread is archived.                                                                                             |
|                   `locked` {:?} | Boolean                       | Whether the thread is locked.                                                                                               |
|                `invitable` {:?} | Boolean                       | Whether the thread is invitable.                                                                                            |
|                   `pinned` {:?} | Boolean                       | Whether the thread is pinned.                                                                                               |
| `default_threads_slowmode` {:?} | Number                        | Default slowmode for threads created inside this channel.                                                                   |
|             `tag_required` {:?} | Boolean                       | Whether a tag is required for posts created in this channel. Only works for forum channels.                                 |
|       `default_sort_order` {:?} | String                        | The default sort order of posts in this channel. Can be `recent_activity` or `creation_time` Only works for forum channels. |
|         `default_reaction` {:?} | [Emoji](/parsables/emoji.md)  | The default sort order of posts in this channel. Can be `recent_activity` or `creation_time` Only works for forum channels. |
|                  `bitrate` {:?} | Number                        | The bitrate of this channel. Only works for voice channels.                                                                 |
|               `user_limit` {:?} | Number                        | The maximum number of users allowed in this channel. Only works for voice channels.                                         |