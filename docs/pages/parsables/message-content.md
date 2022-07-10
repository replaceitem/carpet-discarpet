`message_content`

Can also be parsed directly from a string (In which case only a `content` is present).

| Value              | Type                                                                     | Description                                                                                                                          |
|--------------------|--------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| `content`          | String                                                                   | Message content as a string, same thing as specifying just a string instead of a map                                                 |
| `attachments`      | List of [Attachments](../attachment) (optional)                          | A list of all the attachments on this message                                                                                        |
| `embeds`           | List of [Embeds](../embeds/embed) (optional)                             | A list of all the embeds on this message                                                                                             |
| `components`       | List of List of [Message components](../components/component) (optional) | Each item in this list is one row of message components, and each sub-list (row) contains Components (Text inputs are not supported) |
| `allowed_mentions` | [Allowed mentions](../allowed-mentions) (optional)                       | Allowed mentions of this message                                                                                                     |
| `reply_to`         | [Message](../values/message) (optional)                               | Message this message is replying to                                                                                                  |
| `nonce`            | String (optional)                                                        | Nonce of the message                                                                                                                 |
| `tts`              | boolean (optional)                                                       | Whether this message is a text-to-speech message                                                                                     |
| `ephemeral`        | boolean (optional)                                                       | (only for interactions) When true, this message will only be visible to the user who invoked the interaction                         |
| `suppress_embeds`  | boolean (optional)                                                       | (only for interactions) When true, embeds will not be included                                                                       |