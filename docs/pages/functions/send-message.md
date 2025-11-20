### `dc_send_message(target, content)`

!!! example-scripts inline end "Example scripts"
    * [Reply](/examples/reply.md)
    * [Embed](/examples/embed.md)
    * [Attachments](/examples/attachments.md)

Sends a message to a target.

{% include 'blocking-function.md' %}


### {input:}

| Parameter | Type                                                                                  | Description                            |
|----------:|:--------------------------------------------------------------------------------------|:---------------------------------------|
|  `target` | [Channel](/values/channel.md), [User](/values/user.md), [Webhook](/values/webhook.md) | The target to use to send the message. |
| `content` | [Message content object](/schemas/message-content.md)                                 | The content of the message.            |


### {output:}

#### {output values:}

* The sent [message](/values/message.md).

#### {output exceptions:}

Throws an exception on failure.

* `api_exception`
* `missing_permission`