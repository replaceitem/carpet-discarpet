`dc_server`

A Discord server

#### Queryable:

| Property         | Type                                                | Description                                                |
|------------------|-----------------------------------------------------|------------------------------------------------------------|
| `name`           | String                                              | The name of the server                                     |
| `id`             | String                                              | The ID of the server                                       |
| `users`          | List of [Users](../user)                            | All users in this server (this requires the member Intent) |
| `channels`       | List of [Channels](../channel)                      | All channels in this server                                |
| `roles`          | List of [Roles](../role)                            | All roles in this server                                   |
| `webhooks`       | List of [Webhooks](../webhook)                      | All webhooks in this server                                |
| `slash_commands` | List of [Slash commands](../commands/slash-command) | All slash commands in this server                          |