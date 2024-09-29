---
icon: octicons/question-16
---

Discarpet wraps around [Javacord](https://github.com/Javacord/Javacord) with new scarpet values, functions and events.
It also uses parsables as a way to define more complex things like embeds, slash commands or message components.

## Discarpet values

### Querying

Each value has things that can be queried from them.
This works similar to scarpet's entity value.
For example, in scarpet you can get an entities health using `entity~'health'`.

Discarpet works the same. For example, you can get the channel a message is
in using `message~'channel'`.
The values that can be queried from a value are listed in their documentation page.

### Type names

All type names (accessible using scarpet's `type(value)` function) of Discarpet values
are prefixed with `dc_` to easily associate them with Discarpet.
The internal type name is listed at the top of each values' documentation page.

### Getting values by their ID

Many discord entities have an ID.
You can use the [Value from ID](/functions/value-from-id.md) function to create values from their ID.

## Discarpet parsables

Many things in Discarpet can be parsed and built using maps.
These parsable values have fields which are defined from the key-value pairs of the map.
Those fields and their types are listed in the documentation of each parsable.

#### Example

As an example, a value from a parsable with these fields

| Value    | Type                                     | Description                             |
|----------|------------------------------------------|-----------------------------------------|
| `name`   | String                                   | Some name                               |
| `id`     | String                                   | The ID                                  |
| `length` | Number                                   | Number example field                    |
| `hidden` | Boolean<br>(optional, defaults to false) | Whether this example parsable is hidden |

would look like this

```sc
example = {
    'name' -> 'replaceitem',
    'id' -> '0123456789',
    'length' -> 4,
    'hidden' -> true
};
```