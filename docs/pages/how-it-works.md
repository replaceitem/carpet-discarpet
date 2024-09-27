Discarpet adds new scarpet values, functions and events.
It also introduces parsables as a way to define more complex things like embeds, slash commands or message components.

## Discarpet values

### Querying

Each value has things that can be queried from them.
This works similar to scarpets' entity value.
For example, in scarpet you can get an entities health using `entity~'health'`.

Discarpet works the same. For example, you can get the channel a message is
in using `message~'channel'`.
The values that can be queried from a value are listed in their documentation page.

### Type names

All type names (accessible using scarpets `type(value)` function) of discarpet values
are prefixed with `dc_` to easily associate them with discarpet.
The internal type name is listed at the top of each values' documentation page.

### Getting values by their id

Many discord entities have an id.
You can use the [Value from id](/functions/value-from-id.md) function to create values from their id.

## Discarpet parsables

Many things in discarpet can be parsed and built from a map value.
These parsable values have fields which are defined from the key-value pairs of the map.
Those fields and their types are listed in the documentation of each parsable.

#### Example

As an example, a value from a parsable with these fields

| Value    | Type                                     | Description                             |
|----------|------------------------------------------|-----------------------------------------|
| `name`   | String                                   | Some name                               |
| `id`     | String                                   | The id                                  |
| `length` | Number                                   | Number example field                    |
| `hidden` | Boolean<br>(optional, defaults to false) | Whether this example parsable is hidden |

would look like this

```sc
example = {
  'name'->'replaceitem',
  'id'->'0123456789',
  'length'->4,
  'hidden'->true
};
```