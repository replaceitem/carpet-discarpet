`dc_user`

Represents a regular user, or a bot.


### {query:}

|               Property | Type                            | Description                                                                                                                                                       |
|-----------------------:|:--------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|                 `user` | [User](/values/user.md)         | The user value of this member.                                                                                                                                    |
|               `server` | [Server](/values/server.md)     | The server of the member.                                                                                                                                         |
|                `color` | String                          | The display color of the member as a hex string.                                                                                                                  |
|                `roles` | List of [Role](/values/role.md) | The roles this member has assigned.                                                                                                                               |
|             `nickname` | String or null                  | The nickname of the member, if present.                                                                                                                           |
|       `effective_name` | String                          | The display name of the member as displayed in the discord client.                                                                                                |
|        `online_status` | String                          | The [online status](#online-status-values) of the member.                                                                                                         |
|          `is_boosting` | Boolean                         | Whether the member is a server booster.                                                                                                                           |
|             `is_owner` | Boolean                         | Whether the member is the server owner.                                                                                                                           |
|           `is_pending` | Boolean                         | Whether this member hasn't passed the guild's Membership Screening requirements.                                                                                  |
|              `is_self` | Boolean                         | Whether the member is the currently logged in bot account itself.<br>Useful to prevent bots from replying to itself.                                              |
|          `timeout_end` | Number or null                  | Unix timestamp when the timeout of the member ends.                                                                                                               |
|           `avatar_url` | String or null                  | The url of the per-server avatar of the user.                                                                                                                     |
| `effective_avatar_url` | String                          | The url of the displayed avatar of the user. If the user has a per-server avatar, it is returned, otherwise the global avatar or default user avatar is returned. |

#### Online status values

* `online`
* `idle`
* `do_not_disturb`
* `invisible`
* `offline`
* `unknown`