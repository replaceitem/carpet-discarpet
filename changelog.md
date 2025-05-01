* The `__on_discord_message_edit` event now no longer provides the previous message and whether it was actually edited
* The `__on_discord_message_delete` now only gives the deleted message id and the channel object instead of the message object
* Most `~'type'` values will be different, consult the docs
* Removed `user~'discriminated_name`
* Added `message~'stripped_content'`
* Added `message~'stickers'`
* Added `emoji~'type'`
* Replaced with `reaction~'message'` with `reaction~'message_id'`
* Removed `slash_command_interaction_option~'options'`
* Button parsable style is now no longer the color, but the types (primary, secondary,...)
* Emoji fields in parsables now support many more string notations
* The channel or message for creating a thread is no longer provided in the parsable, but as an argument to `dc_create_thread`
* Remove the slow mode delay when creating a thread, since that's not supported by JDA
* Exception hierachy has changed
* The command value types are now all the same value type `dc_command` and can be distinguished with `command~'type'`
* Removed `message~'attachment'`
* `slash_command_interaction~'arguments'` and `slash_command_interaction~'arguments_by_name'` no longer include the subcommand and subcommand group
* Added `slash_command_interaction~'subcommand'` and `slash_command_interaction~'subcommand_group'`