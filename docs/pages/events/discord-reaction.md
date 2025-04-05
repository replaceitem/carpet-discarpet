### `__on_discord_reaction(reaction, user, added)`

Executes when a user reacts to a message with some emoji.


### {event inputs:}

|      Value | Type                            | Description                                      |
|-----------:|:--------------------------------|:-------------------------------------------------|
| `reaction` | [Reaction](/values/reaction.md) | The reaction that was made containing the emoji. |
|     `user` | [User](/values/user.md)         | The user who reacted.                            |
|    `added` | Boolean                         | Whether if the reaction was added or removed.    |