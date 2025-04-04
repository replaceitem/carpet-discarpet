`message_content`

Used to create a message's content.

Can also be parsed directly from a string (which is equivalent to only setting `content`).


### {input:}

|                         Value | Type                                                              | Description                                                                                 |
|------------------------------:|:------------------------------------------------------------------|:--------------------------------------------------------------------------------------------|
|                     `content` | String                                                            | The content of the message.                                                                 |
|            `attachments` {:?} | List of [Attachments](/parsables/attachment.md)                   | The attachments in the message.                                                             |
|               `stickers` {:?} | List of [Stickers](/values/sticker.md) or Numbers                 | The stickers in the message, specified by values or IDs.                                    |
|                 `embeds` {:?} | List of [Embeds](/parsables/embed.md)                             | The embeds in the message.                                                                  |
|             `components` {:?} | List of Lists of [Components](/parsables/components/component.md) | The rows of containers with message components.[^1]                                         |
|       `allowed_mentions` {:?} | [Allowed mentions](#allowed-mentions)                             | Allowed mentions of the message.                                                            |
|               `reply_to` {:?} | [Message](/values/message.md)                                     | The message to reply or reference to.                                                       |
|                  `nonce` {:?} | String                                                            | The nonce of the message.                                                                   |
|                    `tts` {:?} | Boolean                                                           | Whether if this message should be read by text-to-speech.                                   |
|              `ephemeral` {:?} | Boolean                                                           | Whether if this message should only be visible to the user who invoked the interaction.[^2] |
|        `suppress_embeds` {:?} | Boolean                                                           | Whether if embeds should not be included.                                                   |
| `suppress_notifications` {:?} | Boolean                                                           | Whether if this message should suppress triggering push and desktop notifications.          |



## Allowed mentions

`allowed_mentions`

Used for configuring allowed mentions.

!!! note
    All boolean values are `false` by default when allowed mentions is specified in your message content.
    <br>
    Otherwise when unspecified, they are `true` by default.


### {input:}

|                   Value | Type            | Description                                             |
|------------------------:|:----------------|:--------------------------------------------------------|
|    `mention_roles` {:?} | Boolean         | Whether if roles can be mentioned.                      |
|    `mention_users` {:?} | Boolean         | Whether if users can be mentioned.                      |
| `mention_everyone` {:?} | Boolean         | Whether if @everyone and @here can be mentioned.        |
|                 `roles` | List of Strings | Roles that should be mentioned, specified by their IDs. |
|                 `users` | List of Strings | Users that should be mentioned, specified by their IDs. |



[^1]: [Text inputs](/parsables/components/text-input.md) are not supported.
[^2]: Only for [interactions](/values/interactions/interaction.md).