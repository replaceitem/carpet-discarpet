### `dc_create_thread(thread)`

Creates a thread.

{% include 'blocking-function.md' %}


### {input:}

| Parameter | Type                                    | Description                              |
|----------:|:----------------------------------------|:-----------------------------------------|
|  `thread` | [Thread parsable](/parsables/thread.md) | The details to use to create the thread. |


### {output:}

#### {output values:}

* The created [thread](/values/channel.md).

#### {output exceptions:}

* Throws an exception on failure.
* `bad_request`