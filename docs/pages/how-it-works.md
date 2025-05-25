---
icon: octicons/question-16
---


Discarpet wraps around [Javacord](https://github.com/Javacord/Javacord) with new scarpet functions, values, and events.
It also uses map values with predefined schemas as a way to define more complex things like
embeds, slash commands, or message components.



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



## Discarpet object schemas

Many functions in Discarpet have parameters that use scarpet map values.
This works like objects in JavaScripts.
Discarpet defines schemas for these objects.

As an example, here is how you would send a discord message with text and an embed.

```sc
dc_send_message(channel, {
    'content' -> 'Hello world!',
    'embeds' -> [
        {
            'title' -> 'Example embed',
            'description' -> 'This is an example embed',
        }
    ],
});
```

The properties of those schemas and their types are listed in the documentation of each object schema.
This example uses the [Message content schema](/schemas/message-content.md) and [Embed schema](/schemas/embed.md).

As an example, a schema with these properties:

|      Key | Type    | Description                                              |
|---------:|:--------|:---------------------------------------------------------|
|   `name` | String  | The name of the player.                                  |
|     `id` | String  | The ID of the player.                                    |
|   `size` | Number  | The size of the player.                                  |
| `hidden` | Boolean | Whether this player is hidden.<br>(`false` by default)   |

could look like this:

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
        * `api_exception`
        <br>The discord api replied that this request has failed
        * `missing_permission`
        <br>You do not have the permission to do that
        * `rate_limit`
        <br>You sent too many requests within a short timespan[^1]
        * `http_exception`
        <br>The request failed before reaching the discord api


### Accessing exceptions

The exception value can be accessed like this:

```sc title="Getting exception details"
test() -> (
    dc_send_message(global_channel, 'hello world!');
);

try(test(), 'discord_exception', print(_));
```

#### `api_exception`

For the `api_exception` type, the exception value is a map of details about the error:

* `message` - The message for the `code`
* `code` - The Discord status code according to
  [this list](https://discord.com/developers/docs/topics/opcodes-and-status-codes#http).
* `body` - The contents of the HTTP response body directly from discord.

```sc title="Example api_exception exception value"
{
    'code' -> 400,
    'message' -> '...',
    'body' -> {
        ...
    }
}
```

#### `missing_permission`

For the `missing_permission` type, the exception value is a map of details about the error:

* `message` - The message of the exception
* `permission` - The [permission name](https://discord.com/developers/docs/topics/permissions#permissions-bitwise-permission-flags) that was missing
* `server` - The server id where the permission was missing.
* `channel` - The channel id where the permission was missing.

```sc title="Example api_exception exception value"
{
    'code' -> 400,
    'message' -> '...',
    'server' -> '012345678901234567',
    'channel' -> '123456789012345678',
}
```

## Blocking functions and properties

Some functions/properties may freeze your game temporarily when invoking/querying them.

Make sure to wrap them in a
[`task`](https://github.com/gnembon/fabric-carpet/blob/master/docs/scarpet/language/SystemFunctions.md#taskfunction--args-task_threadexecutor-function--args)
to avoid freezing your game.



[^1]: This exception is pretty rare, since Javacord will queue requests to avoid rate limits.
      <br>If far too many requests are sent, you might hit the limit anyway.