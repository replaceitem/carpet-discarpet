### `dc_respond_interaction(interaction,type)`
### `dc_respond_interaction(interaction,type,message)`
### `dc_respond_interaction(interaction,type,modal)`

{% include 'warning-blocking.md' %}

This function is used for responding to interactions.
The first parameter is any interaction (slash command, button, select menu) from its corresponding event.
Discord interactions expect a response within 3 seconds after executing it.
Either, that response is directly sending an answer,
or telling discord that the answer will come, which gives you a 15-minute time to send a followup response.

The `type` can be one of

* `'RESPOND_LATER'` This does not require the third `message` parameter,
  and just tells discord that the interaction was received and an answer will come.
  You will then need to send a RESPOND_FOLLOWUP response within 15 minutes.

* `'RESPOND_IMMEDIATELY'` This sends an immediate response which has to come within 3 seconds.
  The `message` needs to be specified as the third parameter for this.

* `'RESPOND_FOLLOWUP'` This is used for sending a followup response within 15 minutes after the `RESPOND_LATER` response has been sent.
  The `message` needs to be specified as the third parameter for this.

* `'RESPOND_MODAL'` Opens a modal for the user. Requires a [Modal](/parsables/components/modal.md) as the third parameter.

The `message` parameter the same as the [Message content](/parsables/message-content.md) parameter in `dc_send_message`

This function returns `null`, except if using `RESPOND_FOLLOWUP`,
a [message value](/values/message.md) with the sent message will be returned.