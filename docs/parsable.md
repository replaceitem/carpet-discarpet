# Parsable values

Many discord entities don't have their own value, as they can be represented with scarpet maps, lists, and other values.

### Allowed mentions
`allowed_mentions`

Note that all of the options default to false, meaning that as soon as the allowed mentions are specified, all mentions are disabled by default.

| Value              | Type                                  | Description                                      |
|--------------------|---------------------------------------|--------------------------------------------------|
| `mention_roles`    | boolean (optional, defaults to false) | Whether roles can be mentioned                   |
| `mention_users`    | boolean (optional, defaults to false) | Whether users can be mentioned                   |
| `mention_everyone` | boolean (optional, defaults to false) | Whether `@everyone` and `@here` can be mentioned |
| `roles`            | List of Role ids (String)             | Roles that should be mentioned                   |
| `users`            | List of User ids (String)             | Users that should be mentioned                   |

### Attachment
`attachment`

An attachment can be created in different ways.
From a File, a URL or from a string which will be the raw bytes of the file.

| Value     | Type                                                                                      | Description                                  |
|-----------|-------------------------------------------------------------------------------------------|----------------------------------------------|
| `file`    | String (optional)                                                                         | File path for attached file                  |
| `url`     | String (optional)                                                                         | URL of the file to attach                    |
| `bytes`   | String (optional)                                                                         | String, which will be the file's binary data |
| `image`   | Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) (optional) | Image to be sent as an attachment            |
| `name`    | String (optional)                                                                         | File name if using `bytes`                   |
| `spoiler` | boolean (optional, defaults to false)                                                     | Whether this attachment is a spoiler         |

### Button
`button`

| Value       | Type                                                    | Description                                                                    |
|-------------|---------------------------------------------------------|--------------------------------------------------------------------------------|
| `component` | String                                                  | Must be `button`                                                               |
| `id`        | String (optional, not required for `url` style buttons) | Custom id of the button. Used to identify them when pressed                    |
| `label`     | String                                                  | The text shown on the button                                                   |
| `disabled`  | boolean (optional, defaults to false)                   | Whether the button is greyed out or pressable                                  |
| `style`     | String (optional, defaults to grey)                     | Button style. Can be `blurple`, `grey`, `green`, `red` and `url`               |
| `emoji`     | String or Emoji value (optional)                        | The emoji shown on the button                                                  |
| `url`       | String (optional, only required for `url` style)        | The URL opened when clicking the button. This is only used for the `url` style |

### Color
`color`

Can also be parsed directly from a list `[r,g,b]`, or a number (`0xRRGGBB`)

| Value | Type   | Description     |
|-------|--------|-----------------|
| `r`   | number | Red component   |
| `g`   | number | Green component |
| `b`   | number | Blue component  |

### Component
`component`

Can be either a [Button](#Button), a [Select menu](#Select menu) or a [Text input](#Text-input).
In all cases, the values from the corresponding parsables must be included.

| Value       | Type   | Description                                     |
|-------------|--------|-------------------------------------------------|
| `component` | String | Must be `button`, `select_menu` or `text_input` |

### Embed author
`embed_author`

Can also be parsed directly from a [User](/docs/values.md#User) value, or a string (which will only set `name`).

| Value  | Type                                                                                                | Description                                                              |
|--------|-----------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| `name` | String                                                                                              | The displayed name of the author                                         |
| `url`  | String (optional)                                                                                   | The URL link when clicking on the author name                            |
| `icon` | String or Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) (optional) | The URL/File path/Image of the icon/avatar shown next to the author name |

### Embed field
`embed_field`

| Value    | Type               | Description                            |
|----------|--------------------|----------------------------------------|
| `name`   | String             | The name or title of this field        |
| `value`  | String             | The value or description of this field |
| `inline` | boolean (optional) | Whether this field is inline or not    |

### Embed footer
`embed_footer`

| Value  | Type                                                                                                | Description                                          |
|--------|-----------------------------------------------------------------------------------------------------|------------------------------------------------------|
| `text` | String                                                                                              | The footer text                                      |
| `icon` | String or Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) (optional) | The URL/File path/image of the icon next to the text |

### Embed
`embed`

| Value         | Type                                                                                                | Description                                                             |
|---------------|-----------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------|
| `title`       | String                                                                                              | The title of the embed                                                  |
| `url`         | String (optional)                                                                                   | The URL redirect when clicking on the embed title                       |
| `description` | String (optional)                                                                                   | Description text below the title                                        |
| `author`      | [Embed author](#Embed-author) (optional)                                                            | The author shown on top of the embed                                    |
| `fields`      | List of [Embed fields](#Embed-field) (optional)                                                     | All fields inside the embed                                             |
| `color`       | Color (optional)                                                                                    | The color of the embed                                                  |
| `footer`      | [Embed footer](#Embed-footer) (optional)                                                            | The footer shown at the bottom of the embed                             |
| `image`       | String or Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) (optional) | The URL/File path/image which will be shown in the embed                |
| `thumbnail`   | String or Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) (optional) | The URL/File path/image which will be shown as a thumbnail in the embed |
| `timestamp`   | [Timestamp](#Timestamp) (optional)                                                                  | The timestamp of the embed, which will be shown at the bottom           |

### Message content
`message_content`

Can also be parsed directly from a string (In which case only a `content` is present).

| Value              | Type                                                        | Description                                                                                                                          |
|--------------------|-------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| `content`          | String                                                      | Message content as a string, same thing as specifying just a string instead of a map                                                 |
| `attachments`      | List of [Attachments](#Attachment) (optional)               | A list of all the attachments on this message                                                                                        |
| `embeds`           | List of [Embeds](#Embed) (optional)                         | A list of all the embeds on this message                                                                                             |
| `components`       | List of List of [Message components](#Component) (optional) | Each item in this list is one row of message components, and each sub-list (row) contains Components (Text inputs are not supported) |
| `allowed_mentions` | [Allowed mentions](#Allowed-mentions) (optional)            | Allowed mentions of this message                                                                                                     |
| `reply_to`         | [Message](/docs/values.md#Message) (optional)               | Message this message is replying to                                                                                                  |
| `nonce`            | String (optional)                                           | Nonce of the message                                                                                                                 |
| `tts`              | boolean (optional)                                          | Whether this message is a text-to-speech message                                                                                     |
| `ephemeral`        | boolean (optional)                                          | (only for interactions) When true, this message will only be visible to the user who invoked the interaction                         |
| `suppress_embeds`  | boolean (optional)                                          | (only for interactions) When true, embeds will not be included                                                                       |

### Message context menu builder
`message_context_menu_builder`

| Value         | Type   | Description      |
|---------------|--------|------------------|
| `name`        | String | The command name |

### Modal
`modal`

| Value              | Type                                             | Description                                                                                                                                      |
|--------------------|--------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
| `id`               | String                                           | Custom id for this modal. Used to identify when submitted                                                                                        |
| `title`            | String                                           | Title of the modal popup                                                                                                                         |
| `components`       | List of List of [Message components](#Component) | Each item in this list is one row of message components, and each sub-list (row) contains Components (Currently only [Text input](#Text-input)s) |

### Select menu option
`select_menu_option`

| Value            | Type                             | Description                                |
|------------------|----------------------------------|--------------------------------------------|
| `value`          | String                           | The internal value of this option          |
| `label`          | String                           | The text shown on the option               |
| `emoji`          | String or Emoji value (optional) | The emoji shown on the option              |
| `description`    | String (optional)                | A description for this option              |
| `default_option` | boolean (optional)               | Whether this is selected by default or not |

### Select menu
`select_menu`

| Value         | Type                                               | Description                                                 |
|---------------|----------------------------------------------------|-------------------------------------------------------------|
| `component`   | String                                             | Must be `select_menu`                                       |
| `id`          | String                                             | Custom id of the button. Used to identify them when pressed |
| `options`     | List of [Select menu options](#Select-menu-option) | All options selectable on this select menu                  |
| `min`         | number (optional)                                  | The minimum number of options that need to be selected      |
| `max`         | number (optional)                                  | The maximum number of options that need to be selected      |
| `placeholder` | String (optional)                                  | The text displayed when nothing is selected yet             |

### Slash command builder
`slash_command_builder`

| Value         | Type                          | Description                    |
|---------------|-------------------------------|--------------------------------|
| `name`        | String                        | The slash command name         |
| `description` | String                        | The description                |
| `options`     | List of slash command options | The sub-options of the command |

### Slash command option choice
`slash_command_option_choice`

| Value   | Type   | Description                                                                    |
|---------|--------|--------------------------------------------------------------------------------|
| `name`  | String | The visible autocompleted filled in choice for the option                      |
| `value` | String | The value that will be received in the slash command event as the option value |

### Slash command option
`slash_command_option`

There are two things this can do, depending on the `type`.
Either add subcommand literals, or parameters to the command. 
Sub command groups are always on the first "layer",
while subcommands are always one layer deeper than sub command groups.
Note that this is quite limited in comparison to minecraft commands.
All paths of the command tree have to have either just a sub command, or a sub command group with sub commands each.
This means that the length of the command chains (without the other options that aren't subcommands) has to be equal for all subcommands.
See: https://canary.discord.com/developers/docs/interactions/application-commands#subcommands-and-subcommand-groups

| Value         | Type                                                                            | Description                                                                                                                                                           |
|---------------|---------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `type`        | String                                                                          | The type of [slash command option](https://discord.com/developers/docs/interactions/application-commands#application-command-object-application-command-option-type). |
| `name`        | String                                                                          | The name of this option                                                                                                                                               |
| `description` | String                                                                          | The description shown for this command option                                                                                                                         |
| `required`    | boolean (optional, defaults to false)                                           | Whether this option is required to be specified                                                                                                                       |
| `options`     | List of [Slash command options](#Slash-command-option) (optional)               | Sub-options to this sub-command/group. This is only for `sub_command` or `sub_command_group`.                                                                         |
| `choices`     | List of [Slash command option choices](#Slash-command-option-choice) (optional) | Autocompletable choices for this command option                                                                                                                       |

### Text input
`text_input`

| Value         | Type               | Description                                                                                                                      |
|---------------|--------------------|----------------------------------------------------------------------------------------------------------------------------------|
| `id`          | String             | The id of the text input                                                                                                         |
| `style`       | String             | The [style](https://discord.com/developers/docs/interactions/message-components#text-inputs-text-input-styles) of the text input |
| `label`       | String             | The label of the text input                                                                                                      |
| `min_length`  | number (optional)  | The minimum length of the text in the text input                                                                                 |
| `max_length`  | number (optional)  | The maximum length of the text in the text input                                                                                 |
| `required`    | boolean (optional) | Whether this text input is required                                                                                              |
| `value`       | String (optional)  | The pre-filled value for the text input                                                                                          |
| `placeholder` | String (optional)  | The placeholder text shown if the text input is empty                                                                            |

### Timestamp
`timestamp`

Can also be parsed directly from a number, or a string `'now'`, which will parse to the current instant.

| Value          | Type   | Description                       |
|----------------|--------|-----------------------------------|
| `epoch_millis` | number | Milliseconds since the epoch time |

### User context menu builder
`user_context_menu_builder`

| Value         | Type   | Description      |
|---------------|--------|------------------|
| `name`        | String | The command name |

### Webhook message profile
`webhook_message_profile`

Used when updating the webhook profile along with sending a message

| Value    | Type              | Description                              |
|----------|-------------------|------------------------------------------|
| `name`   | String (optional) | The username of the webhook              |
| `avatar` | String (optional) | A URL to the avatar shown on the webhook |

### Webhook profile
`webhook_profile`

Used when creating a webhook

| Value    | Type                                                                                                | Description                                                                     |
|----------|-----------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| `name`   | String                                                                                              | The username of the webhook                                                     |
| `avatar` | String or Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) (optional) | A URL/File path/image to the avatar shown on the webhook                        |
| `reason` | String (optional)                                                                                   | Reason shown in Audit log, only for `dc_create_webhook` and `dc_update_webhook` |

### Webhook profile updater
`webhook_profile_updater`

Used when updating a webhook profile

| Value    | Type                                                                                                | Description                                                                     |
|----------|-----------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| `name`   | String (optional)                                                                                   | The username of the webhook                                                     |
| `avatar` | String or Image from [Scarpet Graphics](https://github.com/replaceitem/scarpet-graphics) (optional) | A URL/File path/image to the avatar shown on the webhook                        |
| `reason` | String (optional)                                                                                   | Reason shown in Audit log, only for `dc_create_webhook` and `dc_update_webhook` |