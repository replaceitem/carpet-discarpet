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