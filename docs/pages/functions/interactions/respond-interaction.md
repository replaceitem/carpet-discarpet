### `dc_respond_interaction(interaction, type, content?)`

{% include 'warning-blocking.md' %}

Sends a response to an interaction.

If using `RESPOND_FOLLOWUP`, returns the [message](/values/message.md) that was sent.

- `interaction` {->} Any interaction
  {:} The interaction to respond to.
- `type` {->} String
  {:} The [type](#response-types) of response to use.
- `content` {:?} {->}
  [Message content](/parsables/message-content.md),
  [Modal parsable](/parsables/components/modal.md)
  {:} The content of the response.

#### Response types

* `RESPOND_LATER` - Tells that the interaction was received, and a response will come later.
  <br>You will then need to send a `RESPOND_FOLLOWUP` response within 15 minutes.
  <br>This does not require the third `content` parameter.
* `RESPOND_IMMEDIATELY` - Sends an immediate response which has to come within 3 seconds.
  <br>The `content` parameter requires a [Message content](/parsables/message-content.md) for this.
* `RESPOND_FOLLOWUP` - Sends a followup response for `RESPOND_LATER`.
  <br>The `content` parameter requires a [Message content](/parsables/message-content.md) for this.
* `RESPOND_MODAL` - Opens a modal for the user.
  <br>The `content` parameter requires a [Modal parsable](/parsables/components/modal.md) for this.
