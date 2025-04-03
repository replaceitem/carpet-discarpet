`webhook_profile`

Used for creating a webhook.

| Value         | Type                                        | Description                                                                                        |
|--------------:|---------------------------------------------|----------------------------------------------------------------------------------------------------|
| `name`        | String                                      | The display name of the webhook.                                                                   |
| `avatar` {:?} | String, or Image from [Scarpet Graphics][1] | The image URL (or image file) used for the webhook's avatar.                                       |
| `reason` {:?} | String                                      | The reason shown in audit log.<br>(Only for [`dc_create_webhook`][2] and [`dc_update_webhook`][3]) |

[1]: https://github.com/replaceitem/scarpet-graphics

[2]: /functions/webhooks/create-webhook.md
[3]: /functions/webhooks/update-webhook.md