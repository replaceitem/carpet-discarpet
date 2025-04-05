---
icon: octicons/history-16
---


<!--
## x.y.z – <small>January 1, 1970</small> { id="x.y.z" }
-->



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