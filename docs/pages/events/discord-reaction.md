### `__on_discord_reaction(reaction, member, added)`

Executes when a user reacts to a message with some emoji.


### {event inputs:}

|      Value | Type                            | Description                                      |
|-----------:|:--------------------------------|:-------------------------------------------------|
| `reaction` | [Reaction](/values/reaction.md) | The reaction that was made containing the emoji. |
|   `member` | [Member](/values/member.md)     | The member who reacted.                          |
|    `added` | Boolean                         | Whether the reaction was added or removed.       |