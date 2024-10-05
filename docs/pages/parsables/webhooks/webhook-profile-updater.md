`webhook_profile_updater`

Used when updating a webhook profile

| Value    | Type                                                                                                   | Description                                                                              |
|----------|--------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------|
| `name`   | String<br>(optional)                                                                                   | The display name of the webhook.                                                         |
| `avatar` | String or Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics)<br>(optional) | The image URL (or image file) used for the webhook's avatar.                             |
| `reason` | String<br>(optional)                                                                                   | The reason shown in audit log.<br>(Only for `dc_create_webhook` and `dc_update_webhook`) |