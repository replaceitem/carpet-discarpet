`allowed_mentions`

!!! note
    All options are `false` by default,
    meaning that as soon as allowed mentions are specified in the [message content](/parsables/message-content.md),
    all mentions are disabled by default.

| Value                   | Type                            | Description                                   |
|------------------------:|---------------------------------|-----------------------------------------------|
| `mention_roles` {:?}    | Boolean<br>(`false` by default) | Whether roles can be mentioned.               |
| `mention_users` {:?}    | Boolean<br>(`false` by default) | Whether users can be mentioned.               |
| `mention_everyone` {:?} | Boolean<br>(`false` by default) | Whether @everyone and @here can be mentioned. |
| `roles`                 | List of Role IDs in Strings     | Roles that should be mentioned.               |
| `users`                 | List of User IDs in Strings     | Users that should be mentioned.               |