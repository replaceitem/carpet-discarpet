`dc_role`

Represents a server role.

#### Queryable:

| Property               | Type               | Description                                                           |
|-----------------------:|--------------------|-----------------------------------------------------------------------|
| `name`                 | String             | The name of the role.                                                 |
| `id`                   | String             | The ID of the role.                                                   |
| `mention_tag`          | String             | The tag used to mention this role.                                    |
| `color`                | String             | The hex color of the role color.                                      |
| `position`             | Number             | The position of this role as it is sorted in the server settings.     |
| `server`               | [Server][1]        | The server of this role.                                              |
| `users`                | List of [Users][2] | All users with the role.                                              |
| `displayed_separately` | Boolean            | Whether users with this role are shown separately on the member list. |
| `is_everyone_role`     | Boolean            | Whether this role is the @everyone role.                              |
| `managed`              | Boolean            | Whether this role is managed by an integration.                       |
| `mentionable`          | Boolean            | Whether this role is mentionable by users.                            |

[1]: /values/server.md
[2]: /values/user.md