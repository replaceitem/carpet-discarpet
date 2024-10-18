### `dc_create_thread(thread)`

{% include 'warning-blocking.md' %}

Creates a thread from a message or in a channel.

The `thread` is a [`thread` parsable](/parsables/thread.md).

Returns a thread [`channel`](/values/channel.md)

Throws an exception on failure