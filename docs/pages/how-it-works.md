---
icon: octicons/question-16
---


Discarpet wraps around [Javacord](https://github.com/Javacord/Javacord) with new scarpet functions, values, and events.
It also uses parsables as a way to define more complex things like embeds, slash commands, or message components.


## Discarpet values

### Querying values

Each value has things that can be queried from them.
This works similar to scarpet's entity value.
For example, in scarpet you can get an entities health using `entity~'health'`.

Discarpet works the same. For example, you can get the channel a message is
in using `message~'channel'`. Properties that can be queried from a value are listed in their documentation page.

### Type names

All type names (accessible using scarpet's `type()` function) of Discarpet values
are prefixed with `dc_` to easily associate them with Discarpet.
The internal type name of a value is listed in their documentation.

### Getting values

You can use the appropriate function under ["Value functions"](/functions/values/channel-from-id.md) to get its value.


## Discarpet parsables

Many things in Discarpet can be parsed and built using maps.
These parsables have properties which are defined from the key-value pairs of the map.
Those properties and their types are listed in the documentation of each parsable.

As an example, a value from a parsable with these properties:

| Value    | Type    | Description                                            |
|---------:|---------|--------------------------------------------------------|
| `name`   | String  | The name of the player.                                |
| `id`     | String  | The ID of the player.                                  |
| `size`   | Number  | The size of the player.                                |
| `hidden` | Boolean | Whether this player is hidden.<br>(`false` by default) |

would look like this:

```sc
example = {
    'name' -> 'replaceitem',
    'id' -> '0123456789',
    'size' -> 4,
    'hidden' -> true
};
```


## Discarpet exceptions

Discarpet adds exception types that can be caught using scarpet's `try()` function.
The exception hierarchy is as follows:

- `exception`
<br> (Base scarpet exception)
    - `discord_exception`
    <br> (Base Discarpet exception)
        - `missing_intent`
        {:} You do not have the intent to do that
        - `api_exception`
        <br> (General exception for requests to the Discord API)
            - `missing_permission`
            {:} You do not have the permission to do that
            - `rate_limit`
            {:} You sent too many requests within a short timespan
            - `bad_request`
            {:} You sent invalid data

All exceptions Discarpet uses have `discord_exception` as the base exception type.

All exceptions that were returned from the Discord API will be an `api_exception`, which also have additional information in them you can query.

`rate_limit` exceptions are rare, since Javacord will queue requests to avoid rate limits. If far too many requests are sent, you might hit the limit anyway.

### Example

The exception value can be accessed like this:

```sc title="Getting exception details"
test() -> (
    dc_send_message(global_channel, '');
);

try(test(), 'discord_exception', print(_));
```

### Format

Here's an example of what the exception format looks like:

```sc title="Example exception value"
{
    'code' -> 403,
    'body' -> {
        'code' -> 50013,
        'message' -> 'Missing Permissions'
    }
}
```

#### Explanation:

- `code` - The Discord HTTP status code according to [this list][http codes].
- `body` - The contents of the exception.
    - `code` - The Discord JSON status code according to [this list][json code].
    - `message` - The error string also provided by the Discord API response.

The `body` may have additional properties you can query.

[http codes]: https://discord.com/developers/docs/topics/opcodes-and-status-codes#http
[json codes]: https://discord.com/developers/docs/topics/opcodes-and-status-codes#json