---
icon: octicons/question-16
---

Discarpet wraps around [Javacord](https://github.com/Javacord/Javacord) with new scarpet values, functions and events.
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

### Getting values by their ID

Many Discord entities have an ID.
You can use the appropriate [value from ID function](/functions/value-from-id.md) to get its value from their ID.

## Discarpet parsables

Many things in Discarpet can be parsed and built using maps.
These parsables have properties which are defined from the key-value pairs of the map.
Those properties and their types are listed in the documentation of each parsable.

As an example, a value from a parsable with these properties:

| Value    | Type                                     | Description                             |
|----------|------------------------------------------|-----------------------------------------|
| `name`   | String                                   | Some name                               |
| `id`     | String                                   | The ID                                  |
| `length` | Number                                   | Number example field                    |
| `hidden` | Boolean<br>(optional, defaults to false) | Whether this example parsable is hidden |

would look like this:

```sc
example = {
    'name' -> 'replaceitem',
    'id' -> '0123456789',
    'length' -> 4,
    'hidden' -> true
};
```