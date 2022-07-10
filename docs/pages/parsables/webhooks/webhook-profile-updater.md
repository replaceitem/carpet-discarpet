`webhook_profile_updater`

Used when updating a webhook profile

| Value    | Type                                                                                                | Description                                                                     |
|----------|-----------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| `name`   | String (optional)                                                                                   | The username of the webhook                                                     |
| `avatar` | String or Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) (optional) | A URL/File path/image to the avatar shown on the webhook                        |
| `reason` | String (optional)                                                                                   | Reason shown in Audit log, only for `dc_create_webhook` and `dc_update_webhook` |