`webhook_profile_updater`

Used for updating a webhook's profile.


### {map:}

|           Key | Type                       | Description                                         |
|--------------:|:---------------------------|:----------------------------------------------------|
|   `name` {:?} | String                     | The display name of the webhook.                    |
| `avatar` {:?} | [File](/parsables/file.md) | The image file to be used for the webhook's avatar. |
| `reason` {:?} | String                     | The reason shown in audit log.[^1]                  |
