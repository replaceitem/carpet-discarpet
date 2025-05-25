Due to the [sunsetting of Javacord](https://github.com/user-attachments/assets/d5e8b4fe-60d1-4dcb-95e7-cf95933d1193),
this mod migrated to JDA, which meant that introducing breaking changes was inevitable,
so I chose to use this update to add more improvements that introduce breaking changes.

The naming convention of "parsables" in the documentation and changelogs have changed.
These map values used as function parameters are now referred to as "schemas" or "objects".
A "schema" is the format of the fields a parsable has,
while an "object" will be referred to as a map value following that schema.

## Breaking changes

The following changes may break existing scripts.
Make sure to update them to work with this new discarpet version.

### Event changes

Due to JDA having no built-in message caching, the following events were changed:

* The `__on_discord_message_edit` event now no longer provides the previous message and whether it was actually edited
* The `__on_discord_message_delete` now only gives the deleted message id and the channel object instead of the message object

### Function changes

* The channel or message for creating a thread is no longer provided in the schema (*previously "parsable"*), but as an argument to `dc_create_thread`
* `dc_react` has been split to `dc_add_reaction(msg, emoji)` and `dc_remove_reaction(msg, emoji?, user?)`
* `dc_set_channel_topic` was removed in favor of the new `dc_update_channel`
* Removed `dc_get_display_name` in favor of `member~'effective_name'`
* Removed `dc_get_timeout` in favor of `member~'timeout_end'`
* Removed `dc_get_user_color` in favor of `member~'color'`
* Removed `dc_get_user_roles` in favor of `member~'roles'`
* Added `dc_update_presence()` to replace `dc_set_status()` and `dc_set_activity()`

### Value changes

* The command value types are now all the same value type `dc_command` and can be distinguished with `command~'type'`
* Removed `user~'discriminated_name`, since discriminators are phased out by discord
* Replaced `reaction~'message'` with `reaction~'message_id'` since messages are not cached anymore
* `slash_command_interaction~'arguments'` and `slash_command_interaction~'arguments_by_name'`
  no longer include the subcommand and subcommand group.
  Additionally, `slash_command_interaction_option~'options'` was removed,
  since subcommand groups and subcommands are no longer expressed as nested options,
  but can be retrieved using `interaction~'sub_command'` or `~'sub_command_group'`.
* Replaced `~'is_subcommand_or_group'` in slash command interaction option values with `~'type'`

### Schema changes

* Replaced `thread` schema `channel_type` with `is_private`.
* Changes to message content schema
  * Renamed `reply_to` to `referenced_message` since it can now also used to forward messages
  * Added `message_reference_type` which can be set to `forward` to forward messages instead of replying
  * Made `content` field optional for messages without content, like forwarded messages
* The `file`, `url`, `bytes`, and `image` fields of the attachment schema have been moved to a `file` parsable
  * The `file` field is now no longer a system file path, but a scarpet resource path, just like it is used in scarpet's `read_file`.
  * `bytes` has been renamed to `string`
* The following fields of schemas now use the `file` schema (see above)
  * `thumbnail` and `image` in `embed`
  * `icon` in `embed_author` and `embed_footer`
  * `avatar` in `webhook_profile` and `webhook_profile_updater`

### Type strings

ALL type strings are now returned in lowercase.
Additionally, the type fields of schemas are now all case-insensitive,
so you can interchangeably use upper or lower case strings everywhere.

Most values returned by the `~'type'` queries of values are now also different:

#### `~'type'` strings

The `type`s of a channel value have changed:

* `SERVER_TEXT_CHANNEL` -> `text`
* `SERVER_VOICE_CHANNEL` -> `voice`
* `SERVER_FORUM_CHANNEL` -> `forum`
* `SERVER_STAGE_VOICE_CHANNEL` -> `stage`
* `SERVER_NEWS_CHANNEL` -> `news`
* `SERVER_STORE_CHANNEL` -> *removed*
* `SERVER_PUBLIC_THREAD` -> `guild_public_thread`
* `SERVER_PRIVATE_THREAD` -> `guild_private_thread`
* `SERVER_NEWS_THREAD` -> `guild_news_thread`
* `PRIVATE_CHANNEL` -> `private`
* `GROUP_CHANNEL` -> `group`
* `CHANNEL_CATEGORY` -> `category`
* `SERVER_DIRECTORY_CHANNEL` -> *removed*
* - -> `media`

Other changes:

* `webhook~'type'` `CHANNEL_FOLLOWER` changed to `follower`
* `sticker~'type'` `SERVER` changed to `guild`

#### Schema type strings

The `component` type in a component schema have partially different names now:

* `BUTTON` -> `button`
* `SELECT_MENU_STRING` -> `string_select`
* `SELECT_MENU_USER` -> `user_select`
* `SELECT_MENU_ROLE` -> `role_select`
* `SELECT_MENU_MENTIONABLE` -> `mentionable_select`
* `SELECT_MENU_CHANNEL` -> `channel_select`
* `TEXT_INPUT` -> `text_input`

The `style` of the button schema is now no longer the color name, but the types name as discord refers to them:

* `BLURPLE` -> `primary`
* `GREY` -> `secondary`
* `GREEN` -> `success`
* `RED` -> `danger`
* `URL` -> `link`

### Other breaking changes

* Exception hierarchy and values have changed

## New features

* Added `slash_command_interaction~'subcommand'` and `slash_command_interaction~'subcommand_group'`
* Added `channel_types` to select menu schema
* Added `mention_channels`, `mention_emojis`, `mention_slash_commands`, `mention_here` fields to allowed mentions schema
* Added `mention_replied_user` to allowed mentions
* Emoji fields in schemas now support many more string notations
* Added `dc_remove_reaction(msg, emoji?, user?)`
* Added `message~'stripped_content'`
* Added `message~'stickers'`
* Added `emoji~'type'`
* Added `reaction~'channel'` and `reaction~'server'`
* Added `respond_later_data` schema to third parameter of `dc_respond_interaction` when using type `respond_later` to specify ephemeral.

### New file schema

Added a new `file` schema for specifying the source of a file

* It uses some of the same fields previously found in the `attachment` schema
* The `file` field is now no longer a system file path, but a scarpet resource path, just like it is used in scarpet's `read_file`.
* `bytes` has been renamed to `string`
* `base64` was added as a way to provide data for the file
* This is now used for the following fields of schemas:
  * `thumbnail` and `image` in `embed`
  * `icon` in `embed_author` and `embed_footer`
  * `avatar` in `webhook_profile` and `webhook_profile_updater`
  * The `file`, `url`, `bytes` and `image` fields of the `attachment` field have been replaced with a single `file` field.

### New member value

Added `dc_member` value type, referencing a user in a server.
This can be used to retrieve data of a user specific to a server, like nicknames and roles.
In order to retrieve this value, the following has been added:

* Added `message~'member'`
* Added `message~'member'`
* Added `interaction~'member'`
* Added `role~'members'`
* Added `server~'members'`
* Added `channel~'position`
* Added `dc_member_from_user(user, server)` function for retrieving a member from the user and server.

### Update channel function

* Added `dc_update_channel`, replacing `dc_set_channel_topic`, allowing to update many options of channels.
* Added `channel_updater` schema.