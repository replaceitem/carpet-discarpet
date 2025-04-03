`message_content`

Used to create a message's content.

Can also be parsed directly from a string (which is equivalent to only setting `content`).

| Value                         | Type                             | Description                                                                                                                                  |
|------------------------------:|----------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| `content`                     | String                           | The text content of the message. Same thing as specifying just a string instead of a map.                                                    |
| `attachments` {:?}            | List of [Attachments][1]         | The attachments in the message.                                                                                                              |
| `stickers` {:?}               | List of [Stickers][2] or Numbers | The stickers in the message.                                                                                                                 |
| `embeds` {:?}                 | List of [Embeds][3]              | The embeds in the message.                                                                                                                   |
| `components` {:?}             | List of Lists of [Components][4] | Each item in the list is one row of message components, and each sub-list (row) contains components.<br>([Text inputs][7] are not supported) |
| `allowed_mentions` {:?}       | [Allowed mentions][5]            | Allowed mentions of the message.                                                                                                             |
| `reply_to` {:?}               | [Message][6]                     | The message to reply or reference to.                                                                                                        |
| `nonce` {:?}                  | String                           | The nonce of the message.                                                                                                                    |
| `tts` {:?}                    | Boolean                          | Whether if the message should be read by text-to-speech.                                                                                     |
| `ephemeral` {:?}              | Boolean                          | Whether if the message should only be visible to the user who invoked the interaction.<br>(Only for [interactions][8])                       |
| `suppress_embeds` {:?}        | Boolean                          | Whether if embeds should not be included.                                                                                                    |
| `suppress_notifications` {:?} | Boolean                          | Whether if the message sent should not trigger push and desktop notifications.                                                               |

[1]: /parsables/attachment.md
[2]: /values/sticker.md
[3]: /parsables/embed.md
[4]: /parsables/components/component.md
[5]: /parsables/allowed-mentions.md
[6]: /values/message.md

[7]: /parsables/components/text-input.md
[8]: /values/interactions/interaction.md