`dc_server`

A Discord server

#### Queryable:

| Property         | Type                                                        | Description                                                |
|------------------|-------------------------------------------------------------|------------------------------------------------------------|
| `name`           | String                                                      | The name of the server                                     |
| `id`             | String                                                      | The ID of the server                                       |
| `users`          | List of [Users](/values/user.md)                            | All users in this server (this requires the member Intent) |
| `channels`       | List of [Channels](/values/channel.md)                      | All channels in this server                                |
| `roles`          | List of [Roles](/values/role.md)                            | All roles in this server                                   |
| `webhooks`       | List of [Webhooks](/values/webhook.md)                      | All webhooks in this server                                |
| `slash_commands` | List of [Slash commands](/values/commands/slash-command.md) | All slash commands in this server                          |