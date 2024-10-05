`webhook_profile`

Used when creating a webhook

| Value    | Type                                                                                                    | Description                                                                              |
|----------|---------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------|
| `name`   | String                                                                                                  | The display name of the webhook.                                                         |
| `avatar` | String, or Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics)<br>(optional) | The image URL (or image file) used for the webhook's avatar.                             |
| `reason` | String<br>(optional)                                                                                    | The reason shown in audit log.<br>(Only for `dc_create_webhook` and `dc_update_webhook`) |