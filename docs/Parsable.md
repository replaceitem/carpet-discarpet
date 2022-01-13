# Parsable values

Many discord entities don't have their own value, as they can be represented with scarpet maps, lists, and other values.

### Message content

For simplicity, message content can just be a string, which is the content of the message.

For more complex messages, a map with the following values is used:

| Value | Type | Description |
|---|---|---|
| `content` | String | Message content as a string, same thing as specifying just a string instead of a map |
| `attachments` | List of [Attachments](#Attachment) (optional) | A list of all the attachments on this message |
| `embeds` | List of [Embeds](#Embed) (optional) | A list of all the embeds on this message |
| `components` | List of List of [Message components](#Message-component) (optional) | Each item in this list is one row of message components, and each sub-list (row) contains Components |

Example:

```js
{
    'content'->'I am sending you some files',
    'attachments'->[
        {
            //attachment #1 content
        },
        {
            //attachment #2 content
        }
    ],
    'embeds'->[
        {
            //embed #1 content
        }
    ],
    'components'->[
        [
            {
                // first component in row #1
            },
            {
                // second component in row #1
            }
        ],
        [
            {
                // first component in row #2
            },
            {
                // second component in row #2
            }
        ]
    ]
}
```

### Attachment

An attachment can be created in three ways.
From a File, a URL or from a string which will be the raw bytes of the file.

For a File, only a `file` value is specified:
```js
{
    'file'->'C:/some/path/to/a/file.zip'
}
```

For a URL, only a `url` value is specified:
```js
{
    'file'->'https://www.example.com/some/file/url.csv'
}
```

For a byte array, a `bytes` and `name` value is specified:
```js
{
    'bytes'->'This would be the contents of this txt file',
    'name'->'MyFile.txt'
}
```

### Embed

An embed is represented by a map with the following values:

| Value | Type | Description |
|---|---|---|
| `title` | String | The title of the embed |
| `url` | String (optional) | The URL redirect when clicking on the embed title |
| `description` | String (optional) | Description text below the title |
| `author` | [Embed author](#Embed-author) (optional) | The author shown on top of the embed |
| `field` | List of [Embed fields](#Embed-field) (optional) | All fields inside the embed |
| `color` | Color (optional) | The color of the embed |
| `footer` | [Embed footer](#Embed-footer) (optional) | The footer shown at the bottom of the embed |
| `image` | String (optional) | The URL to an image which will be shown in the embed |
| `thumbnail` | String (optional) | The URL to an image which will be shown as a thumbnail in the embed |
| `timestamp` | [Timestamp](#Timestamp) (optional) | The timestamp of the embed, which will be shown at the bottom |

### Embed author

An embed author can be specified by a User value, which will use the username and avatar of the user.
It can also be parsed from a map with the following values:

| Value | Type | Description |
|---|---|---|
| `name` | String | The displayed name of the author |
| `url` | String | The URL link when clicking on the author name |
| `icon` | String | The URL of the icon/avatar shown next to the author name |

Otherwise, the embed author will just be the string representation of the given value as a name, without URL or icon.

Example:

```js
{
    'name'->'replaceitem',
    'url'->'https://github.com/replaceitem',
    'icon'->'https://avatars3.githubusercontent.com/u/40722305?s=460&u=ae87da388b3b0aeab05edf67cef1c6f7208727d3&v=4'
}
```

### Embed field

| Value | Type | Description |
|---|---|---|
| `name` | String | The name or title of this field |
| `value` | String | The value or description of this field |
| `inline` | boolean (optional) | Whether this field is inline or not. Defaults to false |

### Color

A color can be represented by a number, which in hexadecimal representation equals 0xRRGGBB red, green and blue values.

It can also be parsed from a list with three items, a red, green and blue value: `[255,128,0]` would be yellow.

### Embed footer

The embed footer is a map with two values:

| Value | Type | Description |
|---|---|---|
| `text` | String | The footer text |
| `icon` | String | The URL of the icon next to the text |

Alternatively, the footer can be just a string, which will be the text of the footer, and no icon will be used.

### Timestamp

A timestamp can just be a numeric value with a unix (or epoch) time in milliseconds (what `unix_time()` returns).

Alternatively, a string `'now'` will be the current timestamp, which would be the same as using `unix_time()`.

Otherwise, it will be attempted to parse the provided value as a string to a timestamp. For more information, search "java Instant.parse".

### Message component

There are two types of message components. Buttons and Select Menus.
Both have an `id` to identify them in for example the interaction event.
The type of the message component is specified with the `component` value.
Buttons and select menus both have the following values:

| Value | Type | Description |
|---|---|---|
| `component` | String | The type of component. Either `button` or `select_menu` |
| `id` | String | An identifier to identify components later |
| `disabled` | boolean (optional) | Whether this component is disabled. Defaults to false |

#### Button

| Value | Type | Description |
|---|---|---|
| `style` | String | Button style. Can be `blurple`, `grey`, `green`, `red` and `url` |
| `label` | String (optional) | The text shown on the button |
| `emoji` | String or Emoji value (optional) | The emoji shown on the button |
| `url` | String (optional, only required for `url` style) | The URL opened when clicking the button. This is only used for the `url` style |

Examples:
```js
{
    'component'->'buttom',
    'id'->'delete_button',
    'style'->'red',
    'label'->'Click here to delete something',
    'emoji'->'❌'
}
```
```js
{
    'component'->'button',
    'id'->'link_to_github_button',
    'style'->'url',
    'label'->'Click here to get to my GitHub',
    'emoji'->'😀',
    'url'->'https://github.com/replaceitem'
}
```

#### Select menu

| Value | Type | Description |
|---|---|---|
| `options` | List of [Select menu options](#Select-menu-option) | All options selectable on this select menu |
| `min` | number (optional) | The minimum number of options that need to be selected |
| `max` | number (optional) | The maximum number of options that need to be selected |
| `placeholder` | String (optional) | The text displayed when nothing is selected yet |

Example:
```js
{
    'component'->'select_menu',
    'id'->'food_select_menu',
    'options'->[
        option1, //see select menu option parsable
        option2
    ],
    'min'->2,
    'max'->4,
    'placeholder'->'Select your favorite foods'
}
```

### Select menu option

| Value | Type | Description |
|---|---|---|
| `value` | String | The internal value of this option. |
| `label` | String | The text shown on the option |
| `description` | String (optional) | A description for this option |
| `emoji` | String or Emoji value (optional) | The emoji shown on the option |
| `default` | boolean (optional) | Whether this is selected by default or not |

Example:
```js
{
    'value'->'bread',
    'label'->'Bread',
    'description'->'Made out of wheat',
    'emoji'->'🍞',
    'default'->'true'
}
```

### Slash command option

There are two things this can do, depending on the `type`.
Either add subcommand literals, or parameters to the command. 
Sub command groups are always on the first "layer",
while subcommands are always one layer deeper than sub command groups.
Note that this is quite limited in comparison to minecraft commands.
All paths of the command tree have to have either just a sub command, or a sub command group with sub commands each.
This means that the length of the command chains (without the other options that aren't subcommands) has to be equal for all subcommands.
See: https://canary.discord.com/developers/docs/interactions/application-commands#subcommands-and-subcommand-groups

| Value | Type | Description |
|---|---|---|
| `type` | String | The type of slash command option. Can be `sub_command`,`sub_command_group`, `string`, `integer`, `boolean`, `user`, `channel`, `role` and `mentionable` |
| `name` | String | The name of this option |
| `description` | String | The description shown for this command option |
| `required` | boolean (optional, defaults to false) | Whether this option is required to be specified |
| `options` | List of [Slash command options](#Slash-command-option) (optional) | Sub-options to this sub-command/group. This is only for `sub_command` or `sub_command_group`. |
| `choices` | List of [Slash command choices](#Slash-command-choice) (optional) | Autocompletable choices for this command option |


### Slash command choice

| Value | Type | Description |
|---|---|---|
| `name` | String | The visible autocompleted filled in choice for the option |
| `value` | String | The value that will be received in the slash command event as the option value |