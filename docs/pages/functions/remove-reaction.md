### `dc_remove_reaction(message, emoji?, user?)`

Removes reactions from a message.
If only a message is provided, all reactions are cleared.
If an emoji is specified, only reactions with that emoji are cleared.
Specifying a user only removes the reaction from that user with that emoji.


### {input:}

| Parameter | Type                          | Description                               |
|----------:|:------------------------------|:------------------------------------------|
| `message` | [Message](/values/message.md) | The message on which to remove reactions. |
|   `emoji` | [Emoji](/parsables/emoji.md)  | The emoji to remove reactions for.        |
|    `user` | [User](/values/user.md)       | The user to remove reactions for.         |


### {output:}

#### {output exceptions:}

* Throws an exception on failure.
* `api_exception` or `missing_permission`