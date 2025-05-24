### `dc_create_thread(target,thread)`

Creates a thread channel.

{% include 'blocking-function.md' %}


### {input:}

| Parameter | Type                                                           | Description                                                                                    |
|----------:|:---------------------------------------------------------------|:-----------------------------------------------------------------------------------------------|
|  `target` | [Channel](/values/channel.md) or [Message](/values/message.md) | The channel where this thread should be created, or message with which the thread should start |
|  `thread` | [Thread parsable](/parsables/thread.md)                        | The details to use to create the thread.                                                       |


### {output:}

#### {output values:}

* The created [thread channel](/values/channel.md).

#### {output exceptions:}

Throws an exception on failure.
* `api_exception`
* `missing_permission`