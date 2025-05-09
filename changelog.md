* The `__on_discord_message_edit` event now no longer provides the previous message and whether it was actually edited
* The `__on_discord_message_delete` now only gives the deleted message id and the channel object instead of the message object
* Most `~'type'` values will be different, consult the docs
* Removed `user~'discriminated_name`
* Added `message~'stripped_content'`
* Added `message~'stickers'`
* Added `emoji~'type'`
* Replaced `reaction~'message'` with `reaction~'message_id'`
* Added `reaction~'channel'` and `reaction~'server'`
* Removed `slash_command_interaction_option~'options'`
* Fixed some enum-like string fields in parsables being case-sensitive. You can now use upper and lower case strings everywhere.
* All `~'type'` queries are now lower case strings
* The `component` type in a component parsable have partially different names now:
  * `BUTTON` -> `button`
  * `SELECT_MENU_STRING` -> `string_select`
  * `SELECT_MENU_USER` -> `user_select`
  * `SELECT_MENU_ROLE` -> `role_select`
  * `SELECT_MENU_MENTIONABLE` -> `mentionable_select`
  * `SELECT_MENU_CHANNEL` -> `channel_select`
  * `TEXT_INPUT` -> `text_input`
* The `style` of a button parsable now no longer the color name, but the types name:
  * `BLURPLE` -> `primary`
  * `GREY` -> `secondary`
  * `GREEN` -> `success`
  * `RED` -> `danger`
  * `URL` -> `link`
* The `type`s of a channel value have changed:
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
* Sticker type `server` changed to `guild`
* `webhook~'type'` `CHANNEL_FOLLOWER` changed to `follower`
* Emoji fields in parsables now support many more string notations
* The channel or message for creating a thread is no longer provided in the parsable, but as an argument to `dc_create_thread`
* Remove the slow mode delay when creating a thread, since that's not supported by JDA
* Exception hierachy has changed
* The command value types are now all the same value type `dc_command` and can be distinguished with `command~'type'`
* Removed `message~'attachment'`
* `slash_command_interaction~'arguments'` and `slash_command_interaction~'arguments_by_name'` no longer include the subcommand and subcommand group
* Added `slash_command_interaction~'subcommand'` and `slash_command_interaction~'subcommand_group'`
* Replaced `thread` parsable `channel_type` with `is_private`.
* Added `channel_types` to select menu parsable
* Replaced `~'is_subcommand_or_group'` in slash command interaction option values with `~'type'`
* Added `mention_channels`, `mention_emojis`, `mention_slash_commands`, `mention_here` fields to allowed mentions parsable
* Added `mention_replied_user` to allowed mentions
* `dc_react` has been split to `dc_add_reaction(msg, emoji)` and `dc_remove_reaction(msg, emoji?, user?)`
* Added `dc_member` value type, referencing a user in a server. This can be used to retrieve data of a user specific to a server, like nicknames and roles.
  * Added `message~'member'`
  * Added `interaction~'member'`
  * Added `role~'members'`
  * Added `server~'members'`
* Changes to message parsable
  * Made `content` field optional for messages without content, like forwarded messages
  * Renamed `reply_to` to `referenced_message` since it can now also used to forward messages
  * Added `message_reference_type` which can be set to `forward` to forward messages