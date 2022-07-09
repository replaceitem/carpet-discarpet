`dc_role`

A discord role.

Queryable:

| Property               | Type          | Description                                                      |
|------------------------|---------------|------------------------------------------------------------------|
| `name`                 | String        | The name of the role                                             |
| `id`                   | String        | The id of the role                                               |
| `mention_tag`          | String        | The tag used to mention this role                                |
| `color`                | String        | Hex color string of the role color                               |
| `position`             | number        | The position of this role as it is sorted in the server settings |
| `server`               | Server        | The server of this role                                          |
| `users`                | List of Users | All users with the role                                          |
| `displayed_separately` | boolean       | Are the users with this role shown separately in the member list |
| `is_everyone_role`     | boolean       | Whether this role is the @everyone role                          |
| `managed`              | boolean       | Whether this role is managed by an integration or not            |
| `mentionable`          | boolean       | Whether this role is mentionable or not                          |