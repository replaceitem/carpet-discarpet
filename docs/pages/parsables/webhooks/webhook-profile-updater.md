`webhook_profile_updater`

Used for updating a webhook's profile.


### {map:}

|           Key | Type                                                                                     | Description                                                  |
|--------------:|:-----------------------------------------------------------------------------------------|:-------------------------------------------------------------|
|   `name` {:?} | String                                                                                   | The display name of the webhook.                             |
| `avatar` {:?} | String or Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) | The image URL (or image file) used for the webhook's avatar. |
| `reason` {:?} | String                                                                                   | The reason shown in audit log.[^1]                           |


[^1]: Only applicable for [`dc_create_webhook`](/functions/webhooks/create-webhook.md) and [`dc_update_webhook`](/functions/webhooks/update-webhook.md).