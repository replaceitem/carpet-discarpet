---
icon: octicons/question-16
---


Discarpet wraps around [Javacord](https://github.com/Javacord/Javacord) with new scarpet functions, values, and events.
It also uses parsables as a way to define more complex things like embeds, slash commands, or message components.



## Discarpet values


### Querying values

Each value has things that can be queried from them.
This works similar to scarpet's entity value.
For example, in scarpet you can get an entity's health using `entity~'health'`.

Discarpet works the same.
For example, you can get the [channel](/values/channel.md)
where a [message](/values/message.md) was sent using `message~'channel'`.
Properties that can be queried from a value are listed in their documentation page.


### Type names

All type names of Discarpet values
are prefixed with `dc_` to easily associate them with Discarpet.
You can get a value's type using scarpet's `type()` function.
The type name of a value also is listed in their documentation page.


### Getting values

You can use the appropriate function under ["Value functions"](/functions/values/channel-from-id.md)
to get the value you need.

Most value functions require you to input an ID.
[See here for how to get IDs](/setup.md#enabling-developer-mode).

Some values can also give you other values.



## Discarpet parsables

Many things in Discarpet can be parsed and built using maps.

These parsables have properties which are defined from the key-value pairs of the map.
Those properties and their types are listed in the documentation of each parsable.

As an example, a parsable with these properties:

|      Key | Type    | Description                                               |
|---------:|:--------|:----------------------------------------------------------|
|   `name` | String  | The name of the player.                                   |
|     `id` | String  | The ID of the player.                                     |
|   `size` | Number  | The size of the player.                                   |
| `hidden` | Boolean | Whether if this player is hidden.<br>(`false` by default) |

would look like this:

```sc
example = {
    'name' -> 'replaceitem',
    'id' -> '1234567890',
    'size' -> 4,
    'hidden' -> true
};
```



## Discarpet exceptions

When using a function or querying something,
it may fail for whatever reason and throw an uncaught exception.

Some document pages mention if they can throw an uncaught exception,
sometimes with additional details and a brief explanation as to why it happened.

All Discarpet exceptions can be caught under `discord_exception` using the
[`try`](https://github.com/gnembon/fabric-carpet/blob/master/docs/scarpet/language/FunctionsAndControlFlow.md#tryexpr-tryexpr-user_catch_expr-tryexpr-type-catch_expr-type-catch_expr-) function.


### Exception hierarchy

* `exception`
<br>(Base scarpet exception)
    * `discord_exception`
    <br>(Base Discarpet exception)
        * `missing_intent`
        <br>You do not have the intent to do that
        * `api_exception`
        <br>(General exception for requests to the Discord API)
            * `missing_permission`
            <br>You do not have the permission to do that
            * `rate_limit`
            <br>You sent too many requests within a short timespan[^1]
            * `bad_request`
            <br>You sent data that Discord considers as invalid


### Accessing exceptions

The exception value can be accessed like this:

```sc title="Getting exception details"
test() -> (
    dc_send_message(global_channel, 'hello world!');
);

try(test(), 'discord_exception', print(_));
```

Let's pretend the message failed to send for some reason.
Here's what the exception format looks like:

```sc title="Example exception value"
{
    'code' -> 403,
    'body' -> {
        'code' -> 50013,
        'message' -> 'Missing Permissions'
    }
}
```

#### Structure:

* `code` - The Discord HTTP status code according to
  [this list](https://discord.com/developers/docs/topics/opcodes-and-status-codes#http).
* `body` - The contents of the error.
    * `code` - The Discord JSON status code according to
    [this list](https://discord.com/developers/docs/topics/opcodes-and-status-codes#json).
    * `message` - The error string provided by the response.

An `api_exception` can have additional information that you can access from the `body`.



## Blocking functions and properties

Some functions/properties may freeze your game temporarily when invoking/querying them.

Make sure to wrap them in a
[`task`](https://github.com/gnembon/fabric-carpet/blob/master/docs/scarpet/language/SystemFunctions.md#taskfunction--args-task_threadexecutor-function--args)
to avoid freezing your game.



[^1]: This exception is pretty rare, since Javacord will queue requests to avoid rate limits.
      <br>If far too many requests are sent, you might hit the limit anyway.