`allowed_mentions`

!!! note
    All of the options default to false,
    meaning that as soon as the allowed mentions are specified in the [message content](/parsables/message-content.md),
    all mentions are disabled by default.

| Value              | Type                                  | Description                                      |
|--------------------|---------------------------------------|--------------------------------------------------|
| `mention_roles`    | boolean (optional, defaults to false) | Whether roles can be mentioned                   |
| `mention_users`    | boolean (optional, defaults to false) | Whether users can be mentioned                   |
| `mention_everyone` | boolean (optional, defaults to false) | Whether `@everyone` and `@here` can be mentioned |
| `roles`            | List of Role ids (String)             | Roles that should be mentioned                   |
| `users`            | List of User ids (String)             | Users that should be mentioned                   |