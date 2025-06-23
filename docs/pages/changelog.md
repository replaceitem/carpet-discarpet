---
icon: octicons/history-16
---

<!-- INSERT_HERE -->

## 1.6.1 – <small>June 23, 2025</small> { id="1.6.1" }

* Improved handling of error messages of api_exceptions based on JDA exceptions


## 1.6.0 – <small>May 25, 2025</small> { id="1.6.0" }

Due to the [sunsetting of Javacord](https://github.com/user-attachments/assets/d5e8b4fe-60d1-4dcb-95e7-cf95933d1193),
this mod migrated to JDA, which meant that introducing breaking changes was inevitable,
so I chose to use this update to add more improvements that introduce breaking changes.

The naming convention of "parsables" in the documentation and changelogs have changed.
These map values used as function parameters are now referred to as "schemas" or "objects".
A "schema" is the format of the fields a parsable has,
while an "object" will be referred to as a map value following that schema.

### Breaking changes

The following changes may break existing scripts.
Make sure to update them to work with this new discarpet version.

#### Event changes

Due to JDA having no built-in message caching, the following events were changed:

* The `__on_discord_message_edit` event now no longer provides the previous message and whether it was actually edited
* The `__on_discord_message_delete` now only gives the deleted message id and the channel object instead of the message object

#### Function changes

* The channel or message for creating a thread is no longer provided in the schema (*previously "parsable"*), but as an argument to `dc_create_thread`
* `dc_react` has been split to `dc_add_reaction(msg, emoji)` and `dc_remove_reaction(msg, emoji?, user?)`
* `dc_set_channel_topic` was removed in favor of the new `dc_update_channel`
* Removed `dc_get_display_name` in favor of `member~'effective_name'`
* Removed `dc_get_timeout` in favor of `member~'timeout_end'`
* Removed `dc_get_user_color` in favor of `member~'color'`
* Removed `dc_get_user_roles` in favor of `member~'roles'`
* Added `dc_update_presence()` to replace `dc_set_status()` and `dc_set_activity()`

#### Value changes

* The command value types are now all the same value type `dc_command` and can be distinguished with `command~'type'`
* Removed `user~'discriminated_name`, since discriminators are phased out by discord
* Replaced `reaction~'message'` with `reaction~'message_id'` since messages are not cached anymore
* `slash_command_interaction~'arguments'` and `slash_command_interaction~'arguments_by_name'`
  no longer include the subcommand and subcommand group.
  Additionally, `slash_command_interaction_option~'options'` was removed,
  since subcommand groups and subcommands are no longer expressed as nested options,
  but can be retrieved using `interaction~'sub_command'` or `~'sub_command_group'`.
* Replaced `~'is_subcommand_or_group'` in slash command interaction option values with `~'type'`

#### Schema changes

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

#### Type strings

ALL type strings are now returned in lowercase.
Additionally, the type fields of schemas are now all case-insensitive,
so you can interchangeably use upper or lower case strings everywhere.

Most values returned by the `~'type'` queries of values are now also different:

##### `~'type'` strings

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

##### Schema type strings

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

#### Other breaking changes

* Exception hierarchy and values have changed

### New features

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

#### New file schema

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

#### New member value

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

#### Update channel function

* Added `dc_update_channel`, replacing `dc_set_channel_topic`, allowing to update many options of channels.
* Added `channel_updater` schema.



## 1.5.6 – <small>March 31, 2025</small> { id="1.5.6" }

* Updated to 1.21.5
* New behaviour for errors while executing API requests.
  Previously, functions would return `true` or `false` (sometimes `null` or a value)
  to indicate success or failure. Now those functions no longer return anything
  (or only return a value) and instead throw custom discarpet exceptions that can be caught using `try()`.
  This affects the following functions and queries:
    * These functions no longer return a boolean value to indicate success
    and will instead throw an exception when an error occured:
        * `dc_set_channel_topic`
        * `dc_react`
        * `dc_set_nickname`
        * `dc_add_role`
        * `dc_remove_role`
        * `dc_set_name`
        * `dc_delete`
    * These functions will now throw an exception instead of returning null. When successful they will return a value:
        * `dc_send_message`
        * `dc_send_webhook`
        * `dc_create_webhook`
        * `dc_update_webhook`
        * `dc_create_thread`
        * `dc_create_application_command`
        * `dc_get_global_application_commands`
        * `dc_user_from_id`
        * `dc_message_from_id`
        * `dc_webhook_from_id`
        * `dc_webhook_from_url`
    * These actions now properly handle errors and throw exceptions on failure:
        * `attachment~'download'`
        * `channel~'webhooks'`
        * `server~'webhooks'`
        * `server~'slash_commands'`
        * `user~'private_channel'`
    * Special cases:
        * `dc_respond_interaction` can still return `null` when the response type doesn't create a message. In all other cases it will either throw an exception or return a `Message`
* Added events:
    * `__on_discord_message(message, old_message, is_actual_edit)`
    * `__on_discord_message_delete(message)`
    * `__on_discord_server_member_join(server, user)`
    * `__on_discord_server_member_leave(server, user)`
* Fully removed the deprecated function `dc_create_slash_command`. Use `dc_create_application_command` instead
* Improved reloading of bots
* Split `dc_timeout` into `dc_get_timeout` and `dc_set_timeout`



## 1.5.5 – <small>November 9, 2024</small> { id="1.5.5+2" }

* Fixed errors on Minecraft 1.21.3



## 1.5.5 – <small>April 23, 2024</small> { id="1.5.5+1" }

* Update to Minecraft 1.20.5



## 1.5.4 – <small>December 11, 2023</small> { id="1.5.4" }

* Updated to MC 1.20.4



## 1.5.3 – <small>November 13, 2023</small> { id="1.5.3" }

* Added new sticker value type
* Added `dc_sticker_from_id(stickerId)`
* Added new query options:
* message~'webhook_id'
    * `emoji~'id'`
    * `server~'emojis'`
    * `server~'stickers'`
    * `message~'sticker_ids'`
* Added `stickers` field to message content parsable



## 1.5.2 – <small>November 8, 2023</small> { id="1.5.2" }

* Added new properties to message values:
    * `referenced_message`
    * `type`
    * `link`
    * `flags`
    * `creation_timestamp`
    * `edit_timestamp`
    * `position`



## 1.5.1 – <small>September 7, 2023</small> { id="1.5.1" }

* Fixed discarpet loading events too early, causing log spam with modded entities ([#42](https://github.com/replaceitem/carpet-discarpet/pull/42))
* Removed `__on_command_executed` in favor of builtin `__on_player_command`
* Server voice channels can now be used for sending messages into the buildin text-in-voice channel
* Added suppress_notifications to the message content parsable



## 1.5.0 – <small>June 7, 2023</small> { id="1.5.0" }

* Updated to Minecraft 1.20
* Reloading bots will now no longer freeze the game
* Improved error handling of the /discarpet command



## 1.4.9 – <small>March 20, 2023</small> { id="1.4.9" }

* Updated to Minecraft 1.19.4



## 1.4.8 – <small>February 15, 2023</small> { id="1.4.8" }

* Updated to minecraft 1.19.3
* Added support for user, channel, mentionable and role select menus
* Added new `component_type` property to select menu interaction values for distinguishing between different select menus
* Added `nsfw` property to channel values
* Changed docs to be more consistent and correct for interaction value types



## 1.4.7 – <small>October 19, 2022</small> { id="1.4.7" }

* Fixed (#38)[https://github.com/replaceitem/carpet-discarpet/issues/38]
* Changed the maven group to `net.replaceitem`



## 1.4.6 – <small>August 12, 2022</small> { id="1.4.6" }

* Added `dc_create_thread` [function](https://replaceitem.github.io/carpet-discarpet/functions/create-thread/)
* Added `thread` [parsable](https://replaceitem.github.io/carpet-discarpet/parsables/thread/)
* Improved error message in some cases for parsables
* Using new internal enum parser for parsables



## 1.4.5 – <small>July 29, 2022</small> { id="1.4.5" }

* Updated to Minecraft 1.19.1
* Fixed webhook profile and webhook profile updater parsable throwing an error when not using an avatar
* The `text` argument `__on_system_message` is now a formatted text value, which allows for better parsing using `encode_json(text)``



## 1.4.4 – <small>July 27, 2022</small> { id="1.4.4" }

* Improved config loading by adding missing config entries on startup
* Fix errors when events trigger when the server is starting or stopping
* Bumped dependency versions (This fixes the server not fully terminating after the world saved)



## 1.4.3 – <small>July 12, 2022</small> { id="1.4.3" }

### BREAKING CHANGES:

* Intents are no longer boolean values, but a list of strings instead (See Setup)
* `dc_get_global_slash_commands` got renamed to `dc_get_global_application_commands` and now returns all types of commands
* `dc_create_slash_command()` got renamed to `dc_create_application_command()` and has an additional `type` argument, and returns the application command value instead of a boolean now
* `slash_command~'id'` now returns the interaction id, instead of the slash command id. Use `slash_command~'command_id'` instead
* The string representation of all discord values is now no longer the type name, but a custom string

### Other changes:

* Added `message_context_menu_builder` and `user_context_menu_builder` parsable
* Added `dc_message_context_menu` and `dc_user_context_menu` values
* Added `dc_message_context_menu_interaction` and `dc_user_context_menu_interaction` values
* Added `ephemeral` and `suppress_embeds` fields to `message_content` parsable
* `dc_create_application_command` now works with message context menu and user context menu commands
* Fixed modal parsable internally being called `embed`
* Internally, renamed `instant` parsable to `timestamp` to match docs

### Revamped documentation

* The docs have moved to [https://replaceitem.github.io/carpet-discarpet/](https://replaceitem.github.io/carpet-discarpet/)
* All functions, values, parsables, events and examples have their own page now
* Added internal parsable names to the docs
* Added documentation on `dc_get_global_application_commands()` which was missing
* Many many more changes and polishes to docs