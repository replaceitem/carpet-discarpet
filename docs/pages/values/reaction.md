`dc_reaction`

A reaction on a message. Main use is in `__on_discord_reaction` event

#### Queryable:

| Property  | Type                          | Description                              |
|-----------|-------------------------------|------------------------------------------|
| `emoji`   | [Emoji](/values/emoji.md)     | The emoji of this reaction               |
| `count`   | Number                        | Amount of reactions with this emoji      |
| `message` | [Message](/values/message.md) | The message this reaction is attached to |