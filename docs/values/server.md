`dc_server`

A Discord server

Queryable:

| Property         | Type                   | Description                                                |
|------------------|------------------------|------------------------------------------------------------|
| `name`           | String                 | The name of the server                                     |
| `id`             | String                 | The ID of the server                                       |
| `users`          | List of Users          | All users in this server (this requires the member Intent) |
| `channels`       | List of Channels       | All channels in this server                                |
| `roles`          | List of Roles          | All roles in this server                                   |
| `webhooks`       | List of Webhooks       | All webhooks in this server                                |
| `slash_commands` | List of Slash commands | All slash commands in this server                          |