`emoji`

Emojis can be parsed directly from an [Emoji value](/values/emoji.md) or a string.
When parsing from a string, the following formats are allowed:

* Unicode emoji string: `'😃'`
* Unicode emoji codepoint notation: `'U+1F602'`
* Unicode emoji from escape codes: `'&#92;uD83D&#92;uDE03'`
* Custom emoji: `'<:dog:123456789123456789>'`
* Animated emoji: `'<a:dance:123456789123456789>'`

It can also be parsed from a map as usual. 

### {map:}

|             Key | Type    | Description                                                |
|----------------:|:--------|:-----------------------------------------------------------|
|     `name` {:?} | String  | The name of a custom emoji. Must be provided with an `id`. |
|       `id` {:?} | String  | The id of a custom emoji.                                  |
|  `unicode` {:?} | String  | The unicode emoji as a string.                             |
| `animated` {:?} | Boolean | Whether the custom emoji is animated                       |