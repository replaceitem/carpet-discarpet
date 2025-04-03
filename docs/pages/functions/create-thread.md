### `dc_create_thread(thread)`

Creates a thread.

{% include 'warning-blocking.md' %}


### {input:}

* `thread` {->} [Thread parsable](/parsables/thread.md)
  {:} The details to use to create the thread.


### {output:}

#### {output values:}

* The created [thread](/values/channel.md).

#### {output exceptions:}

* `bad_request`