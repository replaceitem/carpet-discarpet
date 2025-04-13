* The `__on_discord_message_edit` event now no longer provides the previous message and whether it was actually edited
* The `__on_discord_message_delete` now only gives the deleted message id and the channel object instead of the message object
* Most `~'type'` values will be different
* Removed `user~'discriminated_name`
* Added `message~'stripped_content'`
* Added `message~'stickers'`
* Added `emoji~'type'`
* Replaced with `reaction~'message'` with `reaction~'message_id'`
* Removed `slash_command_interaction_option~'options'`

* (Internal) docs for interaction values should include the base ones from InteractionValue