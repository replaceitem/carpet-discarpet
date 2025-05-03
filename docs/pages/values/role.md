`dc_role`

Represents a server role.


### {query:}

|               Property | Type                                 | Description                                                              |
|-----------------------:|:-------------------------------------|:-------------------------------------------------------------------------|
|                 `name` | String                               | The name of the role.                                                    |
|                   `id` | String                               | The ID of the role.                                                      |
|          `mention_tag` | String                               | The tag used to mention this role.                                       |
|                `color` | String                               | The hex color of the role color.                                         |
|             `position` | Number                               | The position of this role as it is sorted in the server settings.        |
|               `server` | [Server](/values/server.md)          | The server of this role.                                                 |
|              `members` | List of [Members](/values/member.md) | All server members with the role.                                        |
|                `users` | List of [Users](/values/user.md)     | All users with the role.                                                 |
| `displayed_separately` | Boolean                              | Whether if users with this role are shown separately on the member list. |
|     `is_everyone_role` | Boolean                              | Whether if this role is the @everyone role.                              |
|              `managed` | Boolean                              | Whether if this role is managed by an integration (e.g. bots).           |
|          `mentionable` | Boolean                              | Whether if this role is mentionable by users.                            |