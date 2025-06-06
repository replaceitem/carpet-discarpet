`message_content`

Used to create a message's content.

Can also be parsed directly from a String, which is equivalent to only setting `content`.


### {map:}

|                           Key | Type                                                                   | Description                                                                              |
|------------------------------:|:-----------------------------------------------------------------------|:-----------------------------------------------------------------------------------------|
|                `content` {:?} | String                                                                 | The content of the message.                                                              |
|            `attachments` {:?} | List of [Attachment objects](/schemas/attachment.md)                   | The attachments in the message.                                                          |
|               `stickers` {:?} | List of [Stickers](/values/sticker.md) or Numbers                      | The stickers in the message, specified by values or IDs.                                 |
|                 `embeds` {:?} | List of [Embed objects](/schemas/embed.md)                             | The embeds in the message.                                                               |
|             `components` {:?} | List of Lists of [Component objects](/schemas/components/component.md) | The rows of containers with components.[^1]                                              |
|       `allowed_mentions` {:?} | [Allowed mentions object](#allowed-mentions)                           | Allowed mentions of the message.                                                         |
|     `referenced_message` {:?} | [Message](/values/message.md)                                          | The message to reply to or forward, depending on `message_reference_type`.               |
| `message_reference_type` {:?} | String                                                                 | `default` for replying to the `referenced_message` or `forward` for forwarding it.       |
|                  `nonce` {:?} | String                                                                 | The nonce of the message.                                                                |
|                    `tts` {:?} | Boolean                                                                | Whether this message should be read by text-to-speech.                                   |
|              `ephemeral` {:?} | Boolean                                                                | Whether this message should only be visible to the user who invoked the interaction.[^2] |
|        `suppress_embeds` {:?} | Boolean                                                                | Whether embeds should not be included.                                                   |
| `suppress_notifications` {:?} | Boolean                                                                | Whether this message should suppress triggering push and desktop notifications.          |



## Allowed mentions

`allowed_mentions`

Used for configuring allowed mentions.

!!! note
    All boolean values are `false` by default when allowed mentions is specified in your message content.
    <br>
    Otherwise when unspecified, they are `true` by default.


### {map:}

|                           Key | Type            | Description                                                                                        |
|------------------------------:|:----------------|:---------------------------------------------------------------------------------------------------|
|          `mention_roles` {:?} | Boolean         | Whether roles can be mentioned. (Defaults to false)                                                |
|          `mention_users` {:?} | Boolean         | Whether users can be mentioned. (Defaults to false)                                                |
|       `mention_channels` {:?} | Boolean         | Whether channels can be mentioned. (Defaults to false)                                             |
|         `mention_emojis` {:?} | Boolean         | Whether emojis can be mentioned. (Defaults to false)                                               |
| `mention_slash_commands` {:?} | Boolean         | Whether slash commands can be mentioned. (Defaults to false)                                       |
|           `mention_here` {:?} | Boolean         | Whether @here can be mentioned. (Defaults to false)                                                |
|       `mention_everyone` {:?} | Boolean         | Whether @everyone can be mentioned. (Defaults to false)                                            |
|   `mention_replied_user` {:?} | Boolean         | Whether the author of the replied to message (`reply_to`) should be mentioned. (Defaults to false) |
|                  `roles` {:?} | List of Strings | Roles that should be mentioned, specified by their IDs.                                            |
|                  `users` {:?} | List of Strings | Users that should be mentioned, specified by their IDs.                                            |



[^1]: [Text inputs](/schemas/components/text-input.md) are not supported.
[^2]: Only for [interactions](/values/interactions/interaction.md).