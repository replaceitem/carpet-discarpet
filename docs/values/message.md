`dc_message`

A Discord message which has been sent. Main use is in `__on_discord_message` event

Queryable:

| Property           | Type                | Description                                                                                                                                                                         |
|--------------------|---------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `content`          | String              | Get the content (text) of the message, not that things like emojis, mentions or channels will appear as their id. For the readable content, see below.                              |
| `readable_content` | String              | Get the content and replace all emojis, mentions, channels with their readable representation. Note that if a user is not cached, mentions to him may not get parsed.               |
| `id`               | String              | Get the id of the message                                                                                                                                                           |
| `channel`          | Channel             | Get the channel this message is inside                                                                                                                                              |
| `user`             | User                | Get the user that wrote this message. Note that this may fail (and return null) if the user is not cached, but if queried after the `__on_discord_message` event, it should be fine |
| `server`           | Server              | Get the server this message was written in                                                                                                                                          |
| `delete`           | boolean             | This is not actually a query, but it removes the message. Returns false if the bot does not have permission to delete the message, otherwise false                                  |
| `nonce`            | String              | The nonce of this message                                                                                                                                                           |
| `attachments`      | List of Attachments | A list of attachments on this message                                                                                                                                               |