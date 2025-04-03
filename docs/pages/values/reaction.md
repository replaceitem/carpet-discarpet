`dc_reaction`

Represents a reaction on a message.

#### Queryable:

| Property  | Type         | Description                               |
|----------:|--------------|-------------------------------------------|
| `emoji`   | [Emoji][1]   | The emoji of this reaction.               |
| `count`   | Number       | The amount of reactions with this emoji.  |
| `message` | [Message][2] | The message this reaction is attached to. |

[1]: /values/emoji.md
[2]: /values/message.md