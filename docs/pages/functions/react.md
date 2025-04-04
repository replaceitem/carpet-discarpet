### `dc_react(message, emoji)`

Reacts to a message with an emoji.


### {input:}

| Parameter | Type                              | Description                                                             |
|----------:|:----------------------------------|:------------------------------------------------------------------------|
| `message` | [Message](/values/message.md)     | The message to react to.                                                |
|   `emoji` | [Emoji](/values/emoji.md), String | The emoji to use for the reaction.<br>Can be a unicode emoji (e.g. 🎮️). |


### {output:}

#### {output values:}

* Null, if successful.

#### {output exceptions:}

* Throws an exception on failure.
* `missing_permissions`
    * `50013` - The channel has "Add Reactions" disabled.