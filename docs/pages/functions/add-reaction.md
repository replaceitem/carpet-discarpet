### `dc_add_reaction(message, emoji)`

!!! example-scripts inline end "Example scripts"
    * [Reactions](/examples/reactions.md)

Reacts to a message with an emoji.


### {input:}

| Parameter | Type                              | Description                        |
|----------:|:----------------------------------|:-----------------------------------|
| `message` | [Message](/values/message.md)     | The message to react to.           |
|   `emoji` | [Emoji object](/schemas/emoji.md) | The emoji to use for the reaction. |


### {output:}

#### {output exceptions:}

Throws an exception on failure.

* `api_exception`
* `missing_permission`